package stersectas.domain.game;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import stersectas.documentation.HibernateConstructor;

// @MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@Accessors(fluent = true)
@Getter
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
	@AttributeOverride(name = "id", column = @Column(name = "masterId", nullable = false, unique = false))
	private GamerId masterId;

	@HibernateConstructor
	protected Game() {
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

	public boolean isRecruiting() {
		return this instanceof RecruitingGame;
	}

	public boolean isArchived() {
		return this instanceof ArchivedGame;
	}

	public boolean isMaster(Gamer gamer) {
		return masterId.equals(gamer.gamerId());
	}

}