package stersectas.application.game;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stersectas.application.security.SecurityService;
import stersectas.domain.game.Description;
import stersectas.domain.game.MaximumPlayers;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGame;
import stersectas.domain.game.RecruitingGameRepository;

@Service
public class GameService {

	private SecurityService securityService;

	private RecruitingGameRepository recruitingGameRepository;

	@Autowired
	public GameService(
			SecurityService securityService,
			RecruitingGameRepository recruitingGameRepository) {
		this.securityService = securityService;
		this.recruitingGameRepository = recruitingGameRepository;
	}

	@Transactional
	public void createGame(CreateGame createGame) {
		recruitingGameRepository.save(new RecruitingGame(
				new Name(createGame.getName()),
				new Description(createGame.getDescription()),
				new MaximumPlayers(createGame.getMaximumPlayers()),
				securityService.currentUser().getUserId()));

	}

	public RecruitingGame findRecruitingGameByName(String name) {
		return recruitingGameRepository.findByName(new Name(name));
	}

}