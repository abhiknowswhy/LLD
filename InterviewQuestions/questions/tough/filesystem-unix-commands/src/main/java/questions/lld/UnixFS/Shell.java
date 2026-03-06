package questions.lld.UnixFS;

import java.util.ArrayList;
import java.util.List;

/**
 * Unix-like shell that interprets text commands on an in-memory file system.
 *
 * Supported commands:
 * - pwd                       — print working directory
 * - ls [path]                 — list directory contents
 * - cd path                   — change directory
 * - mkdir path                — create directory
 * - touch filename            — create empty file
 * - echo filename content     — append a line to a file
 * - cat filename              — print file contents
 * - grep filename pattern     — search lines matching pattern
 * - find basePath pattern     — find files matching glob pattern
 * - rm name                   — remove file or empty directory
 * - mv source dest            — move/rename a file
 * - wc filename               — word count (lines, words, chars)
 */
public class Shell {

    private final DirNode root;
    private DirNode cwd;

    public Shell() {
        root = new DirNode("");
        cwd = root;
    }

    /** Executes a single command string. */
    public void execute(String commandLine) {
        String[] tokens = commandLine.trim().split("\\s+", 3);
        String cmd = tokens[0];
        System.out.println("$ " + commandLine);
        try {
            switch (cmd) {
                case "pwd" -> doPwd();
                case "ls" -> doLs(tokens.length > 1 ? tokens[1] : null);
                case "cd" -> doCd(requireArg(tokens, 1, "cd"));
                case "mkdir" -> doMkdir(requireArg(tokens, 1, "mkdir"));
                case "touch" -> doTouch(requireArg(tokens, 1, "touch"));
                case "echo" -> doEcho(requireArg(tokens, 1, "echo"), tokens.length > 2 ? tokens[2] : "");
                case "cat" -> doCat(requireArg(tokens, 1, "cat"));
                case "grep" -> doGrep(requireArg(tokens, 1, "grep"), tokens.length > 2 ? tokens[2] : "");
                case "find" -> doFind(requireArg(tokens, 1, "find"), tokens.length > 2 ? tokens[2] : "*");
                case "rm" -> doRm(requireArg(tokens, 1, "rm"));
                case "mv" -> doMv(requireArg(tokens, 1, "mv"), tokens.length > 2 ? tokens[2] : null);
                case "wc" -> doWc(requireArg(tokens, 1, "wc"));
                default -> System.out.println("  Unknown command: " + cmd);
            }
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private void doPwd() { System.out.println("  " + cwd.getPath()); }

    private void doLs(String path) {
        DirNode dir = (path != null) ? resolveDir(path) : cwd;
        dir.listAll().forEach(e -> System.out.println("  " + e));
    }

    private void doCd(String path) {
        if (path.equals("..")) {
            cwd = cwd.getParent();
        } else {
            cwd = resolveDir(path);
        }
    }

    private void doMkdir(String path) {
        if (path.startsWith("/")) {
            // absolute path — create recursively
            String[] parts = path.substring(1).split("/");
            DirNode current = root;
            for (String part : parts) {
                DirNode sub = current.getSubdirectory(part);
                if (sub == null) {
                    sub = new DirNode(part);
                    current.addSubdirectory(sub);
                }
                current = sub;
            }
        } else {
            DirNode dir = new DirNode(path);
            cwd.addSubdirectory(dir);
        }
    }

    private void doTouch(String name) {
        cwd.addFile(new FileNode(name));
    }

    private void doEcho(String filename, String content) {
        FileNode file = resolveFile(filename);
        file.appendLine(content);
    }

    private void doCat(String filename) {
        FileNode file = resolveFile(filename);
        file.getLines().forEach(l -> System.out.println("  " + l));
    }

    private void doGrep(String filename, String pattern) {
        FileNode file = resolveFile(filename);
        file.getLines().stream()
                .filter(l -> l.contains(pattern))
                .forEach(l -> System.out.println("  " + l));
    }

    private void doFind(String basePath, String pattern) {
        DirNode base = resolveDir(basePath);
        List<String> results = new ArrayList<>();
        findRecursive(base, pattern, results);
        results.forEach(r -> System.out.println("  " + r));
    }

    private void doRm(String name) {
        if (cwd.getFile(name) != null) {
            cwd.removeFile(name);
        } else if (cwd.getSubdirectory(name) != null) {
            cwd.removeSubdirectory(name);
        } else {
            System.out.println("  Not found: " + name);
        }
    }

    private void doMv(String source, String dest) {
        if (dest == null) throw new IllegalArgumentException("mv requires source and destination");
        FileNode file = cwd.getFile(source);
        if (file == null) throw new IllegalArgumentException("File not found: " + source);
        cwd.removeFile(source);

        // If dest contains '/', parse directory and new name
        int lastSlash = dest.lastIndexOf('/');
        if (lastSlash >= 0) {
            String dirPath = dest.substring(0, lastSlash);
            String newName = dest.substring(lastSlash + 1);
            DirNode destDir = resolveDir(dirPath);
            destDir.addFile(new FileNode(newName));
            FileNode newFile = destDir.getFile(newName);
            file.getLines().forEach(newFile::appendLine);
        } else {
            // rename in place
            FileNode newFile = new FileNode(dest);
            file.getLines().forEach(newFile::appendLine);
            cwd.addFile(newFile);
        }
    }

    private void doWc(String filename) {
        FileNode file = resolveFile(filename);
        System.out.println("  " + file.lineCount() + " lines, " +
                file.wordCount() + " words, " + file.charCount() + " chars");
    }

    // --- Helpers ---

    private DirNode resolveDir(String path) {
        if (path.equals("/")) return root;
        String[] parts;
        DirNode current;
        if (path.startsWith("/")) {
            parts = path.substring(1).split("/");
            current = root;
        } else {
            parts = path.split("/");
            current = cwd;
        }
        for (String part : parts) {
            if (part.equals("..")) {
                current = current.getParent();
            } else {
                DirNode sub = current.getSubdirectory(part);
                if (sub == null) throw new IllegalArgumentException("Directory not found: " + part);
                current = sub;
            }
        }
        return current;
    }

    private FileNode resolveFile(String name) {
        FileNode file = cwd.getFile(name);
        if (file == null) throw new IllegalArgumentException("File not found: " + name);
        return file;
    }

    private void findRecursive(DirNode dir, String pattern, List<String> results) {
        for (FileNode file : dir.getFiles().values()) {
            if (matchGlob(file.getName(), pattern)) {
                String path = dir.getPath();
                results.add((path.equals("/") ? "/" : path + "/") + file.getName());
            }
        }
        for (DirNode sub : dir.getSubdirectories().values()) {
            findRecursive(sub, pattern, results);
        }
    }

    private boolean matchGlob(String name, String pattern) {
        if (pattern.equals("*")) return true;
        if (pattern.startsWith("*")) return name.endsWith(pattern.substring(1));
        return name.equals(pattern);
    }

    private String requireArg(String[] tokens, int idx, String cmdName) {
        if (tokens.length <= idx) throw new IllegalArgumentException(cmdName + " requires an argument");
        return tokens[idx];
    }
}
