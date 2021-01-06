package pl.pmerdala.springframework.sfgpetclinic.services.springdatajpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.pmerdala.springframework.sfgpetclinic.model.Owner;
import pl.pmerdala.springframework.sfgpetclinic.services.OwnerService;

@Disabled("Disable until we learn Mock")
class OwnerSDJpaServiceTest {

    OwnerService ownerService;

    @BeforeEach
    void setUp() {
        ownerService = new OwnerSDJpaService(null, null, null);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByLastName() {
        Owner owner = ownerService.findByLastName("Merdala");
    }

    @Test
    void findAllByLastNameLike() {
    }

}