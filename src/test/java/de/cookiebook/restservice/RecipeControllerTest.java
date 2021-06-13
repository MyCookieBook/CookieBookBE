package de.cookiebook.restservice;

import de.cookiebook.restservice.recipe.Recipe;
import de.cookiebook.restservice.recipe.RecipeController;
import de.cookiebook.restservice.recipe.RecipeRepository;
import de.cookiebook.restservice.user.User;
import de.cookiebook.restservice.user.UserRepository;
import de.cookiebook.restservice.user.UserController;
import de.cookiebook.restservice.ingredients.Ingredient;
import de.cookiebook.restservice.ingredients.IngredientRepository;
import de.cookiebook.restservice.category.*;
import de.cookiebook.restservice.config.*;
import de.cookiebook.restservice.materials.*;
import de.cookiebook.restservice.steps.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeControllerTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeController recipeController;

    @Spy
    List<Recipe> recipes = new ArrayList<>();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        recipes = recipeController.getRecipes(68);
    }

    @Test
    public void getAllTest() {
        when(recipeRepository.findAll()).thenReturn(recipes);
        recipeController.getRecipes(68);

        verify(recipeRepository, atLeastOnce()).findAll();
    }

    @Test
    public void getAllByCategoryTest() {
        Recipe recipe = recipes.get(0);

        when(recipeController.getRecipesByCategory("MAINDISH", 68)).thenReturn(recipeRepository.getCategory());
        when(recipeRepository.findAllByCategory(Category.MAINDISH).thenReturn();

        recipeController.getRecipesByCategory();

        verify(recipeRepository, atLeastOnce()).findAllByCategory(any());
        verify(recipeController, atLeastOnce()).getRecipesByCategory();
    }

    @Test
    public void deleteRecipeWhenPresentTest() {
        Recipe recipe = recipes.get(0);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(recipe));
        doNothing().when(recipeRepository).deleteById(anyLong());

        assertEquals(recipeController.deleteRecipe(), new ResponseEntity(HttpStatus.OK));

        verify(recipeRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    public void deleteRecipeWhenNotPresentTest() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(recipeController.deleteRecipe(), new ResponseEntity(HttpStatus.NOT_FOUND));

        verify(recipeRepository, never()).deleteById(anyLong());
    }
}