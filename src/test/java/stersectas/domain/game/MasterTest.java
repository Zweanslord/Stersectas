package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MasterTest {

	@Test
	public void create() {
		Master master = new Master("test");

		assertEquals("test", master.name());
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalName() {
		new Master(" ");
	}

}
