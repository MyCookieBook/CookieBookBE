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

        String splitter = "&&&";
        String attribute = "%%%";
        String empty = "§§§";

        String recipe = "";
        if (this.id == null) {
            recipe += empty;
        } else {
            recipe += this.id.toString();
        }
        recipe += splitter;
        if (this.category == null || this.category.equals(Category.DEFAULT1)) {
            recipe += empty;
        } else {
            recipe += this.category;
        }
        recipe += splitter;
        if (this.subcategory == null) {
            recipe += empty;
        } else {
            recipe += this.subcategory;
        }
        recipe += splitter;
        if (this.title == null || this.title.equals("")) {
            recipe += empty;
        } else {
            recipe += this.title;
        }
        recipe += splitter;
        if (this.author == null || this.author.equals("")) {
            recipe += empty;
        } else {
            recipe += this.author;
        }
        recipe += splitter;
        if (this.bookmark) {
            recipe += "true";
        } else {
            recipe += "false";
        }
        recipe += splitter;
        if (this.duration == null) {
            recipe += empty;
        } else {
            recipe += this.duration.toString();
        }
        recipe += splitter;
        if (this.calory.equals("")) {
            recipe += empty;
        } else {
            recipe += this.calory;
        }
        recipe += splitter;
        if (this.difficultyLevel == 0) {
            recipe += empty;
        } else {
            recipe += this.difficultyLevel.toString();
        }
        recipe += splitter;
        if (this.ingredients == null || this.ingredients.size() == 0) {
            recipe += empty;
        } else {
            for (int i = 0; i < this.ingredients.size(); i++) {
                if (!this.ingredients.get(i).getIngredientName().equals("")) {
                    recipe += this.ingredients.get(i).getIngredientName();
                    if (i != this.ingredients.size() - 1) {
                        recipe += attribute;
                    }
                }else if(this.ingredients.get(i).getIngredientName() == null){
                    recipe += empty;
                    break;
                }
            }
        }
        recipe += splitter;
        if (this.material == null || this.material.size() == 0) {
            recipe += empty;
        } else {
            for (int i = 0; i < this.material.size(); i++) {
                recipe += this.material.get(i).getMaterialName();
                if (i != this.material.size() - 1) {
                    recipe += attribute;
                }
            }
        }
        recipe += splitter;
        if (this.steps == null || this.steps.size() == 0) {
            recipe += empty;
        } else {
            for (int i = 0; i < this.steps.size(); i++) {
                recipe += this.steps.get(i).getStepName();
                if (i != this.steps.size() - 1) {
                    recipe += attribute;
                }
            }
        }
        recipe += splitter;
        if (this.link == null || this.link.equals("")) {
            recipe += empty;
        } else {
            recipe += this.link;
        }
        recipe += splitter;
        if (this.other == null || this.other.equals("")) {
            recipe += empty;
        } else {
            recipe += this.other;
        }

        return recipe;
    }
}

