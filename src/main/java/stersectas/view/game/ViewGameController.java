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
import stersectas.application.game.GamerService;

@Controller
@RequestMapping("/game")
public class ViewGameController {

	private final GameQueryService gameQueryService;
	private final GamerService gamerService;

	@Autowired
	public ViewGameController(
			GameQueryService gameQueryService,
			GamerService gamerService) {
		this.gameQueryService = gameQueryService;
		this.gamerService = gamerService;
	}

	@RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
	public String view(@PathVariable String gameId, HttpServletRequest request, Model model) {
		DetailedGame game = gameQueryService.findDetailedGameById(gameId);
		model.addAttribute("game", game);
		model.addAttribute("isMaster", gamerService.isCurrentGamerTheMasterOfGame(gameId));
		return "game/view";
	}
}