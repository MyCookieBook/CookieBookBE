package de.cookiebook.restservice.recipe;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;
import de.cookiebook.restservice.ingredients.IngredientRepository;
import de.cookiebook.restservice.materials.MaterialRepository;
import de.cookiebook.restservice.steps.StepRepository;
import de.cookiebook.restservice.user.User;
import de.cookiebook.restservice.user.UserController;
import de.cookiebook.restservice.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    StepRepository stepRepository;

    @Autowired
    UserController userController;

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

                    recipe.setUserId(userId);
                    recipeRepository.save(recipe);
                    return recipe.getId();
                }
            } else {
                System.out.println("failed adding recipe");
                userController.logUserOut(userId);
                return 0;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    // Delete recipe
    @PostMapping("/recipes/delete")
    public long deleteRecipe(@RequestParam(value = "recipeId") long id, @RequestParam(value = "userId") long userId, HttpServletResponse response) {
        try {
            User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                if (!recipeRepository.existsById(id)) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return 40;
                }
                recipeRepository.deleteById(id);
                response.setStatus(HttpServletResponse.SC_OK);
                return 20;
            } else {
                userController.logUserOut(userId);
                return 40;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return 40;
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
    public List<String> getFavoriteRecipes(@RequestParam(value = "userId") long userId) {
        try {
            User user = userRepository.findById(userId);
            List<String> favoriteRecipes = new ArrayList<>();
            if (userController.validateDurration(user)) {
                List<Recipe> allRecipes = recipeRepository.findByUserId(userId);
                for (Recipe allRecipe : allRecipes) {
                    if (allRecipe.isBookmark()) {
                        favoriteRecipes.add(allRecipe.toString());
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
    public List<String> getRecipesByCategory(@PathVariable String stringCategory, @RequestParam(value = "userId") long userId) {
        try {
            User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                List<String> recipes = new ArrayList<>();
                Category category = recipeRepository.getCategory(stringCategory);
                List<Recipe> recipeList = recipeRepository.findAllByCategory(category);
                for (Recipe recipe : recipeList) {
                    if (recipe.getUserId() == userId) {
                        recipes.add(recipe.toString());
                    }
                }
                return recipes;
            } else {
                userController.logUserOut(userId);
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
                    if (recipe.getUserId() == userId) {
                        recipes.add(recipe.toString());
                    }
                }
                return recipes;
            } else {
                userController.logUserOut(userId);
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // find by Title
    @GetMapping(value = "/recipeslist/search")
    public List<String> getAllByName(@RequestParam String term, @RequestParam(value = "userId") long userId) {
        try {
            List<Recipe> returnRecipes;
            List<String> recipeList = new ArrayList<>();
            User user = userRepository.findById(userId);
            if (userController.validateDurration(user)) {
                returnRecipes = recipeRepository.findAllByIngredientsIngredientNameContainingIgnoreCase(term);
                returnRecipes.addAll(recipeRepository.findAllByTitleContainingIgnoreCase(term));
                returnRecipes.addAll(recipeRepository.findAllByOtherContainingIgnoreCase(term));
                returnRecipes.addAll(recipeRepository.findAllByMaterialMaterialNameContainingIgnoreCase(term));
                returnRecipes.addAll(recipeRepository.findAllByStepsStepNameContainingIgnoreCase(term));
                for (int i = 0; i < returnRecipes.size(); i++) {
                    if (returnRecipes.get(i).getUserId() != userId) {
                        returnRecipes.remove(i);
                    }
                }
                HashSet<Recipe> recipeHash = new HashSet<Recipe>(returnRecipes);

                Object[] object = recipeHash.toArray();
                for (Object o : object) {
                    recipeList.add(o.toString());
                }
//                recipeList.sort();
                return recipeList;
            } else {
                userController.logUserOut(userId);
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // bookmark favourite
    @PostMapping("/recipe/bookmark")
    public long bookmarkRecipe(@RequestParam(value = "recipeId") long recipeId, @RequestParam(value = "userId") long userId, HttpServletResponse response) {
        try {
            User user = userRepository.getOne(userId);
            Recipe recipe = recipeRepository.getOne(recipeId);
            if (userController.validateDurration(user)) {
                recipe.setBookmark(true);
                recipeRepository.save(recipe);
                System.out.println("bookmarked recipe");
                response.setStatus(HttpServletResponse.SC_OK);
                return 20;
            } else {
                userController.logUserOut(userId);
                return 40;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return 40;
        }
    }

    // delete bookmark
    @DeleteMapping("/recipe/deleteBookmark")
    public long deleteBookmark(@RequestParam(value = "recipeId") long recipeId, @RequestParam(value = "userId") long userId, HttpServletResponse response) {
        try {
            User user = userRepository.getOne(userId);
            Recipe recipe = recipeRepository.getOne(recipeId);
            if (userController.validateDurration(user)) {
                recipe.setBookmark(false);
                recipeRepository.save(recipe);
                response.setStatus(HttpServletResponse.SC_OK);
                return 20;
            } else {
                userController.logUserOut(userId);
                return 40;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return 40;
        }
    }
}