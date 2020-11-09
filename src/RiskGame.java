import javax.swing.*;
import java.util.*;


/**
 * This is the main class of the Risk Game. It creates and instantiates the players, territories and continents, creates the parser
 * and takes input from the user for the command words, number of players, player names, attacking and defending territories,
 * attacking and defending dice numbers, starts the battle, determines the game standing of players and determines the outcome
 * of the game.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version October 25, 2020
 */
public class RiskGame implements Observer {


    private List<Player> players;
    private int currentPlayerIndex;
    private TurnPhase phase;
    private boolean gameInProgress;
    private InputParser parser;
    private int numPlayers;

    private Territory fromTerritory;
    private Territory toTerritory;
    private int attackDiceNum;
    private int defendDiceNum;

    public static final int MIN_ARMY_ATTACK = 2;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    public static final int MAX_ATTACK_DICE = 3;
    public static final int MAX_DEFEND_DICE = 2;
    public static final int MIN_DICE_ROLL = 1;
    public static final int MAX_DICE_ROLL = 6;
    public static final int MIN_ARMY_TO_MOVE_FROM_ATTACK = 5;

    private RiskView riskFrame;

    /**
     * Constructor of the RiskGame class. It initializes the field values, sets up the initial game state
     */
    public RiskGame(){
        players = new ArrayList<>();
        gameInProgress = true;

        //create all Players, Territories and Continents
        setupOptions();
        currentPlayerIndex = 0;

        //auto assign starting player armies to territories
        autoPlaceArmies();

        attackDiceNum = 0;
        defendDiceNum = 0;

        phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
    }

