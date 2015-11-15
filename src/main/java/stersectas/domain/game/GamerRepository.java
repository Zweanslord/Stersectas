package stersectas.domain.game;

import java.util.Optional;

public interface GamerRepository {

	Gamer save(Gamer gamer);

	Optional<Gamer> findByGamerId(GamerId gamerId);

}