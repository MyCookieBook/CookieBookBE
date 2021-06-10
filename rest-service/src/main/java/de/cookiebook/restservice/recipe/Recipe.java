package de.cookiebook.restservice.recipe;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;
import de.cookiebook.restservice.ingredients.Ingredient;
import de.cookiebook.restservice.materials.Material;
import de.cookiebook.restservice.steps.Step;
import de.cookiebook.restservice.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "recipes3")
public class Recipe {
    @Id
    @GeneratedValue
    private Long id;
    private String author;
    private String title;
    @Column(nullable = true)
    private Integer duration;
    @Column(nullable = true)
    private Integer difficultyLevel;
    private Category category;
    private Subcategory subcategory;
    private String link;
    private String calory;
    private boolean bookmark;
    private String other;
    private long userId;

    /*@ManyToMany(cascade = {CascadeType.MERGE })
    @JoinTable(
    		  name = "recipe_user", 
    		  joinColumns = @JoinColumn(name = "idRecipe", referencedColumnName = "id"), 
    		  inverseJoinColumns = @JoinColumn(name = "idUser", referencedColumnName = "id"))*/

    @OneToMany(cascade = {CascadeType.MERGE})
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    @OneToMany(cascade = {CascadeType.MERGE})
    private List<Material> material = new ArrayList<Material>();
    @OneToMany(cascade = {CascadeType.MERGE})
    private List<Step> steps = new ArrayList<Step>();

    public Recipe(String author,
                  String title,
                  Integer duration,
                  Integer difficultyLevel,
                  List<Material> material,
                  List<Step> steps,
                  String link,
                  String calory,
                  List<Ingredient> ingredients,
//    				List<User> bookmarks,
                  Category category,
                  Subcategory subcategory,
                  String other) {

        this.author = author;
        this.title = title;
        this.duration = duration;
        this.difficultyLevel = difficultyLevel;
        this.material = material;
        this.steps = steps;
        this.link = link;
        this.calory = calory;
        this.ingredients = ingredients;
//        this.bookmarks = bookmarks;
        this.category = category;
        this.subcategory = subcategory;
        this.other = other;
    }

    @Override
    public String toString() {

        return "Recipe{" +
                "id=" + this.id +
                ", author='" + this.author + '\'' +
                ", title='" + this.title + '\'' +
                ", duration='" + this.duration + '\'' +
                ", difficultyLevel=" + this.difficultyLevel +
                ", steps='" + this.steps + '\'' +
                ", link='" + this.link + '\'' +
                ", ingredients='" + this.ingredients + '\'' +
                ", material='" + this.material + '\'' +
                ", calory='" + this.calory + '\'' +
                ", bookmarked='" + this.bookmark + '\'' +
                '}';
    }
}

