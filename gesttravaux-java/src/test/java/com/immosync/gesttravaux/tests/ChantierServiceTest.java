package com.immosync.gesttravaux.tests;

import com.immosync.gesttravaux.models.*;
import com.immosync.gesttravaux.services.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

/**
 * Unit tests for ChantierService.
 *
 * @author GestTravaux Pro
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChantierServiceTest {

    private ChantierService chantierService;
    private ProprietaireService proprietaireService;
    private BienService bienService;
    private static Integer createdChantierId;
    private static Integer testBienId;
    private static Integer testProprietaireId;

    @BeforeEach
    public void setUp() {
        chantierService = new ChantierService();
        proprietaireService = new ProprietaireService();
        bienService = new BienService();
    }

    @Test
    @Order(1)
    @DisplayName("Test: Setup Test Data")
    public void testSetupTestData() {
        // Create test proprietaire
        Proprietaire proprietaire = new Proprietaire();
        proprietaire.setNomProprietaire("Martin");
        proprietaire.setPrenomProprietaire("Sophie");
        proprietaire.setEmailProprietaire("sophie.martin@test.com");
        Proprietaire createdProprietaire = proprietaireService.createProprietaire(proprietaire);
        testProprietaireId = createdProprietaire.getIdProprietaire();

        // Create test bien
        Bien bien = new Bien();
        bien.setAdresseBien("123 Rue de Test");
        bien.setVilleBien("Paris");
        bien.setProprietaire(createdProprietaire);
        Bien createdBien = bienService.createBien(bien);
        testBienId = createdBien.getIdBien();

        assertNotNull(testProprietaireId);
        assertNotNull(testBienId);
    }

    @Test
    @Order(2)
    @DisplayName("Test: Create Chantier")
    public void testCreateChantier() {
        if (testBienId != null) {
            Optional<Bien> bien = bienService.findById(testBienId);
            if (bien.isPresent()) {
                Chantier chantier = new Chantier();
                chantier.setAdresseChantier("123 Rue de Test");
                chantier.setVilleChantier("Paris");
                chantier.setBien(bien.get());
                chantier.setInfoChantier("Rénovation complète");
                chantier.setStatutChantier(0);

                Chantier created = chantierService.createChantier(chantier);

                assertNotNull(created);
                assertNotNull(created.getIdChantier());
                assertEquals("Paris", created.getVilleChantier());
                assertEquals(0, created.getStatutChantier());

                createdChantierId = created.getIdChantier();
            }
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test: Find Chantier by ID")
    public void testFindById() {
        if (createdChantierId != null) {
            Optional<Chantier> found = chantierService.findById(createdChantierId);
            assertTrue(found.isPresent());
            assertEquals("Paris", found.get().getVilleChantier());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test: Update Chantier Status")
    public void testUpdateStatut() {
        if (createdChantierId != null) {
            Chantier updated = chantierService.updateStatut(createdChantierId, 1);
            assertEquals(1, updated.getStatutChantier());
            assertEquals("En cours", updated.getStatutString());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test: Find Chantiers by Status")
    public void testFindByStatut() {
        List<Chantier> chantiers = chantierService.findByStatut(1);
        assertNotNull(chantiers);
        assertTrue(chantiers.size() > 0);
    }

    @Test
    @Order(6)
    @DisplayName("Test: Get All Chantiers")
    public void testGetAllChantiers() {
        List<Chantier> chantiers = chantierService.getAllChantiers();
        assertNotNull(chantiers);
        assertTrue(chantiers.size() > 0);
    }

    @Test
    @Order(7)
    @DisplayName("Test: Validation - Invalid Status")
    public void testValidationInvalidStatus() {
        if (createdChantierId != null) {
            assertThrows(IllegalArgumentException.class, () -> {
                chantierService.updateStatut(createdChantierId, 5);
            });
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test: Delete Chantier")
    public void testDeleteChantier() {
        if (createdChantierId != null) {
            chantierService.deleteChantier(createdChantierId);
            Optional<Chantier> found = chantierService.findById(createdChantierId);
            assertFalse(found.isPresent());
        }
    }

    @Test
    @Order(9)
    @DisplayName("Test: Cleanup Test Data")
    public void testCleanupTestData() {
        if (testBienId != null) {
            bienService.deleteBien(testBienId);
        }
        if (testProprietaireId != null) {
            proprietaireService.deleteProprietaire(testProprietaireId);
        }
    }
}
