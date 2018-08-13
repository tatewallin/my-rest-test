package com.mutualofomaha.gld.myresttest.dao.repo;

import com.mutualofomaha.gld.myresttest.dao.entity.OvpBenCaseTotals;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OvpBenCaseTotalsRepository extends CrudRepository<OvpBenCaseTotals, String>{

    List<OvpBenCaseTotals> findByI(Long i);
}
