import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TerritoryTest {
    //TODO use methods in RiskMap
    @Test
    public void testGetName() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertEquals("Territory1", territory1.getName());
    }

    @Test
    public void testGetXPos() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertEquals(0, territory1.getXPos());
    }

    @Test
    public void testGetYPos() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertEquals(0, territory1.getYPos());
    }

    @Test
    public void testGetOwner() {
        RiskGame rg = new RiskGame(true);
        Player player1 = new Player("Player1", rg);
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setOwner(player1);
        assertEquals(player1, territory1.getOwner());
    }

    @Test
    public void testChangeOwner() {
        RiskGame rg = new RiskGame(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setOwner(player1);
        assertEquals(player1, territory1.getOwner());

        territory1.setOwner(player2);
        assertEquals(player2, territory1.getOwner());
    }

    @Test
    public void testGetArmies() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertEquals(0, territory1.getArmies());
    }

    @Test
    public void testChangeArmies() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertEquals(0, territory1.getArmies());

        territory1.setArmies(1);
        assertEquals(1, territory1.getArmies());
    }

    @Test
    public void testToStringNoArmy() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertEquals("Territory1: 0 armies", territory1.toString());
    }

    @Test
    public void testToString1Army() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertEquals("Territory1: 0 armies", territory1.toString());

        territory1.setArmies(1);
        assertEquals("Territory1: 1 armies", territory1.toString());
    }

    @Test
    public void testSubtractArmies() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setArmies(2);
        assertEquals(2, territory1.getArmies());

        territory1.subtractArmies(1);
        assertEquals(1, territory1.getArmies());
    }

    @Test
    public void testAddArmies() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertEquals(0, territory1.getArmies());

        territory1.addArmies(1);
        assertEquals(1, territory1.getArmies());
    }

    @Test
    public void testGetAdjacentTerritories() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), territory1.getAdjacentTerritories().toArray());
    }

    @Test
    public void testSetAdjacentTerritories() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), territory1.getAdjacentTerritories().toArray());

        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);
        Territory territory4 = new Territory("Territory4", 0, 0);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory2);
        territoryList.add(territory3);
        territoryList.add(territory4);

        territory1.setAdjacentTerritories(territoryList);
        assertArrayEquals(territoryList.toArray(), territory1.getAdjacentTerritories().toArray());
    }

    @Test
    public void testGetAdjacentEnemyTerritories() {
        RiskGame rg = new RiskGame(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        territory1.setOwner(player1);
        territory2.setOwner(player1);

        Territory territory3 = new Territory("EnemyTerritory3", 0, 0);
        Territory territory4 = new Territory("EnemyTerritory4NotAdjacent", 0, 0);
        territory3.setOwner(player2);
        territory4.setOwner(player2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory2);
        territoryList.add(territory3);
        territory1.setAdjacentTerritories(territoryList);

        List<Territory> enemyTerritoryList = new ArrayList<>();
        enemyTerritoryList.add(territory3);

        assertArrayEquals(enemyTerritoryList.toArray(), territory1.getAdjacentEnemyTerritories().toArray());
    }
}