package stersectas.domain.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdTest {

	@Test
	public void createId() {
		Id id = new TestId("This is a long enough identifier for validation purposes.");
		assertEquals("This is a long enough identifier for validation purposes.", id.id());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTooShortId() {
		new TestId("Too short");
	}

	class TestId extends Id {
		public TestId(String id) {
			super(id);
		}
	}

}
