package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trade")
@Getter
@Setter
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Column(name = "account", length = 30, nullable = false)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Column(name = "type", length = 30, nullable = false)
    private String type;

    @Column(name = "buyQuantity")
    private Double buyQuantity;

    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @Column(name = "buyPrice")
    private Double buyPrice;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "tradeDate", insertable = false, updatable = false)
    private Timestamp tradeDate;

    @Column(name = "security", length = 125)
    private String security;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "trader", length = 125)
    private String trader;

    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Column(name = "book", length = 125)
    private String book;

    @Column(name = "creationName", length = 125)
    private String creationName;

    @Column(name = "creationDate", insertable = false, updatable = false)
    private Timestamp creationDate;

    @Column(name = "revisionName", length = 125)
    private String revisionName;

    @Column(name = "revisionDate", insertable = false, updatable = false)
    private Timestamp revisionDate;

    @Column(name = "dealName", length = 125)
    private String dealName;

    @Column(name = "dealType", length = 125)
    private String dealType;

    @Column(name = "sourceListId", length = 125)
    private String sourceListId;

    @Column(name = "side", length = 125)
    private String side;

}
