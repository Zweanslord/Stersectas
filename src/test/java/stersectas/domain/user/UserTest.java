package stersectas.domain.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTest {

	@Test
	public void newUserIsDisabled() {
		User user = createTestUser();

		assertTrue(user.isDisabled());
		assertFalse(user.isEnabled());
	}

	@Test
	public void enableUser() {
		User user = createTestUser();
		user.enable();

		assertTrue(user.isEnabled());
		assertFalse(user.isDisabled());
	}

	@Test
	public void enableEnabledUser() {
		User user = createTestUser();
		user.enable();
		user.enable();

		assertTrue(user.isEnabled());
		assertFalse(user.isDisabled());
	}

	@Test
	public void disableUser() {
		User user = createTestUser();
		user.enable();
		user.disable();

		assertTrue(user.isDisabled());
		assertFalse(user.isEnabled());
	}

	@Test
	public void disableDisabledUser() {
		User user = createTestUser();
		user.disable();

		assertTrue(user.isDisabled());
		assertFalse(user.isEnabled());
	}

	@Test(expected = IllegalStateException.class)
	public void cannotPromoteDisabledUser() {
		User user = createTestUser();
		user.promoteToAdministrator();
	}

	@Test
	public void promoteToAdministrator() {
		User user = createTestUser();
		user.enable();
		user.promoteToAdministrator();

		assertTrue(user.isAdministrator());
	}

	@Test
	public void promoteAdministratorToAdministrator() {
		User user = createTestUser();
		user.enable();
		user.promoteToAdministrator();
		user.promoteToAdministrator();

		assertTrue(user.isAdministrator());
	}

	@Test(expected = IllegalStateException.class)
	public void cannotDemoteDisabledUser() {
		User user = createTestUser();
		user.demoteToUser();
	}

	@Test
	public void demoteUser() {
		User user = createTestUser();
		user.enable();
		user.promoteToAdministrator();
		user.demoteToUser();

		assertFalse(user.isAdministrator());
	}

	@Test
	public void demoteRegularUser() {
		User user = createTestUser();
		user.enable();
		user.demoteToUser();

		assertFalse(user.isAdministrator());
	}

	@Test
	public void setNewPassword() {
		User user = createTestUser();
		user.enable();
		user.setPassword("12345");

		assertTrue("12345".equals(user.getPassword()));
	}

	@Test(expected = IllegalStateException.class)
	public void cannotSetPasswordOfDisabledUser() {
		User user = createTestUser();
		user.setPassword("12345");
	}

	private static User createTestUser() {
		return new User(
				new UserId("12345678901234567890"),
				"test",
				"test@test.com",
				"password");
	}

}