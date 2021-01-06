package pl.pmerdala.springframework.sfgpetclinic.services.springdatajpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springframework.sfgpetclinic.model.Visit;
import pl.pmerdala.springframework.sfgpetclinic.repositories.VisitRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository repository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void whenNotVisitInRepositoryFindAllReturnEmptySet() {
        assertThat(service.findAll()).isNotNull().hasSize(0);
        verify(repository,times(1)).findAll();
    }

    @Test
    void whenVisitInRepositoryFindAllReturnSet() {
        when(repository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(new Visit())));
        assertThat(service.findAll()).isNotNull().hasSize(1);
        verify(repository,times(1)).findAll();
    }
    @Test
    void WhenNotVisitInrepositoryFindByIdReturnNull() {
        when(repository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(new Visit())));
        assertThat(service.findAll()).isNotNull().hasSize(1);
        verify(repository,times(1)).findAll();
    }

    @Test
    void save() {
        Visit visit = new Visit();
        when(repository.save(any(Visit.class))).thenReturn(visit);
        Visit saveVisit = service.save(visit);
        verify(repository).save(any(Visit.class));
        assertThat(saveVisit).isNotNull();
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        service.delete(visit);
        verify(repository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(repository).deleteById(1L);
    }
}