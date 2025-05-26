package ui;

import java.util.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Pantry;
import model.Recipe;
import model.RecipeBook;
import model.Review;
import model.Store;

import persistence.JsonReader;
import persistence.JsonReaderPantry;
import persistence.JsonWriter;
import persistence.JsonWriterPantry;

// represents the kitchen application
public class Kitchen {
    private static final String JSON_STORE = "./data/recipeBook.json";
    private static final String JSON_PANTRY = "./data/pantry.json";
    // public static final int WIDTH = 1000;
	// public static final int HEIGHT = 700;

    private RecipeBook recipeBook;
    private Pantry pantry;
    private Store store;

    private Boolean isRunning;
    private Scanner scanner;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private JsonReaderPantry jsonReaderPantry;
    private JsonWriterPantry jsonWriterPantry;

    // JPanel menu;
    // JPanel recipeMenu;
    // JPanel pantryMenu;
    // JPanel cards;
    // private JButton recipeBookButton;
    // private JButton pantryButton;
    // private JButton exitButton;

    //EFFECTS: contructs users kitchen, with empty recipe book and 
    // empty pantry
    public Kitchen() throws FileNotFoundException {
        initiat();

        seperator();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReaderPantry = new JsonReaderPantry(JSON_PANTRY);
        jsonWriterPantry = new JsonWriterPantry(JSON_PANTRY);
        System.out.println("Welcome to Rocco's Kitchen!");
        System.out.println("your very own virtual kitchen!");
        // initializeGraphics();
        seperator();

        //takeInput();
    }

    //MODIFIES: this
    //EFFECTS: initializes variables
    public void initiat() {
        recipeBook = new RecipeBook();
        pantry = new Pantry();
        store = new Store();
        isRunning = true;
        scanner = new Scanner(System.in);
    }

    //EFFECTS: offers menu to user
    public void printMenu() {
        seperator();

        System.out.println("Please select an option:\n");
        System.out.println("a: Go to Recipe Book");
        System.out.println("b: Go to Pantry");
        System.out.println("c: Close Kitchen");

        seperator();
    }

    //EFFECTS: takes care of input
    public void takeInput() { 
        while (isRunning) { 
            printMenu();
            String input = this.scanner.nextLine();

            toNextStep(input);
        }
    }

    //EFFECTS: takes care of input
    public void takeRecipeInput() {
        while (isRunning) {
            printRecipeMenu();
            String input = this.scanner.nextLine();
            
            toNextRecipeStep(input);
        }
    }

    //EFFECTS: takes care of input
    public void takePantryInput() {
        while (isRunning) {
            printPantryMenu(); 
            String input = this.scanner.nextLine();

            toNextPantryStep(input);
        }
    }

    //EFFECTS: goes to next step according to given letter
    public void toNextStep(String input) {
        if (input.equals("a")) {
            takeRecipeInput();
        } else if (input.equals("b")) {
            takePantryInput();
        } else if (input.equals("c")) {
            isRunning = false;
        } else {
            System.out.println("Invalid option inputted. Please try again.");
        }
    }

    public void printRecipeMenu() {
        seperator();
        // ArrayList<JButton> buttons = new ArrayList<>();
        System.out.println("Please select an option:\n");
        System.out.println("a: See Recipes in Recipe Book");
        System.out.println("b: Add new Recipe");
        System.out.println("c: Search for Recipe");
        System.out.println("d: Choose Recipe");
        System.out.println("e: Save Recipe Book to file");
        System.out.println("f: Load Recipe Book");
        System.out.println("g: Retrn to previous menu");

        seperator();
    }

    public void toNextRecipeStep(String input) {
        if (input.equals("a")) {
            manageRecipeBook();
        } else if (input.equals("b")) {
            manageAddRecipe();
        } else if (input.equals("c")) {
            manageSearch();
        } else if (input.equals("d")) {
            chooseRecipe();
        } else if (input.equals("e")) {
            saveRecipeBook();
        } else if (input.equals("f")) {
            loadRecipeBook();
        } else if (input.equals("g")) {
            takeInput();
        } else {
            System.out.println("Invalid option inputted. Please try again.");
        }
    }

    public void printPantryMenu() {
        seperator();

        System.out.println("Please select an option:\n");
        System.out.println("a: See Pantry");
        System.out.println("b: Go to Store");
        System.out.println("c: Save Pantry to file");
        System.out.println("d: Load Pantry");
        System.out.println("e: Return to previous menu");

        seperator();
    }

