package com.koerber.inventory.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryHandlerFactory {

    private final StandardInventoryHandler standardInventoryHandler;

    public InventoryHandlerFactory(StandardInventoryHandler standardInventoryHandler) {
        this.standardInventoryHandler = standardInventoryHandler;
    }

    public InventoryHandler getHandler(String type) {
        // Currently returns standard handler
        // Can be extended for other types (e.g., "PERISHABLE", "SEASONAL")
        switch (type.toUpperCase()) {
            case "STANDARD":
            default:
                return standardInventoryHandler;
        }
    }

    public InventoryHandler getDefaultHandler() {
        return standardInventoryHandler;
    }
}
