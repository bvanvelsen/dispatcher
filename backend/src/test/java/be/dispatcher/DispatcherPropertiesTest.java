package be.dispatcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DispatcherPropertiesTest extends DispatcherSpringJunit4Test{

	@Autowired
	private DispatcherProperties dispatcherProperties;

	@Test
	public void expectValuesInjectedFromPropertiesFile() {
		assertThat(dispatcherProperties.getWorldBoundingboxMinLat()).isEqualTo(50.8683593937);
		assertThat(dispatcherProperties.getWorldBoundingboxMaxLat()).isEqualTo(51.0024728092);
		assertThat(dispatcherProperties.getWorldBoundingboxMinLon()).isEqualTo(5.1794953728);
		assertThat(dispatcherProperties.getWorldBoundingboxMaxLon()).isEqualTo(5.4558704844);
	}

}