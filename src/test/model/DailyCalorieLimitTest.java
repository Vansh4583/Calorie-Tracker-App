package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DailyCalorieLimitTest {

    DailyCalorieLimit d1;

    @BeforeEach
    public void setup(){
        d1 = new DailyCalorieLimit();
    }

    @Test
    public void testConstructor(){
        assertEquals(2000, d1.getCalorieLimit());
    }

    @Test
    public void testSetter(){
        d1.setCalorieLimit(3000);
        assertEquals(3000, d1.getCalorieLimit());
    }
}

