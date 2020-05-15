package com.savethepet.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Class that represents pet
 *
 * @author Alexey Klimov
 */
@Entity
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long shelterId;

    private String breed;

    private Gender gender;

    private String img = "default img";

    private transient Location location;

    private Status status;

}
