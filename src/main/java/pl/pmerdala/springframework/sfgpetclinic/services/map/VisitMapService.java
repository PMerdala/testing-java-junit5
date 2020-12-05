package pl.pmerdala.springframework.sfgpetclinic.services.map;

import pl.pmerdala.springframework.sfgpetclinic.model.Visit;
import pl.pmerdala.springframework.sfgpetclinic.services.VisitService;

public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {
    @Override
    public Visit save(Visit visit) {
        if (visit.getPet() == null || visit.getPet().getOwner() == null || visit.getPet().getId() == null
                || visit.getPet().getOwner().getId() == null) {
            throw new RuntimeException("Invalid Visit");
        }
        return super.save(visit);
    }
}
