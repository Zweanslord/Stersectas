package stersectas.application.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stersectas.application.security.SecurityService;
import stersectas.domain.game.GameId;
import stersectas.domain.game.GameRepository;
import stersectas.domain.user.User;
import stersectas.domain.user.UserId;

@Service
public class GamerService {

	private final GameRepository gameRepository;
	private final SecurityService securityService;

	@Autowired
	public GamerService(
			GameRepository gameRepository,
			SecurityService securityService) {
		this.gameRepository = gameRepository;
		this.securityService = securityService;
	}

	public boolean isCurrentGamerTheMasterOfGame(String gameId) {
		User currentUser = securityService.currentUser();
		UserId masterId = gameRepository.findByGameId(new GameId(gameId)).get().masterId();
		return currentUser.getUserId().equals(masterId);
	}

}
