package src.command;

/**
 * Command interface for implementing the Command pattern
 * Used for undo/redo operations
 */
public interface Command {
    void execute();
    void undo();
}
