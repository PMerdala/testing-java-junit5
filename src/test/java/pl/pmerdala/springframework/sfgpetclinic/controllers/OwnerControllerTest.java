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

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @AfterEach
    void tearDown() {
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    void processCreationFormNotCorrectDataForOwnerShouldReturnCreateorUpdateOwnerForm() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        final String viewName = controller.processCreationForm(owner, bindingResult);
        //then
        assertThat(viewName).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    void processCreationFormNotCorrectDataForOwnerShouldInvokeHasError() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        final String viewName = controller.processCreationForm(owner, bindingResult);
        //then
        then(bindingResult).should().hasErrors();
    }

    @Test
    void processCreationFormNotCorrectDataForOwnerShouldNotInteractiveWithService() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        final String viewName = controller.processCreationForm(owner, bindingResult);
        //then
        then(service).shouldHaveNoInteractions();
    }

    @Test
    void processCreationFormCorrectDataForOwnerShouldReturnRedirectOwners5() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);
        //when
        final String viewName = controller.processCreationForm(owner, bindingResult);
        //then
        assertThat(viewName).isEqualTo(REDIRECT_OWNERS_5);
    }

    @Test
    void processCreationFormCorrectDataForOwnerInvokeHasError() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);
        //when
        final String viewName = controller.processCreationForm(owner, bindingResult);
        //then
        then(bindingResult).should().hasErrors();
    }

    @Test
    void processCreationFormCorrectDataForOwnerShouldInteractiveWithService() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);
        //when
        final String viewName = controller.processCreationForm(owner, bindingResult);
        //then
        then(service).should().save(any());
    }
}