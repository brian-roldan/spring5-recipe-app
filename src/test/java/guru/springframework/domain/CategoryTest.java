package guru.springframework.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

	Category category;

	@Before
	public void setUp() {
		category = new Category();
	}

	@Test
	public void testGetId() {
		Long id = 4l;
		
		category.setId(id);
		
		assertEquals(id, category.getId());
	}

	@Test
	public void testGetDescription() {
		String testDescription = "FOOBAR";
		
		category.setDescription(testDescription);
		
		assertEquals(testDescription, category.getDescription());
	}

	@Test
	public void testGetRecipes() {
		
		Recipe testRecipe = new Recipe();
		
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(testRecipe);
		
		assertTrue(recipes.contains(testRecipe));
		assertEquals(1, recipes.size());
	}

}
