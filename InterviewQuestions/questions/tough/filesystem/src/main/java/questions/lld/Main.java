package questions.lld;

import questions.lld.FileSystem.*;

/**
 * Demonstrates an in-memory hierarchical file system.
 *
 * Features:
 * - Composite pattern: File and Directory share a common FileSystemEntry interface
 * - Create, delete, move, search operations
 * - Size calculation (recursive for directories)
 * - Path-based navigation
 * - Tree display
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== File System Demo ===\n");

        FileSystem fs = new FileSystem();

        // Create directory structure
        fs.mkdir("/home");
        fs.mkdir("/home/alice");
        fs.mkdir("/home/alice/documents");
        fs.mkdir("/home/alice/photos");
        fs.mkdir("/home/bob");
        fs.mkdir("/etc");

        // Create files
        fs.createFile("/home/alice/documents/resume.txt", 1024);
        fs.createFile("/home/alice/documents/notes.txt", 512);
        fs.createFile("/home/alice/photos/vacation.jpg", 4096);
        fs.createFile("/home/bob/readme.md", 256);
        fs.createFile("/etc/config.yaml", 128);

        // Display tree
        System.out.println("--- File System Tree ---");
        fs.printTree();

        // Size calculations
        System.out.println("\n--- Sizes ---");
        System.out.println("/home/alice size: " + fs.getSize("/home/alice") + " bytes");
        System.out.println("/home size: " + fs.getSize("/home") + " bytes");
        System.out.println("/ (total): " + fs.getSize("/") + " bytes");

        // List directory
        System.out.println("\n--- List /home/alice/documents ---");
        fs.ls("/home/alice/documents").forEach(e -> System.out.println("  " + e));

        // Search
        System.out.println("\n--- Search for '*.txt' ---");
        fs.search("/", "*.txt").forEach(path -> System.out.println("  " + path));

        // Move
        System.out.println("\n--- Move resume.txt to /home/bob ---");
        fs.move("/home/alice/documents/resume.txt", "/home/bob");

        // Delete
        System.out.println("--- Delete /home/alice/photos ---");
        fs.delete("/home/alice/photos");

        System.out.println("\n--- Updated Tree ---");
        fs.printTree();

        System.out.println("\n=== File System Demo Complete ===");
    }
}