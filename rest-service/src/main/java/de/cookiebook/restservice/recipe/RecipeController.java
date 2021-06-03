package de.cookiebook.restservice.recipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;
import de.cookiebook.restservice.user.User;
import de.cookiebook.restservice.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200" )
public class RecipeController {

    RecipeRepository recipeRepository;
    @Autowired
    UserRepository userRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // Add recipe
    @PostMapping("/recipes/add")
    public Recipe addRecipe(@RequestBody Recipe recipe, HttpServletResponse response) {

        recipeRepository.save(recipe);
        System.out.println(recipe);
        return recipe;
    }

    // Read recipe
    @GetMapping("/recipes/read")
    public Recipe readRecipe(@RequestParam("id") Long id, HttpServletResponse response) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(!recipe.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } else {
            return recipe.get();
        }
    }

    // Edit recipe
    @PostMapping("/recipes/edit")
    public Recipe editRecipe(@RequestBody Recipe recipe) {
        recipeRepository.save(recipe);
        System.out.println(recipe);
        return recipe;
    }

    // Delete recipe
    @PostMapping("/recipes/delete")
    public void deleteRecipe(@RequestBody Long id, HttpServletResponse response) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(!recipe.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        System.out.println(recipe + "deleted");
        recipeRepository.deleteById(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }


    // implement find
    @GetMapping("/recipeslist")
    public List<Recipe> getRecipes(){
        return recipeRepository.findAll();
    }
    
    @GetMapping(value = "/recipeslist/byCategory/{Category}")
    public List<Recipe> getRecipesByCategory(@PathVariable Category category){
        return recipeRepository.findAllByCategory(category);
    }
    
    @GetMapping(value = "/recipeslist/bySubcategory/{Subcategory}")
    public List<Recipe> getRecipesByCategory(@PathVariable Subcategory subcategory){
        return recipeRepository.findAllBySubcategory(subcategory);
    }
    
    // bookmark favourite
    @PostMapping("/recipe/bookmark")
    public void bookmarkRecipe(@RequestBody Recipe recipe, @RequestBody User user, HttpServletResponse response) {
    	user.addRecipe(recipe);
    	userRepository.save(user);
    	recipeRepository.save(recipe);
    	
        response.setStatus(HttpServletResponse.SC_OK);
    }
    // delete bookmark
    @DeleteMapping("/recipe/deleteBookmark")
    public void deleteBookmark(@RequestBody Recipe recipe, @RequestBody User user, HttpServletResponse response) {
    	user.deleteBookmark(recipe);
    	userRepository.save(user);
    	recipeRepository.save(recipe); 
    
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
	
