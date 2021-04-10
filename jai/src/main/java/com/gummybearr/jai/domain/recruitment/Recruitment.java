package com.gummybearr.jai.domain.recruitment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(columnList = "hashedValue")})
public class Recruitment {
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

    public Long hashedValue(){
        return this.hashedValue;
    }

    private Long hashedValue(String company, String content) {
        return Long.parseLong( shrinkInteger(company.hashCode()) + "" + shrinkInteger(content.hashCode()));
    }

    private int shrinkInteger(int hashCode){
        return Math.abs(hashCode/10);
    }

    @Override
    public String toString() {
        return "[" + company + "]\n" +
                content + "\n" +
                deadline + "\n\n";
    }

}
