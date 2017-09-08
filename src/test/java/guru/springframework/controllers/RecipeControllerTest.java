package guru.springframework.controllers;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public class RecipeControllerTest {

	RecipeController recipeController;
	
	@Mock RecipeService recipeService;
	@Mock Model model;
	@Captor ArgumentCaptor<Recipe> argumentCaptor;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		recipeController = new RecipeController(recipeService);
	}

	@Test
	public void testMockMVCShowUrl() throws Exception {

		Long recipeId = 1L;
		Recipe recipe = new Recipe();
		recipe.setId(recipeId);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		mockMvc.perform(get(format("/recipe/show/%d", recipeId)))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attribute("recipe", recipe));
		
	}

	@Test
	public void testMockMVCShowUrlMissingRecipe() throws Exception {

		Long recipeId = 1L;
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		
		mockMvc.perform(get(format("/recipe/show/%d", recipeId)))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetRecipe(){
		
		Recipe testRecipe = new Recipe();
		Long recipeId = 1L;
		testRecipe.setId(recipeId);
		
		when(recipeService.findById(recipeId)).thenReturn(testRecipe);
		
		String showPage = "recipe/show";
		String recipeModelAttributeKey = "recipe";
		
		String getRecipeResult = recipeController.getRecipe(recipeId, model);
		
		assertEquals(showPage, getRecipeResult);
		verify(recipeService, times(1)).findById(eq(recipeId));
		verify(model, times(1)).addAttribute(eq(recipeModelAttributeKey), eq(testRecipe));
		
	}
	
	@Test
	public void testGetRecipeMissingRecipe(){
		
		Recipe testRecipe = new Recipe();
		Long recipeId = 1L;
		testRecipe.setId(recipeId);
		
		String showPage = "recipe/show";
		String recipeModelAttributeKey = "recipe";
		
		String getRecipeResult = recipeController.getRecipe(recipeId, model);
		
		assertEquals(showPage, getRecipeResult);
		verify(recipeService, times(1)).findById(eq(recipeId));
		verify(model, never()).addAttribute(eq(recipeModelAttributeKey), eq(testRecipe));
		
	}

}
