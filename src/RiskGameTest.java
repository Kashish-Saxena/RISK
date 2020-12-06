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
    private final int TEST_BATTLE_NO_DEATH_DEFENDER_LOSS = 13;
    private final int TEST_BATTLE_DEATH_CHOOSE_MOVE = 15;
    private final int TEST_PLAYER_ELIMINATION = 16;
    private final int TEST_GAME_OVER = 17;
    private final int TEST_DEPLOY = 18;

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
        Continent continent1 = new Continent("Continent", 0, 0, Color.BLACK, 0);
        continent1.setTerritories(con);

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
        player = player2;
        rg.passTurn();

        test = TEST_PASS_TURN;
        player = player3;
        rg.passTurn();

        test = TEST_PASS_TURN;
        player = player1;
        rg.passTurn();

        test = TEST_PASS_TURN;
        player2.setGameStanding(1);
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
        territory1.setOwner(player1);
        rg.setFromTerritory(territory1);
        rg.setToTerritory(territory1);

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
        RiskMap rm = new RiskMap(true);
        Player player1 = new Player("Player1", rg);
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setArmies(1);
        territory1.setOwner(player1);
        rg.addPlayer(player1);
        rg.addView(this);

        rg.setToTerritory(territory1);
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

        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK, 0);
        continent1.setTerritories(territoryList);

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
        rg.setFromTerritory(territory1);
        rg.setToTerritory(territory2);

        assertEquals(5, territory1.getArmies());
        assertEquals(1, territory2.getArmies());

        test = TEST_MOVE;
        player = player1;
        rg.move(4);
        assertEquals(TurnPhase.ATTACK_CHOOSE_ATTACKERS, rg.getPhase());
        assertEquals(1, territory1.getArmies());
        assertEquals(5, territory2.getArmies());
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

        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK, 0);
        continent1.setTerritories(territoryList);


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

        rg.setFromTerritory(territory1);
        rg.setToTerritory(territory2);
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

        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);

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

        rg.setFromTerritory(territory1);
        rg.setToTerritory(territory2);
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

        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK, 0);
        continent1.setTerritories(territoryList);

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

        rg.setFromTerritory(territory1);
        rg.setToTerritory(territory2);
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

        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);

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

        rg.setFromTerritory(territory1);
        rg.setToTerritory(territory2);
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

        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK,0);
        continent1.setTerritories(territoryList);

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

        rg.setFromTerritory(territory1);
        rg.setToTerritory(territory2);
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

    @Test
    public void testCalculateArmiesToDeploy1(){
        rg = new RiskGame(true, true);
        Player player1 = new Player("Player1", rg);
        RiskMap rm = new RiskMap(true);
        rg.addPlayer(player1);
        rg.addView(this);

        //players owns 1 territory and 1 continent worth 5
        Territory territory1 = new Territory("Territory1", 0, 0);
        RiskMap.addTerritory(territory1);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK, 5);//value of 5
        continent1.setTerritories(territoryList);
        RiskMap.addContinent(territory1, continent1);
        player1.addTerritory(territory1);
        rg.calculateArmiesToDeploy();

        //check for 5 (5 from continents)
        assertEquals(5, rg.getTotalDeployAmount());
    }

    @Test
    public void testCalculateArmiesToDeploy2(){
        rg = new RiskGame(true, true);
        Player player1 = new Player("Player1", rg);
        RiskMap rm = new RiskMap(true);
        rg.addPlayer(player1);
        rg.addView(this);

        //player owns 3 territories and 2 continents, one worth 5 and one worth 5
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory1", 0, 0);
        Territory territory3 = new Territory("Territory1", 0, 0);
        RiskMap.addTerritory(territory1);
        RiskMap.addTerritory(territory2);
        RiskMap.addTerritory(territory3);

        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK, 5);//value of 5
        continent1.setTerritories(territoryList);
        Continent continent2 = new Continent("Continent2", 0, 0, Color.BLACK, 0);//value of 0
        continent1.setTerritories(territoryList);
        RiskMap.addContinent(territory1, continent1);
        RiskMap.addContinent(territory2, continent2);
        RiskMap.addContinent(territory3, continent2);
        player1.addTerritory(territory1);
        player1.addTerritory(territory2);
        player1.addTerritory(territory3);

        rg.calculateArmiesToDeploy();

        //check for 6 (1 from territories and 5 from continents)
        assertEquals(6, rg.getTotalDeployAmount());
    }

    @Test
    public void testCalculateArmiesToDeploy3(){
        rg = new RiskGame(true, true);
        Player player1 = new Player("Player1", rg);
        RiskMap rm = new RiskMap(true);
        rg.addPlayer(player1);
        rg.addView(this);

        //player owns nothing
        rg.calculateArmiesToDeploy();

        //check for 3 (minimum amount to deploy is always 3)
        assertEquals(3, rg.getTotalDeployAmount());
    }

    @Test
    public void testDeployScenario(){
        rg = new RiskGame(true, true);
        Player player1 = new Player("Player1", rg);
        RiskMap rm = new RiskMap(true);
        rg.addPlayer(player1);
        rg.addView(this);

        Territory territory1 = new Territory("Territory1", 0, 0);
        RiskMap.addTerritory(territory1);
        Continent continent1 = new Continent("Continent1", 0, 0, Color.BLACK, 5);//value of 5
        continent1.setTerritories(territoryList);
        RiskMap.addContinent(territory1, continent1);
        player1.addTerritory(territory1);
        rg.calculateArmiesToDeploy();

        //player has 5 to deploy (5 from continents)
        assertEquals(5, rg.getTotalDeployAmount());

        //deploy 1 to territory1
        rg.setDeployTerritory(territory1);
        rg.giveDeployedArmies(1);

        //player should have 4 left to deploy
        assertEquals(4, rg.getTotalDeployAmount());

        //territory1 which started at 0 armies should not have 1
        assertEquals(1, territory1.getArmies());
    }

    @Test
    public void testFortifyScenario(){
        rg = new RiskGame(true, true);
        Player player1 = new Player("Player1", rg);
        RiskMap rm = new RiskMap(true);
        rg.addPlayer(player1);
        rg.addView(this);

        //territory1 has 10
        Territory territory1 = new Territory("Territory1", 0, 0);
        territory1.setArmies(10);

        //territory2 has 1
        Territory territory2 = new Territory("Territory2", 0, 0);
        territory2.setArmies(1);

        rg.setFromTerritory(territory1);
        rg.setToTerritory(territory2);

        //fortify 9 from territory1 to territory2
        rg.fortify(9);

        assertEquals(1, territory1.getArmies());
        assertEquals(10, territory2.getArmies());
    }

    @Test
    public void testRecursiveDepthFirstSearchOnFriendlyTerritories(){
        rg = new RiskGame(true, true);
        Territory territory1 = new Territory("Territory1", 0, 0);
        Territory territory2 = new Territory("Territory2", 0, 0);
        Territory territory3 = new Territory("Territory3", 0, 0);
        Territory territory4 = new Territory("Territory4", 0, 0);
        Territory territory5 = new Territory("Territory5", 0, 0);

        ArrayList<Territory> t1 = new ArrayList<>();
        t1.add(territory2);
        t1.add(territory3);
        territory1.setAdjacentTerritories(t1);

        ArrayList<Territory> t2 = new ArrayList<>();
        t2.add(territory1);
        t2.add(territory3);
        territory2.setAdjacentTerritories(t2);

        ArrayList<Territory> t3 = new ArrayList<>();
        t3.add(territory1);
        t3.add(territory2);
        t3.add(territory4);
        territory3.setAdjacentTerritories(t3);

        ArrayList<Territory> t4 = new ArrayList<>();
        t4.add(territory3);
        territory4.setAdjacentTerritories(t4);

        ArrayList<Territory> visitedTerritories = new ArrayList<>();
        rg.recursiveDepthFirstSearchOnFriendlyTerritories(territory1,visitedTerritories);

        assertEquals(4, visitedTerritories.size());
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
            assertEquals(TurnPhase.SHOW_NEXT_PLAYER_TURN, e.getPhase());
            assertEquals(player, e.getCurrentPlayer());
            test = this.INITIAL;
        }
        else if (test == TEST_SET_ATTACK_DICE1) {
            assertEquals(1, rg.getAttackDiceNum());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(player, e.getCurrentPlayer());
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(1, chooseBoundsEvent.getMaxChoice());
            test = this.INITIAL;
        }
        else if (test == TEST_SET_ATTACK_DICE2) {
            assertEquals(1, rg.getAttackDiceNum());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(player, e.getCurrentPlayer());
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(2, chooseBoundsEvent.getMaxChoice());
            test = this.INITIAL;
        }
        else if (test == TEST_SET_ATTACK_DICE3) {
            assertEquals(1, rg.getAttackDiceNum());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, rg.getPhase());
            assertEquals(TurnPhase.DEFEND_CHOOSE_DICE, e.getPhase());
            RiskEventBounds chooseBoundsEvent = (RiskEventBounds)e;
            assertEquals(player, e.getCurrentPlayer());
            assertEquals(1, chooseBoundsEvent.getMinChoice());
            assertEquals(2, chooseBoundsEvent.getMaxChoice());
            test = this.INITIAL;
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
        else if (test == TEST_BATTLE_NO_DEATH_DEFENDER_LOSS) {
            if (e instanceof RiskEventDiceResults) {
                reachedProperEvent = true;
                RiskEventDiceResults diceResultsEvent = (RiskEventDiceResults)e;
                assertEquals(2, diceResultsEvent.getTerritoryFrom().getArmies());
                assertEquals(1, diceResultsEvent.getTerritoryTo().getArmies());
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