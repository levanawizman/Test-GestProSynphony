<?php

namespace App\Tests\Service;

use App\Service\FileUploadService;
use PHPUnit\Framework\TestCase;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\String\Slugger\AsciiSlugger;

/**
 * FileUploadServiceTest
 *
 * Test cases for FileUploadService
 *
 * @author GestTravaux Pro
 */
class FileUploadServiceTest extends TestCase
{
    private string $photoDirectory;
    private string $documentDirectory;
    private FileUploadService $service;

    protected function setUp(): void
    {
        // Create temporary directories for testing
        $this->photoDirectory = sys_get_temp_dir() . '/test_photos_' . uniqid();
        $this->documentDirectory = sys_get_temp_dir() . '/test_documents_' . uniqid();

        mkdir($this->photoDirectory);
        mkdir($this->documentDirectory);

        $slugger = new AsciiSlugger();
        $this->service = new FileUploadService(
            $this->photoDirectory,
            $this->documentDirectory,
            $slugger
        );
    }

    protected function tearDown(): void
    {
        // Clean up test directories
        $this->removeDirectory($this->photoDirectory);
        $this->removeDirectory($this->documentDirectory);
    }

    /**
     * Test that service returns correct photo directory
     */
    public function testGetPhotoDirectory(): void
    {
        $this->assertEquals($this->photoDirectory, $this->service->getPhotoDirectory());
    }

    /**
     * Test that service returns correct document directory
     */
    public function testGetDocumentDirectory(): void
    {
        $this->assertEquals($this->documentDirectory, $this->service->getDocumentDirectory());
    }

    /**
     * Test photo upload functionality
     */
    public function testUploadPhoto(): void
    {
        // Create a test file
        $testFilePath = sys_get_temp_dir() . '/test_image.jpg';
        file_put_contents($testFilePath, 'test image content');

        $uploadedFile = new UploadedFile(
            $testFilePath,
            'test_image.jpg',
            'image/jpeg',
            null,
            true // Mark as test
        );

        $fileName = $this->service->uploadPhoto($uploadedFile);

        $this->assertNotEmpty($fileName);
        $this->assertStringEndsWith('.jpg', $fileName);
        $this->assertFileExists($this->photoDirectory . '/' . $fileName);

        unlink($testFilePath);
    }

    /**
     * Test document upload functionality
     */
    public function testUploadDocument(): void
    {
        // Create a test PDF file
        $testFilePath = sys_get_temp_dir() . '/test_document.pdf';
        file_put_contents($testFilePath, 'test pdf content');

        $uploadedFile = new UploadedFile(
            $testFilePath,
            'test_document.pdf',
            'application/pdf',
            null,
            true // Mark as test
        );

        $fileName = $this->service->uploadDocument($uploadedFile);

        $this->assertNotEmpty($fileName);
        $this->assertStringEndsWith('.pdf', $fileName);
        $this->assertFileExists($this->documentDirectory . '/' . $fileName);

        unlink($testFilePath);
    }

    /**
     * Test file deletion
     */
    public function testDeleteFile(): void
    {
        // Create a test file
        $testFileName = 'test_file_' . uniqid() . '.jpg';
        $testFilePath = $this->photoDirectory . '/' . $testFileName;
        file_put_contents($testFilePath, 'test content');

        $this->assertFileExists($testFilePath);

        $result = $this->service->deleteFile($testFileName, 'photo');

        $this->assertTrue($result);
        $this->assertFileDoesNotExist($testFilePath);
    }

    /**
     * Test deleting non-existent file
     */
    public function testDeleteNonExistentFile(): void
    {
        $result = $this->service->deleteFile('non_existent_file.jpg', 'photo');

        $this->assertFalse($result);
    }

    /**
     * Test filename sanitization
     */
    public function testFilenameSanitization(): void
    {
        // Create a test file with special characters in name
        $testFilePath = sys_get_temp_dir() . '/test file with spaces.jpg';
        file_put_contents($testFilePath, 'test content');

        $uploadedFile = new UploadedFile(
            $testFilePath,
            'test file with spaces.jpg',
            'image/jpeg',
            null,
            true
        );

        $fileName = $this->service->uploadPhoto($uploadedFile);

        // Filename should be slugified (no spaces)
        $this->assertStringNotContainsString(' ', $fileName);
        $this->assertFileExists($this->photoDirectory . '/' . $fileName);

        unlink($testFilePath);
    }

    /**
     * Helper method to recursively remove directory
     */
    private function removeDirectory(string $dir): void
    {
        if (!is_dir($dir)) {
            return;
        }

        $files = array_diff(scandir($dir), ['.', '..']);
        foreach ($files as $file) {
            $path = $dir . '/' . $file;
            is_dir($path) ? $this->removeDirectory($path) : unlink($path);
        }
        rmdir($dir);
    }
}
