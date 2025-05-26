# My Personal Project

## Rocco's Kitchen

Classes required *for now*:
- Grocery = has a title and have/don't have status
- Pantry = list of Grocery, how many of each
- Store = list of Grocery, has add to pantry option
- Recipes = includes a title, instructions, time needed, and ingredient list
            (ingredient list is a list of Grocery needed for the recipe), has 
            check function to see if all ingredients are acquired, and execute option
            that removes used ingredients from pantry list.
- Recipe Book = list of Recipes, has get/delete recipe function

My project will be a **virtual grocery store and recipe book**
The mini game will allow users to keep an inventory of groceries in their pantries and have the option of using those ingredients in a recipe, provided in the recipe book. Each recipe will have a function to check whether all required ingrediants/groceries are in the inventory. If not, a list of the needed ingredients will be printed out. Executing a recipe will eliminate the used ingrediants from the pantry and more groceries can be acquired from the store.

I believe this project can be used as a way to keep track of real-life household groceries, as food can be sometimes be forgoten and eventually go bad. A shopping list can also be generated for a specific recipe so you are sure to have everything you need for the dish you are craving. I chose this idea because I had recently moved out of my home, and realized the difficulty of remembering everything in my fridge. This eventually leads to a not-so-fun moldy surprise. I think having a way to know exactly what I have and what I dont have will also make shopping for groceries a lot easier.

## User Stories
- As a user, I want to be able to add a grocery to my pantry collection
- As a user, I want to be able to add a recipe to my recipe book
- As a user, I want to be able to store my recipes in the recipe book
- As a user, I want to be able to execute a chosen recipe and have the used ingrediants removed from my pantry
- As a user, I want to be able to see all the recipes I have
- As a user, I want to be able to see the groceries I have in my pantry
- As a user, I want to be able to choose a recipe and know if I have enough ingrediants to execute it
- As a user, I want to be able to save my recipes (if I so choose) 
- As a user, I want to be able to save my reiew for each recipe
- As a user, I want to be able to load my saved recipes (if I so choose) 
- As a user, I want to be able to load my saved review for each recipe (if I so choose)
- As a user, I want to be able to save my pantry (if I so choose)
- As a user, I want to be able to load my pantry (if I so choose)

## Instructions for End User
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by adding a recipe to the recipe book (a list of recipes) with the "Add Recipe" button in the recipe menu.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by searching for a recipe in the recipe book with the "Search for Recipe" button in the recipe menu.
- You can locate my visual component in the saving and loading of the recipe book or pantry, as well as in the making of the recipe.
- You can save the state of my application by pressing the "Save Recipe" or "Save Pantry" button
- You can reload the state of my application by pressing the "Load Recipe" or "Load Pantry" button

## Phase 4: Task 2
Wed Nov 27 13:24:41 PST 2024
System Started.

Wed Nov 27 13:24:42 PST 2024
Event log cleared.

Wed Nov 27 13:24:53 PST 2024
Recipe Added to Recipe Book.

Wed Nov 27 13:25:10 PST 2024
Pantry Saved to File.

## Phase 4: Task 3
Looking at my UML diagram, I notice two main areas that I would want to refracter if given more time. The first section that could be simplified is the classes in the persistance folder, as there are a lot of repetition between the codes. Since I have various classes for writing and reading to/from the file, I want to remove the repeating methods and have an abstract class hold them instead. This way, other classes can just extend the abstract class and have a lot less code. I would also like to adjust my KitchenGUI class in the ui package. Currently, this one class is doing the work of two; performing functions for both the recipe book part of the user interface and the pantry-related tasks. I believe that by splitting the class in two, the code will become much more clearer and not so long. By making these changes, this program will become a lot more reader-friendly and efficient, while still maintaining all functions.