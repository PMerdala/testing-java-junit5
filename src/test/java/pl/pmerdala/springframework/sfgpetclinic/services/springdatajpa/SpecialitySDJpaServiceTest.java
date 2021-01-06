package pl.pmerdala.springframework.sfgpetclinic.services.springdatajpa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springframework.sfgpetclinic.model.Speciality;
import pl.pmerdala.springframework.sfgpetclinic.repositories.SpecialityRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        verify(specialityRepository, times(1)).findById(anyLong());
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

    @Test
    void DoThrowTest() {
        doThrow(new RuntimeException("Boom")).when(specialityRepository).findById(anyLong());
        assertThrows(RuntimeException.class,()->service.findById(1L));
        verify(specialityRepository).findById(anyLong());
    }

    @Test
    void GivenThrowTest() {
        given(specialityRepository.findById(anyLong())).willThrow(new RuntimeException("Boom"));
        assertThrows(RuntimeException.class,()->service.findById(1L));
        then(specialityRepository).should(times(1)).findById(anyLong());
    }

    @Test
    void willThrowGiwenTest() {
        willThrow(new RuntimeException("boom")).given(specialityRepository).findById(anyLong());
        assertThrows(RuntimeException.class,()->service.findById(1L));
        then(specialityRepository).should(atLeast(1)).findById(anyLong());
    }

    @Test
    void saveLambdaTestMatch() {
        String description = "MATCH_ME";
        final Speciality speciality = new Speciality();
        speciality.setDescription(description);

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        given(specialityRepository.save(argThat(argument-> argument.getDescription().equals(description))))
                .willReturn(savedSpeciality);

        Speciality returnSpeciality = service.save(speciality);
        assertThat(returnSpeciality.getId()).isEqualTo(1L);
        then(specialityRepository).should().save(argThat(arg->arg.getDescription().equals(description)));
    }
}