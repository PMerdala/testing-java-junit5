package pl.pmerdala.springframework.sfgpetclinic.services.springdatajpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springframework.sfgpetclinic.model.Vet;
import pl.pmerdala.springframework.sfgpetclinic.repositories.VetRepository;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetSDJpaService service;

    @Test
    void findAll() {
    }

    @Test
    void findByIdWhenIdNotInRepository() {
        when(vetRepository.findById(1l)).thenReturn(Optional.empty());
        assertThat(service.findById(1L)).isNull();
        verify(vetRepository,times(1)).findById(anyLong());
    }

    @Test
    void findByIdWhenIdInRepository() {
        Vet vet = new Vet(1l,"FirstName","LastName",new HashSet<>());
        when(vetRepository.findById(anyLong())).thenReturn(Optional.of(vet));
        assertThat(service.findById(1L)).isSameAs(vet);
        verify(vetRepository,times(1)).findById(anyLong());
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
        service.deleteById(1l);
        //verify(vetRepository,atLeastOnce()).deleteById(1L);
        verify(vetRepository,atMostOnce()).deleteById(1L);
        //verify(vetRepository,times(1)).deleteById(1L);
    }
}