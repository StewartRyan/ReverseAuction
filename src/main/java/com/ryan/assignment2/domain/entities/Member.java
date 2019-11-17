package com.ryan.assignment2.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String passHash;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String phoneNumber;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;

    @OneToMany(mappedBy = "member")
    @EqualsAndHashCode.Exclude
    private Set<Job> jobs;
}
