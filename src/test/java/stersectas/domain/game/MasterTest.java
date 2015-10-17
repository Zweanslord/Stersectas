package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import stersectas.domain.user.UserId;

public class MasterTest {

	@Test
	public void create() {
		Master master = new Master(
				new UserId("0123456789"),
				new Name("test"));

		assertEquals(new UserId("0123456789"), master.userId());
		assertEquals(new Name("test"), master.name());
	}

	@Test
	public void equals() {
		assertEquals(
				new Master(
						new UserId("0123456789"),
						new Name("test")),
				new Master(
						new UserId("0123456789"),
						new Name("test")));
	}

}