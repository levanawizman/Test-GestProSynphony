<?php

namespace App\Entity;

use App\Repository\DevisRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * Devis entity - Quotes/Estimates
 *
 * Represents contractor quotes for specific services on construction sites
 *
 * @author GestTravaux Pro
 */
#[ORM\Entity(repositoryClass: DevisRepository::class)]
#[ORM\Table(name: 'devis')]
class Devis
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'idDevis')]
    private ?int $id = null;

    #[ORM\Column]
    private ?int $prix = null;

    #[ORM\Column(length: 45)]
    private ?string $duree = null;

    #[ORM\ManyToOne(targetEntity: Prestation::class, inversedBy: 'devis')]
    #[ORM\JoinColumn(name: 'prestations_idPrestation', referencedColumnName: 'idPrestation', nullable: false)]
    private ?Prestation $prestation = null;

    #[ORM\ManyToOne(targetEntity: Entrepreneur::class, inversedBy: 'devis')]
    #[ORM\JoinColumn(name: 'entrepreneurs_idEntrepreneur', referencedColumnName: 'idEntrepreneur', nullable: false)]
    private ?Entrepreneur $entrepreneur = null;

    #[ORM\ManyToOne(targetEntity: Chantier::class, inversedBy: 'devis')]
    #[ORM\JoinColumn(name: 'chantiers_idChantier', referencedColumnName: 'idChantier', nullable: false)]
    private ?Chantier $chantier = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPrix(): ?int
    {
        return $this->prix;
    }

    public function setPrix(int $prix): static
    {
        $this->prix = $prix;

        return $this;
    }

    public function getDuree(): ?string
    {
        return $this->duree;
    }

    public function setDuree(string $duree): static
    {
        $this->duree = $duree;

        return $this;
    }

    public function getPrestation(): ?Prestation
    {
        return $this->prestation;
    }

    public function setPrestation(?Prestation $prestation): static
    {
        $this->prestation = $prestation;

        return $this;
    }

    public function getEntrepreneur(): ?Entrepreneur
    {
        return $this->entrepreneur;
    }

    public function setEntrepreneur(?Entrepreneur $entrepreneur): static
    {
        $this->entrepreneur = $entrepreneur;

        return $this;
    }

    public function getChantier(): ?Chantier
    {
        return $this->chantier;
    }

    public function setChantier(?Chantier $chantier): static
    {
        $this->chantier = $chantier;

        return $this;
    }

    /**
     * Get formatted price
     */
    public function getFormattedPrice(): string
    {
        return number_format($this->prix, 2, ',', ' ') . ' €';
    }

    public function __toString(): string
    {
        return 'Devis #' . $this->id . ' - ' . $this->getFormattedPrice();
    }
}
