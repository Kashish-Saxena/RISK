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
    private int numPlayers;

    private Territory deployTerritory;
    private Territory fromTerritory;
    private Territory toTerritory;
    private int attackDiceNum;
    private int defendDiceNum;

    public static final int MIN_ARMY_DEPLOY = 3;
    public static final int MIN_DEPLOY_AMOUNT = 1;
    public static final int MIN_ARMY_ATTACK = 2;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    public static final int MAX_ATTACK_DICE = 3;
    public static final int MAX_DEFEND_DICE = 2;
    public static final int MIN_DICE_ROLL = 1;
    public static final int MAX_DICE_ROLL = 6;
    public static final int MIN_ARMY_TO_MOVE_FROM_ATTACK = 5;

    private RiskView riskFrame;

    private boolean testingMain;
    private boolean testingGame;

    /**
     * Constructor of the RiskGame class. It initializes the field values, sets up the initial game state
     */
    public RiskGame(boolean testingMain, boolean testingGame){
        this.testingMain = testingMain;
        this.testingGame = testingGame;
        players = new ArrayList<>();
        gameInProgress = true;

        //create all Players, Territories and Continents
        if (!testingMain && !testingGame) {
            setupOptions();
        }
        else {
            numPlayers = 0;
        }
        currentPlayerIndex = 0;

        if (!testingMain && !testingGame) {
            //auto assign starting player armies to territories
            autoPlaceArmies();
        }

        attackDiceNum = 0;
        defendDiceNum = 0;

        //start game off with the deploy phase of the first player
        phase = TurnPhase.DEPLOY_CALCULATE_ARMIES_TO_PLACE;
    }

    public void addView(RiskView view) {
        riskFrame = view;

        //note, I removed the initial handleRiskUpdate while making the deploy phase

        //once again the game starts off with the deploy phase of the first player
        //riskFrame.handleRiskUpdate(new RiskEventChooseTerritory(this, TurnPhase.DEPLOY_CALCULATE_ARMIES_TO_PLACE, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
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
        if (numPlayers == 2) {
            numArmiesToPlace = 50;
        } else {
            numArmiesToPlace = 50 - (5 * numPlayers);
        }

        //set armiesToPlace for each Player
        for (Player p : players) {
            p.setArmiesToPlace(numArmiesToPlace);
        }

        fillTerritoriesWithOneArmy();
        addRemainingArmy();
    }

    /**
     * first fill territories with 1 army until every territory has 1 army on it.
     */
    private void fillTerritoriesWithOneArmy(){
        Random ran = new Random();
        int initialNumArmiesToBePlaced = RiskMap.numTerritories()-1;
        int playerIndex = 0;
        int randomTerritoryIndex;

        Player tempPlay = players.get(0);
        Territory tempTerr;

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
            giveTerritory(tempTerr, tempPlay);

            //increment playerIndex and update tempPlay
            if(playerIndex == players.size()-1){ playerIndex = 0; }
            else{playerIndex += 1; }
            tempPlay = players.get(playerIndex);
            initialNumArmiesToBePlaced -= 1;
        }
    }

    /**
     * gives Territory tempTerr to Player tempPlay by setting Territory owner
     * and adding 1 army to the Territory, aslo decrements the Player's armies to place field
     */
    private void giveTerritory(Territory tempTerr, Player tempPlay){
        tempTerr.setOwner(tempPlay);
        tempPlay.addTerritory(tempTerr);
        tempPlay.setArmiesToPlace(tempPlay.getArmiesToPlace() - 1);
        tempTerr.addArmies(1);
    }

    /**
     * now that every territory has at least 1 army on it, add the remaining
     * player armies randomly across the territories which they own
     */
    private void addRemainingArmy() {
        Random ran = new Random();
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
    }

    /**
     * returns the current player from, players who is taking their turn based off currentPlayerIndex
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * calculates how many armies the player has to deploy and sets that value in their armiesToPlace field
     */
    public void calculateArmiesToDeploy(){
        System.out.println("==== entering calculateArmiesToDeploy for " + getCurrentPlayer().getName() + " ====");

        Player currPlayer = getCurrentPlayer();

        int numTerritoriesOwned = currPlayer.getTerritories().size();

        System.out.println("num owned terr " + currPlayer.getTerritories().size());
        System.out.println("owned continents:");
        for(Continent c: currPlayer.getContinents()){
            System.out.println(c.toString());
        }

        int armiesFromTerritoryOwnership = numTerritoriesOwned/3;
        int armiesFromContinentOwnership = 0;
        for(Continent c: currPlayer.getContinents()){
            armiesFromContinentOwnership += c.getValue();
        }

        System.out.println("armies from Territories " + armiesFromTerritoryOwnership);
        System.out.println("armies from continents " + armiesFromContinentOwnership);

        int totalArmies = armiesFromTerritoryOwnership + armiesFromContinentOwnership;

        //pity minimum amount of 3
        if (totalArmies < MIN_ARMY_DEPLOY){
            totalArmies = MIN_ARMY_DEPLOY;
        }

        currPlayer.setArmiesToPlace(totalArmies);

        riskFrame.handleRiskUpdate(new RiskEventShowDeployAmount(this, TurnPhase.DEPLOY_CALCULATE_ARMIES_TO_PLACE,
                getCurrentPlayer(), totalArmies, armiesFromTerritoryOwnership, armiesFromContinentOwnership));

        //set phase to DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO to let player choose the territory(s) to deploy to
        phase = TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO;

        riskFrame.handleRiskUpdate(new RiskEventChooseTerritory(this, TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO, getCurrentPlayer(), getCurrentPlayer().getTerritories()));
    }

    /**
     * fetches the amount of armies the player has to deploy from the player and creates a prompt for an amount
     * to deploy (1 to maxDeployAmount)
     */
    public void promptDeployAmount(){
        System.out.println("==== entering promptDeployAmount ====");
        Player currPlayer = getCurrentPlayer();

        int maxDeployAmount = currPlayer.getArmiesToPlace();
        System.out.println("they can deploy " + maxDeployAmount);

        riskFrame.handleRiskUpdate(new RiskEventBounds(this, TurnPhase.DEPLOY_CHOOSE_DEPLOY_AMOUNT,
                getCurrentPlayer(), MIN_DEPLOY_AMOUNT, maxDeployAmount));
    }



    public void processTerritory(Territory territory) {
        if(phase == TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO){
            deployTerritory = territory;
            System.out.println("deploy territory is " +  deployTerritory.getName());

            //after user clicked on territory to deploy to, prompt the player for a Deploy Amount
            phase = TurnPhase.DEPLOY_CHOOSE_DEPLOY_AMOUNT;
            promptDeployAmount();
        }

        else if (phase == TurnPhase.ATTACK_CHOOSE_ATTACKERS) {
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
        rollDice();
    }

    public int[] simulateBattleFromDiceLists(List<Integer> attackDice, List<Integer> defendDice) {
        Collections.sort(attackDice, Collections.reverseOrder());
        Collections.sort(defendDice, Collections.reverseOrder());

        int attackArmyLoss = 0;
        int defendArmyLoss = 0;

        int maxDiceNum = Math.min(attackDice.size(), defendDice.size());
        for (int i = 0; i < maxDiceNum; i++) {
            if (attackDice.get(i) <= defendDice.get(i)) {
                attackArmyLoss++;
            }
            else {
                defendArmyLoss++;
            }
        }

        int[] losses = new int[2];
        losses[0] = attackArmyLoss;
        losses[1] = defendArmyLoss;
        return losses;
    }

    private void rollDice() {
        Random rand = new Random();
        //this creates a list of results from rolling the number of dice that the attacker requested
        ArrayList<Integer> attackDice = new ArrayList<>();
        for (int i = 0; i < attackDiceNum; i++) {
            attackDice.add(rand.nextInt(MAX_DICE_ROLL) + MIN_DICE_ROLL);
        }

        //this creates a list of results from rolling the number of dice that the defender requested
        ArrayList<Integer> defendDice = new ArrayList<>();
        for (int i = 0; i < defendDiceNum; i++) {
            defendDice.add(rand.nextInt(MAX_DICE_ROLL) + MIN_DICE_ROLL);
        }
        if (!testingMain && !testingGame) {
            battleResults(simulateBattleFromDiceLists(attackDice, defendDice), attackDice, defendDice);
        }
    }

    public void battleResults(int[] battleResults, List<Integer> attackDice, List<Integer> defendDice) {
        int attackArmyLoss = battleResults[0];
        int defendArmyLoss = battleResults[1];

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
            riskFrame.handleRiskUpdate(new RiskEventContinent(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), RiskMap.getContinentFromTerritory(toTerritory)));

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
                riskFrame.handleRiskUpdate(new RiskEventBounds(this, TurnPhase.ATTACK_CHOOSE_MOVE, getCurrentPlayer(), attackDiceNum, fromTerritory.getArmies() - 1));
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
            passTurn(); //change to fortify when available
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
        if (!gameInProgress && !testingMain) {
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
        if (!testingMain) {
            riskFrame.handleRiskUpdate(new RiskEventPlayer(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), player));
        }
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    //below methods are for testing only
    public void addPlayer(Player player) {
        if (testingMain || testingGame) {
            players.add(player);
            numPlayers++;
        }
    }

    public TurnPhase getPhase() {
        //if (testing || testingGame) { //this shouldn't be used, use the RiskEvent
            return phase;
        //}
    }

    public void setPhase(TurnPhase phase) {
        if (testingMain || testingGame) { //this shouldn't be used, use the RiskEvent
            this.phase = phase;
        }
    }

    public int getAttackDiceNum() {
        //if (testing || testingGame) { //this shouldn't be used outside of testing
        return attackDiceNum;
        //}
    }

    public int getDefendDiceNum() {
        //if (testing || testingGame) { //this shouldn't be used outside of testing
        return defendDiceNum;
        //}
    }
}
