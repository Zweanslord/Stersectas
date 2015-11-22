package stersectas.view.member.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.game.ArchiveGame;
import stersectas.application.game.DetailedGame;
import stersectas.application.game.GameQueryService;
import stersectas.application.game.GameService;

@Controller
@RequestMapping("/member/game")
public class ArchiveGameController {

	private final GameQueryService gameQueryService;
	private final GameService gameService;

	@Autowired
	public ArchiveGameController(
			GameQueryService gameQueryService,
			GameService gameService) {
		this.gameQueryService = gameQueryService;
		this.gameService = gameService;
	}

	@RequestMapping(value = "/{gameId}/archive", method = RequestMethod.GET)
	public String archiveGameConfirmation(@PathVariable String gameId, Model model) {
		currentGamerIsMasterOrThrowException(gameId);

		DetailedGame game = gameQueryService.findDetailedGameById(gameId);
		model.addAttribute("game", game);
		return "member/game/archive";
	}

	@RequestMapping(value = "/{gameId}/archive", method = RequestMethod.POST)
	public String archiveGamen(@PathVariable String gameId) {
		currentGamerIsMasterOrThrowException(gameId);

		gameService.archiveGame(new ArchiveGame(gameId));
		return "redirect:/game/" + gameId;
	}

	private void currentGamerIsMasterOrThrowException(String gameId) {
		if (!gameQueryService.isCurrentGamerTheMasterOfGame(gameId)) {
			throw new AccessDeniedException("Only game master is allowed to archive game " + gameId);
		}
	}
}