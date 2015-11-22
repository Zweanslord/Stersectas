package stersectas.application.game;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stersectas.domain.game.ArchivedGameRepository;
import stersectas.domain.game.Game;
import stersectas.domain.game.GameId;
import stersectas.domain.game.GameRepository;
import stersectas.domain.game.Gamer;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGameRepository;

@Service
public class GameQueryService {

	private final RecruitingGameRepository recruitingGameRepository;
	private final ArchivedGameRepository archivedGameRepository;
	private final GameRepository gameRepository;
	private final GamerService gamerService;

	@Autowired
	public GameQueryService(
			RecruitingGameRepository recruitingGameRepository,
			ArchivedGameRepository archivedGameRepository,
			GameRepository gameRepository,
			GamerService gamerService) {
		this.recruitingGameRepository = recruitingGameRepository;
		this.archivedGameRepository = archivedGameRepository;
		this.gameRepository = gameRepository;
		this.gamerService = gamerService;
	}

	@Transactional(readOnly = true)
	public List<ListedGame> listRecruitingGames() {
		return convertToListedGames(recruitingGameRepository.findAllByOrderByNameAsc());
	}

	@Transactional(readOnly = true)
	public List<ListedGame> listArchivedGames() {
		return convertToListedGames(archivedGameRepository.findAllByOrderByNameAsc());
	}

	private List<ListedGame> convertToListedGames(List<? extends Game> games) {
		return games.stream()
				.map(listedGameConversion().apply(masterNameRetriever().apply(gamerService)))
				.collect(Collectors.toList());
	}

	private static Function<Function<Game, Name>, Function<Game, ListedGame>> listedGameConversion() {
		return masterNameRetriever -> game -> new ListedGame(game, masterNameRetriever.apply(game));
	}

	private static Function<GamerService, Function<Game, Name>> masterNameRetriever() {
		return gamerService -> game -> gamerService.findNameByGamerId(game.masterId());
	}

	@Transactional(readOnly = true)
	public DetailedGame findDetailedGameById(String gameId) {
		return detailedGameConversion()
				.apply(masterNameRetriever()
						.apply(gamerService))
				.apply(findGameById(gameId));
	}

	private static Function<Function<Game, Name>, Function<Game, DetailedGame>> detailedGameConversion() {
		return masterNameRetriever -> game -> new DetailedGame(game, masterNameRetriever.apply(game));
	}

	private Game findGameById(String gameId) {
		return gameRepository.findByGameId(new GameId(gameId)).orElseThrow(() -> new GameNotFoundException());
	}

	@Transactional(readOnly = true)
	public boolean isCurrentGamerTheMasterOfGame(String gameId) {
		return isGamerTheMasterOfGameFunction().apply(gamerService.currentGamer()).apply(findGameById(gameId));
	}

	@Transactional(readOnly = true)
	public boolean canCurrentGamerMakeChanges(String gameId) {
		return recruitingGameRepository.findByGameId(new GameId(gameId))
				.map(isGamerTheMasterOfGameFunction().apply(gamerService.currentGamer()))
				.orElse(false);
	}

	public Function<Gamer, Function<Game, Boolean>> isGamerTheMasterOfGameFunction() {
		return gamer -> game -> game.isMaster(gamer);
	}

}