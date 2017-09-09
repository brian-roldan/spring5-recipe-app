package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import guru.springframework.command.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	public void getRecipeByIdTest() {
		Long recipeId = 1L;
		Recipe recipe = new Recipe();
		recipe.setId(recipeId);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		Recipe recipeReturned = recipeService.findById(recipeId);

		assertNotNull("Returned null", recipeReturned);
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();

	}

	@Test
	public void testGetRecipes() {
		Recipe recipe = new Recipe();
		HashSet<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		when(recipeService.getRecipes()).thenReturn(recipeData);

		Set<Recipe> recipes = recipeService.getRecipes();

		assertTrue(recipes.contains(recipe));
		assertEquals(1, recipes.size());
		verify(recipeRepository, times(1)).findAll();
	}

	@Test
	public void testSaveRecipeCommand() {
		RecipeCommand argumentRecipeCommand = new RecipeCommand();
		Recipe detachedRecipe = new Recipe();

		Long recipeId = 1L;
		Recipe repositoryRecipe = new Recipe();
		repositoryRecipe.setId(recipeId);
		RecipeCommand convertedRecipeCommand = new RecipeCommand();
		convertedRecipeCommand.setId(recipeId);

		when(recipeCommandToRecipe.convert(argumentRecipeCommand)).thenReturn(detachedRecipe);
		when(recipeRepository.save(detachedRecipe)).thenReturn(repositoryRecipe);
		when(recipeToRecipeCommand.convert(repositoryRecipe)).thenReturn(convertedRecipeCommand);

		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(argumentRecipeCommand);

		assertNotNull(savedRecipeCommand);
		assertEquals(recipeId, savedRecipeCommand.getId());

		verify(recipeCommandToRecipe, times(1)).convert(eq(argumentRecipeCommand));
		verify(recipeRepository, times(1)).save(eq(detachedRecipe));
		verify(recipeToRecipeCommand, times(1)).convert(eq(repositoryRecipe));
	}

	@Test
	public void testSaveMergeRecipeCommand() {
		Long recipeId = 1L;
		RecipeCommand argumentRecipeCommand = new RecipeCommand();
		argumentRecipeCommand.setId(recipeId);
		RecipeCommand convertedRecipeCommand = new RecipeCommand();
		convertedRecipeCommand.setId(recipeId);

		Recipe detachedRecipe = new Recipe();
		detachedRecipe.setId(recipeId);

		Recipe repositoryRecipe = new Recipe();
		repositoryRecipe.setId(recipeId);

		when(recipeCommandToRecipe.convert(argumentRecipeCommand)).thenReturn(repositoryRecipe);
		when(recipeRepository.save(detachedRecipe)).thenReturn(repositoryRecipe);
		when(recipeToRecipeCommand.convert(repositoryRecipe)).thenReturn(convertedRecipeCommand);

		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(argumentRecipeCommand);

		assertNotNull(savedRecipeCommand);
		assertEquals(recipeId, savedRecipeCommand.getId());

		verify(recipeCommandToRecipe, times(1)).convert(eq(argumentRecipeCommand));
		verify(recipeRepository, times(1)).save(eq(repositoryRecipe));
		verify(recipeToRecipeCommand, times(1)).convert(eq(repositoryRecipe));
	}

	@Test
	public void testDeleteById() {
		Long idToDelete = 2L;
		
		recipeService.deleteRecipeCommand(idToDelete);		
		
		verify(recipeRepository, times(1)).deleteById(eq(idToDelete));
	}

}
