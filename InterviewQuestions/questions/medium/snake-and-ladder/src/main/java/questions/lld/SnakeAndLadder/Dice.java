package questions.lld.SnakeAndLadder;

public class Dice {
    private final int numberOfDice;
    private final int faces;

    public Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
        this.faces = 6;
    }

    public int roll() {
        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            total += (int) (Math.random() * faces) + 1;
        }
        return total;
    }

    /** Deterministic roll for testing */
    public static int fixedRoll(int value) {
        return value;
    }
}
