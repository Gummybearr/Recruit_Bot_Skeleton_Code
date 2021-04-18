package com.gummybearr.jai.domain.alarm;

import javax.persistence.*;

@Entity
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long recruitmentId;

}
