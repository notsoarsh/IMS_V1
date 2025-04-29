package src.command;

import src.InventoryItem;
import src.InventoryManager;

/**
 * Command for adding an item to inventory
 */
public class AddItemCommand implements Command {
    private InventoryManager manager;
    private InventoryItem item;
    
    public AddItemCommand(InventoryManager manager, InventoryItem item) {
        this.manager = manager;
        this.item = item;
    }
    
    @Override
    public void execute() {
        manager.addItem(item);
    }
    
    @Override
    public void undo() {
        manager.deleteItem(item.getItemId());
    }
}
