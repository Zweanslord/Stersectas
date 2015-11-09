package stersectas.application.game;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeGamePlayerMaximum {

	@Size(min = 1)
	private String gameId;

	@Min(1)
	@Max(8)
	private int maximumPlayers;

}