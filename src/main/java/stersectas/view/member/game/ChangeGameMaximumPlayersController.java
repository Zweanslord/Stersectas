package stersectas.view.member.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.game.ChangeGameMaximumPlayers;
import stersectas.application.game.DetailedGame;
import stersectas.application.game.GameQueryService;
import stersectas.application.game.GameService;
import stersectas.application.game.GamerService;

@Controller
@RequestMapping("/member/game")
public class ChangeGameMaximumPlayersController {

	private final GameQueryService gameQueryService;
	private final GameService gameService;
	private final GamerService gamerService;

	@Autowired
	public ChangeGameMaximumPlayersController(
			GameQueryService gameQueryService,
			GameService gameService,
			GamerService gamerService) {
		this.gameQueryService = gameQueryService;
		this.gameService = gameService;
		this.gamerService = gamerService;
	}

	@RequestMapping(value = "/{gameId}/changeMaximumPlayers", method = RequestMethod.GET)
	public String changeGameMaximumPlayers(@PathVariable String gameId, Model model) {
		currentGamerIsMasterOrThrowException(gameId);

		DetailedGame game = gameQueryService.findDetailedGameById(gameId);
		model.addAttribute("gameId", gameId);
		model.addAttribute("changeGameMaximumPlayersForm", new ChangeMaximumPlayersForm(game.getMaximumPlayers()));
		return "member/game/changeMaximumPlayers";
	}

	@RequestMapping(value = "/{gameId}/changeMaximumPlayers", method = RequestMethod.POST)
	public String changeGameMaximumPlayers(@PathVariable String gameId, ChangeMaximumPlayersForm changePlayerMaximumForm,
			BindingResult bindingResult) {
		currentGamerIsMasterOrThrowException(gameId);

		if (!bindingResult.hasErrors()) {
			gameService.changeGameMaximumPlayers(
					new ChangeGameMaximumPlayers(gameId, changePlayerMaximumForm.getMaximumPlayers()));
			return "redirect:/game/" + gameId;
		}
		return "member/game/changeMaximumPlayers";
	}

	private void currentGamerIsMasterOrThrowException(String gameId) {
		if (!gamerService.isCurrentGamerTheMasterOfGame(gameId)) {
			throw new AccessDeniedException("Only game master is allowed to change maximum players of game " + gameId);
		}
	}

}
