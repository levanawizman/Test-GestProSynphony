package com.immosync.gesttravaux.tests;

import com.immosync.gesttravaux.models.Categorie;
import com.immosync.gesttravaux.models.Entrepreneur;
import com.immosync.gesttravaux.services.EntrepreneurService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

/**
 * Unit tests for EntrepreneurService.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntrepreneurServiceTest {

    private EntrepreneurService entrepreneurService;
    private static Integer createdEntrepreneurId;

    @BeforeEach
    public void setUp() {
        entrepreneurService = new EntrepreneurService();
    }

    @Test
    @Order(1)
    @DisplayName("Test: Create Entrepreneur")
    public void testCreateEntrepreneur() {
        Entrepreneur entrepreneur = new Entrepreneur();
        entrepreneur.setNomEntrepreneur("Dupont");
        entrepreneur.setPrenomEntrepreneur("Jean");
        entrepreneur.setEmailEntrepreneur("jean.dupont@test.com");
        entrepreneur.setTelEntrepreneur("0123456789");
        entrepreneur.setVilleDeploiement("Paris");

        Entrepreneur created = entrepreneurService.createEntrepreneur(entrepreneur);

        assertNotNull(created);
        assertNotNull(created.getIdEntrepreneur());
        assertEquals("Dupont", created.getNomEntrepreneur());
        assertEquals("Jean", created.getPrenomEntrepreneur());
        assertEquals("jean.dupont@test.com", created.getEmailEntrepreneur());

        createdEntrepreneurId = created.getIdEntrepreneur();
    }

    @Test
    @Order(2)
    @DisplayName("Test: Find Entrepreneur by ID")
    public void testFindById() {
        if (createdEntrepreneurId != null) {
            Optional<Entrepreneur> found = entrepreneurService.findById(createdEntrepreneurId);
            assertTrue(found.isPresent());
            assertEquals("Dupont", found.get().getNomEntrepreneur());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test: Update Entrepreneur")
    public void testUpdateEntrepreneur() {
        if (createdEntrepreneurId != null) {
            Optional<Entrepreneur> found = entrepreneurService.findById(createdEntrepreneurId);
            if (found.isPresent()) {
                Entrepreneur entrepreneur = found.get();
                entrepreneur.setVilleDeploiement("Lyon");

                Entrepreneur updated = entrepreneurService.updateEntrepreneur(entrepreneur);
                assertEquals("Lyon", updated.getVilleDeploiement());
            }
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test: Get All Entrepreneurs")
    public void testGetAllEntrepreneurs() {
        List<Entrepreneur> entrepreneurs = entrepreneurService.getAllEntrepreneurs();
        assertNotNull(entrepreneurs);
        assertTrue(entrepreneurs.size() > 0);
    }

    @Test
    @Order(5)
    @DisplayName("Test: Search Entrepreneurs by Name")
    public void testSearchByName() {
        List<Entrepreneur> results = entrepreneurService.searchByName("Dupont");
        assertNotNull(results);
        assertTrue(results.size() > 0);
    }

    @Test
    @Order(6)
    @DisplayName("Test: Validation - Empty Name")
    public void testValidationEmptyName() {
        Entrepreneur entrepreneur = new Entrepreneur();
        entrepreneur.setNomEntrepreneur("");
        entrepreneur.setPrenomEntrepreneur("Jean");
        entrepreneur.setEmailEntrepreneur("test@test.com");

        assertThrows(IllegalArgumentException.class, () -> {
            entrepreneurService.createEntrepreneur(entrepreneur);
        });
    }

    @Test
    @Order(7)
    @DisplayName("Test: Validation - Invalid Email")
    public void testValidationInvalidEmail() {
        Entrepreneur entrepreneur = new Entrepreneur();
        entrepreneur.setNomEntrepreneur("Test");
        entrepreneur.setPrenomEntrepreneur("Jean");
        entrepreneur.setEmailEntrepreneur("invalid-email");

        assertThrows(IllegalArgumentException.class, () -> {
            entrepreneurService.createEntrepreneur(entrepreneur);
        });
    }

    @Test
    @Order(8)
    @DisplayName("Test: Delete Entrepreneur")
    public void testDeleteEntrepreneur() {
        if (createdEntrepreneurId != null) {
            entrepreneurService.deleteEntrepreneur(createdEntrepreneurId);
            Optional<Entrepreneur> found = entrepreneurService.findById(createdEntrepreneurId);
            assertFalse(found.isPresent());
        }
    }
}
