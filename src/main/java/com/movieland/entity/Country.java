package com.movieland.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_id_sequence")
    @SequenceGenerator(name = "countries_id_sequence", sequenceName = "countries_id_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String name;
}
