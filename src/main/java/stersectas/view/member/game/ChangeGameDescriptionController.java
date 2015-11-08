package stersectas.view.member.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.game.ChangeGameDescription;
import stersectas.application.game.DetailedGame;
import stersectas.application.game.GameQueryService;
import stersectas.application.game.GameService;
import stersectas.application.game.GamerService;

@Controller
@RequestMapping("/member/game")
public class ChangeGameDescriptionController {

	private final GameQueryService gameQueryService;
	private final GameService gameService;
	private final GamerService gamerService;

	@Autowired
	public ChangeGameDescriptionController(
			GameQueryService gameQueryService,
			GameService gameService,
			GamerService gamerService) {
		this.gameQueryService = gameQueryService;
		this.gameService = gameService;
		this.gamerService = gamerService;
	}

	@RequestMapping(value = "/{gameId}/changeDescription", method = RequestMethod.GET)
	public String changeGameDescription(@PathVariable String gameId, Model model) {
		DetailedGame game = gameQueryService.findDetailedGameById(gameId);
		currentGamerIsMasterOrThrowException(gameId);

		model.addAttribute("gameId", gameId);
		model.addAttribute("changeGameDescriptionForm", new ChangeDescriptionForm(game.getDescription()));
		return "member/game/changeDescription";
	}

	@RequestMapping(value = "/{gameId}/changeDescription", method = RequestMethod.POST)
	public String changeGameDescription(@PathVariable String gameId, ChangeDescriptionForm changeGameDescriptionForm,
			BindingResult bindingResult) {
		currentGamerIsMasterOrThrowException(gameId);

		if (!bindingResult.hasErrors()) {
			gameService.changeGameDescription(
					new ChangeGameDescription(gameId, changeGameDescriptionForm.getDescription()));
			return "redirect:/game/" + gameId;
		}
		return "member/game/changeDescription";
	}

	private void currentGamerIsMasterOrThrowException(String gameId) {
		if (!gamerService.isCurrentGamerTheMasterOfGame(gameId)) {
			throw new AccessDeniedException("Only game master is allowed to change game description " + gameId);
		}
	}

}
