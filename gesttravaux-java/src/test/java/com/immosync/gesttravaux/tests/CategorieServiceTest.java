package com.immosync.gesttravaux.tests;

import com.immosync.gesttravaux.models.Categorie;
import com.immosync.gesttravaux.services.CategorieService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

/**
 * Unit tests for CategorieService.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategorieServiceTest {

    private CategorieService categorieService;
    private static Integer createdCategorieId;

    @BeforeEach
    public void setUp() {
        categorieService = new CategorieService();
    }

    @Test
    @Order(1)
    @DisplayName("Test: Create Categorie")
    public void testCreateCategorie() {
        Categorie categorie = new Categorie();
        categorie.setType("Plomberie");

        Categorie created = categorieService.createCategorie(categorie);

        assertNotNull(created);
        assertNotNull(created.getIdCategorie());
        assertEquals("Plomberie", created.getType());

        createdCategorieId = created.getIdCategorie();
    }

    @Test
    @Order(2)
    @DisplayName("Test: Find Categorie by ID")
    public void testFindById() {
        if (createdCategorieId != null) {
            Optional<Categorie> found = categorieService.findById(createdCategorieId);
            assertTrue(found.isPresent());
            assertEquals("Plomberie", found.get().getType());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test: Update Categorie")
    public void testUpdateCategorie() {
        if (createdCategorieId != null) {
            Optional<Categorie> found = categorieService.findById(createdCategorieId);
            if (found.isPresent()) {
                Categorie categorie = found.get();
                categorie.setType("Plomberie Sanitaire");

                Categorie updated = categorieService.updateCategorie(categorie);
                assertEquals("Plomberie Sanitaire", updated.getType());
            }
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test: Get All Categories")
    public void testGetAllCategories() {
        List<Categorie> categories = categorieService.getAllCategories();
        assertNotNull(categories);
        assertTrue(categories.size() > 0);
    }

    @Test
    @Order(5)
    @DisplayName("Test: Search Categories by Type")
    public void testSearchByType() {
        List<Categorie> results = categorieService.searchByType("Plomberie");
        assertNotNull(results);
        assertTrue(results.size() > 0);
    }

    @Test
    @Order(6)
    @DisplayName("Test: Validation - Empty Type")
    public void testValidationEmptyType() {
        Categorie categorie = new Categorie();
        categorie.setType("");

        assertThrows(IllegalArgumentException.class, () -> {
            categorieService.createCategorie(categorie);
        });
    }

    @Test
    @Order(7)
    @DisplayName("Test: Delete Categorie")
    public void testDeleteCategorie() {
        if (createdCategorieId != null) {
            categorieService.deleteCategorie(createdCategorieId);
            Optional<Categorie> found = categorieService.findById(createdCategorieId);
            assertFalse(found.isPresent());
        }
    }
}
