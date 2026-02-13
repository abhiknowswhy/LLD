package questions.lld.Game.Command;

/**
 * Command pattern — encapsulates a move so it can be executed and undone.
 */
public interface MoveCommand {
    void execute();
    void undo();
}
