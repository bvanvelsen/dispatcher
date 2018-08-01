package be.dispatcher.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;

import be.dispatcher.DispatcherSpringJunit4Test;

public class TimeUtilTest extends DispatcherSpringJunit4Test {

	@Autowired
	private TimeUtil timeUtil;

	@Test
	@Repeat(10)
	public void expectRandomAlarmedTimeIsBetweenGivenValues() {
		int randomTime = timeUtil.randomlyGenerateSecondsInAlarmedStatus();
		assertThat(randomTime).isBetween(TimeUtil.MINIMUM_ALARM_TIME, TimeUtil.MAX_ALARM_TIME);
	}

}