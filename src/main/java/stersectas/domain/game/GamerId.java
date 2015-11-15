package stersectas.domain.game;

import javax.persistence.Embeddable;

import stersectas.documentation.HibernateConstructor;
import stersectas.domain.common.Id;

@Embeddable
public class GamerId extends Id {

	private static final long serialVersionUID = 1L;

	@HibernateConstructor
	protected GamerId() {
	}

	public GamerId(String id) {
		super(id);
	}

}