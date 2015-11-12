package stersectas.view.member.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.game.CreateGame;
import stersectas.application.game.GameService;
import stersectas.application.security.SecurityService;
import stersectas.application.validation.AllValidations;

@Controller
@RequestMapping("/member/game")
public class CreateGameController {

	private final GameService gameService;
	private final SecurityService securityService;

	@Autowired
	public CreateGameController(GameService gameService, SecurityService securityService) {
		this.gameService = gameService;
		this.securityService = securityService;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createGameForm(Model model) {
		model.addAttribute("createGame", new CreateGameForm());
		return "member/game/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createGame(@Validated(AllValidations.class) CreateGameForm createGameForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			gameService.createGame(new CreateGame(
					createGameForm.getName(),
					createGameForm.getDescription(),
					createGameForm.getMaximumPlayers(),
					securityService.currentUser().getUserId().id()));

			return "redirect:/game/" + findGameId(createGameForm.getName());
		}
		return "member/game/create";
	}

	private String findGameId(String gameName) {
		return gameService.findRecruitingGameByName(gameName).gameId().id();
	}

}
