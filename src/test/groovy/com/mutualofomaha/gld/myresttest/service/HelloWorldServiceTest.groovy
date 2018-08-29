package com.mutualofomaha.gld.myresttest.service

import com.mutualofomaha.gld.myresttest.HelloWorldService
import com.mutualofomaha.gld.myresttest.dao.fineos.entity.BusinessEntityKey
import com.mutualofomaha.gld.myresttest.dao.fineos.entity.Todomain
import com.mutualofomaha.gld.myresttest.dao.fineos.repo.TodomainRepository
import com.mutualofomaha.gld.myresttest.dao.gldadmin.entity.OvpBenCaseTotals
import com.mutualofomaha.gld.myresttest.dao.gldadmin.repo.OvpBenCaseTotalsRepository
import spock.lang.Specification

class HelloWorldServiceTest extends Specification{

    def "test doSomeCoolBusinessShit"(){
        given:
            OvpBenCaseTotalsRepository ovpBenCaseTotalsRepository = Mock(OvpBenCaseTotalsRepository)
            TodomainRepository todomainRepository = Mock(TodomainRepository)
            HelloWorldService service = new HelloWorldService(ovpBenCaseTotalsRepository, todomainRepository)
            List<OvpBenCaseTotals> list = new ArrayList<>()
            OvpBenCaseTotals bct = new OvpBenCaseTotals(i:9999, c: 100, overpaymentAmount: 123.45)
            list.add(bct)
        when:
            ovpBenCaseTotalsRepository.findByI(_) >> list
            String response = service.doSomeCoolBusinessShit()
        then:
            assert response == "123.45"
    }

    def "test doSomeCoolBusinessShit2"(){
        given:
            OvpBenCaseTotalsRepository ovpBenCaseTotalsRepository = Mock(OvpBenCaseTotalsRepository)
            TodomainRepository todomainRepository = Mock(TodomainRepository)
            HelloWorldService service = new HelloWorldService(ovpBenCaseTotalsRepository, todomainRepository)
            List<Todomain> list = new ArrayList<>()
            list.add(new Todomain(id: new BusinessEntityKey(100, 9999), name: "Enum domain name"))
        when:
            todomainRepository.findById(_) >> list
            String response = service.doSomeMoreCoolBusinessShit()
        then:
            assert response == "Enum domain name"
    }

}
