package com.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
}
