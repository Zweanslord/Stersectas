package stersectas;

import org.junit.After;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import stersectas.configuration.profile.TestingProfile;
import stersectas.stub.TimeTravellingClock;

/**
 * Super class for Integration Tests (ITs) allowing them to make use of the application configuration, database, etc.
 *
 * Classes extending from BaseIT are expected to end in -IT, not -Test. Those ending with -Test are for tests not
 * requiring any application infrastructure.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { StersectasApplication.class })
@WebAppConfiguration
@ActiveProfiles(TestingProfile.PROFILE)
@Ignore
public abstract class BaseIT {

	@Autowired
	private TimeTravellingClock clock;

	@After
	public void breakDown() {
		clock.travelThroughTimeToOrigin();
	}

}
