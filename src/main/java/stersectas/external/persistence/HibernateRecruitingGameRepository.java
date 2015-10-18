package stersectas.external.persistence;

import org.springframework.data.repository.CrudRepository;

import stersectas.domain.game.RecruitingGame;
import stersectas.domain.game.RecruitingGameRepository;

public interface HibernateRecruitingGameRepository extends CrudRepository<RecruitingGame, Long>,
		RecruitingGameRepository {

}