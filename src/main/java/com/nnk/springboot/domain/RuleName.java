package com.nnk.springboot.domain;

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
@Table(name = "rulename")
@Builder
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "name", length = 125)
    private String name;

    @Column(name = "description", length = 125)
    private String description;

    @Column(name = "json", length = 125)
    private String json;

    @Column(name = "template", length = 512)
    private String template;

    @Column(name = "sqlStr", length = 125)
    private String sqlStr;

    @Column(name = "sqlPart", length = 125)
    private String sqlPart;

}
