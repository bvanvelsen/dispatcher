package be.dispatcher.domain.people;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.Ticks;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public interface Person extends Ticks {
}
