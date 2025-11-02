<?php

namespace App\Entity;

use App\Repository\InspecteurRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * Inspecteur entity - Site inspectors
 *
 * Represents inspectors who monitor construction sites,
 * add observations, and upload documents
 *
 * @author GestTravaux Pro
 */
#[ORM\Entity(repositoryClass: InspecteurRepository::class)]
#[ORM\Table(name: 'inspecteurs')]
class Inspecteur
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'idInspecteur')]
    private ?int $id = null;

    #[ORM\Column(length: 45)]
    private ?string $nomInspecteur = null;

    #[ORM\Column(length: 45)]
    private ?string $prenomInspecteur = null;

    #[ORM\Column(length: 45)]
    private ?string $emailInspecteur = null;

    #[ORM\Column(length: 45)]
    private ?string $telInspecteur = null;

    #[ORM\Column(length: 45)]
    private ?string $secteurInspecteur = null;

    #[ORM\OneToOne(targetEntity: User::class, inversedBy: 'inspecteur')]
    #[ORM\JoinColumn(nullable: true)]
    private ?User $user = null;

    /**
     * @var Collection<int, Chantier>
     */
    #[ORM\OneToMany(targetEntity: Chantier::class, mappedBy: 'inspecteur')]
    private Collection $chantiers;

    public function __construct()
    {
        $this->chantiers = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomInspecteur(): ?string
    {
        return $this->nomInspecteur;
    }

    public function setNomInspecteur(string $nomInspecteur): static
    {
        $this->nomInspecteur = $nomInspecteur;

        return $this;
    }

    public function getPrenomInspecteur(): ?string
    {
        return $this->prenomInspecteur;
    }

    public function setPrenomInspecteur(string $prenomInspecteur): static
    {
        $this->prenomInspecteur = $prenomInspecteur;

        return $this;
    }

    public function getEmailInspecteur(): ?string
    {
        return $this->emailInspecteur;
    }

    public function setEmailInspecteur(string $emailInspecteur): static
    {
        $this->emailInspecteur = $emailInspecteur;

        return $this;
    }

    public function getTelInspecteur(): ?string
    {
        return $this->telInspecteur;
    }

    public function setTelInspecteur(string $telInspecteur): static
    {
        $this->telInspecteur = $telInspecteur;

        return $this;
    }

    public function getSecteurInspecteur(): ?string
    {
        return $this->secteurInspecteur;
    }

    public function setSecteurInspecteur(string $secteurInspecteur): static
    {
        $this->secteurInspecteur = $secteurInspecteur;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): static
    {
        $this->user = $user;

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
            $chantier->setInspecteur($this);
        }

        return $this;
    }

    public function removeChantier(Chantier $chantier): static
    {
        if ($this->chantiers->removeElement($chantier)) {
            // set the owning side to null (unless already changed)
            if ($chantier->getInspecteur() === $this) {
                $chantier->setInspecteur(null);
            }
        }

        return $this;
    }

    /**
     * Get full name for display
     */
    public function getFullName(): string
    {
        return $this->prenomInspecteur . ' ' . $this->nomInspecteur;
    }

    public function __toString(): string
    {
        return $this->getFullName();
    }
}
