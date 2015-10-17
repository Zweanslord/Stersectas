package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NameTest {

	@Test
	public void create() {
		Name name = new Name("testname");
		assertEquals("testname", name.name());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createEmptyName() {
		new Name(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTooLongName() {
		new Name("ThisIsAWayTooLongNameExceedingTheLimitForNames");
	}

	@Test
	public void equals() {
		assertEquals(new Name("test"), new Name("test"));
	}

}