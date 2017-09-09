package guru.springframework.converters;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.command.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (isNull(source)) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        
		if (nonNull(source.getNotes())) {
			recipe.setNotes(notesConverter.convert(source.getNotes()));
		}

        if (!isEmpty(source.getCategories())){
            source.getCategories()
                    .forEach( category -> recipe.getCategories().add(categoryConveter.convert(category)));
        }

        if (!isEmpty(source.getIngredients())){
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}