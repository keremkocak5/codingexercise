
# Package API

A simple in-memory app for storing packages, product details, and an embedded currency converter.



## Running the application

### a. Docker Image  
Make sure you have Java 17, Maven, and Docker installed on your device. Then run the following command at the root folder (where Dockerfile resides) of your project.

mvn clean package

docker build -t codingexercice .

docker run -p 8080:8080 codingexercice

### b. Maven

Run the following command 

mvn spring-boot:run -Dspring-boot.run.profiles=dev

## Accessing the APIs

http://localhost:8080/codingexercise/swagger-ui/index.html


## Dependencies
Due to its in-memory database, this app is not horizontally scalable.
If Frankfurter API or Products API become unresponsive, the coding exercise will fail. No fallback / resilience mechanisms implemented. 


Technical Integration Document for Package Management API
Overview
This API provides various operations to manage packages, including creating, updating, retrieving, and deleting package information. It uses OpenAPI 3.0.1 specifications and is built for interacting with a server at http://localhost:8080/codingexercise.

The following endpoints are provided:

Get a package by ID

Update a package by ID

Delete a package by ID

Get a package by ID and currency

Update a package by ID and currency

Get all packages for a given currency

Create a package for a given currency

Get all packages

Create a package

Base URL
The base URL for the API is:

http://localhost:8080/codingexercise/v1/packages

Authentication
This API does not currently require any authentication mechanisms such as API keys or OAuth.

Endpoints
1. Get a Package by ID
   URL: /v1/packages/id/{id}

Method: GET

Parameters:

id (Path parameter, required): The ID of the package (string, length: 36).

Response:

200 OK – Returns a PackageResponse object containing details of the package.

2. Update a Package by ID
   URL: /v1/packages/id/{id}

Method: POST

Parameters:

id (Path parameter, required): The ID of the package (string, length: 36).

Request Body:

PackageRequest:

name (string, required, minLength: 2, maxLength: 100)

description (string, required, minLength: 2, maxLength: 100)

productIds (array of strings, required): List of product IDs associated with the package.

Response:

200 OK – Returns a PackageResponse object with updated package details.

3. Delete a Package by ID
   URL: /v1/packages/id/{id}

Method: DELETE

Parameters:

id (Path parameter, required): The ID of the package (string, length: 36).

Response:

200 OK – Returns a boolean indicating the success or failure of the deletion.

4. Get a Package by ID and Currency
   URL: /v1/packages/id/{id}/currency/{currency}

Method: GET

Parameters:

id (Path parameter, required): The ID of the package (string, length: 36).

currency (Path parameter, required): The currency code (string, one of: AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD, HUF, IDR, ILS, INR, ISK, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PLN, RON, SEK, SGD, THB, TRY, USD, ZAR).

Response:

200 OK – Returns a PackageResponse object.

5. Update a Package by ID and Currency
   URL: /v1/packages/id/{id}/currency/{currency}

Method: POST

Parameters:

id (Path parameter, required): The ID of the package (string, length: 36).

currency (Path parameter, required): The currency code (string).

Request Body:

PackageRequest: Same as the update request body mentioned above.

Response:

200 OK – Returns a PackageResponse object with updated package details.

6. Get All Packages for a Currency
   URL: /v1/packages/currency/{currency}

Method: GET

Parameters:

currency (Path parameter, required): The currency code (string).

Response:

200 OK – Returns an array of PackageResponse objects.

7. Create a Package for a Currency
   URL: /v1/packages/currency/{currency}

Method: POST

Parameters:

currency (Path parameter, required): The currency code (string).

Request Body:

PackageRequest: Same as the package creation request body.

Response:

200 OK – Returns a PackageResponse object containing the created package.

8. Get All Packages
   URL: /v1/packages/

Method: GET

Response:

200 OK – Returns an array of PackageResponse objects.

9. Create a Package
   URL: /v1/packages/

Method: POST

Request Body:

PackageRequest: Same as the package creation request body.

Response:

200 OK – Returns a PackageResponse object containing the created package.

Data Models
PackageRequest
A request body to create or update a package. Contains the following properties:

name (string, required, minLength: 2, maxLength: 100): The name of the package.

description (string, required, minLength: 2, maxLength: 100): A short description of the package.

productIds (array of strings, required): A list of product IDs associated with the package.

PackageResponse
A response model that represents a package. Contains the following properties:

id (string): The ID of the package.

name (string): The name of the package.

description (string): A description of the package.

products (array of ProductResponse): A list of products associated with the package.

totalPrice (number): The total price of the package.

currencyCode (string): The currency code used for the price.

ProductResponse
A response model for a product. Contains the following properties:

id (string): The ID of the product.

name (string): The name of the product.

price (number): The price of the product.

currencyCode (string): The currency code used for the product price.

Example API Calls
Example 1: Get Package by ID
http
Copy
GET /v1/packages/id/123e4567-e89b-12d3-a456-426614174000
Example 2: Update Package
http
Copy
POST /v1/packages/id/123e4567-e89b-12d3-a456-426614174000
Content-Type: application/json

{
"name": "Updated Package",
"description": "This is an updated package description.",
"productIds": ["product1", "product2"]
}
Example 3: Create Package for Currency
http
Copy
POST /v1/packages/currency/USD
Content-Type: application/json

{
"name": "New Package",
"description": "A new package description.",
"productIds": ["product1", "product2"]
}
Example 4: Delete Package
http
Copy
DELETE /v1/packages/id/123e4567-e89b-12d3-a456-426614174000
Error Handling
The API provides appropriate HTTP status codes and messages to indicate errors. Here are common error responses:

400 Bad Request: Invalid or missing parameters in the request.

404 Not Found: The specified resource (package or product) was not found.

500 Internal Server Error: A generic error message when an unexpected server issue occurs.
