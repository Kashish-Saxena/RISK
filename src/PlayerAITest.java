import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerAITest {

    private RiskGame rg;

    @org.junit.Before
    public void setUp() throws Exception {
        RiskMap rm = new RiskMap(true);
        rg = new RiskGame(true, false);
    }

    @Test
    public void testIsAI() {
        PlayerAI ai = new PlayerAI("ai", rg);
        assertTrue(ai.isAI());
    }

    @Test
    public void testGetDeployTerritory() {
        PlayerAI ai = new PlayerAI("ai", rg);
        Player player2 = new Player("Player2", rg);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);

        List<Territory> territoriesInContinent = new ArrayList<Territory>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        territoriesInContinent.add(territory3);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);

        ai.addTerritory(territory1);
        ai.addTerritory(territory2);
        player2.addTerritory(territory3);

        territory1.setArmies(1);
        territory2.setArmies(1);
        territory3.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory3);
        territory2.setAdjacentTerritories(territoryList);

        assertEquals(territory2, ai.getDeployTerritory());

        territory1.setArmies(1);
        territory2.setArmies(0);
        territory3.setArmies(2);

        territory1.setAdjacentTerritories(territoryList);

        assertEquals(territory2, ai.getDeployTerritory());
    }

    @Test
    public void testGetDeployAmount() {
        PlayerAI ai = new PlayerAI("ai", rg);
        assertEquals(1, ai.getDeployAmount());
    }

    @Test
    public void testHasFavorableAttacks() {
        PlayerAI ai = new PlayerAI("ai", rg);
        Player player2 = new Player("Player2", rg);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);

        List<Territory> territoriesInContinent = new ArrayList<Territory>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        territoriesInContinent.add(territory3);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);

        ai.addTerritory(territory1);
        ai.addTerritory(territory2);
        player2.addTerritory(territory3);

        territory1.setArmies(1);
        territory2.setArmies(1);
        territory3.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory3);
        territory1.setAdjacentTerritories(territoryList);
        territory2.setAdjacentTerritories(territoryList);

        assertFalse(ai.hasFavorableAttacks());

        territory1.setArmies(1);
        territory2.setArmies(2);
        territory3.setArmies(2);
        assertTrue(ai.hasFavorableAttacks());

        territory1.setArmies(1);
        territory2.setArmies(3);
        territory3.setArmies(2);
        assertTrue(ai.hasFavorableAttacks());
    }

    @Test
    public void testGetAttackingTerritory() {
        PlayerAI ai = new PlayerAI("ai", rg);
        Player player2 = new Player("Player2", rg);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);

        List<Territory> territoriesInContinent = new ArrayList<Territory>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        territoriesInContinent.add(territory3);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);

        ai.addTerritory(territory1);
        ai.addTerritory(territory2);
        player2.addTerritory(territory3);

        territory1.setArmies(1);
        territory2.setArmies(2);
        territory3.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory3);
        territory1.setAdjacentTerritories(territoryList);
        territory2.setAdjacentTerritories(territoryList);

        assertEquals(territory2, ai.getAttackingTerritory());
    }

    @Test
    public void testGetTerritoryToAttack() {
        PlayerAI ai = new PlayerAI("ai", rg);
        Player player2 = new Player("Player2", rg);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);
        Territory territory4 = new Territory("Territory4", 0, 0);

        List<Territory> territoriesInContinent = new ArrayList<Territory>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        territoriesInContinent.add(territory3);
        territoriesInContinent.add(territory4);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);
        RiskMap.addTerritory(territory4);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);
        RiskMap.addContinent(territory4, continent1);

        ai.addTerritory(territory1);
        ai.addTerritory(territory2);
        player2.addTerritory(territory3);
        player2.addTerritory(territory4);

        territory1.setArmies(1);
        territory2.setArmies(2);
        territory3.setArmies(2);
        territory4.setArmies(1);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory3);
        territoryList.add(territory4);
        territory1.setAdjacentTerritories(territoryList);
        territory2.setAdjacentTerritories(territoryList);

        assertEquals(territory4, ai.getTerritoryToAttack(territory2));
    }

    @Test
    public void testGetAttackDiceNum() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setArmies(2);
        assertEquals(1, PlayerAI.getAttackDiceNum(territory1));
        territory1.setArmies(3);
        assertEquals(2, PlayerAI.getAttackDiceNum(territory1));
        territory1.setArmies(4);
        assertEquals(3, PlayerAI.getAttackDiceNum(territory1));
        territory1.setArmies(5);
        assertEquals(3, PlayerAI.getAttackDiceNum(territory1));
    }

    @Test
    public void testGetDefendDiceNum() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setArmies(1);
        assertEquals(1, PlayerAI.getDefendDiceNum(territory1));
        territory1.setArmies(2);
        assertEquals(2, PlayerAI.getDefendDiceNum(territory1));
        territory1.setArmies(3);
        assertEquals(2, PlayerAI.getDefendDiceNum(territory1));
    }

    @Test
    public void testGetMovingTerritory() {
        PlayerAI ai = new PlayerAI("ai", rg);
        Player player2 = new Player("Player2", rg);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);

        List<Territory> territoriesInContinent = new ArrayList<Territory>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        territoriesInContinent.add(territory3);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);

        ai.addTerritory(territory1);
        ai.addTerritory(territory2);
        player2.addTerritory(territory3);

        territory1.setArmies(3);
        territory2.setArmies(2);
        territory3.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory2);
        territory1.setAdjacentTerritories(territoryList);

        territoryList = new ArrayList<>();
        territoryList.add(territory3);
        territory2.setAdjacentTerritories(territoryList);

        assertEquals(territory1, ai.getMovingTerritory());
    }

    @Test
    public void testGetTerritoryToFortify() {
        PlayerAI ai = new PlayerAI("ai", rg);
        Player player2 = new Player("Player2", rg);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);

        List<Territory> territoriesInContinent = new ArrayList<Territory>();
        territoriesInContinent.add(territory1);
        territoriesInContinent.add(territory2);
        territoriesInContinent.add(territory3);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoriesInContinent);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);

        ai.addTerritory(territory1);
        ai.addTerritory(territory2);
        player2.addTerritory(territory3);

        territory1.setArmies(3);
        territory2.setArmies(2);
        territory3.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory2);
        territory1.setAdjacentTerritories(territoryList);

        territoryList = new ArrayList<>();
        territoryList.add(territory3);
        territory2.setAdjacentTerritories(territoryList);

        assertEquals(territory2, ai.getTerritoryToFortify(territory1));
    }

    @Test
    public void testGetMoveNum() {
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setArmies(2);
        assertEquals(1, PlayerAI.getMoveNum(territory1));
        territory1.setArmies(3);
        assertEquals(2, PlayerAI.getMoveNum(territory1));
    }
}