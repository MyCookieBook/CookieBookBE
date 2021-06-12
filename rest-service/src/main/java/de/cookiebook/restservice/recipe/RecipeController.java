package de.cookiebook.restservice.recipe;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;
import de.cookiebook.restservice.ingredients.Ingredient;
import de.cookiebook.restservice.ingredients.IngredientRepository;
import de.cookiebook.restservice.materials.MaterialRepository;
import de.cookiebook.restservice.steps.StepRepository;
import de.cookiebook.restservice.user.Status;
import de.cookiebook.restservice.user.User;
import de.cookiebook.restservice.user.UserController;
import de.cookiebook.restservice.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @Autowired
    UserController userController = new UserController(userRepository);
    // Add recipe

    @PostMapping("/recipes/add")
    public long addRecipe(@RequestBody Recipe recipe, @RequestParam(value = "userId") long userId, HttpServletResponse response) {
        try {
            long id;
            User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                if (recipe.getId() == 0) {
                    ingredientRepository.saveAll(recipe.getIngredients());
                    materialRepository.saveAll(recipe.getMaterial());
                    stepRepository.saveAll(recipe.getSteps());
                    recipe.setUserId(userId);
                    recipeRepository.save(recipe);
                    List<Recipe> allRecipes = recipeRepository.findByUserId(userId);
                    for (Recipe allRecipe : allRecipes) {
                        if (allRecipe.getCategory().equals(recipe.getCategory()) &&
                                allRecipe.getDifficultyLevel().equals(recipe.getDifficultyLevel()) &&
                                allRecipe.getDuration().equals(recipe.getDuration()) &&
                                allRecipe.getLink().equals(recipe.getLink()) &&
                                allRecipe.getTitle().equals(recipe.getTitle()) &&
                                allRecipe.getSubcategory().equals(recipe.getSubcategory()) &&
                                allRecipe.getAuthor().equals(recipe.getAuthor()) &&
                                allRecipe.getCalory().equals(recipe.getCalory()) &&
                                allRecipe.isBookmark() == (recipe.isBookmark()) &&
                                allRecipe.getOther().equals(recipe.getOther()) &&
                                allRecipe.getUserId() == (recipe.getUserId())
                        ) {
                            id = allRecipe.getId();
                            return id;
                        }

                    }
                    return 0;
                } else {
//                  edit recipe einfach in add
                    recipeRepository.save(recipe);
                    return recipe.getId();
                }
            } else {
                System.out.println("failed adding recipe");
                return 0;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    // Delete recipe
    @PostMapping("/recipes/delete")
    public void deleteRecipe(@RequestParam(value = "recipeId") long id, @RequestParam(value = "userId") long userId, HttpServletResponse response) {
        try {
            User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                if (!recipeRepository.existsById(id)) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                recipeRepository.deleteById(id);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // implement find
    @GetMapping("/recipeslist")
    public List<Recipe> getRecipes(@RequestParam(value = "userId") long userId) {
        try {
            User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                return recipeRepository.findByUserId(userId);
            } else {
                return null;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/recipeslist/favorite")
    public List<Recipe> getFavoriteRecipes(@RequestParam(value = "userId") long userId) {
        try {
            User user = userRepository.findById(userId);
            List<Recipe> favoriteRecipes = new ArrayList<Recipe>();
            if (userController.validateDurration(user)) {
                List<Recipe> allRecipes = recipeRepository.findByUserId(userId);
                for (Recipe allRecipe : allRecipes) {
                    if (allRecipe.isBookmark()) {
                        favoriteRecipes.add(allRecipe);
                    }
                }
                if (favoriteRecipes.isEmpty()) {
                    return null;
                } else {
                    return favoriteRecipes;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/recipeslist/byCategory/{stringCategory}")
    public List<Recipe> getRecipesByCategory(@PathVariable String stringCategory, @RequestParam(value = "userId") long userId) {
        try {
            User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                Category category = recipeRepository.getCategory(stringCategory);
                return recipeRepository.findAllByCategory(category);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/recipeslist/bySubcategory/{stringSubcategory}")
    public List<String> getRecipesBySubcategory(@PathVariable String stringSubcategory, @RequestParam(value = "userId") long userId) {
        try {
          User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                List<String> recipes = new ArrayList<>();
                Subcategory subcategory = recipeRepository.getSubcategory(stringSubcategory);
                List<Recipe> recipeList = recipeRepository.findAllBySubcategory(subcategory);
                for (Recipe recipe : recipeList) {
                    recipes.add(recipe.toString());
                }
                return recipes;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // find by Title
    @GetMapping(value = "/recipeslist/search")
    public HashSet<Recipe> getAllByName(@RequestParam String term, @RequestParam(value = "userId") long userId) {
        try {
            List<Recipe> returnRecipes = new ArrayList<Recipe>();
            User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                returnRecipes = recipeRepository.findAllByIngredientsIngredientNameContainingIgnoreCase(term);
                returnRecipes.addAll(recipeRepository.findAllByTitleContainingIgnoreCase(term));
                returnRecipes.addAll(recipeRepository.findAllByOtherContainingIgnoreCase(term));
                returnRecipes.addAll(recipeRepository.findAllByMaterialMaterialNameContainingIgnoreCase(term));
                returnRecipes.addAll(recipeRepository.findAllByStepsStepNameContainingIgnoreCase(term));
                return new HashSet<Recipe>(returnRecipes);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // bookmark favourite
    @PostMapping("/recipe/bookmark")
    public void bookmarkRecipe(@RequestParam(value = "recipeId") long recipeId, @RequestParam(value = "userId") long userId, HttpServletResponse response) {
        try {
            User user = userRepository.getOne(userId);
            Recipe recipe = recipeRepository.getOne(recipeId);
            if (userController.validateDurration(user)) {
                recipe.setBookmark(true);
                recipeRepository.save(recipe);
                System.out.println("bookmarked recipe");
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // delete bookmark
    @DeleteMapping("/recipe/deleteBookmark")
    public void deleteBookmark(@RequestParam(value = "recipeId") long recipeId, @RequestParam(value = "userId") long userId, HttpServletResponse response) {
        try {
            User user = userRepository.getOne(userId);
            Recipe recipe = recipeRepository.getOne(recipeId);
            if (userController.validateDurration(user)) {
                recipe.setBookmark(false);
//                user.deleteBookmark(recipe);
//                userRepository.save(user);
                recipeRepository.save(recipe);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
	
