package com.mutualofomaha.gld.myresttest.dao.fineos.entity

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class BusinessEntityKey implements Serializable{

    @Column
    Long c

    @Column
    Long i

    BusinessEntityKey(){}
    BusinessEntityKey(Long c, Long i){
        this.c = c
        this.i = i
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (!(o instanceof BusinessEntityKey)) return false
        BusinessEntityKey that = (BusinessEntityKey) o
        return Objects.equals(c, that.c) &&
                Objects.equals(i, that.i)
    }

    @Override
    int hashCode() {
        return Objects.hash(c, i)
    }
}
