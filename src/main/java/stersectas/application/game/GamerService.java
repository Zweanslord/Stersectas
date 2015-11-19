package stersectas.application.game;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stersectas.application.user.UserInterface;
import stersectas.domain.game.Gamer;
import stersectas.domain.game.GamerId;
import stersectas.domain.game.GamerRepository;
import stersectas.domain.game.Name;
import stersectas.domain.user.User;

/**
 * Single point of access to the User domain, converting Users to Gamers.
 */
@Service
public class GamerService {

	private final UserInterface userInterface;
	private final GamerRepository gamerRepository;

	@Autowired
	public GamerService(
			UserInterface userInterface,
			GamerRepository gamerRepository) {
		this.userInterface = userInterface;
		this.gamerRepository = gamerRepository;
	}

	public Gamer currentGamer() {
		return findGamerById(userInterface.currentUser().getUserId().id());
	}

	@Transactional(readOnly = true)
	public Gamer findGamerById(String gamerId) {
		return gamerRepository.findByGamerId(new GamerId(gamerId))
				.orElseGet(supplyGamerFromUsersFunction().apply(userInterface, gamerId));

	}

	private static BiFunction<UserInterface, String, Supplier<Gamer>> supplyGamerFromUsersFunction() {
		return (userService, gamerId) -> () -> userService.findByUserId(gamerId)
				.map(GamerService::convertToGamer)
				.orElseThrow(GamerNotFoundException::new);
	}

	private static Gamer convertToGamer(User user) {
		return Gamer.create(new GamerId(user.getUserId().id()));
	}

	@Transactional(readOnly = true)
	public Name findNameByGamerId(GamerId gamerId) {
		return userInterface.findByUserId(gamerId.id())
				.map(user -> new Name(user.getUsername()))
				.orElseThrow(GamerNotFoundException::new);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void save(Gamer gamer) {
		gamerRepository.save(gamer);
	}

}
