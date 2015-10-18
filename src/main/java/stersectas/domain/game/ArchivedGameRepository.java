package stersectas.domain.game;

public interface ArchivedGameRepository {

	public ArchivedGame save(ArchivedGame recruitingGame);

	public ArchivedGame findByName(Name name);

}
