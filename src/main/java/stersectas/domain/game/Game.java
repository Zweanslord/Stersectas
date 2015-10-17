package stersectas.domain.game;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import stersectas.documentation.HibernateConstructor;

/**
 * A story where multiple participants can play the events and adventure together.
 * A {@link Master} organizes the game and recruits {@link Player}s.
 */
@Entity
public class Game {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Name name;

	@Embedded
	private Description description;

	private MaximumPlayers maximumPlayers;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "userId.id", column = @Column(name = "masterId", nullable = false, unique = true)),
			@AttributeOverride(name = "name.name", column = @Column(name = "masterName", nullable = false))
	})
	private Master master;

	@Column(nullable = false)
	private GameState state;

	@HibernateConstructor
	protected Game() {
	}

	// TODO add players
	// @ElementCollection
	// @CollectionTable
	// private Set<Player> players;

	public Game(Name name, Description description, MaximumPlayers maximumPlayers, Master master) {
		this.name = name;
		this.description = description;
		this.maximumPlayers = maximumPlayers;
		this.master = master;
		// players = new HashSet<>();
		state = GameState.PREPARING;
	}

	public void adjustMaximumOfPlayers(MaximumPlayers maximumPlayers) {
		if (state.afterStart()) {
			throw new IllegalStateException("Game state does not allow modification of player maximum.");
		}
		this.maximumPlayers = maximumPlayers;
	}

	public void changeName(Name name) {
		if (state.stopped()) {
			throw new IllegalStateException("Can not change name of stopped game.");
		}
		this.name = name;
	}

	public void changeDescription(Description description) {
		if (state.stopped()) {
			throw new IllegalStateException("Can not change description of stopped game.");
		}
		this.description = description;
	}

	public void openRecruitment() {
		if (state.stopped()) {
			throw new IllegalStateException("Can not open for recruitment anymore.");
		}
		state = GameState.RECRUITING;
	}

	public void start() {
		// TODO add player requirement
		state = GameState.RUNNING;
	}

	public void finish() {
		// TODO add running requirement
		state = GameState.FINISHED;
	}

	public void archive() {
		state = GameState.ARCHIVED;
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

	public Master master() {
		return master;
	}

	public GameState state() {
		return state;
	}

}