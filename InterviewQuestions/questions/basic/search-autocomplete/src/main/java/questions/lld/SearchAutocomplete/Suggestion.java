package questions.lld.SearchAutocomplete;

/**
 * Represents a search suggestion with its frequency.
 */
public class Suggestion implements Comparable<Suggestion> {
    private final String sentence;
    private final int frequency;

    public Suggestion(String sentence, int frequency) {
        this.sentence = sentence;
        this.frequency = frequency;
    }

    public String getSentence() { return sentence; }
    public int getFrequency() { return frequency; }

    /**
     * Higher frequency first; ties broken alphabetically.
     */
    @Override
    public int compareTo(Suggestion other) {
        if (this.frequency != other.frequency) {
            return Integer.compare(other.frequency, this.frequency); // descending
        }
        return this.sentence.compareTo(other.sentence); // ascending alphabetical
    }

    @Override
    public String toString() {
        return sentence + " (" + frequency + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Suggestion other)) return false;
        return sentence.equals(other.sentence);
    }

    @Override
    public int hashCode() {
        return sentence.hashCode();
    }
}
