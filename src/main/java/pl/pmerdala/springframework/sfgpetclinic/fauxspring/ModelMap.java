package pl.pmerdala.springframework.sfgpetclinic.fauxspring;

import pl.pmerdala.springframework.sfgpetclinic.model.Pet;

public interface ModelMap {
    void put(String petKey, Pet pet);
}