    public void addView(RiskView view) {
        riskFrame = view;
        riskFrame.handleRiskUpdate(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
    }

    /**
     * setupOptions sets up the game's initial state, takes input from user for the number of players, instantiates player
     * objects, territories, continents and set adjacent territories connections.
     */
    private void setupOptions(){
        System.out.println("===========================================");
        System.out.println("==           RISK MILESTONE 1            ==");
        System.out.println("==                  By                   ==");
        System.out.println("==         David Sciola 101082459        ==");
        System.out.println("==         Kevin Quach 101115704         ==");
        System.out.println("==         Kashish Saxena 101107204      ==");
        System.out.println("===========================================");

        //Prompting for player information
        String str = JOptionPane.showInputDialog("Enter Number of Players (2-6):");
        numPlayers = 0;
        try
        {
            if(str != null)
                numPlayers = Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            numPlayers = 0;
        }
        while (!(numPlayers >= MIN_PLAYERS && numPlayers <= MAX_PLAYERS)) {
            numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Invalid. Please enter number of players between 2-6:"));
        }
        for (int i = 0; i < numPlayers; i++) {
            String name = JOptionPane.showInputDialog("Enter Player " + (i + 1) + "'s name:");
            Player player = new Player(name,this);
            players.add(player);
        }
    }

    /**
     * autoPlaceArmies is the auto unit placement algorithm. it dynamically sets Territory Ownership and army numbers
     * for all players in a few grouped clusters.
     */
    private void autoPlaceArmies() {

        //calculate the number of armies each player has to place
        int numArmiesToPlace;
        if(numPlayers == 2){ numArmiesToPlace = 50; }
        else{numArmiesToPlace = 50 - (5 * numPlayers); }

        //set armiesToPlace for each Player
        for(Player p: players){ p.setArmiesToPlace(numArmiesToPlace); }

        //first fill territories with 1 army until every territory has 1 army on it
        Random ran = new Random();
        int initialNumArmiesToBePlaced = RiskMap.numTerritories()-1;
        int playerIndex = 0;
        int randomTerritoryIndex;

        Player tempPlay = players.get(0);

        Territory tempTerr;

        for(Territory t: RiskMap.getTerritoryMap().values()){
            System.out.println(t.getName() + ": " + t.getArmies());
        }


        while(initialNumArmiesToBePlaced >= 0){

            //point tempTerr towards a territory to add army
            //(20% to be a random un-owned territory, 80% to be an un-owned neighbor territory)

            //default 20% random territory option
            do {
                randomTerritoryIndex = ran.nextInt(RiskMap.numTerritories());
                tempTerr = RiskMap.getTerritoryFromIndex(randomTerritoryIndex);
            }
            while (tempTerr.getOwner() != null);

            if(ran.nextInt(5) != 0) { //80% chance un-owned neighbor territory option
                Set<Territory> freeNeighborTerritories = new HashSet<>();
                for(Territory t: tempPlay.getTerritories()){
                    for(Territory t2: t.getAdjacentTerritories()){
                        if(t2.getOwner() == null){
                            freeNeighborTerritories.add(t2);
                        }
                    }
                }

                //if there was actually no free neighbor territories just remain with default random territory selection
                if(freeNeighborTerritories.size() == 0){
                    continue;
                }
                //else there are free neighbor territories and the player can claim one
                else{
                    int size = freeNeighborTerritories.size();
                    int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
                    int i = 0;
                    for(Territory t : freeNeighborTerritories)
                    {
                        if (i == item){tempTerr = t;}
                        i++;
                    }
                }
            }


            //give player the territory
            tempTerr.setOwner(tempPlay);
            tempPlay.addTerritory(tempTerr);
            tempPlay.setArmiesToPlace(tempPlay.getArmiesToPlace() - 1);
            tempTerr.addArmies(1);

            //increment playerIndex and update tempPlay
            if(playerIndex == players.size()-1){ playerIndex = 0; }
            else{playerIndex += 1; }
            tempPlay = players.get(playerIndex);

            System.out.println(initialNumArmiesToBePlaced);
            initialNumArmiesToBePlaced -= 1;
        }

        //now that every territory has at least 1 army on it, add the remaining
        //player armies randomly across the territories which they own
        int randTerrIndex;
        Territory randTerr;
        for(Player p: players){
            for(int i = p.getArmiesToPlace(); i > 0; i--){
                randTerrIndex = ran.nextInt(p.getTerritories().size());
                randTerr = p.getTerritories().get(randTerrIndex);
                randTerr.addArmies(1);
            }
            p.setArmiesToPlace(0);
        }

        //print the map state after initial auto army placement
        //printMapState();
    }

    /**
     * printMapState prints the current map state of the game.
     */
    private void printMapState(){

        System.out.println();
        System.out.println("================ MAP STATE ================");
        for(Player p: players){
            System.out.println("========== "+ p.getName() +" ==========");
            if (p.getGameStanding() == 0) {//if they aren't dead
              
                //print owned continents
                System.out.println("owned continents: ");
                if (p.getContinents().size() == 0) {
                    System.out.print("none");
                } else {
                    for (Continent c : p.getContinents()) {
                        System.out.println(c);
                    }
                }
                System.out.println("");

                //print owned territories
                System.out.println("owned territories: ");
                for (Territory t : p.getTerritories()) {
                    System.out.println(t.getName() + ":" + t.getArmies());
                }
                System.out.println();
            }
            else{
                System.out.println("dead");
                System.out.println();
            }
        }
    } //deprecate this

    /**
     * printWinner prints the winner of the game and all the other player standings
     */
    private void printWinner(){
        System.out.println("");
        System.out.println("================ GAME OVER ================");
        for(int i = 0; i <= players.size()-1; i++){
            for(Player p : players){
                if(p.getGameStanding() == i){
                    if(i == 0){
                        System.out.println(p.getName() + " wins!");
                    }
                    else{
                        System.out.println(p.getName() + " had a standing of " + (i+1));
                    }
                }
            }
        }
    } //deprecate this

    /**
     * printStalemate prints a stalemate game ending
     */
    private void printStalemate(){
        System.out.println("");
        System.out.println("=============== STALEMATE =================");
        System.out.println("all remaining player don't have enough troops to mount an attack");
    } //deprecate this

    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void processTerritory(Territory territory) {
        if (phase == TurnPhase.ATTACK_CHOOSE_ATTACKERS) {
            fromTerritory = territory;
            phase = TurnPhase.ATTACK_CHOOSE_ENEMY;
            riskFrame.handleRiskUpdate(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ENEMY, getCurrentPlayer(), territory.getAdjacentEnemyTerritories()));
        }
        else if (phase == TurnPhase.ATTACK_CHOOSE_ENEMY) {
            toTerritory = territory;
            phase = TurnPhase.ATTACK_CHOOSE_DICE;
            riskFrame.handleRiskUpdate(new RiskEventBounds(this, TurnPhase.ATTACK_CHOOSE_DICE, getCurrentPlayer(), 1, Math.min(fromTerritory.getArmies() - 1, MAX_ATTACK_DICE)));
        }
        //eventually, move
    }

