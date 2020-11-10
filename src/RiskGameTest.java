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
    private final int TEST_MOVE = 10;
    private final int TEST_BATTLE_NO_DEATH_ATTACKER_LOSS1 = 11;
    private final int TEST_BATTLE_NO_DEATH_ATTACKER_LOSS2 = 12;
    private final int TEST_BATTLE_NO_DEATH_DEFENDER_LOSS = 13;
    private final int TEST_BATTLE_DEATH_NO_CHOOSE_MOVE = 14;
    private final int TEST_BATTLE_DEATH_CHOOSE_MOVE = 15;
    private final int TEST_PLAYER_ELIMINATION = 16;
    private final int TEST_GAME_OVER = 17;

    private List<Territory> territoryList;
    private Player player;
    private boolean reachedProperEvent;

    @org.junit.Before
    public void setUp() throws Exception {
        test = INITIAL;
        territoryList = new ArrayList<>();
        reachedProperEvent = false;
    }

    @Test
    public void testProcessTerritoryChooseAttacker() {
        rg = new RiskGame(true, false);
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
        rg = new RiskGame(true, false);
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
        rg = new RiskGame(true, false);
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
        rg = new RiskGame(true, false);
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
        rg = new RiskGame(true, false);
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
        rg = new RiskGame(true, false);
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
        rg = new RiskGame(true, false);
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
    public void testSetDefendDice() {
        rg = new RiskGame(true, false);
        rg.setDefendDice(1);
        assertEquals(1, rg.getDefendDiceNum());
    }

    @Test
    public void testSimulateBattleResultsFromDiceLists() {
        rg = new RiskGame(true, false);
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

    @Test
    public void testMove() {
        rg = new RiskGame(true, false);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);
        territory1.setArmies(5);
        territory2.setArmies(1);
        territory3.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);
        territoryList.add(territory3);

        Continent continent1 = new Continent("Continent1", territoryList, 0, 0, Color.BLACK);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();
        List<Territory> adj3 = new ArrayList<>();

        adj1.add(territory2);
        adj1.add(territory3);
        territory1.setAdjacentTerritories(adj1);

        adj2.add(territory1);
        adj2.add(territory3);
        territory2.setAdjacentTerritories(adj2);

        adj3.add(territory1);
        adj3.add(territory2);
        territory3.setAdjacentTerritories(adj3);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);

        player1.addTerritory(territory1);
        player1.addTerritory(territory2);
        player2.addTerritory(territory3);

        rg.addView(this);
        rg.processTerritory(territory1);
        rg.processTerritory(territory2);

        assertEquals(5, territory1.getArmies());
        assertEquals(1, territory2.getArmies());

        rg.move(4);
        assertEquals(TurnPhase.ATTACK_CHOOSE_ATTACKERS, rg.getPhase());
        assertEquals(1, territory1.getArmies());
        assertEquals(5, territory2.getArmies());

        player1.removeTerritory(territory1);
        player1.removeTerritory(territory2);

        rg.processTerritory(territory1);
        rg.processTerritory(territory2);
        test = TEST_MOVE;
        player = player2;
        rg.move(0);
    }


    @Test
    public void testBattleNoDeathAttackerLoss1() {
        rg = new RiskGame(false, true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        territory1.setArmies(3);
        territory2.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);

        Continent continent1 = new Continent("Continent1", territoryList, 0, 0, Color.BLACK);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();

        adj1.add(territory2);
        territory1.setAdjacentTerritories(adj1);

        adj2.add(territory1);
        territory2.setAdjacentTerritories(adj2);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        player2.addTerritory(territory2);

        rg.addView(this);

        rg.processTerritory(territory1);
        rg.processTerritory(territory2);
        rg.setAttackDice(1);
        rg.setDefendDice(1);

        List<Integer> attackDice = new ArrayList<>();
        List<Integer> defendDice = new ArrayList<>();
        attackDice.add(1);
        defendDice.add(6);
        reachedProperEvent = false;
        test = TEST_BATTLE_NO_DEATH_ATTACKER_LOSS1;
        rg.battleResults(new int[] {1, 0}, attackDice, defendDice);
        assertTrue(reachedProperEvent);
        assertEquals(player1, rg.getCurrentPlayer());
    }

    @Test
    public void testBattleNoDeathAttackerLoss2() {
        rg = new RiskGame(false, true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        territory1.setArmies(2);
        territory2.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);

        Continent continent1 = new Continent("Continent1", territoryList, 0, 0, Color.BLACK);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();

        adj1.add(territory2);
        territory1.setAdjacentTerritories(adj1);

        adj2.add(territory1);
        territory2.setAdjacentTerritories(adj2);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        player2.addTerritory(territory2);

        rg.addView(this);

        rg.processTerritory(territory1);
        rg.processTerritory(territory2);
        rg.setAttackDice(1);
        rg.setDefendDice(1);

        List<Integer> attackDice = new ArrayList<>();
        List<Integer> defendDice = new ArrayList<>();
        attackDice.add(1);
        defendDice.add(6);

        test = TEST_BATTLE_NO_DEATH_ATTACKER_LOSS2;
        reachedProperEvent = false;
        rg.battleResults(new int[] {1, 0}, attackDice, defendDice);
        assertTrue(reachedProperEvent);
        assertEquals(player2, rg.getCurrentPlayer());
    }

    @Test
    public void testBattleNoDeathDefenderLoss() {
        rg = new RiskGame(false, true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        territory1.setArmies(2);
        territory2.setArmies(2);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);

        Continent continent1 = new Continent("Continent1", territoryList, 0, 0, Color.BLACK);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();

        adj1.add(territory2);
        territory1.setAdjacentTerritories(adj1);

        adj2.add(territory1);
        territory2.setAdjacentTerritories(adj2);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        player2.addTerritory(territory2);

        rg.addView(this);

        rg.processTerritory(territory1);
        rg.processTerritory(territory2);
        rg.setAttackDice(1);
        rg.setDefendDice(1);

        List<Integer> attackDice = new ArrayList<>();
        List<Integer> defendDice = new ArrayList<>();
        attackDice.add(6);
        defendDice.add(1);

        test = TEST_BATTLE_NO_DEATH_DEFENDER_LOSS;
        reachedProperEvent = false;
        rg.battleResults(new int[] {0, 1}, attackDice, defendDice);
        assertTrue(reachedProperEvent);
        assertEquals(player1, rg.getCurrentPlayer());
    }

    @Test
    public void testBattleDeathNoChooseMove() {
        rg = new RiskGame(false, true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        territory1.setArmies(2);
        territory2.setArmies(1);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);

        Continent continent1 = new Continent("Continent1", territoryList, 0, 0, Color.BLACK);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();

        adj1.add(territory2);
        territory1.setAdjacentTerritories(adj1);

        adj2.add(territory1);
        territory2.setAdjacentTerritories(adj2);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        player2.addTerritory(territory2);

        rg.addView(this);

        rg.processTerritory(territory1);
        rg.processTerritory(territory2);
        rg.setAttackDice(1);
        rg.setDefendDice(1);

        List<Integer> attackDice = new ArrayList<>();
        List<Integer> defendDice = new ArrayList<>();
        attackDice.add(6);
        defendDice.add(1);

        test = TEST_BATTLE_DEATH_NO_CHOOSE_MOVE;
        reachedProperEvent = false;
        player = player1;
        rg.battleResults(new int[] {0, 1}, attackDice, defendDice);
        assertTrue(reachedProperEvent);
        assertEquals(player1, rg.getCurrentPlayer());
    }

    @Test
    public void testBattleDeathChooseMove() {
        rg = new RiskGame(false, true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        territory1.setArmies(5);
        territory2.setArmies(1);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);

        Continent continent1 = new Continent("Continent1", territoryList, 0, 0, Color.BLACK);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();

        adj1.add(territory2);
        territory1.setAdjacentTerritories(adj1);

        adj2.add(territory1);
        territory2.setAdjacentTerritories(adj2);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        player2.addTerritory(territory2);

        rg.addView(this);

        rg.processTerritory(territory1);
        rg.processTerritory(territory2);
        rg.setAttackDice(1);
        rg.setDefendDice(1);

        List<Integer> attackDice = new ArrayList<>();
        List<Integer> defendDice = new ArrayList<>();
        attackDice.add(6);
        defendDice.add(1);

        test = TEST_BATTLE_DEATH_CHOOSE_MOVE;
        reachedProperEvent = false;
        player = player1;
        rg.battleResults(new int[] {0, 1}, attackDice, defendDice);
        assertTrue(reachedProperEvent);
        assertEquals(TurnPhase.ATTACK_CHOOSE_MOVE, rg.getPhase());
    }

    @Test
    public void testPlayerElimination() {
        rg = new RiskGame(false, true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        Player player3 = new Player("Player3", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        rg.addPlayer(player3);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);
        territory1.setArmies(2);
        territory2.setArmies(1);
        territory3.setArmies(1);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);
        territoryList.add(territory3);

        Continent continent1 = new Continent("Continent1", territoryList, 0, 0, Color.BLACK);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();
        List<Territory> adj3 = new ArrayList<>();

        adj1.add(territory2);
        adj1.add(territory3);
        territory1.setAdjacentTerritories(adj1);

        adj2.add(territory1);
        adj2.add(territory3);
        territory2.setAdjacentTerritories(adj2);

        adj3.add(territory1);
        adj3.add(territory2);
        territory3.setAdjacentTerritories(adj3);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);
        RiskMap.addContinent(territory3, continent1);

        player1.addTerritory(territory1);
        player2.addTerritory(territory2);
        player3.addTerritory(territory3);

        rg.addView(this);

        rg.processTerritory(territory1);
        rg.processTerritory(territory2);
        rg.setAttackDice(1);
        rg.setDefendDice(1);

        List<Integer> attackDice = new ArrayList<>();
        List<Integer> defendDice = new ArrayList<>();
        attackDice.add(6);
        defendDice.add(1);

        player = player2;
        test = TEST_PLAYER_ELIMINATION;
        reachedProperEvent = false;
        rg.battleResults(new int[] {0, 1}, attackDice, defendDice);
        assertTrue(reachedProperEvent);
    }

    @Test
    public void testGameOver() {
        rg = new RiskGame(false, true);
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Player player2 = new Player("Player2", rg);
        rg.addPlayer(player1);
        rg.addPlayer(player2);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        territory1.setArmies(2);
        territory2.setArmies(1);

        List<Territory> territoryList = new ArrayList<>();
        territoryList.add(territory1);
        territoryList.add(territory2);

        Continent continent1 = new Continent("Continent1", territoryList, 0, 0, Color.BLACK);

        List<Territory> adj1 = new ArrayList<>();
        List<Territory> adj2 = new ArrayList<>();

        adj1.add(territory2);
        territory1.setAdjacentTerritories(adj1);

        adj2.add(territory1);
        territory2.setAdjacentTerritories(adj2);

        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent1);

        player1.addTerritory(territory1);
        player2.addTerritory(territory2);

        rg.addView(this);

        rg.processTerritory(territory1);
        rg.processTerritory(territory2);
        rg.setAttackDice(1);
        rg.setDefendDice(1);

        List<Integer> attackDice = new ArrayList<>();
        List<Integer> defendDice = new ArrayList<>();
        attackDice.add(6);
        defendDice.add(1);

        player = player2;
        test = TEST_GAME_OVER;
        reachedProperEvent = false;
        rg.battleResults(new int[] {0, 1}, attackDice, defendDice);
        assertTrue(reachedProperEvent);
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
        else if (test == TEST_MOVE) {
            assertEquals(player, e.getCurrentPlayer());
        }
        else if (test == TEST_BATTLE_NO_DEATH_ATTACKER_LOSS1) {
            if (e instanceof RiskEventDiceResults) {
                reachedProperEvent = true;
                RiskEventDiceResults diceResultsEvent = (RiskEventDiceResults)e;
                assertEquals(2, diceResultsEvent.getTerritoryFrom().getArmies());
                assertEquals(2, diceResultsEvent.getTerritoryTo().getArmies());
            }
        }
        else if (test == TEST_BATTLE_NO_DEATH_ATTACKER_LOSS2) {
            if (e instanceof RiskEventDiceResults) {
                reachedProperEvent = true;
                RiskEventDiceResults diceResultsEvent = (RiskEventDiceResults)e;
                assertEquals(1, diceResultsEvent.getTerritoryFrom().getArmies());
                assertEquals(2, diceResultsEvent.getTerritoryTo().getArmies());
            }
        }
        else if (test == TEST_BATTLE_NO_DEATH_DEFENDER_LOSS) {
            if (e instanceof RiskEventDiceResults) {
                reachedProperEvent = true;
                RiskEventDiceResults diceResultsEvent = (RiskEventDiceResults)e;
                assertEquals(2, diceResultsEvent.getTerritoryFrom().getArmies());
                assertEquals(1, diceResultsEvent.getTerritoryTo().getArmies());
            }
        }
        else if (test == TEST_BATTLE_DEATH_NO_CHOOSE_MOVE) {
            if (e instanceof RiskEventDiceResults) {
                reachedProperEvent = true;
                RiskEventDiceResults diceResultsEvent = (RiskEventDiceResults)e;
                assertEquals(1, diceResultsEvent.getTerritoryFrom().getArmies());
                assertEquals(1, diceResultsEvent.getTerritoryTo().getArmies());
                assertEquals(player, diceResultsEvent.getTerritoryTo().getOwner());
            }
        }
        else if (test == TEST_BATTLE_DEATH_CHOOSE_MOVE) {
            if (e instanceof RiskEventBounds) {
                reachedProperEvent = true;
                RiskEventBounds boundsResultsEvent = (RiskEventBounds)e;
                assertEquals(1, boundsResultsEvent.getMinChoice());
                assertEquals(4, boundsResultsEvent.getMaxChoice());
            }
        }
        else if (test == TEST_PLAYER_ELIMINATION) {
            if (e instanceof RiskEventPlayer) {
                reachedProperEvent = true;
                RiskEventPlayer playerEvent = (RiskEventPlayer)e;
                assertEquals(1, player.getGameStanding());
                assertEquals(player, playerEvent.getPlayer());
            }
        }
        else if (test == TEST_GAME_OVER) {
            if (e instanceof RiskEventEnd) {
                reachedProperEvent = true;
            }
        }
    }
}