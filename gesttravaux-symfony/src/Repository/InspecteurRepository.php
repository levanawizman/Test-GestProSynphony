<?php

namespace App\Repository;

use App\Entity\Inspecteur;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Inspecteur>
 */
class InspecteurRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Inspecteur::class);
    }

    /**
     * Find an inspecteur by email
     *
     * @param string $email The email address
     * @return Inspecteur|null
     */
    public function findOneByEmail(string $email): ?Inspecteur
    {
        return $this->createQueryBuilder('i')
            ->andWhere('i.emailInspecteur = :email')
            ->setParameter('email', $email)
            ->getQuery()
            ->getOneOrNullResult();
    }
}
