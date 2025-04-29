package src.command;

import src.InventoryItem;
import src.InventoryManager;

/**
 * Command for updating an item in inventory
 */
public class UpdateItemCommand implements Command {
    private InventoryManager manager;
    private InventoryItem oldItem;
    private InventoryItem newItem;
    private String itemId;
    
    public UpdateItemCommand(InventoryManager manager, String itemId, InventoryItem newItem) {
        this.manager = manager;
        this.itemId = itemId;
        this.oldItem = manager.viewItem(itemId);
        this.newItem = newItem;
    }
    
    @Override
    public void execute() {
        manager.updateItem(itemId, newItem);
    }
    
    @Override
    public void undo() {
        if (oldItem != null) {
            manager.updateItem(itemId, oldItem);
        }
    }
}
