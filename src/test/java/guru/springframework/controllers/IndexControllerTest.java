package guru.springframework.controllers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

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

public class IndexControllerTest {

	IndexController indexController;
	
	@Mock RecipeService recipeService;
	@Mock Model model;
	@Captor ArgumentCaptor<Set<Recipe>> argumentCaptor;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void testMockMVCSlashUrl() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
		
	}
	
	@Test
	public void testMockMVCBlankUrl() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		
		mockMvc.perform(get(""))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
		
	}
	
	@Test
	public void testGetIndexPage() {
		
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());
		
		when(recipeService.getRecipes()).thenReturn(recipes);
		String indexPage = "index";
		String recipesModelAttributeKey = "recipes";
		
		String indexPageResult = indexController.getIndexPage(model);
		
		assertEquals(indexPage, indexPageResult);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq(recipesModelAttributeKey), argumentCaptor.capture());
		
		Set<Recipe> recipesFromController = argumentCaptor.getValue();
		assertEquals(recipes.size(), recipesFromController.size());
		
	}

}
