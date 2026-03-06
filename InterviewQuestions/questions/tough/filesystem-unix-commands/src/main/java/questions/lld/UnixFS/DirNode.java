package questions.lld.UnixFS;

import java.util.*;

/**
 * In-memory directory node for the Unix file system.
 */
public class DirNode {

    private final String name;
    private DirNode parent;
    private final Map<String, DirNode> subdirectories = new LinkedHashMap<>();
    private final Map<String, FileNode> files = new LinkedHashMap<>();

    public DirNode(String name) {
        this.name = name;
        this.parent = this; // root points to itself
    }

    public String getName() { return name; }
    public DirNode getParent() { return parent; }
    public void setParent(DirNode parent) { this.parent = parent; }

    public void addSubdirectory(DirNode dir) {
        if (subdirectories.containsKey(dir.getName()) || files.containsKey(dir.getName())) {
            throw new IllegalArgumentException("'" + dir.getName() + "' already exists");
        }
        dir.setParent(this);
        subdirectories.put(dir.getName(), dir);
    }

    public void addFile(FileNode file) {
        if (files.containsKey(file.getName()) || subdirectories.containsKey(file.getName())) {
            throw new IllegalArgumentException("'" + file.getName() + "' already exists");
        }
        files.put(file.getName(), file);
    }

    public DirNode getSubdirectory(String name) { return subdirectories.get(name); }
    public FileNode getFile(String name) { return files.get(name); }

    public void removeFile(String name) {
        if (files.remove(name) == null) throw new IllegalArgumentException("File '" + name + "' not found");
    }

    public void removeSubdirectory(String name) {
        if (subdirectories.remove(name) == null) throw new IllegalArgumentException("Directory '" + name + "' not found");
    }

    public Collection<String> listAll() {
        List<String> all = new ArrayList<>();
        subdirectories.keySet().forEach(d -> all.add(d + "/"));
        all.addAll(files.keySet());
        return all;
    }

    public Map<String, DirNode> getSubdirectories() { return Collections.unmodifiableMap(subdirectories); }
    public Map<String, FileNode> getFiles() { return Collections.unmodifiableMap(files); }

    /** Returns the absolute path of this directory. */
    public String getPath() {
        if (parent == this) return "/";
        String parentPath = parent.getPath();
        return parentPath.equals("/") ? "/" + name : parentPath + "/" + name;
    }
}
