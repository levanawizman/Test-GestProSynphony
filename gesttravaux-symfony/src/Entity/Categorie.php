<?php

namespace App\Entity;

use App\Repository\CategorieRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * Categorie entity - Work categories
 *
 * Represents different types of construction/renovation work categories
 * (e.g., Plumbing, Electrical, Masonry, etc.)
 *
 * @author GestTravaux Pro
 */
#[ORM\Entity(repositoryClass: CategorieRepository::class)]
#[ORM\Table(name: 'categories')]
class Categorie
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'idCategorie')]
    private ?int $id = null;

    #[ORM\Column(length: 45)]
    private ?string $type = null;

    /**
     * @var Collection<int, Prestation>
     */
    #[ORM\OneToMany(targetEntity: Prestation::class, mappedBy: 'categorie')]
    private Collection $prestations;

    /**
     * @var Collection<int, Entrepreneur>
     */
    #[ORM\ManyToMany(targetEntity: Entrepreneur::class, mappedBy: 'categories')]
    private Collection $entrepreneurs;

    public function __construct()
    {
        $this->prestations = new ArrayCollection();
        $this->entrepreneurs = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): static
    {
        $this->type = $type;

        return $this;
    }

    /**
     * @return Collection<int, Prestation>
     */
    public function getPrestations(): Collection
    {
        return $this->prestations;
    }

    public function addPrestation(Prestation $prestation): static
    {
        if (!$this->prestations->contains($prestation)) {
            $this->prestations->add($prestation);
            $prestation->setCategorie($this);
        }

        return $this;
    }

    public function removePrestation(Prestation $prestation): static
    {
        if ($this->prestations->removeElement($prestation)) {
            // set the owning side to null (unless already changed)
            if ($prestation->getCategorie() === $this) {
                $prestation->setCategorie(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Entrepreneur>
     */
    public function getEntrepreneurs(): Collection
    {
        return $this->entrepreneurs;
    }

    public function addEntrepreneur(Entrepreneur $entrepreneur): static
    {
        if (!$this->entrepreneurs->contains($entrepreneur)) {
            $this->entrepreneurs->add($entrepreneur);
            $entrepreneur->addCategory($this);
        }

        return $this;
    }

    public function removeEntrepreneur(Entrepreneur $entrepreneur): static
    {
        if ($this->entrepreneurs->removeElement($entrepreneur)) {
            $entrepreneur->removeCategory($this);
        }

        return $this;
    }

    public function __toString(): string
    {
        return $this->type ?? '';
    }
}
