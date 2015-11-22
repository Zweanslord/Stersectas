package stersectas.view.game;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.game.DetailedGame;
import stersectas.application.game.GameQueryService;

@Controller
@RequestMapping("/game")
public class ViewGameController {

	private final GameQueryService gameQueryService;

	@Autowired
	public ViewGameController(
			GameQueryService gameQueryService) {
		this.gameQueryService = gameQueryService;
	}

	@RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
	public String view(@PathVariable String gameId, HttpServletRequest request, Model model) {
		DetailedGame game = gameQueryService.findDetailedGameById(gameId);
		model.addAttribute("game", game);
		model.addAttribute("isEditable", gameQueryService.canCurrentGamerMakeChanges(gameId));
		return "game/view";
	}
}