    public void passTurn() {
        do {
            currentPlayerIndex++;
            if (currentPlayerIndex >= numPlayers) {
                currentPlayerIndex = 0;
            }
        } while (players.get(currentPlayerIndex).getGameStanding() != 0 );

        attackDiceNum = 0;
        defendDiceNum = 0;
        phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
        riskFrame.handleRiskUpdate(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
    }

    public void setAttackDice(int num) {
        //should check bounds again
        attackDiceNum = num;
        phase = TurnPhase.DEFEND_CHOOSE_DICE;
        riskFrame.handleRiskUpdate(new RiskEventBounds(this, TurnPhase.DEFEND_CHOOSE_DICE, getCurrentPlayer(), 1, Math.min(toTerritory.getArmies(), MAX_DEFEND_DICE)));
    }

    public void setDefendDice(int num) {
        //should check bounds again
        defendDiceNum = num;
        battle();
    }

    private void battle() {
        Random rand = new Random();
        ArrayList<Integer> attackDice = new ArrayList<>();
        for (int i = 0; i < attackDiceNum; i++) {
            attackDice.add(rand.nextInt(MAX_DICE_ROLL) + MIN_DICE_ROLL);
        }
        Collections.sort(attackDice, Collections.reverseOrder());

        ArrayList<Integer> defendDice = new ArrayList<>();
        for (int i = 0; i < defendDiceNum; i++) {
            defendDice.add(rand.nextInt(MAX_DICE_ROLL) + MIN_DICE_ROLL);
        }
        Collections.sort(defendDice, Collections.reverseOrder());

        int attackArmyLoss = 0;
        int defendArmyLoss = 0;

        int maxDiceNum = Math.min(attackDiceNum, defendDiceNum);
        for (int i = 0; i < maxDiceNum; i++) {
            if (attackDice.get(i) <= defendDice.get(i)) {
                attackArmyLoss++;
            }
            else {
                defendArmyLoss++;
            }
        }

        fromTerritory.subtractArmies(attackArmyLoss);
        Player defender = toTerritory.getOwner();

        if (toTerritory.getArmies() > defendArmyLoss) {
            toTerritory.subtractArmies(defendArmyLoss);

            riskFrame.handleRiskUpdate(new RiskEventDiceResults(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), fromTerritory, toTerritory, attackDice, defendDice, attackArmyLoss, defendArmyLoss, defender));
            if (getCurrentPlayer().canAttack()) {
                phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
                riskFrame.handleRiskUpdate(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
            }
            else {
                passTurn();
            }
        }
        else {
            defender.removeTerritory(toTerritory);
            getCurrentPlayer().addTerritory(toTerritory);

            if (fromTerritory.getArmies() < MIN_ARMY_TO_MOVE_FROM_ATTACK) {
                toTerritory.setArmies(fromTerritory.getArmies() - 1);
                fromTerritory.setArmies(1);

                riskFrame.handleRiskUpdate(new RiskEventDiceResults(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), fromTerritory, toTerritory, attackDice, defendDice, attackArmyLoss, -1, defender));
                if (getCurrentPlayer().canAttack()) {
                    phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
                    riskFrame.handleRiskUpdate(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
                }
                else {
                    passTurn();
                }
            }
            else {
                toTerritory.setArmies(0);
                phase = TurnPhase.ATTACK_CHOOSE_MOVE;
                riskFrame.handleRiskUpdate(new RiskEventDiceResults(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), fromTerritory, toTerritory, attackDice, defendDice, attackArmyLoss, -1, defender));
                riskFrame.handleRiskUpdate(new RiskEventBounds(this, TurnPhase.ATTACK_CHOOSE_MOVE, getCurrentPlayer(), MIN_ARMY_TO_MOVE_FROM_ATTACK - 2, fromTerritory.getArmies() - 1));
            }
        }
    }

    public void move(int num) {
        //should check bounds again
        toTerritory.addArmies(num);
        fromTerritory.subtractArmies(num);

        if (getCurrentPlayer().canAttack()) {
            phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
            riskFrame.handleRiskUpdate(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
        }
        else {
            passTurn();
        }
    }

    /**
     * Update is triggered whenever a player is eliminated from the game, it updates the game in progress status and
     * updates the game standing of the player.
     * @param o The observable object.
     * @param arg an argument passed to the notifyObservers method.
     */
    public void update(Observable o, Object arg) {
        updatePlayerGameStanding((Player) o);
        updateGameInProgress();
        if (!gameInProgress) {
            riskFrame.handleRiskUpdate(new RiskEventEnd(this, TurnPhase.END, getCurrentPlayer(), players));
        }
    }

    /**
     * UpdateGameInProgress updates the gameInProgress flag that determines if the game is over or is in progress based
     * on the number of players left in the game.
     *
     */
    private void updateGameInProgress(){
        int playersLeft = 0;
        for (Player p : players){
            if (p.getGameStanding() == 0)
                playersLeft++;
        }
        gameInProgress = (playersLeft >= 2);
    }

    /**
     * updatePlayerGameStanding updates the game standing of the player once the player has been eliminated.
     * @param player The player that was eliminated.
     */
    private void updatePlayerGameStanding(Player player){
        int maxStanding = players.get(0).getGameStanding();
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getGameStanding() > maxStanding)
                maxStanding = players.get(i).getGameStanding();
        }
        player.setGameStanding(maxStanding + 1);
        riskFrame.handleRiskUpdate(new RiskEventPlayer(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), player));
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
