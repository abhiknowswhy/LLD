package questions.lld.FileSystem;

/**
 * Represents a file in the file system (leaf in Composite pattern).
 */
public class File extends FileSystemEntry {

    private long size;

    public File(String name, long size) {
        super(name);
        if (size < 0) throw new IllegalArgumentException("Size must be non-negative");
        this.size = size;
    }

    @Override
    public long getSize() { return size; }

    public void setSize(long size) { this.size = size; }

    @Override
    public boolean isDirectory() { return false; }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + getName() + " (" + size + " bytes)");
    }
}
