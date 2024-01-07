package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SampleDataTest {

    SampleData s1;

    @BeforeEach
    public void setup(){
        s1 = new SampleData();
    }

    @Test
    public void testConstructor(){

        List<FoodItem> success = s1.getFoodItemList();
        assertEquals(9, success.size());

        assertEquals("banana", success.get(1).getName());

    }

    @Test
    public void testFindFoodByName(){

        FoodItem f = s1.findFoodByName("Apple");
        String name = f.getName();

        assertEquals("apple",name );


    }

}
