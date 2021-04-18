package com.gummybearr.jai.domain.recruitment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(columnList = "hashedValue")})
public class Recruitment {

    private static final int HASHCODE_DIVIDER = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long hashedValue;
    private String company;
    private String content;
    private String deadline;
    private Long calculatedDeadline;

    public Recruitment(String company, String content, String deadline, Long calculatedDeadline) {
        this.hashedValue = hashedValue(company, content);
        this.company = company;
        this.content = content;
        this.deadline = deadline;
        this.calculatedDeadline = calculatedDeadline;
    }

    public Long hashedValue() {
        return this.hashedValue;
    }

    private Long hashedValue(String company, String content) {
        return Long.parseLong(shrinkInteger(company.hashCode()) + "" + shrinkInteger(content.hashCode()));
    }

    public String flatString(){
        return String.format("%s %s",this.company, this.content);
    }

    private int shrinkInteger(int hashCode) {
        return Math.abs(hashCode / HASHCODE_DIVIDER);
    }

    @Override
    public String toString() {
        return String.format("[%s]%n%s%n%s%n", company, content, deadline);
    }
}
