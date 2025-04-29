package src.command;

import src.InventoryItem;
import src.InventoryManager;

/**
 * Command for deleting an item from inventory
 */
public class DeleteItemCommand implements Command {
    private InventoryManager manager;
    private InventoryItem item;
    private String itemId;
    
    public DeleteItemCommand(InventoryManager manager, String itemId) {
        this.manager = manager;
        this.itemId = itemId;
        this.item = manager.viewItem(itemId);
    }
    
    @Override
    public void execute() {
        manager.deleteItem(itemId);
    }
    
    @Override
    public void undo() {
        if (item != null) {
            manager.addItem(item);
        }
    }
}
