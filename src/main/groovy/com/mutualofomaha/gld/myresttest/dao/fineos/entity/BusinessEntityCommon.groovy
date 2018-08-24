package com.mutualofomaha.gld.myresttest.dao.fineos.entity

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class BusinessEntityCommon {
    @Column
    String flags
    @Column
    Integer boeversion
    @Column
    Long c_Osuser_Updatedby
    @Column
    Long i_Osuser_Updatedby
}
