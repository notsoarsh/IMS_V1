package src.command;

import src.datastructures.CustomStack;

/**
 * Manages the execution, undoing, and redoing of commands
 */
public class CommandManager {
    private CustomStack<Command> undoStack;
    private CustomStack<Command> redoStack;
    
    public CommandManager() {
        undoStack = new CustomStack<>();
        redoStack = new CustomStack<>();
    }
    
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo stack when a new command is executed
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    public void undo() {
        if (canUndo()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }
    
    public void redo() {
        if (canRedo()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }
}
