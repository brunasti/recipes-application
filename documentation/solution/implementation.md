#Implementation


## General consideration

The RecipesApplication provides 2 sets of different RESTful APIs:

- final user oriented APIs as indicated by the requirement
- full CRUD APIs for an admin a monitoring activity on the system

The first set is the real output of the project, 
while the second enable the test and check of the system from a developer perspective 
and are not intended  for an end user access.
Because of usability in the development the CRUD APIs have not been secured, 
but could and should be locked if not completely removed in a production environment.

## Data Model

### Entity-Relationship model
![ER Dao Model](../images/data-model.png)

### Entities

#### Recipe

#### Ingredient

#### RecipeIngredientRelation
This entity implement the relation between the Recipe and the Ingredient, so to allow a N-M cardinality, and storing the quantity of a particular ingredient in the specific recipe.

#### ApplicationUser

This ApplicationUser entity is intended as a placeholder for the corporate IAM system.

The password is not encrypted  just because of not enough time to cover all the security aspects of a full IAM system.

## Packaging

### it.brunasti.abnamro.recipes
### it.brunasti.abnamro.recipes.db
### it.brunasti.abnamro.recipes.exceptions
### it.brunasti.abnamro.recipes.jwt
### it.brunasti.abnamro.recipes.requests
### it.brunasti.abnamro.recipes.responses
### it.brunasti.abnamro.recipes.services


## Query-by-example feature

The implementation of the Query-by-example needed for the search functionality, is very rude, but working.

It's all contained in the <b>retrieveRecipes</b> function within the <b>RecipeService</b> class, where a set of flags are evaluated based on the presence of input parameters.
It's a complex implementation and algorithm, which could be improved and mostly reengineered to make it more flexible and maintainable, but limit of time prevented such optimization.

With a different DB structure it could be easily been delegated to the DAO layer,
or a more complex query schema could be used always via the DAO components,
but for project simplicity and time constrains I preferred to go brute force
with code, which could be even not performance effective.


## Testing with Postman

Postman can be used to test the RecipesApplication APIs.

A Collection of  predefined requests for Postman are provided in the attached repository file:

[Postmen collection](./ABN-AMRO.postman_collection.json)

To execute the test in Postman you first have to create a Token:

0. start the RecipesApplication 
1. in a browser open the URL: http://localhost:8080/
2. 