package stersectas.domain.game;

public interface RecruitingGameRepository {

	public RecruitingGame save(RecruitingGame recruitingGame);

	public RecruitingGame findByName(Name name);

}