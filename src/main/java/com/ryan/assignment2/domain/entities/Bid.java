package com.ryan.assignment2.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bid
{
    @Id
    @GeneratedValue
    private int bidId;

    @Column(nullable=false)
    private float amount;

    @Column(name="timestamp",
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            updatable=false)
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @ManyToOne()
    @EqualsAndHashCode.Exclude
    private Member member;

    @ManyToOne()
    @EqualsAndHashCode.Exclude
    private Job job;
}
