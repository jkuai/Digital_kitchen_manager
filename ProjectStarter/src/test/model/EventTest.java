package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;


public class EventTest {
	private Event e;
	private Date d;
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Recipe Added to RecipeBook");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("Recipe Added to RecipeBook", e.getDescription());
		assertEquals(d, e.getDate());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Recipe Added to RecipeBook", e.toString());
	}
}
