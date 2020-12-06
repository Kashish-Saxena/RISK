import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

public class RiskMapTest {

    @Test
    public void testGetTerritoryFromString() {
        RiskMap rm = new RiskMap(true);
        Territory territory1 = new Territory("Territory1", 0, 0);
        RiskMap.addTerritory(territory1);
        assertEquals(territory1, RiskMap.getTerritoryFromString("Territory1"));
    }

    @Test
    public void testGetTerritoryFromIndex() {
        RiskMap rm = new RiskMap(true);
        Territory territory1 = new Territory("Territory1", 0, 0);
        RiskMap.addTerritory(territory1);
        assertEquals(territory1, RiskMap.getTerritoryFromIndex(0));

        Territory territory2 = new Territory("Territory2", 0, 0);
        RiskMap.addTerritory(territory2);
        assertEquals(territory2, RiskMap.getTerritoryFromIndex(1));
    }

    @Test
    public void testGetContinentFromTerritory() {
        RiskMap rm = new RiskMap(true);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        assertEquals(continent1, RiskMap.getContinentFromTerritory(territory1));
        assertEquals(continent1, RiskMap.getContinentFromTerritory(territory2));
    }

    @Test
    public void testNumTerritories() {
        RiskMap rm = new RiskMap(true);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);

        assertEquals(0, RiskMap.numTerritories());
        RiskMap.addTerritory(territory1);
        assertEquals(1, RiskMap.numTerritories());
        RiskMap.addTerritory(territory2);
        assertEquals(2, RiskMap.numTerritories());
    }

    @Test
    public void testGetTerritoryMap() {
        RiskMap rm = new RiskMap(true);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Map territoryMap = new HashMap<>();

        assertEquals(territoryMap, RiskMap.getTerritoryMap());

        RiskMap.addTerritory(territory1);
        territoryMap.put(territory1.getName().toLowerCase(), territory1);
        assertEquals(territoryMap, RiskMap.getTerritoryMap());

        RiskMap.addTerritory(territory2);
        territoryMap.put(territory2.getName().toLowerCase(), territory2);
        assertEquals(territoryMap, RiskMap.getTerritoryMap());
    }

    @Test
    public void testGetContinentArrayList() {
        RiskMap rm = new RiskMap(true);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        List<Territory> continent1Territories = new ArrayList<>();
        List<Territory> continent2Territories = new ArrayList<>();
        continent1Territories.add(territory1);
        continent2Territories.add(territory2);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(continent1Territories);
        Continent continent2 = new Continent("Continent2", 0, 0, Color.BLACK,0);
        continent1.setTerritories(continent2Territories);
        List<Continent> continentList = new ArrayList<>();

        assertEquals(continentList, RiskMap.getContinentsArrayList());

        RiskMap.addContinent(territory1, continent1);
        continentList.add(continent1);
        assertEquals(continentList, RiskMap.getContinentsArrayList());

        RiskMap.addContinent(territory2, continent2);
        continentList.add(continent2);
        assertEquals(continentList, RiskMap.getContinentsArrayList());
    }
}