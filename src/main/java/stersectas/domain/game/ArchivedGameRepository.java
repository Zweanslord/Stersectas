package stersectas.domain.game;

import java.util.List;
import java.util.Optional;

public interface ArchivedGameRepository {

	public ArchivedGame save(ArchivedGame recruitingGame);

	public Optional<ArchivedGame> findByName(Name name);

	public List<ArchivedGame> findAllByOrderByNameAsc();

}
