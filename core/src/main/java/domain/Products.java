package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "the field name cannot be null")
    private String name;

    @NotNull(message = "the field name cannot be null")
    private double price;

    @NotNull(message = "the field name cannot be null")
    private int score;

    @NotEmpty(message = "the field name cannot be null")
    private String image;
}
