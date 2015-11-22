package stersectas.view.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import stersectas.application.game.GameQueryService;

@Controller
@RequestMapping("/game")
public class ArchivedGamesController {

	private final GameQueryService gameQueryService;

	@Autowired
	public ArchivedGamesController(GameQueryService gameQueryService) {
		this.gameQueryService = gameQueryService;
	}

	@RequestMapping("/archived")
	public String games(Model model) {
		model.addAttribute("games", gameQueryService.listArchivedGames());
		return "/game/archived";
	}

}