package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MaximumPlayersTest {

	public void create() {
		MaximumPlayers maximumPlayers = new MaximumPlayers(4);
		assertEquals(4, maximumPlayers.maximumPlayers());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMaximumPlayersWithTooFewPlayers() {
		new MaximumPlayers(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMaximumPlayersWithTooManyPlayers() {
		new MaximumPlayers(10);
	}

}
