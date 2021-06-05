package de.cookiebook.restservice.recipe;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;
import de.cookiebook.restservice.ingredients.IngredientRepository;
import de.cookiebook.restservice.materials.MaterialRepository;
import de.cookiebook.restservice.steps.StepRepository;
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

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    StepRepository stepRepository;
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // Add recipe
    @PostMapping("/recipes/add")
    public Recipe addRecipe(@RequestBody Recipe recipe, @RequestParam("userId") long userId, HttpServletResponse response) {
        ingredientRepository.saveAll(recipe.getIngredients());
        //System.out.println(userId);
        materialRepository.saveAll(recipe.getMaterial());
        stepRepository.saveAll(recipe.getSteps());
        recipeRepository.save(recipe);
        System.out.println(recipe);
        return recipe;
    }

    // Read recipe
    @GetMapping("/recipes/read")
    public Recipe readRecipe(@RequestParam("id") Long id, @RequestParam("userId") long userId, HttpServletResponse response) {
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
    public Recipe editRecipe(@RequestBody Recipe recipe, @RequestParam("userId") long userId) {
        recipeRepository.save(recipe);
        System.out.println(recipe);
        return recipe;
    }

    // Delete recipe
    @DeleteMapping("/recipes/deleteRecipe")
    public void deleteRecipe(@RequestParam("id") long id, @RequestParam("userId") long userId, HttpServletResponse response) {
        if(!recipeRepository.existsById(id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        recipeRepository.deleteById(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // implement find
    @GetMapping("/recipeslist")
    public List<Recipe> getRecipes(@RequestParam("userId") long userId){
        return recipeRepository.findAll();
    }
    
    @GetMapping(value = "/recipeslist/byCategory/{Category}")
    public List<Recipe> getRecipesByCategory(@PathVariable("Category") Category category, @RequestParam("userId") long userId){
        return recipeRepository.findAllByCategory(category);
    }
    
    @GetMapping(value = "/recipeslist/bySubcategory/{Subcategory}")
    public List<Recipe> getRecipesByCategory(@PathVariable("Subcategory") Subcategory subcategory, @RequestParam("userId") long userId){
        return recipeRepository.findAllBySubcategory(subcategory);
    }
    
    // bookmark favourite
    @PostMapping("/recipe/bookmark")
    public void bookmarkRecipe(@RequestParam("recipeId") long recipeId, @RequestParam("userId") long userId, HttpServletResponse response) {
    	User user = userRepository.getOne(userId);
    	Recipe recipe = recipeRepository.getOne(recipeId);
        user.addRecipe(recipe);
        //recipe.addBookmark(user); wird bereits bei User gemacht
    	userRepository.save(user);
    	//recipeRepository.save(recipe);

        response.setStatus(HttpServletResponse.SC_OK);
    }
    // delete bookmark
    @DeleteMapping("/recipe/deleteBookmark")
    public void deleteBookmark(@RequestParam("recipeId") long recipeId, @RequestParam("userId") long userId, HttpServletResponse response) {
        User user = userRepository.getOne(userId);
        Recipe recipe = recipeRepository.getOne(recipeId);
        user.deleteBookmark(recipe);
    	userRepository.save(user);
    	//recipeRepository.save(recipe);
    
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
	
