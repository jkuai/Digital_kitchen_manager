package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeBookTest {
    RecipeBook rb1;
    Recipe r1;
    ArrayList<String> ingrediants;
    ArrayList<String> instructions;
    int time;

    ArrayList<String> compare;
    String toast = "Toast";

    @BeforeEach
    void runBefore() {
        ingrediants = new ArrayList<String>();
        instructions = new ArrayList<String>();
        ingrediants.add("bread");
        ingrediants.add("berries");
        ingrediants.add("suger");
        instructions.add("1. Mix berries and suger on low heat for 30min");
        instructions.add("2. cool jam in fridge");
        instructions.add("3. spread jam on bread");
        time = 45;
        r1 = new Recipe("Toast", ingrediants, instructions, time);
        rb1 = new RecipeBook();
        compare = new ArrayList<String>();
    }

    @Test
    void testAddRecipe() {
        assertEquals(compare, rb1.listRecipes());
        rb1.addRecipe(r1);
        compare.add(toast);
        assertEquals(compare, rb1.listRecipes());
        rb1.addRecipe(r1);
        compare.add(toast);
        assertEquals(compare, rb1.listRecipes());
    }

    @Test 
    void testSearch() {
        rb1.addRecipe(r1);
        String have = "Toast";
        assertEquals(1, rb1.searchByTitle(have));
        String notHave = "Eggs";
        assertEquals(0, rb1.searchByTitle(notHave));
    }

    @Test 
    void testGetRecipe() {
        rb1.addRecipe(r1);
        assertEquals(r1, rb1.getRecipe(0));
    }
}

