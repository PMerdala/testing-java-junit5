package pl.pmerdala.springframework.sfgpetclinic.services.springdatajpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springframework.sfgpetclinic.model.Speciality;
import pl.pmerdala.springframework.sfgpetclinic.repositories.SpecialityRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialityRepository specialityRepository;

    @InjectMocks
    SpecialitySDJpaService service;


    @AfterEach
    void tearDown() {
        then(specialityRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void findById() {
        Speciality speciality = new Speciality();
        when(specialityRepository.findById(anyLong()))
                .thenReturn(Optional.of(speciality));
        Speciality foundSpeciality = service.findById(1L);
        assertThat(foundSpeciality).isNotNull();
        verify(specialityRepository,times(1)).findById(anyLong());
    }

    @Test
    void findByIdBDD() {
        Speciality speciality = new Speciality();
        given(specialityRepository.findById(anyLong()))
                .willReturn(Optional.of(speciality));

        Speciality foundSpeciality = service.findById(1L);
        assertThat(foundSpeciality).isNotNull();
        then(specialityRepository).should(times(1)).findById(1L);
    }
}