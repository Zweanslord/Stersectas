package stersectas.domain.game;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import stersectas.documentation.HibernateConstructor;
import stersectas.domain.user.UserId;

// @MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Game {

	@EmbeddedId
	private GameId gameId;

	@Embedded
	private Name name;

	@Embedded
	private Description description;

	@Embedded
	private MaximumPlayers maximumPlayers;

	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "masterId", nullable = false, unique = true))
	private UserId masterId;

	@HibernateConstructor
	protected Game() {
	}

	protected Game(
			GameId gameId,
			Name name,
			Description description,
			MaximumPlayers maximumPlayers,
			UserId masterId) {
		this.gameId = gameId;
		this.name = name;
		this.description = description;
		this.maximumPlayers = maximumPlayers;
		this.masterId = masterId;
	}

	protected void rename(Name name) {
		this.name = name;
	}

	protected void changeDescription(Description description) {
		this.description = description;
	}

	protected void adjustMaximumOfPlayers(MaximumPlayers maximumPlayers) {
		this.maximumPlayers = maximumPlayers;
	}

	public abstract ArchivedGame archive();

	public GameId gameId() {
		return gameId;
	}

	public Name name() {
		return name;
	}

	public Description description() {
		return description;
	}

	public MaximumPlayers maximumPlayers() {
		return maximumPlayers;
	}

	public UserId masterId() {
		return masterId;
	}

}