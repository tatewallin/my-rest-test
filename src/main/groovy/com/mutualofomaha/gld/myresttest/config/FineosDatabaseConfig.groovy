package com.mutualofomaha.gld.myresttest.config

import com.mutualofomaha.gld.myresttest.util.EncryptionDecryptionUtil
import com.mutualofomaha.gld.myresttest.util.R
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jndi.JndiTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

//https://www.baeldung.com/spring-data-jpa-multiple-databases

@Configuration
@EnableJpaRepositories(
        basePackages = "com.mutualofomaha.gld.myresttest.dao.fineos",
        entityManagerFactoryRef = "fineosEntityManager",
        transactionManagerRef = "fineosTransactionManager"
)
class FineosDatabaseConfig  {

    @Value('${spring.profiles.active}')
    private final String environment

    private EncryptionDecryptionUtil encryptionDecryptionUtil

    @Autowired
    FineosDatabaseConfig(EncryptionDecryptionUtil encryptionDecryptionUtil){
        this.encryptionDecryptionUtil = encryptionDecryptionUtil
    }

    @Bean(name = "fineosDatasourceProperties")
    @ConfigurationProperties(prefix = "fineos.datasource")
    DatasourceProperties dataSourceProperties(){
        return new DatasourceProperties()
    }

    @Bean(name = "fineosDataSource")
    DataSource dataSource() {
        DatasourceProperties props = dataSourceProperties()
        DataSource ds
        if(environment == "Unit"){
            println(environment)
            println(props.url)
            println(props.driverClassName)
            println(props.username)
            println(props.jndiName)
            println(props.password)

            ds = DataSourceBuilder.create()
            .username(props.username)
            .password(encryptionDecryptionUtil.decrypt(props.password, R.Security.CRYPT_KEY))
            .driverClassName(props.driverClassName)
            .url(props.url)
            .build()

        }else{
            ds =  (DataSource) new JndiTemplate().lookup(props.jndiName)
        }
        return ds
    }

    @Bean
    LocalContainerEntityManagerFactoryBean fineosEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean()
        em.setDataSource(dataSource())
        em.setPackagesToScan("com.mutualofomaha.gld.myresttest.dao.fineos")

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter()
        em.setJpaVendorAdapter(vendorAdapter)
        HashMap<String, Object> properties = new HashMap<>()
        properties.put("hibernate.hbm2ddl.auto","none")
        properties.put("hibernate.dialect","org.hibernate.dialect.OracleDialect")
        em.setJpaPropertyMap(properties)

        return em
    }

    @Bean
    PlatformTransactionManager fineosTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager()
        transactionManager.setEntityManagerFactory(fineosEntityManager().getObject())
        return transactionManager
    }
}