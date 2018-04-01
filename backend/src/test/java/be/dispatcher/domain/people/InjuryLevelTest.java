package be.dispatcher.domain.people;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class InjuryLevelTest {

	@Test
	public void InjuryLevelMinorRequiresNoMUG() {
		assertThat(InjuryLevel.MINOR.requiresMUG()).isFalse();
	}

	@Test
	public void InjuryLevelMediocreRequiresNoMUG() {
		assertThat(InjuryLevel.MEDIOCRE.requiresMUG()).isFalse();
	}

	@Test
	public void InjuryLevelSevereRequiresMUG() {
		assertThat(InjuryLevel.SEVERE.requiresMUG()).isTrue();
	}

}