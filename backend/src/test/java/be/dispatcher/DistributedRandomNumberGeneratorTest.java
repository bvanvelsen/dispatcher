package be.dispatcher;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DistributedRandomNumberGeneratorTest {

	@Test
	public void expectRandomNumerInRange() {
		Map<Integer, Integer> distributonMap = new HashMap<>();
		distributonMap.put(1, 0);
		distributonMap.put(2, 0);
		distributonMap.put(3, 0);
		distributonMap.put(4, 0);
		for (int i = 0; i < 10000; i++) {
			DistributedRandomNumberGenerator distributedRandomNumberGenerator = new DistributedRandomNumberGenerator();
			distributedRandomNumberGenerator.addNumber(1, 0.8);
			distributedRandomNumberGenerator.addNumber(2, 0.1);
			distributedRandomNumberGenerator.addNumber(3, 0.07);
			distributedRandomNumberGenerator.addNumber(4, 0.03);
			int distributedRandomNumber = distributedRandomNumberGenerator.getDistributedRandomNumber();
			distributonMap.put(distributedRandomNumber, distributonMap.get(distributedRandomNumber) + 1);
		}
		assertThat(distributonMap.get(1)).isBetween(7500, 8500);
		assertThat(distributonMap.get(2)).isBetween(500, 1500);
		assertThat(distributonMap.get(3)).isBetween(450, 950);
		assertThat(distributonMap.get(4)).isBetween(200, 400);
	}

}