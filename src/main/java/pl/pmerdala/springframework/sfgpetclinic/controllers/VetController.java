package pl.pmerdala.springframework.sfgpetclinic.controllers;

import pl.pmerdala.springframework.sfgpetclinic.fauxspring.Model;
import pl.pmerdala.springframework.sfgpetclinic.services.VetService;

public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    public String listVets(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }
}
