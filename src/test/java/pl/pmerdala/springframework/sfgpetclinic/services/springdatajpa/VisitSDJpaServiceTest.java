package pl.pmerdala.springframework.sfgpetclinic.services.springdatajpa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springframework.sfgpetclinic.model.Visit;
import pl.pmerdala.springframework.sfgpetclinic.repositories.VisitRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository repository;

    @InjectMocks
    VisitSDJpaService service;

    @AfterEach
    void tearDown() {
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void whenNotVisitInRepositoryFindAllReturnEmptySet() {
        assertThat(service.findAll()).isNotNull().hasSize(0);
        then(repository).should(times(1)).findAll();
    }

    @Test
    void whenVisitInRepositoryFindAllReturnSet() {
        given(repository.findAll()).willReturn(new ArrayList<>(Arrays.asList(new Visit())));
        assertThat(service.findAll()).isNotNull().hasSize(1);
        then(repository).should(times(1)).findAll();
    }

    @Test
    void WhenNotVisitInrepositoryFindByIdReturnNull() {
        given(repository.findAll()).willReturn(new ArrayList<>(Arrays.asList(new Visit())));
        assertThat(service.findAll()).isNotNull().hasSize(1);
        then(repository).should(times(1)).findAll();
    }

    @Test
    void save() {
        Visit visit = new Visit();
        given(repository.save(any(Visit.class))).willReturn(visit);
        Visit saveVisit = service.save(visit);
        assertThat(saveVisit).isNotNull();
        then(repository).should().save(any(Visit.class));
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        service.delete(visit);
        then(repository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        then(repository).should().deleteById(1L);
    }
}