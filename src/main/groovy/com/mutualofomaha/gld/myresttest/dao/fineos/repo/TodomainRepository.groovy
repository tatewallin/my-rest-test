package com.mutualofomaha.gld.myresttest.dao.fineos.repo

import com.mutualofomaha.gld.myresttest.dao.fineos.entity.BusinessEntityKey
import com.mutualofomaha.gld.myresttest.dao.fineos.entity.Todomain
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional("fineosTransactionManager")
interface TodomainRepository  extends JpaRepository<Todomain, String> {

    List<Todomain> findById(BusinessEntityKey id)
}