package pl.pmerdala.springframework.sfgpetclinic.services.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.pmerdala.springframework.sfgpetclinic.model.Owner;
import pl.pmerdala.springframework.sfgpetclinic.model.PetType;
import pl.pmerdala.springframework.sfgpetclinic.services.PetService;
import pl.pmerdala.springframework.sfgpetclinic.services.PetTypeService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Owner Map Service Test - ")
class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    PetTypeService petTypeService;
    PetService petService;

    @BeforeEach
    void setUp() {
        petTypeService = new PetTypeMapService();
        petService = new PetMapService();
        ownerMapService = new OwnerMapService(petTypeService, petService);
    }

    @DisplayName("Verify Zero Owners")
    @Test
    void ownersAreZero() {
        int ownerCount = ownerMapService.findAll().size();
        assertThat(ownerCount).isZero();
    }

    @DisplayName("Pet Type -")
    @Nested
    class TestCreatePetTypes {
        @BeforeEach
        void setUp() {
            PetType petType1 = new PetType(1L, "Dog");
            PetType petType2 = new PetType(2L, "Cat");
            petTypeService.save(petType1);
            petTypeService.save(petType2);
        }

        @DisplayName("Test Pet Type Count")
        @Test
        void testPetTypeCount() {
            int petTypeCount = petTypeService.findAll().size();
            assertThat(petTypeCount).isNotZero().isEqualTo(2);
        }

        @DisplayName("Save Owners Tests - ")
        @Nested
        class SaveOwnersTests {
            @BeforeEach
            void setUp() {
                ownerMapService.save(new Owner(1L, "Before", "Each"));
            }

            @DisplayName("Save Owner")
            @Test
            void saveOwner() {
                Owner owner = new Owner(2L, "Joe", "Buck");
                Owner savedOwner = ownerMapService.save(owner);
                assertThat(savedOwner).isNotNull();
            }

            @DisplayName("Find Owners Tests - ")
            @Nested
            class FindOwnersTests {

                @BeforeEach
                void setUp() {
                    ownerMapService.save(new Owner(3L, "Before 2", "Each"));
                }

                @DisplayName("Find Owner")
                @Test
                void findOwner() {
                    Owner foundOwner = ownerMapService.findById(1L);
                    assertThat(foundOwner).isNotNull();
                }

                @DisplayName("Find Owner Not Found")
                @Test
                void findOwnerNotFound() {
                    Owner foundOwner = ownerMapService.findById(2L);
                    assertThat(foundOwner).isNull();
                }

                @Test
                void findByLastName() {
                    Owner foundOwner = ownerMapService.findByLastName("Each");
                    assertThat(foundOwner).isNotNull().hasFieldOrPropertyWithValue("lastName", "Each");
                }

                @Test
                void findAllByLastNameLike() {
                    List<Owner> foundOwner = ownerMapService.findAllByLastNameLike("Each");
                    assertThat(foundOwner).isNotNull().hasSize(2).allMatch(owner -> "Each".equals(owner.getLastName()));
                }
            }
        }
    }


    @DisplayName("Another Verify Zero Owners")
    @Test
    void ownersAreZeroAnother() {
        int ownerCount = ownerMapService.findAll().size();
        assertThat(ownerCount).isZero();
    }
}