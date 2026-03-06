package questions.lld.UnixFS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * In-memory file node with line-based content.
 */
public class FileNode {

    private final String name;
    private final List<String> lines = new ArrayList<>();

    public FileNode(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("File name required");
        this.name = name;
    }

    public String getName() { return name; }

    public void appendLine(String line) { lines.add(line); }

    public List<String> getLines() { return Collections.unmodifiableList(lines); }

    public int lineCount() { return lines.size(); }

    public int wordCount() {
        return lines.stream().mapToInt(l -> l.isBlank() ? 0 : l.trim().split("\\s+").length).sum();
    }

    public int charCount() {
        return lines.stream().mapToInt(String::length).sum();
    }
}
