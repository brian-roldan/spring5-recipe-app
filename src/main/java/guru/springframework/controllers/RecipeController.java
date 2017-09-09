package guru.springframework.controllers;

import static java.lang.String.format;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.command.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

@Controller
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/show")
	public String getRecipe(@PathVariable Long recipeId, Model model) {
		Recipe recipe = recipeService.findById(recipeId);
		model.addAttribute("recipe", recipe);
		return "recipe/show";
	}

	@GetMapping
	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}

	@GetMapping
	@RequestMapping("recipe/{recipeId}/update")
	public String updateRecipe(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findRecipeCommandById(recipeId));
		return "recipe/recipeform";
	}
	
	@PostMapping
	@RequestMapping("recipe")
	public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand){
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		
		return format("redirect:/recipe/%d/show/", savedRecipeCommand.getId());
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/delete")
	public String deleteRecipe(@PathVariable Long recipeId){
		recipeService.deleteRecipeCommand(recipeId);
		return "redirect:/";
	}

}
