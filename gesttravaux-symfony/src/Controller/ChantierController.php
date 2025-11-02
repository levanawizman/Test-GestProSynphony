<?php

namespace App\Controller;

use App\Repository\ChantierRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

/**
 * ChantierController - Chantier management
 *
 * Additional chantier-related endpoints and API methods
 *
 * @author GestTravaux Pro
 */
#[Route('/api/chantier')]
#[IsGranted('ROLE_INSPECTEUR')]
class ChantierController extends AbstractController
{
    /**
     * Get chantier data as JSON (for API calls)
     *
     * @param int $id The chantier ID
     * @param ChantierRepository $chantierRepository
     * @return JsonResponse
     */
    #[Route('/{id}/json', name: 'api_chantier_json', methods: ['GET'])]
    public function getChantierJson(int $id, ChantierRepository $chantierRepository): JsonResponse
    {
        $chantier = $chantierRepository->findOneWithRelations($id);

        if (!$chantier) {
            return $this->json(['error' => 'Chantier non trouvé'], Response::HTTP_NOT_FOUND);
        }

        // Verify the chantier is assigned to this inspector
        $user = $this->getUser();
        $inspecteur = $user->getInspecteur();

        if ($chantier->getInspecteur() !== $inspecteur) {
            return $this->json(['error' => 'Accès refusé'], Response::HTTP_FORBIDDEN);
        }

        $bien = $chantier->getBien();

        $data = [
            'id' => $chantier->getId(),
            'address' => $chantier->getFullAddress(),
            'status' => $chantier->getStatutLabel(),
            'info' => $chantier->getInfoChantier(),
            'bien' => [
                'id' => $bien->getId(),
                'address' => $bien->getFullAddress(),
                'latitude' => $bien->getLatitude(),
                'longitude' => $bien->getLongitude(),
            ],
            'devis' => array_map(function($devis) {
                return [
                    'id' => $devis->getId(),
                    'prix' => $devis->getPrix(),
                    'duree' => $devis->getDuree(),
                    'prestation' => $devis->getPrestation()->getLibelle(),
                    'categorie' => $devis->getPrestation()->getCategorie()->getType(),
                    'entrepreneur' => $devis->getEntrepreneur()->__toString(),
                ];
            }, $chantier->getDevis()->toArray()),
        ];

        return $this->json($data);
    }
}
