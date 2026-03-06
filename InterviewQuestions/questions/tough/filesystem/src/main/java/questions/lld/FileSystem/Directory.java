package questions.lld.FileSystem;

import java.util.*;

/**
 * Represents a directory in the file system (composite in Composite pattern).
 * Contains child entries (files and subdirectories).
 */
public class Directory extends FileSystemEntry {

    private final Map<String, FileSystemEntry> children = new LinkedHashMap<>();

    public Directory(String name) { super(name); }

    @Override
    public long getSize() {
        return children.values().stream().mapToLong(FileSystemEntry::getSize).sum();
    }

    @Override
    public boolean isDirectory() { return true; }

    /** Adds a child entry to this directory. */
    public void addChild(FileSystemEntry entry) {
        if (children.containsKey(entry.getName())) {
            throw new IllegalArgumentException("Entry '" + entry.getName() + "' already exists in " + getPath());
        }
        entry.setParent(this);
        children.put(entry.getName(), entry);
    }

    /** Removes a child entry by name. */
    public FileSystemEntry removeChild(String name) {
        FileSystemEntry removed = children.remove(name);
        if (removed == null) throw new IllegalArgumentException("No entry named '" + name + "' in " + getPath());
        removed.setParent(null);
        return removed;
    }

    /** Gets a child entry by name. */
    public FileSystemEntry getChild(String name) { return children.get(name); }

    /** Returns all children as an unmodifiable collection. */
    public Collection<FileSystemEntry> getChildren() { return Collections.unmodifiableCollection(children.values()); }

    /** Returns true if this directory has no children. */
    public boolean isEmpty() { return children.isEmpty(); }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + getName() + "/");
        for (FileSystemEntry child : children.values()) {
            child.printTree(indent + "  ");
        }
    }
}
