package pl.pmerdala.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Model")
class PersonTest {

    @Test
    void AfterCreatedPropertiesGetCorrectValue() {
        Person person = createPerson();
        assertAll("Test properties set after create",
                () -> assertEquals(1l, person.getId()),
                () -> assertEquals("Paweł", person.getFirstName()),
                () -> assertEquals("Smith", person.getLastName()));
    }

    @Test
    void testPropertiesSet() {
        Person person = createPerson();
        person.setId(2l);
        person.setFirstName("Adam");
        person.setLastName("Apple");
        assertAll("Test properties set after create",
                () -> assertEquals(2l, person.getId()),
                () -> assertEquals("Adam", person.getFirstName()),
                () -> assertEquals("Apple", person.getLastName()));
    }

    protected Person createPerson() {
        return new Person(1l, "Paweł", "Smith");
    }
}