import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RiskGameTest implements RiskView {
    private RiskGame rg;
    private int test;
    private final int INITIAL = -1;
    private final int TEST_PROCESS_TERRITORY_CHOOSE_ATTACKER = 1;
    private final int TEST_PROCESS_TERRITORY_CHOOSE_ENEMY1 = 2;
    private final int TEST_PROCESS_TERRITORY_CHOOSE_ENEMY2 = 3;
    private final int TEST_PROCESS_TERRITORY_CHOOSE_ENEMY3 = 4;
    private final int TEST_PROCESS_TERRITORY_CHOOSE_ENEMY4 = 5;
    private final int TEST_PASS_TURN = 6;
    private final int TEST_SET_ATTACK_DICE1 = 7;
    private final int TEST_SET_ATTACK_DICE2 = 8;
    private final int TEST_SET_ATTACK_DICE3 = 9;

    private List<Territory> territoryList;
    private Player player;

    @org.junit.Before
    public void setUp() throws Exception {
        test = INITIAL;
        territoryList = new ArrayList<>();
    }

    @Test
    public void testProcessTerritoryChooseAttacker() {
        rg = new RiskGame(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        rg.addPlayer(player1);
        rg.addView(this);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        territoryList.add(territory2);
        territory1.setAdjacentTerritories(territoryList);
        territory1.setOwner(player1);
        territory2.setOwner(player2);

        test = TEST_PROCESS_TERRITORY_CHOOSE_ATTACKER;
        rg.processTerritory(territory1);
    }

    @Test
    public void testProcessTerritoryChooseEnemy1() {
        rg = new RiskGame(true);
        Player player1 = new Player("Player1", rg);
        rg.addPlayer(player1);
        rg.addView(this);

        Territory territory1 = new Territory("Territory1", 0, 0);

        rg.setPhase(TurnPhase.ATTACK_CHOOSE_ATTACKERS);
        rg.processTerritory(territory1);


        rg.setPhase(TurnPhase.ATTACK_CHOOSE_ENEMY);
        test = TEST_PROCESS_TERRITORY_CHOOSE_ENEMY1;
        territory1.setArmies(2);
        rg.processTerritory(territory1);
    }

    @Test
    public void testProcessTerritoryChooseEnemy2() {
        rg = new RiskGame(true);
        Player player1 = new Player("Player1", rg);
        rg.addPlayer(player1);
        rg.addView(this);

        Territory territory1 = new Territory("Territory1", 0, 0);

        rg.setPhase(TurnPhase.ATTACK_CHOOSE_ATTACKERS);
        rg.processTerritory(territory1);


        rg.setPhase(TurnPhase.ATTACK_CHOOSE_ENEMY);
        test = TEST_PROCESS_TERRITORY_CHOOSE_ENEMY2;
        territory1.setArmies(3);
        rg.processTerritory(territory1);
    }

    @Test
    public void testProcessTerritoryChooseEnemy3() {
        rg = new RiskGame(true);
        Player player1 = new Player("Player1", rg);
        rg.addPlayer(player1);
        rg.addView(this);

        Territory territory1 = new Territory("Territory1", 0, 0);

        rg.setPhase(TurnPhase.ATTACK_CHOOSE_ATTACKERS);
        rg.processTerritory(territory1);

        rg.setPhase(TurnPhase.ATTACK_CHOOSE_ENEMY);
        test = TEST_PROCESS_TERRITORY_CHOOSE_ENEMY3;
        territory1.setArmies(4);
        rg.processTerritory(territory1);
    }

    @Test
    public void testProcessTerritoryChooseEnemy4() {
        rg = new RiskGame(true);
        Player player1 = new Player("Player1", rg);
        rg.addPlayer(player1);
        rg.addView(this);

        Territory territory1 = new Territory("Territory1", 0, 0);

        rg.setPhase(TurnPhase.ATTACK_CHOOSE_ATTACKERS);
        rg.processTerritory(territory1);

        rg.setPhase(TurnPhase.ATTACK_CHOOSE_ENEMY);
        test = TEST_PROCESS_TERRITORY_CHOOSE_ENEMY4;
        territory1.setArmies(5);
        rg.processTerritory(territory1);
    }

    @Test
    public void testPassTurn() {
        rg = new RiskGame(true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        Player player3 = new Player("Player3", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        rg.addPlayer(player3);
        rg.addView(this);

        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);

        territory1.setArmies(2);
        territory2.setArmies(2);
        territory3.setArmies(2);

        List<Territory> con = new ArrayList<>();
        con.add(territory1);
        con.add(territory2);
        con.add(territory3);
        Continent continent1 = new Continent("Continent", con, 0, 0, Color.BLACK);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);

        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();
        List<Territory> adj3 = new ArrayList<>();

        adj1.add(territory2);
        adj1.add(territory3);

        adj2.add(territory1);
        adj2.add(territory3);

        adj3.add(territory1);
        adj3.add(territory2);

        territory1.setOwner(player1);
        territory2.setOwner(player2);
        territory3.setOwner(player3);

        territory1.setAdjacentTerritories(adj1);
        territory2.setAdjacentTerritories(adj2);
        territory3.setAdjacentTerritories(adj3);

        player1.addTerritory(territory1);
        player2.addTerritory(territory2);
        player3.addTerritory(territory3);

        test = TEST_PASS_TURN;
        territoryList.add(territory2);
        player = player2;
        rg.passTurn();

        territoryList.clear();
        territoryList.add(territory3);
        player = player3;
        rg.passTurn();

        territoryList.clear();
        territoryList.add(territory1);
        player = player1;
        rg.passTurn();

        player2.setGameStanding(1);
        territoryList.clear();
        territoryList.add(territory3);
        player = player3;
        rg.passTurn();
    }

    @Test
    public void testSetAttackDice() {
        rg = new RiskGame(true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setArmies(1);
        rg.addPlayer(player1);
        rg.addView(this);

        rg.processTerritory(territory1);
        rg.processTerritory(territory1);

        test = TEST_SET_ATTACK_DICE1;
        player = player1;
        rg.setAttackDice(1);

        test = TEST_SET_ATTACK_DICE2;
        territory1.setArmies(2);
        rg.setAttackDice(1);

        test = TEST_SET_ATTACK_DICE3;
        territory1.setArmies(3);
        rg.setAttackDice(1);
    }

    @Test
    public void testSimulateBattleResultsFromDiceLists() {
        rg = new RiskGame(true);
        List<Integer> attackDice = new ArrayList<>();
        List<Integer> defendDice = new ArrayList<>();

        attackDice.add(1);
        defendDice.add(1);
        assertArrayEquals(new int[] {1, 0}, rg.simulateBattleFromDiceLists(attackDice, defendDice));

        attackDice.clear();
        defendDice.clear();
        attackDice.add(2);
        defendDice.add(1);
        assertArrayEquals(new int[] {0, 1}, rg.simulateBattleFromDiceLists(attackDice, defendDice));

        attackDice.clear();
        defendDice.clear();
        attackDice.add(6);
        defendDice.add(6);
        assertArrayEquals(new int[] {1, 0}, rg.simulateBattleFromDiceLists(attackDice, defendDice));

        attackDice.clear();
        defendDice.clear();
        attackDice.add(6);
        attackDice.add(6);
        defendDice.add(6);
        assertArrayEquals(new int[] {1, 0}, rg.simulateBattleFromDiceLists(attackDice, defendDice));

        attackDice.clear();
        defendDice.clear();
        attackDice.add(4);
        attackDice.add(6);
        defendDice.add(5);
        assertArrayEquals(new int[] {0, 1}, rg.simulateBattleFromDiceLists(attackDice, defendDice));

        attackDice.clear();
        defendDice.clear();
        attackDice.add(4);
        attackDice.add(6);
        defendDice.add(5);
        defendDice.add(5);
        assertArrayEquals(new int[] {1, 1}, rg.simulateBattleFromDiceLists(attackDice, defendDice));

        attackDice.clear();
        defendDice.clear();
        attackDice.add(6);
        attackDice.add(6);
        defendDice.add(5);
        defendDice.add(5);
        assertArrayEquals(new int[] {0, 2}, rg.simulateBattleFromDiceLists(attackDice, defendDice));

        attackDice.clear();
        defendDice.clear();
        attackDice.add(1);
        attackDice.add(1);
        attackDice.add(1);
        defendDice.add(6);
        assertArrayEquals(new int[] {1, 0}, rg.simulateBattleFromDiceLists(attackDice, defendDice));

        attackDice.clear();
        defendDice.clear();
        attackDice.add(1);
        defendDice.add(6);
        defendDice.add(6);
        assertArrayEquals(new int[] {1, 0}, rg.simulateBattleFromDiceLists(attackDice, defendDice));
    }

    @Override
    public void handleRiskUpdate(RiskEvent e) {
        if (test == TEST_PROCESS_TERRITORY_CHOOSE_ATTACKER) {
            assertEquals(TurnPhase.ATTACK_CHOOSE_ENEMY, rg.getPhase());
            assertEquals(TurnPhase.ATTACK_CHOOSE_ENEMY, e.getPhase());
            RiskEventChooseTerritory chooseTerritoryEvent = (RiskEventChooseTerritory)e;
            assertEquals(territoryList, chooseTerritoryEvent.getEnabledTerritories());
        }
        else if (test == TEST_PROCESS_TERRITORY_CHOOSE_ENEMY1) {
            assertEquals(TurnPhase.ATTACK_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.ATTACK_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(1, chooseBoundsEvent.getMaxChoice());
        }
        else if (test == TEST_PROCESS_TERRITORY_CHOOSE_ENEMY2) {
            assertEquals(TurnPhase.ATTACK_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.ATTACK_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(2, chooseBoundsEvent.getMaxChoice());
        }
        else if (test == TEST_PROCESS_TERRITORY_CHOOSE_ENEMY3) {
            assertEquals(TurnPhase.ATTACK_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.ATTACK_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(3, chooseBoundsEvent.getMaxChoice());
        }
        else if (test == TEST_PROCESS_TERRITORY_CHOOSE_ENEMY4) {
            assertEquals(TurnPhase.ATTACK_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.ATTACK_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(3, chooseBoundsEvent.getMaxChoice());
        }
        else if (test == TEST_PASS_TURN) {
            assertEquals(TurnPhase.ATTACK_CHOOSE_ATTACKERS, rg.getPhase());
            assertEquals(TurnPhase.ATTACK_CHOOSE_ATTACKERS, e.getPhase());
            RiskEventChooseTerritory chooseTerritoryEvent = (RiskEventChooseTerritory)e;
            assertEquals(player, chooseTerritoryEvent.getCurrentPlayer());
            assertEquals(territoryList, chooseTerritoryEvent.getEnabledTerritories());
        }
        else if (test == TEST_SET_ATTACK_DICE1) {
            assertEquals(1, rg.getAttackDiceNum());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(player, e.getCurrentPlayer());
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(1, chooseBoundsEvent.getMaxChoice());
        }
        else if (test == TEST_SET_ATTACK_DICE2) {
            assertEquals(1, rg.getAttackDiceNum());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(player, e.getCurrentPlayer());
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(2, chooseBoundsEvent.getMaxChoice());
        }
        else if (test == TEST_SET_ATTACK_DICE3) {
            assertEquals(1, rg.getAttackDiceNum());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(player, e.getCurrentPlayer());
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(2, chooseBoundsEvent.getMaxChoice());
        }
    }
}