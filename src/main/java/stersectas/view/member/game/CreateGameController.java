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
	public String createForm(Model model) {
		model.addAttribute("createGame", new CreateGame());
		return "member/game/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String registerUser(@Validated(AllValidations.class) CreateGame createGame, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			createGame(createGame);
			return "redirect:/game/recruiting";
			// TODO make this work return "redirect:" + findId(createGame);
		}
		return "member/game/create";
	}

	private void createGame(CreateGame createGame) {
		createGame.setMasterId(securityService.currentUser().getUserId().id());
		gameService.createGame(createGame);
	}

	private String findId(CreateGame createGame) {
		return gameService.findRecruitingGameByName(createGame.getName()).gameId().id();
	}

}
