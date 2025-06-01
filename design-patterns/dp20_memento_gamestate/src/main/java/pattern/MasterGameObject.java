package pattern;

public class MasterGameObject {
    private GameState gameState;

    public MasterGameObject(GameState initialState) {
        this.gameState = initialState;
    }

    public GameMemento<GameState> getCurrentState() {
        return new GameMemento<>(gameState);
    }

    public void restoreState(GameMemento<GameState> savedState) {
        if (savedState != null) {
            this.gameState = savedState.getState();
        } else {
            throw new IllegalArgumentException("Invalid memento object");
        }
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public GameState getGameState() {
        return this.gameState;
    }
}
