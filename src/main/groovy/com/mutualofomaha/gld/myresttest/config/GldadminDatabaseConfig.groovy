package com.mutualofomaha.gld.myresttest.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

//https://www.baeldung.com/spring-data-jpa-multiple-databases

@Configuration
@EnableJpaRepositories(
        basePackages = "com.mutualofomaha.gld.myresttest.dao.gldadmin",
        entityManagerFactoryRef = "gldadminEntityManager",
        transactionManagerRef = "gldadminTransactionManager"
)
class GldadminDatabaseConfig  {

    @Primary
    @Bean(name = "gldadminDataSource")
    @ConfigurationProperties(prefix = "gldadmin.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build()
    }

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean gldadminEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean()
        em.setDataSource(dataSource())
        em.setPackagesToScan("com.mutualofomaha.gld.myresttest.dao.gldadmin")

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter()
        em.setJpaVendorAdapter(vendorAdapter)
        HashMap<String, Object> properties = new HashMap<>()
        properties.put("hibernate.hbm2ddl.auto","none")
        properties.put("hibernate.dialect","org.hibernate.dialect.OracleDialect")
        em.setJpaPropertyMap(properties)

        return em
    }

    @Primary
    @Bean
    PlatformTransactionManager gldadminTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager()
        transactionManager.setEntityManagerFactory(gldadminEntityManager().getObject())
        return transactionManager
    }
}