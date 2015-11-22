package stersectas.application.game;

import java.util.List;

import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stersectas.domain.game.ArchivedGame;
import stersectas.domain.game.ArchivedGameRepository;
import stersectas.domain.game.Description;
import stersectas.domain.game.GameId;
import stersectas.domain.game.GameRepository;
import stersectas.domain.game.MaximumPlayers;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGame;
import stersectas.domain.game.RecruitingGameRepository;

@Service
public class GameService {

	private final GameRepository gameRepository;
	private final RecruitingGameRepository recruitingGameRepository;
	private final ArchivedGameRepository archivedGameRepository;
	private final GamerService gamerService;

	@Autowired
	public GameService(
			GameRepository gameRepository,
			RecruitingGameRepository recruitingGameRepository,
			ArchivedGameRepository archivedGameRepository,
			GamerService gamerService) {
		this.gameRepository = gameRepository;
		this.recruitingGameRepository = recruitingGameRepository;
		this.archivedGameRepository = archivedGameRepository;
		this.gamerService = gamerService;
	}

	@Transactional
	public void createGame(CreateGame createGame) {
		if (!isGameNameAvailable(createGame.getName())) {
			throw new IllegalArgumentException("Name not available");
		}
		val master = gamerService.findGamerById(createGame.getMasterId());
		val game = master.createRecruitingGame(
				recruitingGameRepository.nextIdentity(),
				new Name(createGame.getName()),
				new Description(createGame.getDescription()),
				new MaximumPlayers(createGame.getMaximumPlayers()));
		master.organizeGame(game);
		recruitingGameRepository.save(game);
		gamerService.save(master);
	}

	@Transactional(readOnly = true)
	public RecruitingGame findRecruitingGameByName(String name) {
		return recruitingGameRepository.findByName(new Name(name)).get();
	}

	@Transactional
	public void renameGame(RenameGame renameGame) {
		if (!isGameNameAvailable(renameGame.getName())) {
			throw new RuntimeException("Name not available");
		}
		// TODO check if this works too
		// recruitingGameRepository.findByGameId(new GameId(renameGame.getGameId())).get()
		// .rename(new Name(renameGame.getName()));
		val recruitingGame = recruitingGameRepository.findByGameId(new GameId(renameGame.getGameId())).get();
		recruitingGame.rename(new Name(renameGame.getName()));
	}

	@Transactional
	public void changeGameMaximumPlayers(ChangeGameMaximumPlayers changeGamePlayerMaximum) {
		RecruitingGame recruitingGame = recruitingGameRepository.findByGameId(
				new GameId(changeGamePlayerMaximum.getGameId())).get();
		recruitingGame.adjustMaximumOfPlayers(new MaximumPlayers(changeGamePlayerMaximum.getMaximumPlayers()));
	}

	@Transactional
	public void changeGameDescription(ChangeGameDescription changeGameDescription) {
		val recruitingGame = recruitingGameRepository.findByGameId(
				new GameId(changeGameDescription.getGameId())).get();
		recruitingGame.changeDescription(new Description(changeGameDescription.getDescription()));
	}

	@Transactional
	public void archiveGame(ArchiveGame archiveGame) {
		val game = gameRepository.findByGameId(new GameId(archiveGame.getGameId())).get();
		gameRepository.delete(game);
		archivedGameRepository.save(game.archive());
	}

	@Transactional(readOnly = true)
	public ArchivedGame findArchivedGameByName(String name) {
		return archivedGameRepository.findByName(new Name(name)).get();
	}

	@Transactional(readOnly = true)
	public boolean isGameNameAvailable(String gameName) {
		return gameRepository.countByName(new Name(gameName)) == 0;
	}

	@Transactional(readOnly = true)
	public List<RecruitingGame> findAllRecruitingGames() {
		return recruitingGameRepository.findAllByOrderByNameAsc();
	}

}