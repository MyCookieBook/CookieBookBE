package de.cookiebook.restservice.recipe;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;
import de.cookiebook.restservice.ingredients.IngredientRepository;
import de.cookiebook.restservice.materials.MaterialRepository;
import de.cookiebook.restservice.steps.StepRepository;
import de.cookiebook.restservice.user.User;
import de.cookiebook.restservice.user.UserController;
import de.cookiebook.restservice.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecipeControllerTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private StepRepository stepRepository;

    @Mock
    private UserController userController;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    RecipeController recipeController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddRecipe_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        Recipe newRecipe = new Recipe();
        newRecipe.setId(0L);
        newRecipe.setCategory(Category.DESSERT);
        newRecipe.setDifficultyLevel(3);
        newRecipe.setDuration(12);
        newRecipe.setTitle("new_recipe");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(ingredientRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(materialRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(stepRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(recipeRepository.save(any())).thenReturn(stubRecipe());
        when(recipeRepository.findByUserId(anyLong())).thenReturn(stubListOfRecipe());
        // Act
        long id = recipeController.addRecipe(newRecipe, 1, httpServletResponse);
        // Assert
        assertEquals(0, id);
    }

    @Test
    public void testAddRecipe_SIMILAR_RECIPE(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        Recipe newRecipe = stubRecipe();
        newRecipe.setId(0L);
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(ingredientRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(materialRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(stepRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(recipeRepository.save(any())).thenReturn(stubRecipe());
        when(recipeRepository.findByUserId(anyLong())).thenReturn(stubListOfRecipe());
        // Act
        long id = recipeController.addRecipe(newRecipe, 1, httpServletResponse);
        // Assert
        assertEquals(1, id);
    }

    @Test
    public void testAddRecipe_RECIPE_ID_EXISTS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        Recipe newRecipe = new Recipe();
        newRecipe.setId(1L);
        newRecipe.setCategory(Category.DESSERT);
        newRecipe.setDifficultyLevel(3);
        newRecipe.setDuration(12);
        newRecipe.setTitle("new_recipe");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.save(any())).thenReturn(stubRecipe());
        when(recipeRepository.findByUserId(anyLong())).thenReturn(stubListOfRecipe());
        // Act
        long id = recipeController.addRecipe(newRecipe, 1, httpServletResponse);
        // Assert
        assertEquals(1, id);
    }

    @Test
    public void testAddRecipe_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        Recipe newRecipe = new Recipe();
        newRecipe.setId(0L);
        newRecipe.setCategory(Category.DESSERT);
        newRecipe.setDifficultyLevel(3);
        newRecipe.setDuration(12);
        newRecipe.setTitle("new_recipe");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(false);
        when(userController.logUserOut(anyLong())).thenReturn(1L);
        // Act
        long id = recipeController.addRecipe(newRecipe, 1, httpServletResponse);
        // Assert
        assertEquals(0, id);
    }

    @Test
    public void testAddRecipe_EXCEPTION(){
        // Arrange
        Recipe newRecipe = new Recipe();
        newRecipe.setId(0L);
        newRecipe.setCategory(Category.DESSERT);
        newRecipe.setDifficultyLevel(3);
        newRecipe.setDuration(12);
        newRecipe.setTitle("new_recipe");
        when(userRepository.findById(anyInt())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        long id = recipeController.addRecipe(newRecipe, 1, httpServletResponse);
        // Assert
        assertEquals(0, id);
    }

    @Test
    public void testDeleteRecipe_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(recipeRepository).deleteById(anyLong());
        // Act
        long statusCode = recipeController.deleteRecipe(1, 1, httpServletResponse);
        // Assert
        assertEquals(20, statusCode);
    }

    @Test
    public void testDeleteRecipe_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(false);
        when(userController.logUserOut(anyInt())).thenReturn(1L);
        // Act
        long statusCode = recipeController.deleteRecipe(1, 1, httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testDeleteRecipe_INVALID_RECIPE(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.existsById(anyLong())).thenReturn(false);
        // Act
        long statusCode = recipeController.deleteRecipe(1, 1, httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testDeleteRecipe_EXCEPTION(){
        // Arrange
        when(userRepository.findById(anyInt())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        long statusCode = recipeController.deleteRecipe(1, 1, httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testGetRecipes_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        List<Recipe> stubListOfRecipe = stubListOfRecipe();
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.findByUserId(anyInt())).thenReturn(stubListOfRecipe);
        // Act
        List<Recipe> recipes = recipeController.getRecipes(1);
        // Assert
        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        assertNotNull(recipes.get(0));
        assertEquals(1, recipes.get(0).getId());
        assertEquals(1, recipes.get(0).getUserId());
        assertEquals("test_recipe", recipes.get(0).getTitle());
    }

    @Test
    public void testGetRecipes_DURATION_INVALID(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        List<Recipe> stubListOfRecipe = stubListOfRecipe();
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(false);
        when(recipeRepository.findByUserId(anyInt())).thenReturn(stubListOfRecipe);
        // Act
        List<Recipe> recipes = recipeController.getRecipes(1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetRecipes_EXCEPTION(){
        // Arrange
        when(userRepository.findById(anyInt())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        List<Recipe> recipes = recipeController.getRecipes(1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetFavoriteRecipes_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        List<Recipe> stubListOfRecipe = stubListOfRecipe();
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.findByUserId(anyInt())).thenReturn(stubListOfRecipe);
        // Act
        List<String> recipes = recipeController.getFavoriteRecipes(1);
        // Assert
        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        assertNotNull(recipes.get(0));
    }

    @Test
    public void testGetFavoriteRecipes_NO_BOOKMARK(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        List<Recipe> stubListOfRecipe = stubListOfRecipe();
        stubListOfRecipe.get(0).setBookmark(false);
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.findByUserId(anyInt())).thenReturn(stubListOfRecipe);
        // Act
        List<String> recipes = recipeController.getFavoriteRecipes(1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetFavoriteRecipes_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(false);
        // Act
        List<String> recipes = recipeController.getFavoriteRecipes(1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetFavoriteRecipes_EXCEPTION(){
        // Arrange
        when(userRepository.findById(anyInt())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        List<String> recipes = recipeController.getFavoriteRecipes(1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetRecipesByCategory_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        List<Recipe> stubListOfRecipe = stubListOfRecipe();
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.getCategory(any())).thenReturn(Category.APPETIZER);
        when(recipeRepository.findAllByCategory(any())).thenReturn(stubListOfRecipe);
        // Act
        List<String> recipes = recipeController.getRecipesByCategory("APPETIZER", 1);
        // Assert
        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        assertNotNull(recipes.get(0));
    }

    @Test
    public void testGetRecipesByCategory_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(false);
        when(userController.logUserOut(anyInt())).thenReturn(1L);
        // Act
        List<String> recipes = recipeController.getRecipesByCategory("APPETIZER", 1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetRecipesByCategory_EXCEPTION(){
        // Arrange
        when(userRepository.findById(anyInt())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        List<String> recipes = recipeController.getRecipesByCategory("APPETIZER", 1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetRecipesBySubcategory_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        List<Recipe> stubListOfRecipe = stubListOfRecipe();
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.getSubcategory(any())).thenReturn(Subcategory.APP_SALAD);
        when(recipeRepository.findAllBySubcategory(any())).thenReturn(stubListOfRecipe);
        // Act
        List<String> recipes = recipeController.getRecipesBySubcategory("APP_SALAD", 1);
        // Assert
        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        assertNotNull(recipes.get(0));
    }

    @Test
    public void testGetRecipesBySubcategory_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.findById(anyInt())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(false);
        when(userController.logUserOut(anyInt())).thenReturn(1L);
        // Act
        List<String> recipes = recipeController.getRecipesBySubcategory("APP_SALAD", 1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetRecipesBySubcategory_EXCEPTION(){
        // Arrange
        when(userRepository.findById(anyInt())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        List<String> recipes = recipeController.getRecipesBySubcategory("APP_SALAD", 1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetAllByName_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.findById(anyLong())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.findAllByIngredientsIngredientNameContainingIgnoreCase(anyString())).thenReturn(new ArrayList<>());
        when(recipeRepository.findAllByTitleContainingIgnoreCase(anyString())).thenReturn(stubListOfRecipe());
        when(recipeRepository.findAllByOtherContainingIgnoreCase(anyString())).thenReturn(new ArrayList<>());
        when(recipeRepository.findAllByMaterialMaterialNameContainingIgnoreCase(anyString())).thenReturn(new ArrayList<>());
        when(recipeRepository.findAllByStepsStepNameContainingIgnoreCase(anyString())).thenReturn(new ArrayList<>());
        // Act
        List<String> recipes = recipeController.getAllByName("test", 1);
        // Assert
        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        assertNotNull(recipes.get(0));
    }

    @Test
    public void testGetAllByName_INVALID_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.findById(anyLong())).thenReturn(stubUser);
        when(userController.validateDurration(any())).thenReturn(false);
        when(userController.logUserOut(anyLong())).thenReturn(1L);
        // Act
        List<String> recipes = recipeController.getAllByName("test", 1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testGetAllByName_EXCEPTION(){
        // Arrange
        when(userRepository.findById(anyLong())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        List<String> recipes = recipeController.getAllByName("test", 1);
        // Assert
        assertNull(recipes);
    }

    @Test
    public void testBookmarkRecipe_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.getOne(anyLong())).thenReturn(stubUser);
        when(recipeRepository.getOne(anyLong())).thenReturn(stubRecipe());
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.save(any())).thenReturn(stubRecipe());
        // Act
        long statusCode = recipeController.bookmarkRecipe(1, 1, httpServletResponse);
        // Assert
        assertEquals(20, statusCode);
    }

    @Test
    public void testBookmarkRecipe_INVALIDATE_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.getOne(anyLong())).thenReturn(stubUser);
        when(recipeRepository.getOne(anyLong())).thenReturn(stubRecipe());
        when(userController.validateDurration(any())).thenReturn(false);
        when(userController.logUserOut(anyInt())).thenReturn(1L);
        // Act
        long statusCode = recipeController.bookmarkRecipe(1, 1, httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testBookmarkRecipe_EXCEPTION(){
        // Arrange
        when(userRepository.getOne(anyLong())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        long statusCode = recipeController.bookmarkRecipe(1, 1, httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testDeleteBookmark_SUCCESS(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.getOne(anyLong())).thenReturn(stubUser);
        when(recipeRepository.getOne(anyLong())).thenReturn(stubRecipe());
        when(userController.validateDurration(any())).thenReturn(true);
        when(recipeRepository.save(any())).thenReturn(stubRecipe());
        // Act
        long statusCode = recipeController.deleteBookmark(1, 1, httpServletResponse);
        // Assert
        assertEquals(20, statusCode);
    }

    @Test
    public void testDeleteBookmark_INVALIDATE_DURATION(){
        // Arrange
        User stubUser = new User("user1@email.com", "user1_password");
        when(userRepository.getOne(anyLong())).thenReturn(stubUser);
        when(recipeRepository.getOne(anyLong())).thenReturn(stubRecipe());
        when(userController.validateDurration(any())).thenReturn(false);
        when(userController.logUserOut(anyInt())).thenReturn(1L);
        // Act
        long statusCode = recipeController.deleteBookmark(1, 1, httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    @Test
    public void testDeleteBookmark_EXCEPTION(){
        // Arrange
        when(userRepository.getOne(anyLong())).thenThrow(new IllegalArgumentException("test exception"));
        // Act
        long statusCode = recipeController.deleteBookmark(1, 1, httpServletResponse);
        // Assert
        assertEquals(40, statusCode);
    }

    private List<Recipe> stubListOfRecipe() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(stubRecipe());
        return recipes;
    }

    private Recipe stubRecipe(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setAuthor("test_author");
        recipe.setTitle("test_recipe");
        recipe.setDuration(15);
        recipe.setDifficultyLevel(3);
        recipe.setCategory(Category.APPETIZER);
        recipe.setSubcategory(Subcategory.APP_SALAD);
        recipe.setLink("test_link");
        recipe.setCalory("100 kcal");
        recipe.setUserId(1);
        recipe.setIngredients(new ArrayList<>());
        recipe.setMaterial(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        recipe.setOther("test_other");
        recipe.setBookmark(true);
        return recipe;
    }

}
