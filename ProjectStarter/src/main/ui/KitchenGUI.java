package ui;

import javax.swing.*;

import model.Event;
import model.EventLog;
import model.Pantry;
import model.Recipe;
import model.RecipeBook;
import model.Review;
import model.Store;
import persistence.JsonReader;
import persistence.JsonReaderPantry;
import persistence.JsonWriter;
import persistence.JsonWriterPantry;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class KitchenGUI extends JFrame {
    private static final String JSON_STORE = "./data/recipeBook.json";
    private static final String JSON_PANTRY = "./data/pantry.json";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;

    private RecipeBook recipeBook;
    private Pantry pantry;
    private Store store;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private JsonReaderPantry jsonReaderPantry;
    private JsonWriterPantry jsonWriterPantry;
    private JFrame window;
    private ImageIcon mainBackground;
    private JLabel mainBackgroundLabel;

    private JPanel mainMenu;
    private JPanel recipeMenu;
    private JPanel pantryMenu;
    private JPanel pages;

    private JButton recipeBookButton;
    private JButton pantryButton;
    private JButton exitButton;

    private JButton seeRecipeButton;
    private JButton addRecipeButton;
    private JButton searchRecipeButton;
    private JButton chooseRecipeButton;
    private JButton saveRecipeButton;
    private JButton loadRecipeButton;
    private JButton returnMainMenuFromRButton;

    private JButton seePantryButton;
    private JButton goStoreButton;
    private JButton savePantryButton;
    private JButton loadPantryButton;
    private JButton returnMainMenuFromPButton;

    //LINKS TO IMAGES USED
    //https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dkitchen%2Bcartoon&psig=AOvVaw3u8PCtp4NUmJuHzJdkp8sm&ust=1732401482902000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKD7m8uA8YkDFQAAAAAdAAAAABAE
    //https://www.google.com/url?sa=i&url=https%3A%2F%2Fdocs.paraview.org%2Fen%2Fv5.11.2%2FUsersGuide%2FsavingResults.html&psig=AOvVaw1FJnPvOtY5tpNGmNA3de0B&ust=1732401085522000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMjQ3Y3_8IkDFQAAAAAdAAAAABAE
    //https://www.google.com/url?sa=i&url=https%3A%2F%2Fdocs.paraview.org%2Fen%2Flatest%2FUsersGuide%2FdataIngestion.html&psig=AOvVaw0jOPssNp052eK1DLrQ3uo_&ust=1732401258259000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOD0_97_8IkDFQAAAAAdAAAAABAE
    //https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vecteezy.com%2Ffree-vector%2Fstir-fried&psig=AOvVaw2BOJ0Qlm-J0AMBwcu_nJTV&ust=1732403835370000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKjx4ayJ8YkDFQAAAAAdAAAAABAZ
    //https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vecteezy.com%2Ffree-vector%2Fburned-food%3Fpage%3D35&psig=AOvVaw2BOJ0Qlm-J0AMBwcu_nJTV&ust=1732403835370000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKjx4ayJ8YkDFQAAAAAdAAAAABBo

    // represents kitchen GUI
    public KitchenGUI() {
        recipeBook = new RecipeBook();
        pantry = new Pantry();
        store = new Store();
        window = new JFrame("Welcome to Rocco's Kitchen: Your Very Own Virtual Kitchen!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(); 
                System.exit(0); 
            }
        });

        EventLog.getInstance().logEvent(new Event("System Started."));

        initializePages();
        initializeButtons();
        addMainMenuButtonsToPanel();
        addRecipeMenuButtonsToPanel();
        addPantryButtonsToPanel();
    }

    // MODIFIES: this
    // EFFECTS: intitialize graphics, panals and buttons
    public void initializePages() {
        mainMenu = new JPanel(new GridLayout(3, 1));
        recipeMenu = new JPanel(new GridLayout(7, 1));
        pantryMenu = new JPanel(new GridLayout(5, 1));
        pages = new JPanel(new CardLayout());
        pages.setSize(WIDTH, HEIGHT - 400);

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReaderPantry = new JsonReaderPantry(JSON_PANTRY);
        jsonWriterPantry = new JsonWriterPantry(JSON_PANTRY);

        pages.add(mainMenu, "Main Menu");
        pages.add(pantryMenu, "Pantry Menu");
        pages.add(recipeMenu, "Recipe Menu");

        // Container pane = getContentPane();
        // pane.add(pages, BorderLayout.CENTER);
        
        window.add(pages, BorderLayout.CENTER);
    
        mainBackground = new ImageIcon("data/kitchen2.jpg");
        mainBackgroundLabel = new JLabel(mainBackground);
        window.add(mainBackgroundLabel, BorderLayout.SOUTH);

        window.setVisible(true);
    }

    //MODIFIES: this
    //EFFECS: helper method to initialize buttons
    public void initializeButtons() {
        recipeBookButton = goToRecipeBookMenuButton("Recipe Book Actions");
        pantryButton = goToPantryMenuButton("Pantry Actions");
        exitButton = exitProgramButton("Exit Program");

        seeRecipeButton = seeRecipeInBookButton("See All Recipes");
        addRecipeButton = addRecipeToBookButton("Add a Recipe To Book");
        searchRecipeButton = searchRecipeInBookButton("Search For a Recipe");
        chooseRecipeButton = chooseRecipeInBookButton("Choose a Recipe");
        saveRecipeButton = saveRecipeBookToFileButton("Save Recipe Book to File");
        loadRecipeButton = loadRecipeFromFileButton("Load Recipe From File");
        returnMainMenuFromRButton = returnToMainMenuButton("Return to Main Menu");

        seePantryButton = seeGroceriesInPantryButton("See All Groceries in Pantry");
        goStoreButton = goToStoreButton("Go to Store");
        savePantryButton = savePantryToFileButton("Save Pantry to File");
        loadPantryButton = loadPantryFromFileButton("Load Pantry From File");
        returnMainMenuFromPButton = returnToMainMenuButton("Return to Main Menu");
    }

    //MODIFIES: this
    //EFFECTS: helper method to add buttons to the panel they belong to
    public void addMainMenuButtonsToPanel() {
        mainMenu.add(recipeBookButton);
        mainMenu.add(pantryButton);
        mainMenu.add(exitButton);
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    //MODIFIES: this
    //EFFECTS: helper method to add recipe buttons to the panel they belong to
    public void addRecipeMenuButtonsToPanel() {
        recipeMenu.add(seeRecipeButton);
        recipeMenu.add(addRecipeButton);
        recipeMenu.add(searchRecipeButton);
        recipeMenu.add(chooseRecipeButton);
        recipeMenu.add(saveRecipeButton);
        recipeMenu.add(loadRecipeButton);
        recipeMenu.add(returnMainMenuFromRButton);
        recipeMenu.revalidate();
        recipeMenu.repaint();
    }

    //MODIFIES: this
    //EFFECTS: helper method to add pantry buttons to the panel they belong to
    public void addPantryButtonsToPanel() {
        pantryMenu.add(seePantryButton);
        pantryMenu.add(goStoreButton);
        pantryMenu.add(savePantryButton);
        pantryMenu.add(loadPantryButton);
        pantryMenu.add(returnMainMenuFromPButton);
        pantryMenu.revalidate();
        pantryMenu.repaint();
    }

    /////////// MAIN MENU BUTTONS //////////////

    //EFFECT: switches between panels
    public void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) pages.getLayout();
        cl.show(pages, panelName);
    }

    //EFFECT: button to go to recipe book menu
    public JButton goToRecipeBookMenuButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(e -> switchPanel("Recipe Menu"));
        return button;
    }

    //EFFECT: button to go to pantry menu
    public JButton goToPantryMenuButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(e -> switchPanel("Pantry Menu"));
        return button;
    }

    //EFFECT: button to exit program
    public JButton exitProgramButton(String name) {
        printLog();
        JButton button = new JButton(name);
        button.addActionListener(e -> {
            printLog();
            System.exit(0);
        });
        return button;
    }

    //EFFECT: prints log to console, clears it
    public void printLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString() + "\n");
        }
        EventLog.getInstance().clear();
    }

