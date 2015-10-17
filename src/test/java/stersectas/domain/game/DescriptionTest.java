package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DescriptionTest {

	@Test
	public void create() {
		Description description = new Description("testdescription");
		assertEquals("testdescription", description.description());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createEmptyDescription() {
		new Description(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTooLongDescription() {
		new Description(
				"Lorem ipsum dolor sit amet, alienum delicata eu nec. Dolorem lobortis duo ut, ne facilisi consulatu intellegat eos, cu est vero utamur appareat. Qui quem minim definitiones ex, sea populo docendi cu, sea mollis dolorum ne. Delectus conclusionemque per eu, duis quando nominavi ex est. Sit sint honestatis an, nec no homero accumsan sensibus, an sed dicam salutatus maiestatis. Lorem ipsum dolor sit amet, alienum delicata eu nec. Dolorem lobortis duo ut, ne facilisi consulatu intellegat eos, cu est vero utamur appareat. Qui quem minim definitiones ex, sea populo docendi cu, sea mollis dolorum ne. Delectus conclusionemque per eu, duis quando nominavi ex est. Sit sint honestatis an, nec no homero accumsan sensibus, an sed dicam salutatus maiestatis. Lorem ipsum dolor sit amet, alienum delicata eu nec. Dolorem lobortis duo ut, ne facilisi consulatu intellegat eos, cu est vero utamur appareat. Qui quem minim definitiones ex, sea populo docendi cu, sea mollis dolorum ne. Delectus conclusionemque per eu, duis quando nominavi ex est. Sit sint honestatis an, nec no homero accumsan sensibus, an sed dicam salutatus maiestatis.");
	}

	@Test
	public void equals() {
		assertEquals(new Description("test"), new Description("test"));
	}

}