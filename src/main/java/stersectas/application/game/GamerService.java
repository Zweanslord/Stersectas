package stersectas.application.game;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stersectas.application.user.UserResource;
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

	private final UserResource userResource;
	private final GamerRepository gamerRepository;

	@Autowired
	public GamerService(
			UserResource userResource,
			GamerRepository gamerRepository) {
		this.userResource = userResource;
		this.gamerRepository = gamerRepository;
	}

	public Gamer currentGamer() {
		return findGamerById(userResource.currentUser().getUserId().id());
	}

	@Transactional(readOnly = true)
	public Gamer findGamerById(String gamerId) {
		return gamerRepository.findByGamerId(new GamerId(gamerId))
				.orElseGet(supplyGamerFromUsersFunction().apply(userResource, gamerId));

	}

	private static BiFunction<UserResource, String, Supplier<Gamer>> supplyGamerFromUsersFunction() {
		return (userResource, gamerId) -> () -> userResource.findByUserId(gamerId)
				.map(GamerService::convertToGamer)
				.orElseThrow(GamerNotFoundException::new);
	}

	private static Gamer convertToGamer(User user) {
		return Gamer.create(new GamerId(user.getUserId().id()));
	}

	@Transactional(readOnly = true)
	public Name findNameByGamerId(GamerId gamerId) {
		return userResource.findByUserId(gamerId.id())
				.map(user -> new Name(user.getUsername()))
				.orElseThrow(GamerNotFoundException::new);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void save(Gamer gamer) {
		gamerRepository.save(gamer);
	}

}
