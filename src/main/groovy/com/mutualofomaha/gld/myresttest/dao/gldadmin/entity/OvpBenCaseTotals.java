package com.mutualofomaha.gld.myresttest.dao.gldadmin.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

//https://vladmihalcea.com/the-best-way-to-map-a-composite-primary-key-with-jpa-and-hibernate/

@Entity
@Table(name="GLDT_OVP_BEN_CASE_TOTALS", schema="gldadmin")
public class OvpBenCaseTotals {
    @Id
    @Column(name="ROWID")
    String rowid;

    @Column(name="RQST_PYMT_EVT_C")
    Long c;

    @Column(name="RQST_PYMT_EVT_I")
    Long i;

    @Column(name="BENEFIT_CASE_NUM")
    String benefitCaseNumber;

    @Column(name="OVERPAYMENT_AMOUNT")
    BigDecimal overpaymentAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="INSERT_TSP")
    Date insertTsp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="BATCH_END_DATE")
    Date batchEndDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="OVP_SETUP_DTE")
    Date ovpSetupDate;
}
