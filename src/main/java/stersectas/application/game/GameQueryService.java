package stersectas.application.game;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stersectas.domain.game.Game;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGameRepository;
import stersectas.domain.user.UserId;
import stersectas.domain.user.UserRepository;

@Service
public class GameQueryService {

	private final RecruitingGameRepository recruitingGameRepository;
	private final UserRepository userService;

	@Autowired
	public GameQueryService(RecruitingGameRepository recruitingGameRepository, UserRepository userService) {
		this.recruitingGameRepository = recruitingGameRepository;
		this.userService = userService;
	}

	public List<ListedGame> listRecruitingGames() {
		return convertToListedGames(recruitingGameRepository.findAllByOrderByNameAsc());
	}

	private List<ListedGame> convertToListedGames(List<? extends Game> games) {
		return games.stream()
				.map(convertToListedGameFunction().apply(userService))
				.collect(Collectors.toList());
	}

	private static Function<UserRepository, Function<Game, ListedGame>> convertToListedGameFunction() {
		return userRepository -> game -> new ListedGame(game, findUserName(userRepository, game.masterId()));
	}

	private static Name findUserName(UserRepository userRepository, UserId userId) {
		return new Name(userRepository.findByUserId(userId).get().getUsername());
	}

}
