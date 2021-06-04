package de.cookiebook.restservice.ingredients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ingredients1")
public class Ingredient {
    @Id
    @GeneratedValue
    private Long id;
    private String ingredientName;

    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
