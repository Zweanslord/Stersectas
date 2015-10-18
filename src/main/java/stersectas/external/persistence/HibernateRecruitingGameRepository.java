package stersectas.external.persistence;

import org.springframework.data.repository.CrudRepository;

import stersectas.domain.game.GameId;
import stersectas.domain.game.RecruitingGame;
import stersectas.domain.game.RecruitingGameRepository;

public interface HibernateRecruitingGameRepository extends CrudRepository<RecruitingGame, GameId>,
		RecruitingGameRepository {

}