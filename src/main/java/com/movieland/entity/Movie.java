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
@Table(name = "movies")
public class Movie {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_id_sequence")
    @SequenceGenerator(name = "movies_id_sequence", sequenceName = "movies_id_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name_russian")
    private String nameRussian;

    @Column(name = "name_native")
    private String nameNative;

    @Column(name = "year_of_release")
    private int yearOfRelease;

    @Column(name = "genre")
    private String genre;

    @Column(name = "rating", columnDefinition = "NUMERIC(8,2)")
    private Double rating;

    @Column(name = "price", columnDefinition = "NUMERIC(8,2)")
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "poster_id", referencedColumnName = "id")
    private Poster poster;

}
