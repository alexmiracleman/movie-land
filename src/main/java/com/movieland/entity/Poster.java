package com.movieland.entity;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "posters")
public class Poster {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posters_id_sequence")
    @SequenceGenerator(name = "posters_id_sequence", sequenceName = "posters_id_sequence", allocationSize = 1)
    private int id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "picture_path")
    private String picturePath;

}
