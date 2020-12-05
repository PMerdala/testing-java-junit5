package pl.pmerdala.springframework.sfgpetclinic.controllers;

import org.apache.commons.lang3.StringUtils;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.BindingResult;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.Model;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.ModelMap;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.WebDataBinder;
import pl.pmerdala.springframework.sfgpetclinic.model.Owner;
import pl.pmerdala.springframework.sfgpetclinic.model.Pet;
import pl.pmerdala.springframework.sfgpetclinic.model.PetType;
import pl.pmerdala.springframework.sfgpetclinic.services.OwnerService;
import pl.pmerdala.springframework.sfgpetclinic.services.PetService;
import pl.pmerdala.springframework.sfgpetclinic.services.PetTypeService;

import javax.validation.Valid;
import java.util.Collection;

public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateForm";

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    public Owner findOwner(Long ownerId) {
        return ownerService.findById(ownerId);
    }

    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    public String initCreationForm(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    public String processCreationForm(Owner owner, Pet pet, BindingResult result, ModelMap model) {
        if (StringUtils.length(pet.getName()) > 0 && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.getPets().add(pet);
        if (result.hasErrors()) {
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petService.save(pet);
        }
        return "redirect:/owners/" + owner.getId();
    }

    public String initUpdateForm(Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    public String precessUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, Model model) {
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            owner.getPets().add(pet);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }

    }
}
