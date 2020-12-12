package pl.pmerdala.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import pl.pmerdala.springframework.sfgpetclinic.model.Vet;
import pl.pmerdala.springframework.sfgpetclinic.services.SpecialityService;
import pl.pmerdala.springframework.sfgpetclinic.services.VetService;
import pl.pmerdala.springframework.sfgpetclinic.services.map.SpecialityMapService;
import pl.pmerdala.springframework.sfgpetclinic.services.map.VetMapService;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("Controller")
class VetControllerTest {

    VetService vetService;
    SpecialityService specialityService;
    VetController vetController;

    @BeforeEach
    void setUp() {
        specialityService = new SpecialityMapService();
        vetService = new VetMapService(specialityService);
        vetController = new VetController(vetService);
        Vet vet1 = new Vet(1l, "Paweł", "Smith", null);
        vetService.save(vet1);
        Vet vet2 = new Vet(2l, "Adam", "Smith", null);
        vetService.save(vet2);
    }

    @Test
    void listVets() {
        ModelMapImpl model = new ModelMapImpl();
        String viewName = vetController.listVets(model);
        assertThat(viewName).isEqualTo("vets/index");
        assertThat(model.map)
                .size().isEqualTo(1);
        assertThat(model.map).containsKey("vets");
        assertThat(model.map.get("vets")).isInstanceOf(Set.class);
        assertThat((Set) model.map.get("vets")).size().isEqualTo(2);

    }

}