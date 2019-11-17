package com.ryan.assignment2.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job
{
    @Id
    @GeneratedValue
    private int jobId;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private LocalDate date;

    @Column(nullable=false)
    private String state = "active";

    @Column(name="timestamp",
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            updatable=false)
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @ManyToOne()
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Member member;

    @OneToMany(mappedBy = "job")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Bid> bids = new HashSet<>();
}
