package de.cookiebook.restservice.recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByRecipeCategory(Category Category);
	List<Recipe> findAllByRecipeSubcategory(Subcategory subcategory);
	
}