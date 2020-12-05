package pl.pmerdala.springframework.sfgpetclinic.services.map;

import pl.pmerdala.springframework.sfgpetclinic.model.Speciality;
import pl.pmerdala.springframework.sfgpetclinic.model.Vet;
import pl.pmerdala.springframework.sfgpetclinic.services.SpecialityService;
import pl.pmerdala.springframework.sfgpetclinic.services.VetService;

public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet save(Vet object) {
        if (object != null) {
            if (object.getSpecialities() != null) {
                if (object.getSpecialities().size() > 0) {
                    object.getSpecialities().stream().forEach(speciality -> {
                        if (speciality.getId() == null) {
                            Speciality saveSpeciality = specialityService.save(speciality);
                            speciality.setId(saveSpeciality.getId());
                        }
                    });
                }
            } else {
                throw new RuntimeException("Specialities is requests");
            }
        }
        return super.save(object);
    }
}
