package pl.pmerdala.springframework.sfgpetclinic.services.map;

import pl.pmerdala.springframework.sfgpetclinic.model.Owner;
import pl.pmerdala.springframework.sfgpetclinic.model.Pet;
import pl.pmerdala.springframework.sfgpetclinic.services.OwnerService;
import pl.pmerdala.springframework.sfgpetclinic.services.PetService;
import pl.pmerdala.springframework.sfgpetclinic.services.PetTypeService;

import java.util.List;

public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Owner save(Owner object) {
        if (object != null) {
            if (object.getPets() != null) {
                object.getPets().forEach(pet -> {
                    if (pet.getPetType() != null) {
                        if (pet.getPetType().getId() == null) {
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }
                    } else {
                        throw new RuntimeException("Pet type is required");
                    }
                    if (pet.getId() == null) {
                        Pet savePet = petService.save(pet);
                        pet.setId(savePet.getId());
                    }
                });
            }
            return super.save(object);
        } else {
            return null;
        }
    }

    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {
        //todo: impl
        return null;
    }
}
