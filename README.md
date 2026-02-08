# korber-assignment
Order-Inventory Microservices
JDK: 17
Spring Boot Version: 3.2.0

Total poms: 3
1 parent pom for order-inventory (Build this pom.xml it will build inventory and order)
2 microservice pom Inventory(port: 8081) and Order(port:8082)

After build run both the microservices seperately, then start with the API calls.

It uses H2 DB uld for both are as below:
http://localhost:8081/h2-console (JDBC URL: jdbc:h2:mem:inventorydb, user: sa, password: )
http://localhost:8082/h2-console (JDBC URL: jdbc:h2:mem:orderdb, user: sa, password: )

End points for Inventory:
GET: http://localhost:8081/inventory/{productId}
POST: http://localhost:8081/inventory/update
Example for POST JSON DATA
{
    "productId": 1001,
    "batchIds": [1],
    "quantities": [10]
}
OUTPUT: Inventory updated successfully

End Point for Order:
POST: http://localhost:8082/order 
Example for POST JSON DATA
{
    "productId": 1001,
    "quantity": 10
}
OUTPUT:
{
    "orderId": 11,
    "productId": 1001,
    "productName": "Laptop",
    "quantity": 10,
    "status": "PLACED",
    "reservedFromBatchIds": [
        1
    ],
    "message": "Order placed. Inventory reserved."
}

