package de.cookiebook.restservice;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class Service {
	
	@Autowired	
	RecipeRepository recipeRepository; 

	public Optional<Recipe> findById(Long id) {
		return recipeRepository.findById(id);
	}
	

}
