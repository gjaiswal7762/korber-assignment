package com.koerber.inventory.handler;

import org.springframework.stereotype.Component;

/**
 * Concrete Abstract Factory for LIFO inventory handling.
 * Creates LifoInventoryHandler instances that prioritize latest expiry dates.
 */
@Component
public class LifoInventoryHandlerFactory implements AbstractInventoryHandlerFactory {

    private final LifoInventoryHandler lifoInventoryHandler;

    public LifoInventoryHandlerFactory(LifoInventoryHandler lifoInventoryHandler) {
        this.lifoInventoryHandler = lifoInventoryHandler;
    }

    @Override
    public InventoryHandler createHandler() {
        return lifoInventoryHandler;
    }

    @Override
    public String getHandlerType() {
        return "LIFO";
    }
}
