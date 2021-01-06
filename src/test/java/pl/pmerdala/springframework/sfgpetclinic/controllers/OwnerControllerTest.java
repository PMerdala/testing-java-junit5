package pl.pmerdala.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.BindingResult;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.Model;
import pl.pmerdala.springframework.sfgpetclinic.model.Owner;
import pl.pmerdala.springframework.sfgpetclinic.services.OwnerService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String OWNERS_FIND_OWNERS = "owners/findOwners";
    public static final String OWNERS_OWNERS_LIST = "owners/ownersList";
    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @AfterEach
    void tearDown() {
        then(service).shouldHaveNoMoreInteractions();
        then(model).shouldHaveNoMoreInteractions();
    }

    @Test
    void processCreationFormNotCorrectDataForOwnerShouldReturnCreateOrUpdateOwnerForm() {
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
        controller.processCreationForm(owner, bindingResult);
        //then
        then(bindingResult).should().hasErrors();
    }

    @Test
    void processCreationFormNotCorrectDataForOwnerShouldNotInteractiveWithService() {
        //given
        final Owner owner = new Owner(5L, "name", "subname");
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        controller.processCreationForm(owner, bindingResult);
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
        controller.processCreationForm(owner, bindingResult);
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
        controller.processCreationForm(owner, bindingResult);
        //then
        then(service).should().save(any());
    }

    @Test
    void processFindFormNullLastNameShouldInvokeServiceOnlyWithWildcard() {
        //given
        Owner owner = new Owner(null, null, null);
        //when
        given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(new ArrayList<>());
        controller.processFindForm(owner, bindingResult, model);
        //then
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%");
    }

    @Test
    void processFindFormEmptyLastNameShouldInvokeServiceOnlyWithWildcard() {
        //given
        Owner owner = new Owner(null, null, "");
        //when
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(service.findAllByLastNameLike(captor.capture())).willReturn(new ArrayList<>());
        controller.processFindForm(owner, bindingResult, model);
        //then
        assertThat(captor.getValue()).isEqualTo("%");
    }

    @Test
    void processFindFormOnlyWhitespaceLastNameShouldInvokeServiceOnlyWithWildcard() {
        //given
        Owner owner = new Owner(null, null, "   ");
        //when
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(service.findAllByLastNameLike(captor.capture())).willReturn(new ArrayList<>());
        controller.processFindForm(owner, bindingResult, model);
        //then
        assertThat(captor.getValue()).isEqualTo("%");
    }

    @Test
    void processFindFormLastNameTestShouldInvokeService() {
        //given
        Owner owner = new Owner(null, null, "Test");
        //when
        controller.processFindForm(owner, bindingResult, model);
        //then
        then(service).should().findAllByLastNameLike(argThat(arg -> arg.equals("%Test%")));
    }

    @Test
    void processFindFormLastNameWithWhitespaceTestShouldInvokeService() {
        //given
        Owner owner = new Owner(null, null, " Test  ");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(service.findAllByLastNameLike(captor.capture())).willReturn(new ArrayList<>());
        //when
        controller.processFindForm(owner, bindingResult, model);
        //then
        assertThat(captor.getValue()).isEqualTo("%Test%");
    }

    @Test
    void processFindFormNotOwnerFoundShouldGoToFindsView() {
        //given
        Owner owner = new Owner(null, null, "Test");
        //when
        String viewName = controller.processFindForm(owner, bindingResult, null);
        //then
        then(service).should().findAllByLastNameLike(any());
        assertThat(viewName).isEqualTo(OWNERS_FIND_OWNERS);
    }

    @Test
    void processFindFormOneOwnerFoundShouldRedirectToOwnerPage() {
        //given
        Owner owner = new Owner(null, null, "Test");
        prepareServiceToFindByName();
        //when
        String viewName = controller.processFindForm(owner, null, null);
        //then
        then(service).should().findAllByLastNameLike(any());
        assertThat(viewName).isEqualTo(REDIRECT_OWNERS_5);
        then(model).shouldHaveNoInteractions();
    }

    @Test
    void processFindFormTwoOwnerFoundShouldGoOwnerList() {
        //given
        Owner owner = new Owner(null, null, "Test2");
        prepareServiceToFindByName();
        //when
        String viewName = controller.processFindForm(owner, null, model);
        //then
        then(service).should().findAllByLastNameLike(any());
        assertThat(viewName).isEqualTo(OWNERS_OWNERS_LIST);
        then(model).should().addAttribute(eq("selections"), any(List.class));
    }

    private void prepareServiceToFindByName() {
        given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willAnswer(invocationOnMock -> {
            List<Owner> findOwners = new ArrayList<>();
            if ("%Test%".equals(invocationOnMock.getArgument(0))) {
                findOwners.add(new Owner(5L, "Test", "Test"));
            } else if ("%Test2%".equals(invocationOnMock.getArgument(0))) {
                findOwners.add(new Owner(5L, "Test", "Test"));
                findOwners.add(new Owner(1L, "Pre", "PretestSub"));
            }
            return findOwners;
        });
    }
}