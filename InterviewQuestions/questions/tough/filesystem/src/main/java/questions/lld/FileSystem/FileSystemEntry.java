package questions.lld.FileSystem;

/**
 * Abstract base for file system entries (Composite pattern).
 * Both File and Directory extend this.
 */
public abstract class FileSystemEntry {

    private String name;
    private Directory parent;

    protected FileSystemEntry(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name must not be blank");
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Directory getParent() { return parent; }
    public void setParent(Directory parent) { this.parent = parent; }

    /** Returns the size in bytes. Directories compute this recursively. */
    public abstract long getSize();

    /** Returns true if this entry is a directory. */
    public abstract boolean isDirectory();

    /** Returns the full path from root to this entry. */
    public String getPath() {
        if (parent == null) return "/";
        String parentPath = parent.getPath();
        return parentPath.equals("/") ? "/" + name : parentPath + "/" + name;
    }

    /** Prints a tree representation with the given indentation. */
    public abstract void printTree(String indent);

    @Override
    public String toString() {
        return (isDirectory() ? "[DIR] " : "[FILE] ") + name +
               (isDirectory() ? "" : " (" + getSize() + " bytes)");
    }
}
