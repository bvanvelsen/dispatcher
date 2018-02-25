package be.dispatcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DispatcherProperties {

	@Value("${worldsize}")
	private String worldSize;

	public String getWorldSize() {
		return worldSize;
	}
}
