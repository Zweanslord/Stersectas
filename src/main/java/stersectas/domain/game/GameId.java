package stersectas.domain.game;

import javax.persistence.Embeddable;

import stersectas.documentation.HibernateConstructor;
import stersectas.domain.common.Id;

@Embeddable
public class GameId extends Id {

	private static final long serialVersionUID = 1L;

	@HibernateConstructor
	protected GameId() {
	}

	public GameId(String id) {
		super(id);
	}

}