package stersectas.domain.user;

import javax.persistence.Embeddable;

import stersectas.documentation.HibernateConstructor;
import stersectas.domain.common.Id;

@Embeddable
public class UserId extends Id {

	private static final long serialVersionUID = 1L;

	@HibernateConstructor
	protected UserId() {
	}

	public UserId(String id) {
		super(id);
	}

}