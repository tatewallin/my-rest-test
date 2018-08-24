package com.mutualofomaha.gld.myresttest

import com.mutualofomaha.gld.myresttest.dao.fineos.entity.BusinessEntityKey
import com.mutualofomaha.gld.myresttest.dao.fineos.entity.Todomain
import com.mutualofomaha.gld.myresttest.dao.fineos.repo.TodomainRepository
import com.mutualofomaha.gld.myresttest.dao.gldadmin.entity.OvpBenCaseTotals
import com.mutualofomaha.gld.myresttest.dao.gldadmin.repo.OvpBenCaseTotalsRepository
import groovy.util.logging.Slf4j
import net.bull.javamelody.MonitoredWithSpring
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
// This annotation seems to be causing spring start up issues
//@MonitoredWithSpring
@Service
class HelloWorldService {

	private OvpBenCaseTotalsRepository ovpBenCaseTotalsRepository
	private TodomainRepository todomainRepository

	// this is constructor based DI.  No need to annotate with autowire.  makes it more clear if you do tho
	@Autowired
	HelloWorldService(OvpBenCaseTotalsRepository ovpBenCaseTotalsRepository, TodomainRepository todomainRepository){
		this.ovpBenCaseTotalsRepository = ovpBenCaseTotalsRepository
		this.todomainRepository = todomainRepository
	}

	String doSomeCoolBusinessShit() {
		log.debug '>>>doSomeCoolBusinessShit'
		List<OvpBenCaseTotals> list = ovpBenCaseTotalsRepository.findByI(101)
		OvpBenCaseTotals x = list.get(0)
		return x.overpaymentAmount.toString()
	}
	String doSomeMoreCoolBusinessShit() {
		log.debug '>>>doSomeMoreCoolBusinessShit'
		List<Todomain> list = todomainRepository.findById(new BusinessEntityKey(100, 2201))
		Todomain x = list.get(0)
		return x.name
	}
}