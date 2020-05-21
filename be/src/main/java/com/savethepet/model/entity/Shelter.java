package com.savethepet.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Class that represents shelter
 *
 * @author Pavel Yudin
 */
@Entity
@Data
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

/*    @OneToMany
    private Set<Pet> pets;*/

    private transient Location location;

    private String timeOfWork;
}
