<?php

namespace App\Repository;

use App\Entity\Chantier;
use App\Entity\Inspecteur;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * ChantierRepository
 *
 * Custom repository methods for Chantier entity
 *
 * @extends ServiceEntityRepository<Chantier>
 * @author GestTravaux Pro
 */
class ChantierRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Chantier::class);
    }

    /**
     * Find all chantiers assigned to a specific inspecteur
     *
     * @param Inspecteur $inspecteur The inspector
     * @return Chantier[] Returns an array of Chantier objects
     */
    public function findByInspecteur(Inspecteur $inspecteur): array
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.inspecteur = :inspecteur')
            ->setParameter('inspecteur', $inspecteur)
            ->orderBy('c.statutChantier', 'ASC')
            ->addOrderBy('c.id', 'DESC')
            ->getQuery()
            ->getResult();
    }

    /**
     * Find active chantiers (in progress) for an inspecteur
     *
     * @param Inspecteur $inspecteur The inspector
     * @return Chantier[] Returns an array of Chantier objects
     */
    public function findActiveByInspecteur(Inspecteur $inspecteur): array
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.inspecteur = :inspecteur')
            ->andWhere('c.statutChantier = :status')
            ->setParameter('inspecteur', $inspecteur)
            ->setParameter('status', Chantier::STATUS_IN_PROGRESS)
            ->orderBy('c.id', 'DESC')
            ->getQuery()
            ->getResult();
    }

    /**
     * Find a chantier by ID with all related data (eager loading)
     *
     * @param int $id The chantier ID
     * @return Chantier|null Returns a Chantier object or null
     */
    public function findOneWithRelations(int $id): ?Chantier
    {
        return $this->createQueryBuilder('c')
            ->leftJoin('c.bien', 'b')->addSelect('b')
            ->leftJoin('b.proprietaire', 'p')->addSelect('p')
            ->leftJoin('c.inspecteur', 'i')->addSelect('i')
            ->leftJoin('c.devis', 'd')->addSelect('d')
            ->leftJoin('d.prestation', 'pr')->addSelect('pr')
            ->leftJoin('pr.categorie', 'cat')->addSelect('cat')
            ->leftJoin('d.entrepreneur', 'e')->addSelect('e')
            ->andWhere('c.id = :id')
            ->setParameter('id', $id)
            ->getQuery()
            ->getOneOrNullResult();
    }

    /**
     * Count chantiers by status for an inspecteur
     *
     * @param Inspecteur $inspecteur The inspector
     * @param int $status The status code
     * @return int The count
     */
    public function countByInspecteurAndStatus(Inspecteur $inspecteur, int $status): int
    {
        return $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->andWhere('c.inspecteur = :inspecteur')
            ->andWhere('c.statutChantier = :status')
            ->setParameter('inspecteur', $inspecteur)
            ->setParameter('status', $status)
            ->getQuery()
            ->getSingleScalarResult();
    }
}
