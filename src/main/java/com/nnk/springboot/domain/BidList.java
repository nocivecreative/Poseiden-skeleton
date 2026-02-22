package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bidlist")
@Builder
@NoArgsConstructor(force = true, access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    // Modification script SQL pour integer Vs. tinyint (seuleument 127 entrées
    // possible)
    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    @Column(name = "account", length = 30, nullable = false)
    private final String account;

    @NotBlank(message = "Type is mandatory")
    @Column(name = "type", length = 30, nullable = false)
    private final String type;

    @Column(name = "bidQuantity")
    private final Double bidQuantity;

    @Column(name = "askQuantity")
    private final Double askQuantity;

    @Column(name = "bid")
    private final Double bid;

    @Column(name = "ask")
    private final Double ask;

    @Column(name = "benchmark", length = 125)
    private final String benchmark;

    @Column(name = "bidListDate")
    private final Timestamp bidListDate;

    @Column(name = "commentary", length = 125)
    private final String commentary;

    @Column(name = "security", length = 125)
    private final String security;

    @Column(name = "status", length = 10)
    private final String status;

    @Column(name = "trader", length = 125)
    private final String trader;

    @Column(name = "book", length = 125)
    private final String book;

    @Column(name = "creationName", length = 125)
    private final String creationName;

    @Column(name = "creationDate")
    // Modification script SQL DEFAULT : NULL ('0000-00-00 00:00:00' pas accepté en
    // mysql 8+)
    private final Timestamp creationDate;

    @Column(name = "revisionName", length = 125)
    private final String revisionName;

    @Column(name = "revisionDate")
    // Modification script SQL : DEFAULT NULL ('0000-00-00 00:00:00' pas accepté en
    // mysql 8+)
    private final Timestamp revisionDate;

    @Column(name = "dealName", length = 125)
    private final String dealName;

    @Column(name = "dealType", length = 125)
    private final String dealType;

    @Column(name = "sourceListId", length = 125)
    private final String sourceListId;

    @Column(name = "side", length = 125)
    private final String side;

}
