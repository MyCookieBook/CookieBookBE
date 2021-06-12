package de.cookiebook.restservice.recipe;

import java.util.List;

import de.cookiebook.restservice.ingredients.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;

import static de.cookiebook.restservice.category.Category.*;
import static de.cookiebook.restservice.category.Subcategory.*;
import static de.cookiebook.restservice.category.Subcategory.DEFAULT;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByCategory(Category category);

    List<Recipe> findAllBySubcategory(Subcategory subcategory);

    List<Recipe> findByUserId (long userId);

    default Category getCategory(String text){
        switch (text){
            case "APPETIZER":
                return APPETIZER;
            case "MAINDISH":
                return MAINDISH;
            case "DESSERT":
                return DESSERT;
            case "OTHER":
                return OTHER;
            case "DRINKS":
                return DRINKS;
            case "BASICS":
                return BASICS;
            default:
                return DEFAULT1;
        }
    }

    default Subcategory getSubcategory(String text){
        switch (text){
            case "APP_SALAD":
                return APP_SALAD;
            case "APP_SOUP":
                return APP_SOUP;
            case "MORSEL":
                return MORSEL;
            case "MAIN_SALAD":
                return MAIN_SALAD;
            case "MAIN_SOUP":
                return MAIN_SOUP;
            case "MEAT":
                return MEAT;
            case "FISH":
                return FISH;
            case "VEGAN":
                return VEGAN;
            case "VEGETARIAN":
                return VEGETARIAN;
            case "CASSEROLE":
                return CASSEROLE;
            case "SIDEDISHES":
                return SIDEDISHES;
            case "DOUGH":
                return DOUGH;
            case "PASTERIES":
                return PASTERIES;
            case "COLDDISHES":
                return COLDDISHES;
            case "FRUITSALAD":
                return FRUITSALAD;
            default:
                return DEFAULT;
        }
    }
    List<Recipe> findAllByTitleContainingIgnoreCase(String term);

    List<Recipe> findAllByOtherContainingIgnoreCase(String term);

    List<Recipe> findAllByIngredientsIngredientNameContainingIgnoreCase(String term);

    List<Recipe> findAllByMaterialMaterialNameContainingIgnoreCase(String term);

    List<Recipe> findAllByStepsStepNameContainingIgnoreCase(String term);



}
