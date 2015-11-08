package stersectas.application.game;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stersectas.domain.game.Game;
import stersectas.domain.game.GameId;
import stersectas.domain.game.GameRepository;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGameRepository;
import stersectas.domain.user.UserRepository;

@Service
public class GameQueryService {

	private final RecruitingGameRepository recruitingGameRepository;
	private final GameRepository gameRepository;
	private final UserRepository userRepository;

	@Autowired
	public GameQueryService(
			RecruitingGameRepository recruitingGameRepository,
			GameRepository gameRepository,
			UserRepository userRepository) {
		this.recruitingGameRepository = recruitingGameRepository;
		this.gameRepository = gameRepository;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public List<ListedGame> listRecruitingGames() {
		return convertToListedGames(recruitingGameRepository.findAllByOrderByNameAsc());
	}

	private List<ListedGame> convertToListedGames(List<? extends Game> games) {
		return games.stream()
				.map(listedGameConversion().apply(masterNameRetriever().apply(userRepository)))
				.collect(Collectors.toList());
	}

	private static Function<Function<Game, Name>, Function<Game, ListedGame>> listedGameConversion() {
		return masterNameRetriever -> game -> new ListedGame(game, masterNameRetriever.apply(game));
	}

	private static Function<UserRepository, Function<Game, Name>> masterNameRetriever() {
		return userRepository -> game -> new Name(userRepository.findByUserId(game.masterId()).get().getUsername());
	}

	@Transactional(readOnly = true)
	public DetailedGame findDetailedGameById(String gameId) {
		return gameRepository
				.findByGameId(new GameId(gameId))
				.map(detailedGameConversion().apply(masterNameRetriever().apply(userRepository)))
				.orElseThrow(() -> new GameNotFoundException());
	}

	private static Function<Function<Game, Name>, Function<Game, DetailedGame>> detailedGameConversion() {
		return masterNameRetriever -> game -> new DetailedGame(game, masterNameRetriever.apply(game));
	}

}
