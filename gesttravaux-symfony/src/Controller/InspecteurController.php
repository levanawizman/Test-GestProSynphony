<?php

namespace App\Controller;

use App\Entity\Chantier;
use App\Entity\Document;
use App\Form\DocumentType;
use App\Form\ObservationType;
use App\Repository\ChantierRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

/**
 * InspecteurController - Inspector interface
 *
 * Handles all inspector-related functionality including:
 * - Dashboard with assigned chantiers
 * - Chantier detail view with map
 * - Adding observations with photos
 * - Uploading documents
 *
 * @author GestTravaux Pro
 */
#[Route('/inspecteur')]
#[IsGranted('ROLE_INSPECTEUR')]
class InspecteurController extends AbstractController
{
    /**
     * Dashboard - List of assigned chantiers
     *
     * @param ChantierRepository $chantierRepository
     * @return Response
     */
    #[Route('/dashboard', name: 'inspecteur_dashboard')]
    public function dashboard(ChantierRepository $chantierRepository): Response
    {
        $user = $this->getUser();
        $inspecteur = $user->getInspecteur();

        if (!$inspecteur) {
            $this->addFlash('error', 'Aucun profil inspecteur associé à votre compte.');
            return $this->redirectToRoute('app_login');
        }

        // Get all chantiers assigned to this inspector
        $chantiers = $chantierRepository->findByInspecteur($inspecteur);

        // Count by status for statistics
        $stats = [
            'pending' => $chantierRepository->countByInspecteurAndStatus($inspecteur, Chantier::STATUS_PENDING),
            'in_progress' => $chantierRepository->countByInspecteurAndStatus($inspecteur, Chantier::STATUS_IN_PROGRESS),
            'completed' => $chantierRepository->countByInspecteurAndStatus($inspecteur, Chantier::STATUS_COMPLETED),
        ];

        return $this->render('inspecteur/dashboard.html.twig', [
            'inspecteur' => $inspecteur,
            'chantiers' => $chantiers,
            'stats' => $stats,
        ]);
    }

    /**
     * List of all chantiers
     *
     * @param ChantierRepository $chantierRepository
     * @return Response
     */
    #[Route('/chantiers', name: 'inspecteur_chantiers_list')]
    public function chantiersList(ChantierRepository $chantierRepository): Response
    {
        $user = $this->getUser();
        $inspecteur = $user->getInspecteur();

        if (!$inspecteur) {
            $this->addFlash('error', 'Aucun profil inspecteur associé à votre compte.');
            return $this->redirectToRoute('app_login');
        }

        $chantiers = $chantierRepository->findByInspecteur($inspecteur);

        return $this->render('inspecteur/chantiers_list.html.twig', [
            'chantiers' => $chantiers,
        ]);
    }

    /**
     * Chantier detail with map and information
     *
     * @param int $id The chantier ID
     * @param ChantierRepository $chantierRepository
     * @return Response
     */
    #[Route('/chantier/{id}', name: 'inspecteur_chantier_detail')]
    public function chantierDetail(int $id, ChantierRepository $chantierRepository): Response
    {
        $user = $this->getUser();
        $inspecteur = $user->getInspecteur();

        // Get chantier with all relations (eager loading)
        $chantier = $chantierRepository->findOneWithRelations($id);

        if (!$chantier) {
            throw $this->createNotFoundException('Chantier non trouvé');
        }

        // Verify the chantier is assigned to this inspector
        if ($chantier->getInspecteur() !== $inspecteur) {
            throw $this->createAccessDeniedException('Vous n\'êtes pas autorisé à accéder à ce chantier');
        }

        return $this->render('inspecteur/chantier_detail.html.twig', [
            'chantier' => $chantier,
        ]);
    }

    /**
     * Add observation with photo
     *
     * @param int $chantierId The chantier ID
     * @param Request $request
     * @param ChantierRepository $chantierRepository
     * @param EntityManagerInterface $entityManager
     * @return Response
     */
    #[Route('/chantier/{chantierId}/add-observation', name: 'inspecteur_add_observation')]
    public function addObservation(
        int $chantierId,
        Request $request,
        ChantierRepository $chantierRepository,
        EntityManagerInterface $entityManager
    ): Response {
        $user = $this->getUser();
        $inspecteur = $user->getInspecteur();

        $chantier = $chantierRepository->find($chantierId);

        if (!$chantier) {
            throw $this->createNotFoundException('Chantier non trouvé');
        }

        // Verify the chantier is assigned to this inspector
        if ($chantier->getInspecteur() !== $inspecteur) {
            throw $this->createAccessDeniedException('Vous n\'êtes pas autorisé à accéder à ce chantier');
        }

        $document = new Document();
        $document->setFileType('photo');

        $form = $this->createForm(ObservationType::class, $document);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // The VichUploader will handle the file upload automatically
            $entityManager->persist($document);
            $entityManager->flush();

            // Link the document to the chantier
            $chantier->setDocument($document);
            $chantier->setDocumentsLibelleDoc($document->getLibelleDoc());
            $entityManager->flush();

            $this->addFlash('success', 'Observation ajoutée avec succès');

            return $this->redirectToRoute('inspecteur_chantier_detail', ['id' => $chantierId]);
        }

        return $this->render('inspecteur/add_observation.html.twig', [
            'form' => $form,
            'chantier' => $chantier,
        ]);
    }

    /**
     * Upload document (PDF)
     *
     * @param int $chantierId The chantier ID
     * @param Request $request
     * @param ChantierRepository $chantierRepository
     * @param EntityManagerInterface $entityManager
     * @return Response
     */
    #[Route('/chantier/{chantierId}/upload-document', name: 'inspecteur_upload_document')]
    public function uploadDocument(
        int $chantierId,
        Request $request,
        ChantierRepository $chantierRepository,
        EntityManagerInterface $entityManager
    ): Response {
        $user = $this->getUser();
        $inspecteur = $user->getInspecteur();

        $chantier = $chantierRepository->find($chantierId);

        if (!$chantier) {
            throw $this->createNotFoundException('Chantier non trouvé');
        }

        // Verify the chantier is assigned to this inspector
        if ($chantier->getInspecteur() !== $inspecteur) {
            throw $this->createAccessDeniedException('Vous n\'êtes pas autorisé à accéder à ce chantier');
        }

        $document = new Document();

        $form = $this->createForm(DocumentType::class, $document);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // The VichUploader will handle the file upload automatically
            $entityManager->persist($document);
            $entityManager->flush();

            // Link the document to the chantier
            $chantier->setDocument($document);
            $chantier->setDocumentsLibelleDoc($document->getLibelleDoc());
            $entityManager->flush();

            $this->addFlash('success', 'Document téléchargé avec succès');

            return $this->redirectToRoute('inspecteur_chantier_detail', ['id' => $chantierId]);
        }

        return $this->render('inspecteur/upload_document.html.twig', [
            'form' => $form,
            'chantier' => $chantier,
        ]);
    }
}
