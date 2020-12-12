package pl.pmerdala.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model")
class OwnerTest {
    @Test
    void AfterCreatedPropertiesGetCorrectValue() {
        Owner owner = createOwner();
        owner.setAddress("Górna");
        owner.setCity("Pabianice");
        owner.setTelephone("999999999");
        assertAll("Test properties set after create",
                () -> assertAll("Test Person properties",
                        () -> assertEquals(1l, owner.getId()),
                        () -> assertEquals("Paweł", owner.getFirstName()),
                        () -> assertEquals("Smith", owner.getLastName())),
                () -> assertAll("Test Owner properties",
                        () -> assertEquals("Górna", owner.getAddress()),
                        () -> assertEquals("Pabianice", owner.getCity()),
                        () -> assertEquals("999999999", owner.getTelephone()))

        );
    }

    @Test
    void testPropertiesSet() {
        Owner owner = createOwner();
        owner.setId(2l);
        owner.setFirstName("Adam");
        owner.setLastName("Apple");
        owner.setAddress("Górna");
        owner.setCity("Pabianice");
        owner.setTelephone("999999999");
        assertAll("Test properties set after create",
                () -> assertAll("Test Person properties",
                        () -> assertEquals(2l, owner.getId()),
                        () -> assertEquals("Adam", owner.getFirstName()),
                        () -> assertEquals("Apple", owner.getLastName())),
                () -> assertAll("Test Owner properties",
                        () -> assertEquals("Górna", owner.getAddress()),
                        () -> assertEquals("Pabianice", owner.getCity()),
                        () -> assertEquals("999999999", owner.getTelephone()))
        );
    }

    @Test
    void testPetsAfterCreated() {
        Owner owner = createOwner();
        assertAll("Sprawdzenie Pets",
                ()->assertEquals(0,owner.getPets().size()),
                ()->assertNotNull(owner.getPets())
        );
    }

    protected Owner createOwner() {
        return new Owner(1l, "Paweł", "Smith");
    }
}