import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    private RiskGame rg;

    @org.junit.Before
    public void setUp() throws Exception {
        RiskMap rm = new RiskMap(true, false);
        rg = new RiskGame(true, false);
    }

    @Test
    public void testGetName() {
        Player player1 = new Player("Player1", rg);
        assertEquals("Player1", player1.getName());
    }

    @Test
    public void testGetTerritories() {
        Player player1 = new Player("Player1", rg);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getTerritories().toArray());
    }

    @Test
    public void testGetAttackableTerritories() {
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory enemyTerritory1 = new Territory("EnemyTerritory3", 0, 0);
        Territory enemyTerritory2 = new Territory("EnemyTerritory4Unadjacent", 0, 0);

        territory1.setArmies(1);
        territory2.setArmies(2);
        enemyTerritory1.setArmies(1);
        enemyTerritory2.setArmies(1);

        territory1.setOwner(player1);
        territory2.setOwner(player1);
        enemyTerritory1.setOwner(player2);
        enemyTerritory2.setOwner(player2);

        List<Territory> territory1Adjacent = new ArrayList<Territory>();
        territory1Adjacent.add(territory2);
        territory1Adjacent.add(enemyTerritory1);

        List<Territory> territory2Adjacent = new ArrayList<Territory>();
        territory2Adjacent.add(territory1);
        territory2Adjacent.add(enemyTerritory1);

        territory1.setAdjacentTerritories(territory1Adjacent);
        territory2.setAdjacentTerritories(territory2Adjacent);

        List<Territory> territoriesInContinent = new ArrayList<Territory>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        territoriesInContinent.add(enemyTerritory1);
        territoriesInContinent.add(enemyTerritory2);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(enemyTerritory1);
        RiskMap.addTerritory(enemyTerritory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(enemyTerritory1, continent1);
        RiskMap.addContinent(enemyTerritory2, continent1);

        player1.addTerritory(territory1);
        player1.addTerritory(territory2);
        player2.addTerritory(enemyTerritory1);
        player2.addTerritory(enemyTerritory2);

        List<Territory> attackableTerritories = new ArrayList<Territory>();
        attackableTerritories.add(territory2);

        assertArrayEquals(attackableTerritories.toArray(), player1.getAttackableTerritories().toArray());
    }

    @Test
    public void testAddTerritory() {
        Player player1 = new Player("Player1", rg);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getTerritories().toArray());

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);

        List<Territory> territoryList = new ArrayList<Territory>();
        territoryList.add(territory1);

        List<Territory> territoriesInContinent = new ArrayList<>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        assertArrayEquals(territoryList.toArray(), player1.getTerritories().toArray());

        territoryList.add(territory2);
        player1.addTerritory(territory2);
        assertArrayEquals(territoryList.toArray(), player1.getTerritories().toArray());
    }

    @Test
    public void testTerritoryOwner() {
        Player player1 = new Player("Player1", rg);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getTerritories().toArray());

        Territory territory1 = new Territory("Territory1", 0, 0);
        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);

        RiskMap.addTerritory(territory1);
        RiskMap.addContinent(territory1, continent1);

        player1.addTerritory(territory1);
        assertEquals(player1, territory1.getOwner());
    }

    @Test
    public void testChangeTerritoryOwner() {
        Player player1 = new Player("Player1", rg);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getTerritories().toArray());

        Territory territory1 = new Territory("Territory1", 0, 0);
        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);

        RiskMap.addTerritory(territory1);
        RiskMap.addContinent(territory1, continent1);

        player1.addTerritory(territory1);
        assertEquals(player1, territory1.getOwner());

        Player player2 = new Player("Player2", rg);
        player2.addTerritory(territory1);
        assertEquals(player2, territory1.getOwner());
    }

    @Test
    public void testNewContinentOwner() {
        Player player1 = new Player("Player1", rg);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getTerritories().toArray());

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory1", 0, 0);
        List<Territory> territoryList = new ArrayList<Territory>();
        territoryList.add(territory1);
        territoryList.add(territory2);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);
        List<Continent> continentList = new ArrayList<>();
        continentList.add(continent1);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getContinents().toArray());

        player1.addTerritory(territory2);
        assertArrayEquals(continentList.toArray(), player1.getContinents().toArray());
    }

    @Test
    public void testRemoveTerritory() { //doesn't test what happens if they die yet (how do you check if a message is sent to observers? done in RiskGameTest I guess)
        Player player1 = new Player("Player1", rg);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getTerritories().toArray());

        Territory territory1 = new Territory("Territory1", 0, 0);
        List<Territory> territoryList = new ArrayList<Territory>();
        territoryList.add(territory1);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);

        RiskMap.addTerritory(territory1);
        RiskMap.addContinent(territory1, continent1);
        rg.addPlayer(player1);

        player1.addTerritory(territory1);
        assertArrayEquals(territoryList.toArray(), player1.getTerritories().toArray());

        player1.removeTerritory(territory1);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getTerritories().toArray());
    }

    @Test
    public void testRemoveContinentOwner() {
        Player player1 = new Player("Player1", rg);
        assertArrayEquals((new ArrayList<Territory>()).toArray(), player1.getTerritories().toArray());

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory1", 0, 0);
        List<Territory> territoryList = new ArrayList<Territory>();
        territoryList.add(territory1);
        territoryList.add(territory2);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);
        List<Continent> continentList = new ArrayList<>();
        continentList.add(continent1);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        player1.addTerritory(territory2);
        assertArrayEquals(continentList.toArray(), player1.getContinents().toArray());

        player1.removeTerritory(territory1);
        assertArrayEquals((new ArrayList<Continent>()).toArray(), player1.getContinents().toArray());
    }

    @Test
    public void testGetGameStanding() {
        Player player1 = new Player("Player1", rg);
        assertEquals(0, player1.getGameStanding());
    }

    @Test
    public void testSetGameStanding() {
        Player player1 = new Player("Player1", rg);
        player1.setGameStanding(1);
        assertEquals(1, player1.getGameStanding());
    }

    @Test
    public void testCanAttack() {
        Player player1 = new Player("Player1", rg);
        assertFalse(player1.canAttack());

        Player player2 = new Player("Player2", rg);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);

        List<Territory> territoryList = new ArrayList<Territory>();
        territoryList.add(territory2);
        territory1.setAdjacentTerritories(territoryList);

        List<Territory> territoriesInContinent = new ArrayList<Territory>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        territory1.setArmies(2);
        territory2.setArmies(1);
        player1.addTerritory(territory1);
        player2.addTerritory(territory2);

        rg.addPlayer(player1);
        rg.addPlayer(player2);

        assertTrue(player1.canAttack());

        territory1.setArmies(1);
        assertFalse(player1.canAttack());

        player1.removeTerritory(territory1);
        assertFalse(player1.canAttack());
    }

    @Test
    public void testGetArmiesToPlace() {
        Player player1 = new Player("Player1", rg);
        assertEquals(0, player1.getArmiesToPlace());
    }

    @Test
    public void testSetArmiesToPlace() {
        Player player1 = new Player("Player1", rg);
        player1.setArmiesToPlace(1);
        assertEquals(1, player1.getArmiesToPlace());
    }
}