package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@NoArgsConstructor
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    // TODO : prze : ALTER TABLE pour integer Vs. tinyint (seuleument 127 entrées
    // possible)
    private Integer id;

    @Column(name = "CurveId")
    private Integer curveId;

    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    @Column(name = "term")
    private Double term;

    @Column(name = "value")
    private Double value;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public CurvePoint(Integer id, Integer curveId, Double term, Double value) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

}
