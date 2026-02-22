package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "curvepoint")
@Builder
@NoArgsConstructor(force = true, access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    // Modification script SQL pour integer Vs. tinyint (seuleument 127 entrées
    // possible)
    private final Integer id;

    @Column(name = "CurveId")
    private final Integer curveId;

    @Column(name = "asOfDate")
    private final Timestamp asOfDate;

    @Column(name = "term")
    private final Double term;

    @Column(name = "value")
    private final Double value;

    @Column(name = "creationDate")
    private final Timestamp creationDate;

}
