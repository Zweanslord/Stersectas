package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void create() {
		Player player = new Player("test");

		assertEquals("test", player.name());
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalName() {
		new Master(" ");
	}

}