/////////// RECIPE BUTTONS //////////////

    //MODIFIES: this
    //EFFECT: helper method to set up JPanels
    public void setUpJPanel(JFrame viewFrame, JPanel viewPanel) {
        viewFrame.add(viewPanel);
        viewFrame.setSize(300, 300);
        viewFrame.setLocationRelativeTo(null);
        viewFrame.setVisible(true);
    }

    //EFFECT: button to see recipes in book
    public JButton seeRecipeInBookButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                int number = 1;
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
                for (String recipeNames : recipeBook.listRecipes()) {
                    JLabel label = new JLabel(number + ". " + recipeNames);
                    viewPanel.add(label);
                    number++;
                }
                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;  
    }

    //EFFECT: generates a test box to take in user input, retunns string
    public String textBox(String name, JPanel viewPanel) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        

        JLabel textLabel = new JLabel(name);
        viewPanel.add(textLabel);
        viewPanel.add(textField);

        int result = JOptionPane.showConfirmDialog(
                null, viewPanel, name, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            return textField.getText();
        } else {
            return ""; 
        }
    }

    //EFFECT: displays a panel where ingredient string can be selected with button, returns the string
    public ArrayList<String> chooseAnIngredient(JPanel viewPanel) {
        ArrayList<String> ingredients = new ArrayList<>();
        viewPanel.add(new JLabel("Choose your ingredients"));

        //make ingredient buttons
        JButton rice = new JButton("Rice");
        JButton eggs = new JButton("Eggs");
        JButton oil = new JButton("Oil");
        JButton toast = new JButton("Toast");
        JButton jam = new JButton("Jam");

        ingredients = addFunctionToIngredient(ingredients, rice, "Rice", viewPanel);
        ingredients = addFunctionToIngredient(ingredients, eggs, "Eggs", viewPanel);
        ingredients = addFunctionToIngredient(ingredients, oil, "Oil", viewPanel);
        ingredients = addFunctionToIngredient(ingredients, toast, "Toast", viewPanel);
        ingredients = addFunctionToIngredient(ingredients, jam, "Jam", viewPanel);

        viewPanel.add(rice);
        viewPanel.add(eggs);
        viewPanel.add(oil);
        viewPanel.add(toast);
        viewPanel.add(jam);
        viewPanel.revalidate();
        viewPanel.repaint();

        return ingredients;
    }

    //EFFECT: add action to ingredient button helper method
    public ArrayList<String> addFunctionToIngredient(ArrayList<String> ingrediants, JButton button, 
                                                     String name, JPanel viewPanel) {
        JLabel text = new JLabel();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingrediants.add(name);
                text.setText(name + " has been added!");
            }
        });
        viewPanel.add(text);
        return ingrediants;
    }

    //MODIFIES: recipeBook
    //EFFECT: button to add a recipe to the book
    public JButton addRecipeToBookButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

                Recipe newRecipe;
                String title = textBox("Enter Name for Recipe: ", viewPanel);
                ArrayList<String> ingrediants = chooseAnIngredient(viewPanel);
                ArrayList<String> instructions = new ArrayList<>();
                instructions.add(textBox("Enter instructions for this recipe: ", viewPanel));
                int timeNeeded = Integer.parseInt(textBox("Enter time needed for Recipe: ", viewPanel));

                newRecipe = new Recipe(title, ingrediants, instructions, timeNeeded);
                recipeBook.addRecipe(newRecipe);
                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;
    }

    //EFFECT: button to search for a recipe in book, prints index of the recipe, 
    // or message if not in book
    public JButton searchRecipeInBookButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

                String input = textBox(name, viewPanel);
                JLabel result = new JLabel();
                int isFound = recipeBook.searchByTitle(input);
                if (isFound != 0) {
                    result.setText("Recipe found! It's Recipe #" + isFound);
                } else {
                    result.setText("Recipe not found");
                }
                viewPanel.add(result);

                setUpJPanel(viewFrame, viewPanel);
            }
            
        });
        return button;
    }

    //MODIFIES: recipeBook
    //EFFECT: button to choose recipe at given index, privides small menu of what to do
    public JButton chooseRecipeInBookButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

                viewPanel.add(new JLabel("Which Recipe would you like to make? Enter Recipe Number: "));
                int input = Integer.parseInt(textBox(name, viewPanel));
                Recipe chosenRecipe = recipeBook.getRecipe(input - 1);

                if (checkIfReady(chosenRecipe.getIngrediants())) {
                    ready(chosenRecipe, viewPanel);
                } else {
                    viewPanel.add(new JLabel("You Don't Have Enough Ingredients!"));
                }
                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;
    }

    //EFFECTS: in the case that we can do something with the recipe
    public void ready(Recipe chosenRecipe, JPanel viewPanel) {
        JLabel message = new JLabel("You have all the ingrediants needed! What would you like to do?");
        viewPanel.add(message);

        //make ingredient buttons
        JButton makeRecipe = chooseRecipeMakeButton("Make Recipe", chosenRecipe, viewPanel);
        JButton seeStatus = seeRecipeStatusButton("See Recipe Status", chosenRecipe, viewPanel);

        viewPanel.add(makeRecipe);
        viewPanel.add(seeStatus);
        viewPanel.revalidate();
        viewPanel.repaint();
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

    //MODIFIES: recipe, recipeBook 
    //EFFECT: button to choose and make recipe, prompts for reply
    public JButton chooseRecipeMakeButton(String name, Recipe chosenRecipe, JPanel viewPanel) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenRecipe.execute(pantry);
                viewPanel.add(new JLabel("Recipe has been made!"));
                ImageIcon madeImage = new ImageIcon("data/made.jpg");
                viewPanel.add(new JLabel(madeImage));
                viewPanel.add(promptReviewButton("Add a Review for the Recipe: ", chosenRecipe));
            }
        });
        return button;
    }

    //MODIFIES: chosenRecipe
    //EFFECT: button to prompt for review
    public JButton promptReviewButton(String name, Recipe chosenRecipe) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

                Review review = new Review();
                review.triedBefore();
                review.updateDate(textBox("Enter Date of Review:", viewPanel));
                review.updateReview(textBox("Enter Review", viewPanel));
                chosenRecipe.updateReview(review);

                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;
    }

    //EFFECT: button so see recipe status
    public JButton seeRecipeStatusButton(String name, Recipe chosenRecipe, JPanel viewPanel) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPanel.add(new JLabel("Recipe name: " + chosenRecipe.getTitle()));
                viewPanel.add(new JLabel("Recipe ingrediants: "));
                printing(viewPanel, chosenRecipe.getIngrediants());
                viewPanel.add(new JLabel("Recipe insructions: "));
                printing(viewPanel, chosenRecipe.getInstructions());
                viewPanel.add(new JLabel("Recipe Time Needed: " + chosenRecipe.getTime()));

                Review review = chosenRecipe.getReview();
                if (!review.getIsTried()) {
                    viewPanel.add(new JLabel(chosenRecipe.getTitle() + " has not yet been made before"));
                } else {
                    viewPanel.add(new JLabel("This Recipe was last made on: " + review.getDateOfReview()));
                    viewPanel.add(new JLabel("Last time you said it was: " + review.getReview()));
                }
            }   
        });
        return button;
    }

    //EFFECT: helper method for printing ingredeiants and instructions
    public void printing(JPanel viewPanel, ArrayList<String> list) {
        for (String s : list) {
            viewPanel.add(new JLabel(s));
        }
    }   

    //MODIFIES: this
    //EFFECT: button to save current recipe book to file
    public JButton saveRecipeBookToFileButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
                ImageIcon savedImage = new ImageIcon("data/saved.png");
                viewPanel.add(new JLabel(savedImage));

                try {
                    jsonWriter.open();
                    jsonWriter.write(recipeBook);
                    jsonWriter.close();
                    viewPanel.add(new JLabel("Recipe Book Saved to " + JSON_STORE));
                } catch (FileNotFoundException f) {
                    viewPanel.add(new JLabel("Unable to Read To File"));
                }

                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;
    }

    //MODIFIES: this
    //EFFECTS: button that loads recipe book from file
    public JButton loadRecipeFromFileButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
                ImageIcon loadedImage = new ImageIcon("data/loaded.png");
                viewPanel.add(new JLabel(loadedImage));

                try {
                    recipeBook = jsonReader.readBook();
                    viewPanel.add(new JLabel("Loaded Recipe Book from " + JSON_STORE));
                } catch (IOException f) {
                    viewPanel.add(new JLabel("Unable to Read from File " + JSON_STORE));
                }

                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;
    }

    //EFFECTS: button to return to main menu
    public JButton returnToMainMenuButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(e -> switchPanel("Main Menu"));
        return button;
    }

