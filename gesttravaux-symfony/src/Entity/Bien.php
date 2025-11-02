<?php

namespace App\Entity;

use App\Repository\BienRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * Bien entity - Properties
 *
 * Represents real estate properties where construction/renovation work takes place
 *
 * @author GestTravaux Pro
 */
#[ORM\Entity(repositoryClass: BienRepository::class)]
#[ORM\Table(name: 'biens')]
class Bien
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'idBien')]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $adresseBien = null;

    #[ORM\Column(length: 45)]
    private ?string $villeBien = null;

    #[ORM\Column(length: 45, nullable: true)]
    private ?string $fk_idProprietaire = null;

    #[ORM\Column(type: 'decimal', precision: 10, scale: 8, nullable: true)]
    private ?string $latitude = null;

    #[ORM\Column(type: 'decimal', precision: 11, scale: 8, nullable: true)]
    private ?string $longitude = null;

    #[ORM\ManyToOne(targetEntity: Proprietaire::class, inversedBy: 'biens')]
    #[ORM\JoinColumn(name: 'proprietaires_idProprietaire', referencedColumnName: 'idProprietaire', nullable: false)]
    private ?Proprietaire $proprietaire = null;

    /**
     * @var Collection<int, Chantier>
     */
    #[ORM\OneToMany(targetEntity: Chantier::class, mappedBy: 'bien')]
    private Collection $chantiers;

    public function __construct()
    {
        $this->chantiers = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getAdresseBien(): ?string
    {
        return $this->adresseBien;
    }

    public function setAdresseBien(string $adresseBien): static
    {
        $this->adresseBien = $adresseBien;

        return $this;
    }

    public function getVilleBien(): ?string
    {
        return $this->villeBien;
    }

    public function setVilleBien(string $villeBien): static
    {
        $this->villeBien = $villeBien;

        return $this;
    }

    public function getFkIdProprietaire(): ?string
    {
        return $this->fk_idProprietaire;
    }

    public function setFkIdProprietaire(?string $fk_idProprietaire): static
    {
        $this->fk_idProprietaire = $fk_idProprietaire;

        return $this;
    }

    public function getLatitude(): ?string
    {
        return $this->latitude;
    }

    public function setLatitude(?string $latitude): static
    {
        $this->latitude = $latitude;

        return $this;
    }

    public function getLongitude(): ?string
    {
        return $this->longitude;
    }

    public function setLongitude(?string $longitude): static
    {
        $this->longitude = $longitude;

        return $this;
    }

    public function getProprietaire(): ?Proprietaire
    {
        return $this->proprietaire;
    }

    public function setProprietaire(?Proprietaire $proprietaire): static
    {
        $this->proprietaire = $proprietaire;

        return $this;
    }

    /**
     * @return Collection<int, Chantier>
     */
    public function getChantiers(): Collection
    {
        return $this->chantiers;
    }

    public function addChantier(Chantier $chantier): static
    {
        if (!$this->chantiers->contains($chantier)) {
            $this->chantiers->add($chantier);
            $chantier->setBien($this);
        }

        return $this;
    }

    public function removeChantier(Chantier $chantier): static
    {
        if ($this->chantiers->removeElement($chantier)) {
            // set the owning side to null (unless already changed)
            if ($chantier->getBien() === $this) {
                $chantier->setBien(null);
            }
        }

        return $this;
    }

    /**
     * Get full address for display
     */
    public function getFullAddress(): string
    {
        return $this->adresseBien . ', ' . $this->villeBien;
    }

    public function __toString(): string
    {
        return $this->getFullAddress();
    }
}
