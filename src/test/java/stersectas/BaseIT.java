package stersectas;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Super class for Integration Tests (ITs) allowing them to make use of the application configuration, database, etc.
 *
 * Classes extending from BaseIT are expected to end in -IT, not -Test. Those ending with -Test are for tests not
 * requiring any application infrastructure.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StersectasApplication.class)
@WebAppConfiguration
@Ignore
public class BaseIT {

}
