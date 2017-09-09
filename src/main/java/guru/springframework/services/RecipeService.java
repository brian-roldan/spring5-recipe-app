package guru.springframework.services;

import java.util.Set;

import guru.springframework.command.RecipeCommand;
import guru.springframework.domain.Recipe;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

	Recipe findById(long id);
	
	RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

	RecipeCommand findRecipeCommandById(Long recipeId);
}
