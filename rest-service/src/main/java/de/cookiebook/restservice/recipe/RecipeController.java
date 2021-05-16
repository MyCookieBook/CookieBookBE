package de.cookiebook.restservice.recipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * Endpoints einbauen siehe google drive tab (grün markiert)
 * Edit recipe
 * unterkategorien einbinden
 * Search recipe implementieren nach kategorie und unterkategorie suchen
 *
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200" )
public class RecipeController {

    RecipeRepository recipeRepository;
    @Autowired
    TagRepository tagRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // Add recipe
    @PostMapping("/recipes/add")
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        if(recipe.getTags() != null) {
            List<Tag> tags = recipe.getTags();
            recipe.getTags().clear();
            for(int i = 0; i < tags.size(); i++) {
                tags.get(i).addRecipe(recipe);
                tagRepository.save(tags.get(i));
            }
        }

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
        /*if(recipeRepository.findById(recipe.getId()) == null ||recipe.getTitle() == null ||
                recipe.getIngredients() == null || recipe.getSteps() == null) {
            System.out.println("Cannot find recipe.");
            return null;
        }*/
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

}
	
