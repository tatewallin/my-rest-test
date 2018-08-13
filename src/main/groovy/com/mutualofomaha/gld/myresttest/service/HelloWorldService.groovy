package com.mutualofomaha.gld.myresttest

import com.mutualofomaha.gld.myresttest.dao.entity.OvpBenCaseTotals
import com.mutualofomaha.gld.myresttest.dao.repo.OvpBenCaseTotalsRepository
import groovy.util.logging.Slf4j
import net.bull.javamelody.MonitoredWithSpring
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
// This annotation seems to be causing spring start up issues
//@MonitoredWithSpring
@Service
class HelloWorldService {

	private OvpBenCaseTotalsRepository ovpBenCaseTotalsRepository

	// this is constructor based DI.  No need to annotate with autowire.  makes it more clear if you do tho
	@Autowired
	HelloWorldService(OvpBenCaseTotalsRepository ovpBenCaseTotalsRepository){
		this.ovpBenCaseTotalsRepository = ovpBenCaseTotalsRepository
	}

	String doSomeCoolBusinessShit() {
		log.debug '>>>doSomeCoolBusinessShit'
		List<OvpBenCaseTotals> list = ovpBenCaseTotalsRepository.findByI(101)
		OvpBenCaseTotals x = list.get(0)
		x.overpaymentAmount
		return x.overpaymentAmount.toString()
	}
}