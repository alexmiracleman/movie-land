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
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id",
    referencedColumnName = "id")
    private User user;

    @Column(name = "text")
    private String text;

}
