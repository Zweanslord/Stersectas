package stersectas.application.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stersectas.application.security.SecurityService;
import stersectas.domain.game.ArchivedGame;
import stersectas.domain.game.ArchivedGameRepository;
import stersectas.domain.game.Description;
import stersectas.domain.game.Game;
import stersectas.domain.game.GameId;
import stersectas.domain.game.GameRepository;
import stersectas.domain.game.MaximumPlayers;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGame;
import stersectas.domain.game.RecruitingGameRepository;

@Service
public class GameService {

	private final SecurityService securityService;
	private final GameRepository gameRepository;
	private final RecruitingGameRepository recruitingGameRepository;
	private final ArchivedGameRepository archivedGameRepository;

	@Autowired
	public GameService(
			SecurityService securityService,
			GameRepository gameRepository,
			RecruitingGameRepository recruitingGameRepository,
			ArchivedGameRepository archivedGameRepository) {
		this.securityService = securityService;
		this.gameRepository = gameRepository;
		this.recruitingGameRepository = recruitingGameRepository;
		this.archivedGameRepository = archivedGameRepository;
	}

	@Transactional
	public void createGame(CreateGame createGame) {
		recruitingGameRepository.save(new RecruitingGame(
				recruitingGameRepository.nextIdentity(),
				new Name(createGame.getName()),
				new Description(createGame.getDescription()),
				new MaximumPlayers(createGame.getMaximumPlayers()),
				securityService.currentUser().getUserId()));
	}

	@Transactional(readOnly = true)
	public RecruitingGame findRecruitingGameByName(String name) {
		return recruitingGameRepository.findByName(new Name(name)).get();
	}

	@Transactional
	public void archiveGame(ArchiveGame archiveGame) {
		Game game = gameRepository.findByGameId(new GameId(archiveGame.getGameId())).get();
		gameRepository.delete(game);
		archivedGameRepository.save(game.archive());
	}

	@Transactional(readOnly = true)
	public ArchivedGame findArchivedGameByName(String name) {
		return archivedGameRepository.findByName(new Name(name)).get();
	}

}