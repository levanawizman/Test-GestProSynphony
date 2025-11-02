<?php

namespace App\Form;

use App\Entity\Document;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Validator\Constraints\NotBlank;

/**
 * DocumentType - Form for uploading documents (PDFs, diagnostics)
 *
 * Used by inspectors to upload technical documents like DPE,
 * diagnostic reports, etc.
 *
 * @author GestTravaux Pro
 */
class DocumentType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('fileType', ChoiceType::class, [
                'label' => 'Type de document',
                'choices' => [
                    'DPE (Diagnostic de Performance Énergétique)' => 'dpe',
                    'Diagnostic Bruit' => 'diagnostic_bruit',
                    'Diagnostic Amiante' => 'diagnostic_amiante',
                    'Diagnostic Plomb' => 'diagnostic_plomb',
                    'Diagnostic Termites' => 'diagnostic_termites',
                    'Plan de construction' => 'plan',
                    'Rapport d\'inspection' => 'rapport',
                    'Autre document' => 'autre',
                ],
                'attr' => [
                    'class' => 'form-select',
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez sélectionner un type de document',
                    ]),
                ],
            ])
            ->add('libelleDoc', TextType::class, [
                'label' => 'Nom du document',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Ex: DPE Appartement 12',
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez entrer un nom pour le document',
                    ]),
                ],
            ])
            ->add('observation', TextareaType::class, [
                'label' => 'Notes / Commentaires',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 3,
                    'placeholder' => 'Ajoutez des notes sur ce document...',
                ],
                'required' => false,
            ])
            ->add('documentFile', FileType::class, [
                'label' => 'Fichier PDF',
                'mapped' => false,
                'required' => true,
                'attr' => [
                    'class' => 'form-control',
                    'accept' => 'application/pdf',
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez sélectionner un fichier',
                    ]),
                    new File([
                        'maxSize' => '10M',
                        'mimeTypes' => [
                            'application/pdf',
                        ],
                        'mimeTypesMessage' => 'Veuillez télécharger un fichier PDF valide',
                    ])
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Document::class,
        ]);
    }
}