/////////// PANTRY BUTTONS //////////////

    //EFFECT: button to see all goceries in pantry
    public JButton seeGroceriesInPantryButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
                for (String grocery : pantry.getPantry()) {
                    JLabel label = new JLabel(grocery);
                    viewPanel.add(label);
                }
                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;  
    }

    //MODIFIES: pantry
    //EFFECT: button that provides grocery options to add to pantry
    public JButton goToStoreButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

                ArrayList<String> groceries = chooseAnIngredient(viewPanel);
                pantry.setPantry(groceries);

                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;
    }

    //MODIFIES: this
    //EFFECT: button to save current recipe book to file
    public JButton savePantryToFileButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
                ImageIcon savedImage = new ImageIcon("data/saved.png");
                viewPanel.add(new JLabel(savedImage));

                try {
                    jsonWriterPantry.open();
                    jsonWriterPantry.write(pantry);
                    jsonWriterPantry.close();
                    viewPanel.add(new JLabel("Pantry Saved to " + JSON_PANTRY));
                } catch (FileNotFoundException f) {
                    viewPanel.add(new JLabel("Unable to Read To File"));
                }

                setUpJPanel(viewFrame, viewPanel);
            }
        });
        return button;
    }

    //MODIFIES: this
    //EFFECTS: button that loads pantry from file
    public JButton loadPantryFromFileButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame(name);
                JPanel viewPanel = new JPanel();
                viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
                ImageIcon loadedImage = new ImageIcon("data/loaded.png");
                viewPanel.add(new JLabel(loadedImage));

                try {
                    pantry = jsonReaderPantry.readPantry();
                    viewPanel.add(new JLabel("Loaded Pantry from " + JSON_STORE));
                } catch (IOException f) {
                    viewPanel.add(new JLabel("Unable to Read from File " + JSON_STORE));
                }

                viewFrame.add(viewPanel);
                viewFrame.setSize(300, 300);
                viewFrame.setLocationRelativeTo(null);
                viewFrame.setVisible(true);
            }
        });
        return button;
    }
}