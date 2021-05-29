package de.cookiebook.restservice.category;

public enum Category {
    VORSPEISE, HAUPTGERICHTE, DESSERT, SONSTIGES, GETRÄNKE, GRUNDREZEPTE;
}

/*
@Data
//overrides to avoid loop and exception java.lang.StackOverflowError: null
@EqualsAndHashCode(exclude = "recipes")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
 */
