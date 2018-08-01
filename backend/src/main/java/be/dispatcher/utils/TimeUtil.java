package be.dispatcher.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class TimeUtil {

	static final int MINIMUM_ALARM_TIME = 90;
	static final int MAX_ALARM_TIME = 3 * 60;

	public int randomlyGenerateSecondsInAlarmedStatus() {
		return new Random().ints(MINIMUM_ALARM_TIME, MAX_ALARM_TIME).findFirst().getAsInt();
	}
}