    public void toNextPantryStep(String input) {
        if (input.equals("a")) {
            managePantry();
        } else if (input.equals("b")) {
            manageStore();
        } else if (input.equals("c")) {
            savePantry();
        } else if (input.equals("d")) {
            loadPantry();
        } else if (input.equals("e")) {
            takeInput();
        } else {
            System.out.println("Invalid option inputted. Please try again.");
        }
    }

    //EFFECTS: manage choice of "see recipes"
    public void manageRecipeBook() {
        System.out.println("Printing all recipes...");
        if (recipeBook.listRecipes().isEmpty()) {
            System.out.println("No Recipes at the moment!");
        } else {
            printRecipeBook();
        }
    }

    //REQUIRES: recipeBook is not empty
    //EFFECTS: prints out all recipes
    public void printRecipeBook() {
        int index = 0;
        for (String r : recipeBook.listRecipes()) {
            System.out.println((index + 1) + "." + r);
            index++;                                                                               
        }
    }
    
    //MODIFIES: recipeBook
    //EFFECTS: manage choice of "add recipe" 
    public void manageAddRecipe() {
        System.out.println("Enter the Title of the new recipe: ");
        String title = this.scanner.nextLine();

        System.out.println("Enter the ingrediants needed for the new recipe: ");
        ArrayList<String> ingrediants = newIngrediants();

        System.out.println("Enter the instructions for the new recipe: ");
        ArrayList<String> instructions = newInstructions();

        System.out.println("Enter the time needed for the new recipe: ");
        int timeNeeded = this.scanner.nextInt();

        Recipe newRecipe = new Recipe(title, ingrediants, instructions, timeNeeded);
        recipeBook.addRecipe(newRecipe);

        System.out.println("New recipe added! Returning to menu...");
        scanner = new Scanner(System.in);
    }

    //EFFECTS: collect new instructions
    public ArrayList<String> newInstructions() {
        ArrayList<String> instructions = new ArrayList<String>();
        Boolean more = true;

        while (more) {
            System.out.println("next step: ");
            String next = this.scanner.nextLine();
            instructions.add(next);

            System.out.println("anything else? (please enter 'n' for no, otherwise press random key)");
            String anyMore = this.scanner.nextLine();
            if (anyMore.equals("n")) {
                more = false;
            }
        }

        return instructions;
    }

    //EFFECTS: collect new ingrediants
    public ArrayList<String> newIngrediants() {
        ArrayList<String> ingrediants = new ArrayList<String>();
        Boolean more = true;

        while (more) {
            System.out.println("we will need: ");
            String next = this.scanner.nextLine();

            while (!checkIngrediant(next)) {
                System.out.println("not a valid ingrediant, please re-enter");
                next = this.scanner.nextLine();
            } 
            ingrediants.add(next);

            System.out.println("anything else? (please enter 'n' for no, otherwise press random key)");
            String anyMore = this.scanner.nextLine();
            if (anyMore.equals("n")) {
                more = false;
            }
        }

        return ingrediants;
    }

