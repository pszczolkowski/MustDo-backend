package pl.pszczolkowski.mustdo.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.pszczolkowski.mustdo.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyAppTest.class)
public class MyAppTest {
	@Autowired
	private ConfigurableApplicationContext	context;

	@Test
	public void test() {
		assertTrue(context.isActive());
	}
}
