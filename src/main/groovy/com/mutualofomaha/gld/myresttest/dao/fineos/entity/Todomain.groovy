package com.mutualofomaha.gld.myresttest.dao.fineos.entity

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name="TODOMAIN", schema="fineos")
class Todomain {
    @EmbeddedId
    BusinessEntityKey id

    @Embedded
    BusinessEntityCommon common
    @Column
    String name
    @Column
    Boolean iseditable
    @Column
    String description
    @Column
    Boolean alphasort
    @Column
    Boolean defaultfirst
    @Column
    Long partitionid
}
