package pattern;

public final class GameMemento<T> {
    private final T state;

    GameMemento(T state) {
        this.state = state;
    }

    T getState() {
        return state;
    }
}
