package com.koerber.inventory.handler;

/**
 * Abstract Factory interface for creating inventory handlers.
 * Defines the contract for concrete factories that produce specific handler implementations.
 * Supports extensibility for multiple product families and handling strategies.
 */
public interface AbstractInventoryHandlerFactory {
    
    /**
     * Creates and returns an InventoryHandler instance.
     * @return A concrete implementation of InventoryHandler
     */
    InventoryHandler createHandler();
    
    /**
     * Returns the type/strategy name of the handler this factory creates.
     * @return Strategy name (e.g., "STANDARD", "LIFO", "EXPRESS")
     */
    String getHandlerType();
}
