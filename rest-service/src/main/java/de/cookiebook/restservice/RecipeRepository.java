package de.cookiebook.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.cookiebook.restservice.Recipe;
import de.cookiebook.restservice.Category;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
}