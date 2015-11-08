package stersectas.view.member.game;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import stersectas.application.game.DetailedGame;
import stersectas.application.game.GameQueryService;
import stersectas.application.game.GameService;
import stersectas.application.game.GamerService;
import stersectas.application.game.RenameGame;
import stersectas.application.validation.AllValidations;

@Controller
@RequestMapping("/member/game")
public class RenameGameController {

	private final GameQueryService gameQueryService;
	private final GameService gameService;
	private final GamerService gamerService;

	@Autowired
	public RenameGameController(
			GameQueryService gameQueryService,
			GameService gameService,
			GamerService gamerService) {
		this.gameQueryService = gameQueryService;
		this.gameService = gameService;
		this.gamerService = gamerService;
	}

	@RequestMapping(value = "/{gameId}/rename", method = RequestMethod.GET)
	public String createGameForm(@PathVariable String gameId,
			HttpServletRequest request, Model model) throws NoSuchRequestHandlingMethodException {
		DetailedGame game = findGameAndCheckExistence(gameId, request);
		currentGamerAllowedToMakeChange(gameId);

		model.addAttribute("game", game);
		model.addAttribute("renameGameForm", new RenameGameForm());
		return "member/game/rename";
	}

	@RequestMapping(value = "/{gameId}/rename", method = RequestMethod.POST)
	public String createGame(@PathVariable String gameId,
			@Validated(AllValidations.class) RenameGameForm renameGameForm,
			BindingResult bindingResult, HttpServletRequest request) throws NoSuchRequestHandlingMethodException {
		findGameAndCheckExistence(gameId, request);
		currentGamerAllowedToMakeChange(gameId);

		if (!bindingResult.hasErrors()) {
			gameService.renameGame(new RenameGame(gameId, renameGameForm.getName()));
			return "redirect:/game/" + gameId;
		}
		return "member/game/rename";
	}

	private DetailedGame findGameAndCheckExistence(String gameId, HttpServletRequest request)
			throws NoSuchRequestHandlingMethodException {
		return gameQueryService.findDetailedGameById(gameId)
				.orElseThrow(() -> new NoSuchRequestHandlingMethodException(request));
	}

	private void currentGamerAllowedToMakeChange(String gameId) {
		if (!gamerService.isCurrentGamerTheMasterOfGame(gameId)) {
			throw new AccessDeniedException("Current gamer is not game master of game " + gameId);
		}
	}

}