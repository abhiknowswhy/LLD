package questions.lld;

import questions.lld.GameLeaderboard.*;

import java.util.List;

/**
 * Demo driver for the Game Leaderboard LLD.
 * Exercises game/player registration, score submission, top-K queries,
 * rank lookups, score history, thread-safe concurrent submissions,
 * and validation / immutability checks.
 */
public class Main {
    public static void main(String[] args) {

        // ╔══════════════════════════════════════════════════════════════╗
        // ║                     GAME LEADERBOARD                       ║
        // ╚══════════════════════════════════════════════════════════════╝

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     GAME LEADERBOARD                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── SETUP PHASE ──────────────────────────────────────────────

        System.out.println("\n========== SETUP PHASE ==========\n");

        LeaderboardService service = new LeaderboardService();

        // --- Games ---
        Game chess = new Game("G1", "Chess", "ELO-style: winner gets opponent's lost points");
        Game tetris = new Game("G2", "Tetris", "Points per line cleared, combo multiplier");
        Game trivia = new Game("G3", "Trivia", "10 points per correct answer");
        service.registerGame(chess);
        service.registerGame(tetris);
        service.registerGame(trivia);
        System.out.println("Registered: " + chess);
        System.out.println("Registered: " + tetris);
        System.out.println("Registered: " + trivia);

        // --- Players ---
        Player alice = new Player("P1", "Alice");
        Player bob = new Player("P2", "Bob");
        Player charlie = new Player("P3", "Charlie");
        Player diana = new Player("P4", "Diana");
        Player eve = new Player("P5", "Eve");
        service.registerPlayer(alice);
        service.registerPlayer(bob);
        service.registerPlayer(charlie);
        service.registerPlayer(diana);
        service.registerPlayer(eve);
        System.out.println("Registered: " + alice);
        System.out.println("Registered: " + bob);
        System.out.println("Registered: " + charlie);
        System.out.println("Registered: " + diana);
        System.out.println("Registered: " + eve);

        // ── EXERCISE PHASE ───────────────────────────────────────────

        System.out.println("\n========== EXERCISE PHASE ==========\n");

        // --- Submit Chess scores ---
        System.out.println("--- Submitting Chess Scores ---");
        service.submitScore("P1", "G1", 1500);
        service.submitScore("P2", "G1", 1800);
        service.submitScore("P3", "G1", 1650);
        service.submitScore("P4", "G1", 1900);
        service.submitScore("P5", "G1", 1750);
        System.out.println("5 chess scores submitted");

        // Alice plays more chess rounds
        service.submitScore("P1", "G1", 200);
        service.submitScore("P1", "G1", 350);
        System.out.println("Alice played 2 more chess rounds (total 3 submissions)");

        // --- Submit Tetris scores ---
        System.out.println("\n--- Submitting Tetris Scores ---");
        service.submitScore("P1", "G2", 45000);
        service.submitScore("P2", "G2", 62000);
        service.submitScore("P3", "G2", 38000);
        service.submitScore("P4", "G2", 55000);
        service.submitScore("P5", "G2", 71000);
        System.out.println("5 tetris scores submitted");

        // Eve dominates Tetris with another round
        service.submitScore("P5", "G2", 30000);
        System.out.println("Eve played another Tetris round");

        // --- Submit Trivia scores ---
        System.out.println("\n--- Submitting Trivia Scores ---");
        service.submitScore("P1", "G3", 80);
        service.submitScore("P2", "G3", 90);
        service.submitScore("P3", "G3", 100);
        System.out.println("3 trivia scores submitted (only 3 of 5 players)");

        // --- Top-K Queries ---
        System.out.println("\n--- Top-3 Chess Leaderboard ---");
        List<PlayerScore> chessTop3 = service.getTopPlayers("G1", 3);
        int rank = 1;
        for (PlayerScore ps : chessTop3) {
            System.out.printf("  #%d  %-10s  %,d pts  (%d submissions)%n",
                    rank++, ps.getPlayer().getName(), ps.getTotalPoints(), ps.getHistory().size());
        }

        System.out.println("\n--- Top-3 Tetris Leaderboard ---");
        List<PlayerScore> tetrisTop3 = service.getTopPlayers("G2", 3);
        rank = 1;
        for (PlayerScore ps : tetrisTop3) {
            System.out.printf("  #%d  %-10s  %,d pts  (%d submissions)%n",
                    rank++, ps.getPlayer().getName(), ps.getTotalPoints(), ps.getHistory().size());
        }

        System.out.println("\n--- Full Trivia Leaderboard (top 10, only 3 exist) ---");
        List<PlayerScore> triviaAll = service.getTopPlayers("G3", 10);
        rank = 1;
        for (PlayerScore ps : triviaAll) {
            System.out.printf("  #%d  %-10s  %,d pts%n",
                    rank++, ps.getPlayer().getName(), ps.getTotalPoints());
        }

        // --- Rank Queries ---
        System.out.println("\n--- Rank Queries ---");
        System.out.println("Alice's Chess rank : " + service.getPlayerRank("P1", "G1"));
        System.out.println("Bob's Chess rank   : " + service.getPlayerRank("P2", "G1"));
        System.out.println("Diana's Chess rank : " + service.getPlayerRank("P4", "G1"));
        System.out.println("Eve's Tetris rank  : " + service.getPlayerRank("P5", "G2"));
        System.out.println("Diana's Trivia rank: " + service.getPlayerRank("P4", "G3") + " (not ranked)");

        // --- Score History ---
        System.out.println("\n--- Alice's Chess Score History ---");
        List<Score> aliceChessHistory = service.getPlayerScoreHistory("P1", "G1");
        aliceChessHistory.forEach(s -> System.out.println("  " + s));

        System.out.println("\n--- Eve's Tetris Score History ---");
        List<Score> eveTetrisHistory = service.getPlayerScoreHistory("P5", "G2");
        eveTetrisHistory.forEach(s -> System.out.println("  " + s));

        // --- Real-time Update: score changes rank ---
        System.out.println("\n--- Real-time Update: Charlie surges in Chess ---");
        System.out.println("Charlie's rank before: " + service.getPlayerRank("P3", "G1"));
        service.submitScore("P3", "G1", 2000);
        System.out.println("Charlie scores 2000 more points!");
        System.out.println("Charlie's rank after : " + service.getPlayerRank("P3", "G1"));
        System.out.println("\nUpdated Chess Top-5:");
        List<PlayerScore> chessTop5 = service.getTopPlayers("G1", 5);
        rank = 1;
        for (PlayerScore ps : chessTop5) {
            System.out.printf("  #%d  %-10s  %,d pts%n",
                    rank++, ps.getPlayer().getName(), ps.getTotalPoints());
        }

        // --- Thread-safe Concurrent Submissions ---
        System.out.println("\n--- Thread-safe Concurrent Submissions (10 threads) ---");
        Leaderboard tetrisBoard = service.getLeaderboard("G2");
        long eveBefore = tetrisBoard.getPlayerScore("P5").getTotalPoints();
        System.out.println("Eve's Tetris total before: " + eveBefore);

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> service.submitScore("P5", "G2", 1000));
            threads[i].start();
        }
        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        long eveAfter = tetrisBoard.getPlayerScore("P5").getTotalPoints();
        System.out.println("Eve's Tetris total after : " + eveAfter);
        System.out.println("Expected increase: 10000, Actual: " + (eveAfter - eveBefore));
        System.out.println(eveAfter - eveBefore == 10000 ? "PASS: thread-safe!" : "FAIL: race condition!");

        // ── VERIFICATION PHASE ───────────────────────────────────────

        System.out.println("\n========== VERIFICATION PHASE ==========\n");

        // Immutability checks
        System.out.println("--- Immutability Checks ---");
        try {
            service.getTopPlayers("G1", 3).add(new PlayerScore(alice, chess));
            System.out.println("ERROR: should not be mutable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: getTopPlayers() returns unmodifiable list");
        }

        try {
            aliceChessHistory.add(null);
            System.out.println("ERROR: should not be mutable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: getHistory() returns unmodifiable list");
        }

        try {
            service.getAllGames().clear();
            System.out.println("ERROR: should not be mutable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: getAllGames() returns unmodifiable collection");
        }

        try {
            service.getAllPlayers().clear();
            System.out.println("ERROR: should not be mutable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: getAllPlayers() returns unmodifiable collection");
        }

        // Validation checks
        System.out.println("\n--- Validation Checks ---");
        try {
            new Game("", "Bad", "rules");
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        try {
            new Player(null, "Bad");
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        try {
            new Score(alice, chess, -10, java.time.Instant.now());
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        try {
            service.submitScore("P999", "G1", 100);
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        try {
            service.getTopPlayers("G999", 5);
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        try {
            service.registerGame(chess);
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        // Summary
        System.out.println("\n========== SUMMARY ==========");
        System.out.println("Games   : " + service.getAllGames().size());
        System.out.println("Players : " + service.getAllPlayers().size());
        System.out.println("Chess   : " + service.getLeaderboard("G1").size() + " players ranked");
        System.out.println("Tetris  : " + service.getLeaderboard("G2").size() + " players ranked");
        System.out.println("Trivia  : " + service.getLeaderboard("G3").size() + " players ranked");

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               GAME LEADERBOARD — DEMO DONE                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}