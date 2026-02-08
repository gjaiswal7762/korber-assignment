package com.koerber.inventory.handler;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * Provider for managing multiple Abstract Factory implementations.
 * Acts as a registry and factory selector for different inventory handling strategies.
 * 
 * Usage Example:
 * InventoryHandler handler = factoryProvider.getHandler("STANDARD");
 * InventoryHandler lifoHandler = factoryProvider.getHandler("LIFO");
 */
@Component
public class InventoryHandlerFactoryProvider {

    private final Map<String, AbstractInventoryHandlerFactory> factories;
    private final String defaultHandlerType = "STANDARD";

    public InventoryHandlerFactoryProvider(
            StandardInventoryHandlerFactory standardFactory,
            LifoInventoryHandlerFactory lifoFactory) {
        
        this.factories = new HashMap<>();
        registerFactory(standardFactory);
        registerFactory(lifoFactory);
    }

    /**
     * Register a new factory implementation.
     * Enables runtime addition of new handling strategies without modifying this class.
     * 
     * @param factory The abstract factory to register
     */
    public void registerFactory(AbstractInventoryHandlerFactory factory) {
        factories.put(factory.getHandlerType(), factory);
    }

    /**
     * Get a handler for the specified strategy type.
     * 
     * @param strategyType The strategy type (e.g., "STANDARD", "LIFO")
     * @return InventoryHandler instance for the specified strategy
     * @throws IllegalArgumentException if strategy type not found
     */
    public InventoryHandler getHandler(String strategyType) {
        AbstractInventoryHandlerFactory factory = factories.get(strategyType.toUpperCase());
        if (factory == null) {
            throw new IllegalArgumentException(
                    "Unknown inventory handler strategy: " + strategyType + 
                    ". Available strategies: " + factories.keySet());
        }
        return factory.createHandler();
    }

    /**
     * Get the default handler (STANDARD - FIFO with earliest expiry first).
     * 
     * @return Default InventoryHandler instance
     */
    public InventoryHandler getDefaultHandler() {
        return getHandler(defaultHandlerType);
    }

    /**
     * Get all registered handler types.
     * Useful for dynamic UI or logging purposes.
     * 
     * @return Set of registered handler type names
     */
    public java.util.Set<String> getAvailableHandlerTypes() {
        return factories.keySet();
    }
}
