package stersectas.view.member.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.game.DetailedGame;
import stersectas.application.game.GameQueryService;
import stersectas.application.game.GameService;
import stersectas.application.game.RenameGame;
import stersectas.application.validation.AllValidations;

@Controller
@RequestMapping("/member/game")
public class RenameGameController {

	private final GameQueryService gameQueryService;
	private final GameService gameService;

	@Autowired
	public RenameGameController(
			GameQueryService gameQueryService,
			GameService gameService) {
		this.gameQueryService = gameQueryService;
		this.gameService = gameService;
	}

	@RequestMapping(value = "/{gameId}/rename", method = RequestMethod.GET)
	public String renameGame(@PathVariable String gameId, Model model) {
		currentGamerIsMasterOrThrowException(gameId);

		DetailedGame game = gameQueryService.findDetailedGameById(gameId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("renameGameForm", new RenameForm(game.getName()));
		return "member/game/rename";
	}

	@RequestMapping(value = "/{gameId}/rename", method = RequestMethod.POST)
	public String renameGame(@PathVariable String gameId, @Validated(AllValidations.class) RenameForm renameGameForm,
			BindingResult bindingResult) {
		currentGamerIsMasterOrThrowException(gameId);

		if (!bindingResult.hasErrors()) {
			gameService.renameGame(new RenameGame(gameId, renameGameForm.getName()));
			return "redirect:/game/" + gameId;
		}
		return "member/game/rename";
	}

	private void currentGamerIsMasterOrThrowException(String gameId) {
		if (!gameQueryService.isCurrentGamerTheMasterOfGame(gameId)) {
			throw new AccessDeniedException("Only game master is allowed to rename game " + gameId);
		}
	}

}