package stersectas.application.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import lombok.val;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stersectas.BaseIT;
import stersectas.domain.game.ArchivedGameRepository;
import stersectas.domain.game.Game;
import stersectas.domain.game.GameRepository;
import stersectas.domain.game.GamerRepository;
import stersectas.domain.game.RecruitingGameRepository;

public class GameQueryServiceIT extends BaseIT {

	@Autowired private GamerRepository gamerRepository;
	@Autowired private GameRepository gameRepository;
	@Autowired private RecruitingGameRepository recruitingGameRepository;
	@Autowired private ArchivedGameRepository archivedGameRepository;

	private UserInterfaceStub userInterfaceStub = new UserInterfaceStub();

	@Before
	public void setUp() {
		userInterfaceStub.reset();
	}

	@Test
	@Transactional
	public void currentGamerIsGameMaster() {
		val gamerService = setupGamerService();
		val gameService = setupGameService(gamerService);
		val gameQueryService = setupGameQueryService(gamerService);
		val game = createRecruitingGame(gameService, gamerService.currentGamer().gamerId().id());

		val isMaster = gameQueryService.isCurrentGamerTheMasterOfGame(game.gameId().id());

		assertTrue(isMaster);
	}

	@Test
	@Transactional
	public void currentGamerIsNotGameMaster() {
		val anotherGameMasterId = "ANOTHER_GAME_MASTER_ID";
		userInterfaceStub.addUser(anotherGameMasterId);
		val gamerService = setupGamerService();
		val gameService = setupGameService(gamerService);
		val gameQueryService = setupGameQueryService(gamerService);
		val game = createRecruitingGame(gameService, anotherGameMasterId);

		val isMaster = gameQueryService.isCurrentGamerTheMasterOfGame(game.gameId().id());

		assertFalse(isMaster);
	}

	@Test
	@Transactional
	public void currentGamerCanMakeChanges() {
		val gamerService = setupGamerService();
		val gameService = setupGameService(gamerService);
		val gameQueryService = setupGameQueryService(gamerService);
		val game = createRecruitingGame(gameService, gamerService.currentGamer().gamerId().id());

		val isMaster = gameQueryService.canCurrentGamerMakeChanges(game.gameId().id());

		assertTrue(isMaster);
	}

	@Test
	@Transactional
	public void currentGamerCanNotMakeChanges() {
		val gamerService = setupGamerService();
		val gameService = setupGameService(gamerService);
		val gameQueryService = setupGameQueryService(gamerService);
		val game = createArchivedGame(gameService, gamerService.currentGamer().gamerId().id());

		val isMaster = gameQueryService.canCurrentGamerMakeChanges(game.gameId().id());

		assertFalse(isMaster);
	}

	@Test
	@Transactional
	public void anotherGamerCanNotMakeChanges() {
		val anotherGameMasterId = "ANOTHER_GAME_MASTER_ID";
		userInterfaceStub.addUser(anotherGameMasterId);
		val gamerService = setupGamerService();
		val gameService = setupGameService(gamerService);
		val gameQueryService = setupGameQueryService(gamerService);
		val game = createRecruitingGame(gameService, anotherGameMasterId);

		val isMaster = gameQueryService.canCurrentGamerMakeChanges(game.gameId().id());

		assertFalse(isMaster);
	}

	private GamerService setupGamerService() {
		return new GamerService(userInterfaceStub, gamerRepository);
	}

	private GameService setupGameService(GamerService gamerService) {
		return new GameService(
				gameRepository,
				recruitingGameRepository,
				archivedGameRepository,
				gamerService);
	}

	private GameQueryService setupGameQueryService(GamerService gamerService) {
		return new GameQueryService(
				recruitingGameRepository,
				archivedGameRepository,
				gameRepository,
				gamerService);
	}

	private Game createRecruitingGame(GameService gameService, String masterId) {
		val name = "Test-game";
		gameService.createGame(
				CreateGameTestBuilder.defaultBuilder()
						.name(name)
						.masterId(masterId)
						.build());
		return gameService.findRecruitingGameByName(name);
	}

	private Game createArchivedGame(GameService gameService, String masterId) {
		val game = createRecruitingGame(gameService, masterId);
		gameService.archiveGame(
				new ArchiveGame(game.gameId().id()));
		return gameService.findArchivedGameByName(game.name().name());
	}

}