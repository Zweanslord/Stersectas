package stersectas.external.persistence;

import org.springframework.data.repository.CrudRepository;

import stersectas.domain.game.Gamer;
import stersectas.domain.game.GamerId;
import stersectas.domain.game.GamerRepository;

public interface HibernateGamerRepository extends CrudRepository<Gamer, GamerId>, GamerRepository {

}
