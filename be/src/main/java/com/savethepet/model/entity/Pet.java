package com.savethepet.model.entity;

import com.sun.istack.Nullable;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Nullable
    private String breed;

    @Nullable
    private Gender gender;

    private String img;

    private Position position;

    private Status status;

}
