import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ContinentTest {

    @Test
    public void testToString() {
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        assertEquals("Continent1", continent1.toString());
    }

    @Test
    public void testGetTerritoriesEmpty() {
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(  new ArrayList<Territory>()) ;
        assertArrayEquals((new ArrayList<Territory>()).toArray(), continent1.getTerritories().toArray());
    }

    @Test
    public void testGetTerritories() {
        Territory territory1 = new Territory("territory1", 0, 0);
        Territory territory2 = new Territory("territory2", 0, 0);
        Territory territory3 = new Territory("territory3", 0, 0);
        List<Territory> territoryList = new ArrayList<Territory>();
        territoryList.add(territory1);
        territoryList.add(territory2);
        territoryList.add(territory3);

        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);
        assertArrayEquals(territoryList.toArray(), continent1.getTerritories().toArray());
    }

    @Test
    public void testGetXPos() {
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        assertEquals(0, continent1.getXPos());
    }

    @Test
    public void testGetYPos() {
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        assertEquals(0, continent1.getYPos());
    }
    
    @Test
    public void testGetColor() {
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        assertEquals(Color.BLACK, continent1.getColor());
    }
}