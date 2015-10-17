package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import stersectas.domain.user.UserId;

public class PlayerTest {

	@Test
	public void create() {
		Player player = new Player(
				new UserId("0123456789"),
				new Name("test"));

		assertEquals(new UserId("0123456789"), player.userId());
		assertEquals(new Name("test"), player.name());
	}

	@Test
	public void equals() {
		assertEquals(
				new Player(
						new UserId("0123456789"),
						new Name("test")),
				new Player(
						new UserId("0123456789"),
						new Name("test")));
	}

}