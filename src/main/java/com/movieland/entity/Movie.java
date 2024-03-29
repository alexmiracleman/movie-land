package com.movieland.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private int id;

    @Column(name = "name_russian")
    private String nameRussian;

    @Column(name = "name_native")
    private String nameNative;

    @Column(name = "year_of_release")
    private int yearOfRelease;

    @Column(name = "rating", columnDefinition = "NUMERIC(8,2)")
    private Double rating;

    @Column(name = "price", columnDefinition = "NUMERIC(8,2)")
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "poster_id", referencedColumnName = "id")
    private Poster poster;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
            name = "movies_genre_map",
            joinColumns = @JoinColumn(
                    name = "movie_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "genre_id",
                    referencedColumnName = "id"
            )
    )
    private List<Genre> genres;
}
