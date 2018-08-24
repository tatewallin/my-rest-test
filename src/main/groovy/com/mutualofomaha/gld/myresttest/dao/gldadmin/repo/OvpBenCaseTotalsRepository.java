package com.mutualofomaha.gld.myresttest.dao.gldadmin.repo;

import com.mutualofomaha.gld.myresttest.dao.gldadmin.entity.OvpBenCaseTotals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional("gldadminTransactionManager")
public interface OvpBenCaseTotalsRepository extends JpaRepository<OvpBenCaseTotals, String> {

    List<OvpBenCaseTotals> findByI(Long i);
}
