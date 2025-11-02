<?php

namespace App\Entity;

use App\Repository\ChantierRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * Chantier entity - Construction sites
 *
 * Represents construction/renovation sites with assigned inspectors,
 * quotes, and associated documents
 *
 * Status values:
 * - 0: En attente (Pending)
 * - 1: En cours (In progress)
 * - 2: Terminé (Completed)
 * - 3: Annulé (Cancelled)
 *
 * @author GestTravaux Pro
 */
#[ORM\Entity(repositoryClass: ChantierRepository::class)]
#[ORM\Table(name: 'chantiers')]
class Chantier
{
    public const STATUS_PENDING = 0;
    public const STATUS_IN_PROGRESS = 1;
    public const STATUS_COMPLETED = 2;
    public const STATUS_CANCELLED = 3;

    public const STATUS_LABELS = [
        self::STATUS_PENDING => 'En attente',
        self::STATUS_IN_PROGRESS => 'En cours',
        self::STATUS_COMPLETED => 'Terminé',
        self::STATUS_CANCELLED => 'Annulé',
    ];

    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'idChantier')]
    private ?int $id = null;

    #[ORM\Column(length: 45)]
    private ?string $villeChantier = null;

    #[ORM\Column(length: 255)]
    private ?string $adresseChantier = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $infoChantier = null;

    #[ORM\Column]
    private ?int $statutChantier = self::STATUS_PENDING;

    #[ORM\ManyToOne(targetEntity: Inspecteur::class, inversedBy: 'chantiers')]
    #[ORM\JoinColumn(name: 'inspecteurs_idInspecteur', referencedColumnName: 'idInspecteur')]
    private ?Inspecteur $inspecteur = null;

    #[ORM\ManyToOne(targetEntity: Bien::class, inversedBy: 'chantiers')]
    #[ORM\JoinColumn(name: 'biens_idBien', referencedColumnName: 'idBien', nullable: false)]
    private ?Bien $bien = null;

    #[ORM\ManyToOne(targetEntity: Document::class, inversedBy: 'chantiers')]
    #[ORM\JoinColumn(name: 'documents_idDocuments', referencedColumnName: 'idDocuments')]
    private ?Document $document = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $documents_libelleDoc = null;

    /**
     * @var Collection<int, Devis>
     */
    #[ORM\OneToMany(targetEntity: Devis::class, mappedBy: 'chantier')]
    private Collection $devis;

    public function __construct()
    {
        $this->devis = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getVilleChantier(): ?string
    {
        return $this->villeChantier;
    }

    public function setVilleChantier(string $villeChantier): static
    {
        $this->villeChantier = $villeChantier;

        return $this;
    }

    public function getAdresseChantier(): ?string
    {
        return $this->adresseChantier;
    }

    public function setAdresseChantier(string $adresseChantier): static
    {
        $this->adresseChantier = $adresseChantier;

        return $this;
    }

    public function getInfoChantier(): ?string
    {
        return $this->infoChantier;
    }

    public function setInfoChantier(?string $infoChantier): static
    {
        $this->infoChantier = $infoChantier;

        return $this;
    }

    public function getStatutChantier(): ?int
    {
        return $this->statutChantier;
    }

    public function setStatutChantier(int $statutChantier): static
    {
        $this->statutChantier = $statutChantier;

        return $this;
    }

    /**
     * Get status label
     */
    public function getStatutLabel(): string
    {
        return self::STATUS_LABELS[$this->statutChantier] ?? 'Inconnu';
    }

    /**
     * Get status badge CSS class
     */
    public function getStatutBadgeClass(): string
    {
        return match($this->statutChantier) {
            self::STATUS_PENDING => 'badge bg-warning text-dark',
            self::STATUS_IN_PROGRESS => 'badge bg-primary',
            self::STATUS_COMPLETED => 'badge bg-success',
            self::STATUS_CANCELLED => 'badge bg-secondary',
            default => 'badge bg-secondary',
        };
    }

    public function getInspecteur(): ?Inspecteur
    {
        return $this->inspecteur;
    }

    public function setInspecteur(?Inspecteur $inspecteur): static
    {
        $this->inspecteur = $inspecteur;

        return $this;
    }

    public function getBien(): ?Bien
    {
        return $this->bien;
    }

    public function setBien(?Bien $bien): static
    {
        $this->bien = $bien;

        return $this;
    }

    public function getDocument(): ?Document
    {
        return $this->document;
    }

    public function setDocument(?Document $document): static
    {
        $this->document = $document;

        return $this;
    }

    public function getDocumentsLibelleDoc(): ?string
    {
        return $this->documents_libelleDoc;
    }

    public function setDocumentsLibelleDoc(?string $documents_libelleDoc): static
    {
        $this->documents_libelleDoc = $documents_libelleDoc;

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
            $devi->setChantier($this);
        }

        return $this;
    }

    public function removeDevi(Devis $devi): static
    {
        if ($this->devis->removeElement($devi)) {
            // set the owning side to null (unless already changed)
            if ($devi->getChantier() === $this) {
                $devi->setChantier(null);
            }
        }

        return $this;
    }

    /**
     * Get full address for display
     */
    public function getFullAddress(): string
    {
        return $this->adresseChantier . ', ' . $this->villeChantier;
    }

    public function __toString(): string
    {
        return 'Chantier #' . $this->id . ' - ' . $this->getFullAddress();
    }
}
