package de.cookiebook.restservice.recipe;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200" )
public class RecipeController {

	RecipeRepository recipeRepository;
	
	public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
	}
	
    @RequestMapping(value = "/addrecipe", method = RequestMethod.POST)
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        if(recipe.getTitle() == null ||
                recipe.getIngredients() == null || recipe.getSteps() == null) { // was waren nochmal die Pflichtfelder??
            return null;
        }
        recipeRepository.save(recipe);
        System.out.println(recipe);
        return recipe;
    }
    
    @RequestMapping(value = "/edit-recipe", method = RequestMethod.POST)
    public Recipe editRecipe(@RequestBody Recipe recipe) {
        //check if there's no valid id, recipe name, ingredients or steps, then can't update
        if(recipeRepository.findById(recipe.getId()) == null ||recipe.getTitle() == null ||
                recipe.getIngredients() == null || recipe.getSteps() == null) {
            System.out.println("Cannot find recipe.");
            return null;
        }
        recipeRepository.save(recipe);
        System.out.println(recipe);
        return recipe;
    }
}
	
