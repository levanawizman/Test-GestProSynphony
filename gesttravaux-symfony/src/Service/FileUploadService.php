<?php

namespace App\Service;

use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\String\Slugger\SluggerInterface;

/**
 * FileUploadService - Handles file uploads
 *
 * Service for uploading files (photos, documents) with proper
 * naming and error handling
 *
 * @author GestTravaux Pro
 */
class FileUploadService
{
    public function __construct(
        private string $photoDirectory,
        private string $documentDirectory,
        private SluggerInterface $slugger
    ) {
    }

    /**
     * Upload a photo file
     *
     * @param UploadedFile $file The uploaded file
     * @return string The filename
     * @throws FileException If upload fails
     */
    public function uploadPhoto(UploadedFile $file): string
    {
        return $this->upload($file, $this->photoDirectory);
    }

    /**
     * Upload a document file
     *
     * @param UploadedFile $file The uploaded file
     * @return string The filename
     * @throws FileException If upload fails
     */
    public function uploadDocument(UploadedFile $file): string
    {
        return $this->upload($file, $this->documentDirectory);
    }

    /**
     * Generic file upload method
     *
     * @param UploadedFile $file The uploaded file
     * @param string $targetDirectory The target directory
     * @return string The filename
     * @throws FileException If upload fails
     */
    private function upload(UploadedFile $file, string $targetDirectory): string
    {
        $originalFilename = pathinfo($file->getClientOriginalName(), PATHINFO_FILENAME);
        $safeFilename = $this->slugger->slug($originalFilename);
        $fileName = $safeFilename . '-' . uniqid() . '.' . $file->guessExtension();

        try {
            $file->move($targetDirectory, $fileName);
        } catch (FileException $e) {
            throw new FileException('Erreur lors du téléchargement du fichier: ' . $e->getMessage());
        }

        return $fileName;
    }

    /**
     * Delete a file
     *
     * @param string $filename The filename to delete
     * @param string $type The type ('photo' or 'document')
     * @return bool Success status
     */
    public function deleteFile(string $filename, string $type = 'photo'): bool
    {
        $directory = $type === 'photo' ? $this->photoDirectory : $this->documentDirectory;
        $filepath = $directory . '/' . $filename;

        if (file_exists($filepath)) {
            return unlink($filepath);
        }

        return false;
    }

    /**
     * Get the photo directory path
     *
     * @return string
     */
    public function getPhotoDirectory(): string
    {
        return $this->photoDirectory;
    }

    /**
     * Get the document directory path
     *
     * @return string
     */
    public function getDocumentDirectory(): string
    {
        return $this->documentDirectory;
    }
}
