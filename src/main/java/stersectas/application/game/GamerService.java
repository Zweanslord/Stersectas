package stersectas.application.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stersectas.application.security.SecurityService;
import stersectas.domain.user.User;
import stersectas.domain.user.UserId;

@Service
public class GamerService {

	private final GameService gameService;
	private final SecurityService securityService;

	@Autowired
	public GamerService(
			GameService gameService,
			SecurityService securityService) {
		this.gameService = gameService;
		this.securityService = securityService;
	}

	public boolean isCurrentGamerTheMasterOfGame(String gameId) {
		User currentUser = securityService.currentUser();
		UserId masterId = gameService.findGameById(gameId).masterId();
		return currentUser.getUserId().equals(masterId);
	}

}
