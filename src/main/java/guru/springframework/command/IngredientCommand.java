package guru.springframework.command;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class IngredientCommand {
	private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;
}
