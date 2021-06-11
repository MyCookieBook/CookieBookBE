package de.cookiebook.restservice.recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.cookiebook.restservice.category.Category;
import de.cookiebook.restservice.category.Subcategory;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByCategory(Category Category);

    List<Recipe> findAllBySubcategory(Subcategory subcategory);

    List<Recipe> findByUserId (long userId);

    List<Recipe> findAllByTitleContainingIgnoreCase(String term);

}