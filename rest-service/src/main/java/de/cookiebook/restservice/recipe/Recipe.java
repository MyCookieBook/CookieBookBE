package de.cookiebook.restservice.recipe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private Long id;
    // User implementieren
    private String title; // Titel als varchar?
    private int duration;
    private int difficultyLevel;
    private String ingredients;
    private String steps;
    // Was ist mit Waiting time gemeint?
    private String link;
    private String nutritionalValues;
    private String otherInformation;

    public Recipe() {
    }

    ;

    public Recipe(String title) {
        this.title = title;
    }

    public Recipe(String title, Integer duration, Integer difficultyLevel, String ingredients, String steps, String link, String nutritionalValues, String otherInformation) {
        this.title = title;
        this.duration = duration;
        this.difficultyLevel = difficultyLevel;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }


    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNutritionalValues() {
        return nutritionalValues;
    }

    public void setNutritionalValues(String nutritionalValues) {
        this.nutritionalValues = nutritionalValues;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", ingredients='" + ingredients + '\'' +
                ", steps='" + steps + '\'' +
                ", link='" + link + '\'' +
                ", nutritionalValues='" + nutritionalValues + '\'' +
                ", otherInformation='" + otherInformation + '\'' +
                '}';
    }

}

