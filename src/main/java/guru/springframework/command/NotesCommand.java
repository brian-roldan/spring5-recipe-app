package guru.springframework.command;

import lombok.Data;

@Data
public class NotesCommand {
	private Long id;
    private String recipeNotes;
}
