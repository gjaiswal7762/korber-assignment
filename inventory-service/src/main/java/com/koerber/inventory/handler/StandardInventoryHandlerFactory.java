package com.koerber.inventory.handler;

import org.springframework.stereotype.Component;

/**
 * Concrete Abstract Factory for Standard (FIFO) inventory handling.
 * Creates StandardInventoryHandler instances that prioritize earliest expiry dates.
 */
@Component
public class StandardInventoryHandlerFactory implements AbstractInventoryHandlerFactory {

    private final StandardInventoryHandler standardInventoryHandler;

    public StandardInventoryHandlerFactory(StandardInventoryHandler standardInventoryHandler) {
        this.standardInventoryHandler = standardInventoryHandler;
    }

    @Override
    public InventoryHandler createHandler() {
        return standardInventoryHandler;
    }

    @Override
    public String getHandlerType() {
        return "STANDARD";
    }
}
