package com.ryan.assignment2.domain.entities;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue
    private int jobId;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private LocalDate date;

    @ManyToOne()
    @EqualsAndHashCode.Exclude
    private Member member;
}
