package pl.pmerdala.springframework.sfgpetclinic.controllers;

import pl.pmerdala.springframework.sfgpetclinic.fauxspring.BindingResult;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.Model;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.ModelAndView;
import pl.pmerdala.springframework.sfgpetclinic.fauxspring.WebDataBinder;
import pl.pmerdala.springframework.sfgpetclinic.model.Owner;
import pl.pmerdala.springframework.sfgpetclinic.services.OwnerService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public class OwnerController {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    public String findOwners(Model model) {
        model.addAttribute("owner", new Owner(null, null, null));
        return "owners/findOwners";
    }

    public String processFindForm(Owner owner, BindingResult result, Model model) {
        //allow parameterless GET request for /owners to retur all records
        final String searchString = Optional.ofNullable(owner.getLastName())
                .filter(name -> !name.trim().isEmpty())
                .map(name -> "%" + name.trim() + "%").orElse("%");
        //find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike(searchString);
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", " not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            //multiple owners found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    public ModelAndView showOwner(Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    public String initCreationForm(Model model) {
        model.addAttribute("owner", new Owner(null, null, null));
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            Owner saveOwner = ownerService.save(owner);
            return "redirect:/owners/" + saveOwner.getId();
        }
    }

    public String initUpdateOwnerForm(Long ownerId, Model model) {
        model.addAttribute(ownerService.findById(ownerId));
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, Long ownerId) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
            Owner saveOwner = ownerService.save(owner);
            return "redirect:/owners/" + saveOwner.getId();
        }
    }
}
