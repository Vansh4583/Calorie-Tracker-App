package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the food Item class
class FoodItemTest {

    FoodItem f;

    @BeforeEach
    public void setup(){
        f = new FoodItem(500, "Pizza");
    }

    @Test
    public void testConstructor(){
        assertEquals(500, f.getCalories());
        assertEquals("Pizza", f.getName());
        assertEquals(1,f.getQuantity());

    }
    @Test
    public void testGetTotalCalories(){
        assertEquals(500, f.getTotalFoodCalories());
        f.setQuantity(2);
        assertEquals(1000, f.getTotalFoodCalories());
    }

    @Test
    public void testSetName(){
        f.setName("Grapes");
        assertEquals("Grapes", f.getName());
    }

    @Test
    public void testSetCalories(){
        f.setCalories(30);
        assertEquals(30, f.getCalories());
    }

    @Test
    public void testGetImage() {
        assertNull(f.getImage());
    }

    @Test
    public void testSetImage() {
        ImageIcon image = new ImageIcon("demo.jpg");
        f.setImage(image);
        assertEquals(image, f.getImage());
    }
}