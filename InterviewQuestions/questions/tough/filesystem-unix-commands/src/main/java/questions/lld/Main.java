package questions.lld;

import questions.lld.UnixFS.*;

/**
 * Demonstrates Unix-like commands on an in-memory file system.
 *
 * Features:
 * - Commands: pwd, ls, cd, mkdir, touch, echo, cat, grep, find, rm, mv, wc
 * - Working directory tracking
 * - Relative and absolute path support
 * - Command pattern for extensibility
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Unix File System Commands Demo ===\n");

        Shell shell = new Shell();

        shell.execute("pwd");
        shell.execute("mkdir /home");
        shell.execute("mkdir /home/user");
        shell.execute("mkdir /home/user/docs");
        shell.execute("cd /home/user");
        shell.execute("pwd");

        shell.execute("touch notes.txt");
        shell.execute("echo notes.txt Hello World!");
        shell.execute("echo notes.txt This is line two.");
        shell.execute("touch readme.md");
        shell.execute("echo readme.md # My Project");
        shell.execute("echo readme.md A sample readme file.");

        shell.execute("ls");
        shell.execute("cat notes.txt");

        shell.execute("cd docs");
        shell.execute("touch log.txt");
        shell.execute("echo log.txt ERROR: something failed");
        shell.execute("echo log.txt INFO: all good");
        shell.execute("echo log.txt ERROR: another failure");
        shell.execute("pwd");

        System.out.println("\n--- grep for ERROR ---");
        shell.execute("grep log.txt ERROR");

        System.out.println("\n--- find from /home ---");
        shell.execute("find /home *.txt");

        System.out.println("\n--- wc ---");
        shell.execute("wc log.txt");

        System.out.println("\n--- mv and rm ---");
        shell.execute("cd /home/user");
        shell.execute("mv notes.txt docs/notes_moved.txt");
        shell.execute("ls docs");
        shell.execute("rm readme.md");
        shell.execute("ls");

        System.out.println("\n=== Unix Commands Demo Complete ===");
    }
}