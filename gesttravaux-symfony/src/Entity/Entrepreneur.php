<?php

namespace App\Entity;

use App\Repository\EntrepreneurRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * Entrepreneur entity
 *
 * Represents contractors who can bid on projects and perform work
 *
 * @author GestTravaux Pro
 */
#[ORM\Entity(repositoryClass: EntrepreneurRepository::class)]
#[ORM\Table(name: 'entrepreneurs')]
class Entrepreneur
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'idEntrepreneur')]
    private ?int $id = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $villeDeploiment = null;

    #[ORM\Column(length: 255)]
    private ?string $nomEntrepreneur = null;

    #[ORM\Column(length: 255)]
    private ?string $prenomEntrepreneur = null;

    #[ORM\Column(length: 255)]
    private ?string $emailEntrepreneur = null;

    #[ORM\Column(length: 45)]
    private ?string $telEntrepreneur = null;

    /**
     * @var Collection<int, Categorie>
     */
    #[ORM\ManyToMany(targetEntity: Categorie::class, inversedBy: 'entrepreneurs')]
    #[ORM\JoinTable(name: 'entrepreneurs_has_categories')]
    #[ORM\JoinColumn(name: 'entrepreneurs_idEntrepreneur', referencedColumnName: 'idEntrepreneur')]
    #[ORM\InverseJoinColumn(name: 'categories_idCategorie', referencedColumnName: 'idCategorie')]
    private Collection $categories;

    /**
     * @var Collection<int, Devis>
     */
    #[ORM\OneToMany(targetEntity: Devis::class, mappedBy: 'entrepreneur')]
    private Collection $devis;

    public function __construct()
    {
        $this->categories = new ArrayCollection();
        $this->devis = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getVilleDeploiment(): ?string
    {
        return $this->villeDeploiment;
    }

    public function setVilleDeploiment(?string $villeDeploiment): static
    {
        $this->villeDeploiment = $villeDeploiment;

        return $this;
    }

    public function getNomEntrepreneur(): ?string
    {
        return $this->nomEntrepreneur;
    }

    public function setNomEntrepreneur(string $nomEntrepreneur): static
    {
        $this->nomEntrepreneur = $nomEntrepreneur;

        return $this;
    }

    public function getPrenomEntrepreneur(): ?string
    {
        return $this->prenomEntrepreneur;
    }

    public function setPrenomEntrepreneur(string $prenomEntrepreneur): static
    {
        $this->prenomEntrepreneur = $prenomEntrepreneur;

        return $this;
    }

    public function getEmailEntrepreneur(): ?string
    {
        return $this->emailEntrepreneur;
    }

    public function setEmailEntrepreneur(string $emailEntrepreneur): static
    {
        $this->emailEntrepreneur = $emailEntrepreneur;

        return $this;
    }

    public function getTelEntrepreneur(): ?string
    {
        return $this->telEntrepreneur;
    }

    public function setTelEntrepreneur(string $telEntrepreneur): static
    {
        $this->telEntrepreneur = $telEntrepreneur;

        return $this;
    }

    /**
     * @return Collection<int, Categorie>
     */
    public function getCategories(): Collection
    {
        return $this->categories;
    }

    public function addCategory(Categorie $category): static
    {
        if (!$this->categories->contains($category)) {
            $this->categories->add($category);
        }

        return $this;
    }

    public function removeCategory(Categorie $category): static
    {
        $this->categories->removeElement($category);

        return $this;
    }

    /**
     * @return Collection<int, Devis>
     */
    public function getDevis(): Collection
    {
        return $this->devis;
    }

    public function addDevi(Devis $devi): static
    {
        if (!$this->devis->contains($devi)) {
            $this->devis->add($devi);
            $devi->setEntrepreneur($this);
        }

        return $this;
    }

    public function removeDevi(Devis $devi): static
    {
        if ($this->devis->removeElement($devi)) {
            // set the owning side to null (unless already changed)
            if ($devi->getEntrepreneur() === $this) {
                $devi->setEntrepreneur(null);
            }
        }

        return $this;
    }

    public function __toString(): string
    {
        return $this->prenomEntrepreneur . ' ' . $this->nomEntrepreneur;
    }
}
