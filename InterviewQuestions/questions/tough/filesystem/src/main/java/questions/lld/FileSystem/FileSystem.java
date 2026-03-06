package questions.lld.FileSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory file system with path-based operations.
 *
 * Design:
 * - Composite pattern: File and Directory share FileSystemEntry base
 * - Path resolution by walking the directory tree
 * - Supports: mkdir, createFile, delete, move, ls, search, getSize
 */
public class FileSystem {

    private final Directory root;

    public FileSystem() {
        this.root = new Directory("");
    }

    /** Creates a directory at the given absolute path (mkdir -p behavior). */
    public void mkdir(String path) {
        String[] parts = splitPath(path);
        Directory current = root;
        for (String part : parts) {
            FileSystemEntry child = current.getChild(part);
            if (child == null) {
                Directory newDir = new Directory(part);
                current.addChild(newDir);
                current = newDir;
            } else if (child.isDirectory()) {
                current = (Directory) child;
            } else {
                throw new IllegalArgumentException("'" + part + "' exists as a file, cannot create directory");
            }
        }
    }

    /** Creates a file at the given absolute path with the specified size. */
    public void createFile(String path, long size) {
        int lastSlash = path.lastIndexOf('/');
        String dirPath = path.substring(0, lastSlash);
        String fileName = path.substring(lastSlash + 1);
        if (dirPath.isEmpty()) dirPath = "/";
        Directory parent = resolveDirectory(dirPath);
        parent.addChild(new File(fileName, size));
    }

    /** Deletes a file or directory at the given path. */
    public void delete(String path) {
        FileSystemEntry entry = resolve(path);
        if (entry == root) throw new IllegalArgumentException("Cannot delete root");
        entry.getParent().removeChild(entry.getName());
    }

    /** Moves an entry from one path to another directory. */
    public void move(String sourcePath, String destDirPath) {
        FileSystemEntry entry = resolve(sourcePath);
        if (entry == root) throw new IllegalArgumentException("Cannot move root");
        Directory destDir = resolveDirectory(destDirPath);
        entry.getParent().removeChild(entry.getName());
        destDir.addChild(entry);
    }

    /** Lists entries in the given directory. */
    public List<FileSystemEntry> ls(String path) {
        Directory dir = resolveDirectory(path);
        return new ArrayList<>(dir.getChildren());
    }

    /** Returns the total size of a file or directory. */
    public long getSize(String path) {
        return resolve(path).getSize();
    }

    /** Searches for files matching a glob pattern (e.g., "*.txt") starting from the given path. */
    public List<String> search(String basePath, String pattern) {
        FileSystemEntry base = resolve(basePath);
        List<String> results = new ArrayList<>();
        searchRecursive(base, pattern, results);
        return results;
    }

    /** Prints the entire file system tree. */
    public void printTree() {
        root.printTree("");
    }

    // --- Private helpers ---

    private void searchRecursive(FileSystemEntry entry, String pattern, List<String> results) {
        if (!entry.isDirectory()) {
            if (matchesGlob(entry.getName(), pattern)) results.add(entry.getPath());
        } else {
            Directory dir = (Directory) entry;
            for (FileSystemEntry child : dir.getChildren()) {
                searchRecursive(child, pattern, results);
            }
        }
    }

    private boolean matchesGlob(String name, String pattern) {
        // Simple glob: *.ext or exact match
        if (pattern.startsWith("*")) {
            return name.endsWith(pattern.substring(1));
        }
        return name.equals(pattern);
    }

    private FileSystemEntry resolve(String path) {
        if (path.equals("/")) return root;
        String[] parts = splitPath(path);
        FileSystemEntry current = root;
        for (String part : parts) {
            if (!current.isDirectory()) throw new IllegalArgumentException("'" + part + "' is not a directory");
            current = ((Directory) current).getChild(part);
            if (current == null) throw new IllegalArgumentException("Path not found: " + path);
        }
        return current;
    }

    private Directory resolveDirectory(String path) {
        FileSystemEntry entry = resolve(path);
        if (!entry.isDirectory()) throw new IllegalArgumentException(path + " is not a directory");
        return (Directory) entry;
    }

    private String[] splitPath(String path) {
        if (!path.startsWith("/")) throw new IllegalArgumentException("Path must be absolute: " + path);
        String trimmed = path.substring(1); // remove leading /
        if (trimmed.isEmpty()) return new String[0];
        return trimmed.split("/");
    }
}
