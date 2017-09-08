package guru.springframework.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

	@Autowired UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Test
	public void testByDescriptionTeaspoon() {
		
		String teaspoonDescription = "Teaspoon";
		
		Optional<UnitOfMeasure> teaspoonUnitOfMeasure = unitOfMeasureRepository.findByDescription(teaspoonDescription);
		assertEquals(teaspoonDescription, teaspoonUnitOfMeasure.get().getDescription());
		
	}
	
	@Test
	public void testByDescriptionCup() {
		
		String cupDescription = "Cup";
		
		Optional<UnitOfMeasure> cupUnitOfMeasure = unitOfMeasureRepository.findByDescription(cupDescription);
		assertEquals(cupDescription, cupUnitOfMeasure.get().getDescription());
		
	}

}
