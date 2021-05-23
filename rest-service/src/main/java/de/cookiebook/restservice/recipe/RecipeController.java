package de.cookiebook.restservice.recipe;

import de.cookiebook.restservice.user.User;
import de.cookiebook.restservice.user.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    RecipeRepository recipeRepository;
    UserController userController;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /*
        Für Sarah: wenn null ans Frontend zurück kommt, stimmt etwas im backend nicht --> error nachricht ausgeben
     */

    @RequestMapping(value = "/addrecipe", method = RequestMethod.POST)
    public Recipe addRecipe(@RequestBody Recipe recipe, @RequestBody User user) {
        try {
            if ( user.isLoggedIn() && userController.validateDurration(user)) {
                if (recipe.getTitle() == null ||
                        recipe.getIngredients() == null || recipe.getSteps() == null) { // was waren nochmal die Pflichtfelder??
                    return null;
                }
                recipeRepository.save(recipe);
                System.out.println(recipe);
                return recipe;
            }else{
            /*
                Sarah muss im Frontend abfragen ob sie ein Rezept bekommt aber leer ist, wenn ja soll sie auf die Login seite zurück verweisen
             */
                return new Recipe();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/edit-recipe", method = RequestMethod.POST)
    public Recipe editRecipe(@RequestBody Recipe recipe, @RequestBody User user) {
        try {
            if (user.isLoggedIn() && userController.validateDurration(user)) {
                //check if there's no valid id, recipe name, ingredients or steps, then can't update
                if (recipeRepository.findById(recipe.getId()) == null || recipe.getTitle() == null ||
                        recipe.getIngredients() == null || recipe.getSteps() == null) {
                    System.out.println("Cannot find recipe.");
                    return null;
                }
                recipeRepository.save(recipe);
                System.out.println(recipe);
                return recipe;
            } else {
                /*
                    Sarah muss im Frontend abfragen ob sie ein Rezept bekommt aber leer ist, wenn ja soll sie auf die Login seite zurück verweisen
                 */
                return new Recipe();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
	
