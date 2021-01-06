package pl.pmerdala.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.BindingResult;
import pl.pmerdala.springframework.sfgpetclinic.model.Owner;
import pl.pmerdala.springframework.sfgpetclinic.services.OwnerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @AfterEach
    void tearDown() {
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    void processCreationFormNotCorrectDataForOwnerShouldReturnCreateorUpdateOwnerForm() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        final BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        final String result = controller.processCreationForm(owner, bindingResult);
        //then
        assertThat(result).isEqualTo("owners/createOrUpdateOwnerForm");
    }

    @Test
    void processCreationFormNotCorrectDataForOwnerShouldInvokeHasError() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        final BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        final String result = controller.processCreationForm(owner, bindingResult);
        //then
        then(bindingResult).should().hasErrors();
    }

    @Test
    void processCreationFormNotCorrectDataForOwnerShouldNotInteractiveWithService() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        final BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        final String result = controller.processCreationForm(owner, bindingResult);
        //then
        then(service).shouldHaveNoInteractions();
    }

    @Test
    void processCreationFormCorrectDataForOwnerShouldReturnRedirectOwners5() {
        final Owner owner = new Owner(5L, "name", "subname");
        final BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);
        final String result = controller.processCreationForm(owner, bindingResult);
        assertThat(result).isEqualTo("redirect:/owners/5");
    }

    @Test
    void processCreationFormCorrectDataForOwnerInvokeHasError() {
        final Owner owner = new Owner(5L, "name", "subname");
        final BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);
        final String result = controller.processCreationForm(owner, bindingResult);
        then(bindingResult).should().hasErrors();
    }

    @Test
    void processCreationFormCorrectDataForOwnerShouldInteractiveWithService() {
        final Owner owner = new Owner(5L, "name", "subname");
        final BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);
        final String result = controller.processCreationForm(owner, bindingResult);
        then(service).should().save(any());
    }
}