    //EFFECTS: check if valid ingrediant
    public Boolean checkIngrediant(String i) {
        for (String s : store.getGroceries()) {
            if (i.equals(s)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: manage choice of "search for recipe"
    public void manageSearch() {
        System.out.println("Looking for recipe called: ");
        String find = this.scanner.nextLine();

        System.out.println("Searching...");
        int isFound = recipeBook.searchByTitle(find);

        if (isFound != 0) {
            System.out.println("Recipe found! It's recipe #" + isFound);
        } else {
            System.out.println("Recipe not found, returning to menu...");
        }
    }


    //MODIFIES: recipeBook
    //EFFECTS: manage choice of "choose recipe at int i"
    public void chooseRecipe() {
        if (recipeBook.listRecipes().isEmpty()) {
            System.out.print("No recipes, please add one! Returning to main menu...");
            takeInput();
        }

        System.out.println("Which recipe would you like to make? Enter recipe number: ");
        printRecipeBook();
        int whichRecipe = this.scanner.nextInt();

        Recipe currentRecipe = recipeBook.getRecipe(whichRecipe - 1);

        if (checkIfReady(currentRecipe.getIngrediants())) {
            ready(currentRecipe);
        } else {
            System.out.print("You dont have enough ingrediants! Returning to main menu... \n");
            scanner = new Scanner(System.in);
        }
    }

    //EFFECTS: in the case that we can do something with the recipe
    public void ready(Recipe currentRecipe) {
        scanner = new Scanner(System.in);

        System.out.println("You have all the ingrediants needed!");
        doWhatWithRecipeMenu();
        String input = this.scanner.nextLine();

        if (input.equals("m")) {
            makeRecipe(currentRecipe);
        } else if (input.equals("s")) {
            seeRecipeStatus(currentRecipe);
        } else if (input.equals("n")) {
            System.out.println("Returning to main menu...");
        } 
    }

    //EFFECTS: provides options for chosen recipe
    public void doWhatWithRecipeMenu() {
        seperator();
        System.out.println("What would you like to do with this recipe?: ");
        
        System.out.println("m: Make Recipe");
        System.out.println("s: See Recipe Status");
        System.out.println("n: Return to Menu");
        
        seperator();
    }

    //EFFECTS: allows user to see recipe status
    public void seeRecipeStatus(Recipe currentRecipe) {
        Review review = currentRecipe.getReview();

        if (review.getIsTried() == false) {
            System.out.println(currentRecipe.getTitle() + " not yet made before");
        } else if (review.getIsTried() == true) {
            System.out.println(currentRecipe.getTitle() + " was: " + review.getReview());
            System.out.println("This was last make on " + review.getDateOfReview());
        }
    }

    //MODIFIES: recipeBook
    //EFFECTS: allows user to execute recipe
    public void makeRecipe(Recipe currentRecipe) {
        currentRecipe.execute(pantry);
        System.out.println("Recipe has been made! Please add a review: ");

        promptReview(currentRecipe);
    }

    //MODIFIES: recipeBook
    //EFFECTS: prompt for review for executed recipe
    public void promptReview(Recipe currentRecipe) {
        String newReview = this.scanner.nextLine();
                
        System.out.println("Please enter the date: ");
        String newDate = this.scanner.nextLine();

        currentRecipe.updateReview(newReview, newDate);
    }

    //EFFECTS: check if all required ingrediants are in pantry
    public Boolean checkIfReady(ArrayList<String> ingrediantsNeeded) {
        int count = 0;
        if (pantry.getPantry().isEmpty()) {
            return false;
        } else {
            for (String need : ingrediantsNeeded) {
                for (String have : pantry.getPantry()) {
                    if (need.equals(have)) {
                        count++;
                        break;
                    } 
                }
            } 
        } 

        if (count == ingrediantsNeeded.size()) {
            return true;
        } else {
            return false; 
        }
    }

    //EFFECTS: manage choice of "see pantry"
    public void managePantry() {
        System.out.println("Printing all groceries in pantry...");
        if (pantry.getPantry().isEmpty()) {
            System.out.println("No Groceries at the moment!");
        } else {
            for (String s : pantry.getPantry()) {
                System.out.println(s);
            }
        }
    }

    //MODIFIES: pantry
    //EFFECTS: manage choice of "Go to store"
    public void manageStore() {
        seperator();

        System.out.println("Opening store...");
        printGroceries();
        seperator();

        System.out.println("What would you like?");
        int input = this.scanner.nextInt();
        String grocery = store.findGrocery(input);
        seperator();

        System.out.println(grocery + " Added to pantry!");
        pantry.add(grocery);
        scanner = new Scanner(System.in);
    }

    //EFFECTS: prints out all available groceries
    public void printGroceries() {
        ArrayList<String> groceries = store.getGroceries();
        int index = 0;
        for (String s : groceries) {
            System.out.println((index) + "." + s);
            index++;
        }
    }

    //EFFECTS: save the current recipe book to file
    protected void saveRecipeBook() {
        try {
            jsonWriter.open();
            jsonWriter.write(recipeBook);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads recipe book from file
    protected void loadRecipeBook() {
        try {
            recipeBook = jsonReader.readBook();
            System.out.println("Loaded Recipe Book from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    //EFFECTS: save the current pantry to the file
    protected void savePantry() {
        try {
            jsonWriterPantry.open();
            jsonWriterPantry.write(pantry);
            jsonWriterPantry.close();
            System.out.println("Saved to " + JSON_PANTRY);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read to file: " + JSON_PANTRY);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads pantry from file
    protected void loadPantry() {
        try {
            pantry = jsonReaderPantry.readPantry();
            System.out.println("Loaded Pantry from " + JSON_PANTRY);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_PANTRY);
        }
    }

    //EFFECTS: print out seperator 
    public void seperator() {
        System.out.println("----------------------");
    }

}
