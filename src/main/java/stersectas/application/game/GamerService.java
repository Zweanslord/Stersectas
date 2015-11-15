package stersectas.application.game;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stersectas.application.security.SecurityService;
import stersectas.domain.game.Gamer;
import stersectas.domain.game.GamerId;
import stersectas.domain.game.GamerRepository;
import stersectas.domain.game.Name;
import stersectas.domain.user.User;
import stersectas.domain.user.UserId;
import stersectas.domain.user.UserRepository;

@Service
public class GamerService {

	private final UserRepository userRepository;
	private final SecurityService securityService;
	private final GamerRepository gamerRepository;

	@Autowired
	public GamerService(
			UserRepository userRepository,
			SecurityService securityService,
			GamerRepository gamerRepository) {
		this.userRepository = userRepository;
		this.securityService = securityService;
		this.gamerRepository = gamerRepository;
	}

	public Gamer currentGamer() {
		return findGamerById(securityService.currentUser().getUserId().id());
	}

	@Transactional(readOnly = true)
	public Gamer findGamerById(String gamerId) {
		return gamerRepository.findByGamerId(new GamerId(gamerId))
				.orElseGet(supplyGamerFromUsersFunction().apply(userRepository, new UserId(gamerId)));

	}

	private static BiFunction<UserRepository, UserId, Supplier<Gamer>> supplyGamerFromUsersFunction() {
		return (userRepository, userId) -> () -> userRepository.findByUserId(userId)
				.map(user -> convertToGamer(user))
				.orElseThrow(() -> new GamerNotFoundException());
	}

	private static Gamer convertToGamer(User user) {
		return Gamer.create(new GamerId(user.getUserId().id()));
	}

	@Transactional(readOnly = true)
	public Name findNameByGamerId(GamerId gamerId) {
		return userRepository.findByUserId(new UserId(gamerId.id()))
				.map(user -> new Name(user.getUsername()))
				.orElseThrow(() -> new GamerNotFoundException());
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void save(Gamer gamer) {
		gamerRepository.save(gamer);
	}

}
