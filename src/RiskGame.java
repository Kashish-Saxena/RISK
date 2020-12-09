import javax.swing.*;
import java.util.*;

/**
 * This is the main class of the Risk Game. It creates and instantiates the players, territories and continents, creates the parser
 * and takes input from the user for the command words, number of players, player names, attacking and defending territories,
 * attacking and defending dice numbers, starts the battle, determines the game standing of players and determines the outcome
 * of the game.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version November 23, 2020
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
    private int totalDeployAmount;

    public static final int MIN_ARMY_DEPLOY = 3;
    public static final int MIN_DEPLOY_AMOUNT = 1;
    public static final int MIN_FORTIFY_AMOUNT = 1;
    public static final int MIN_ARMY_ATTACK = 2;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    public static final int MAX_ATTACK_DICE = 3;
    public static final int MAX_DEFEND_DICE = 2;
    public static final int MIN_DICE_ROLL = 1;
    public static final int MAX_DICE_ROLL = 6;
    public static final int MIN_ARMY_TO_MOVE_FROM_ATTACK = 5;

    private ArrayList<RiskView> riskViews;

    private boolean testingMain;
    private boolean testingGame;

    /**
     * Constructor of the RiskGame class. It initializes the field values, sets up the initial game state.
     */
    public RiskGame(boolean testingMain, boolean testingGame){
        this.testingMain = testingMain;
        this.testingGame = testingGame;
        players = new ArrayList<>();
        riskViews = new ArrayList<RiskView>();
        gameInProgress = true;

        numPlayers = 0;
        currentPlayerIndex = 0;

        attackDiceNum = 0;
        defendDiceNum = 0;

        //start game off with the deploy phase of the first player
        phase = TurnPhase.DEPLOY_CALCULATE_ARMIES_TO_PLACE;
    }

    /**
     * Adds a view to the RiskGame Model.
     * @param view View to be added.
     */
    public void addView(RiskView view) {
        riskViews.add(view);
        if (riskViews.size() == 1) { //first view added must initialize the game
            notifyAllViews(new RiskEventBounds(this, TurnPhase.INITIAL_SETUP, new Player("player", this), RiskGame.MIN_PLAYERS, RiskGame.MAX_PLAYERS));
        }
    }

    /**
     * Helper method to notify all views that an update has occured to the model.
     * This method just invokes the handleRiskUpdate of all views and passes the RiskEvent e to all the views
     * @param e the event to send to all views
     */
    private void notifyAllViews(RiskEvent e){
        for(RiskView v: riskViews){
            v.handleRiskUpdate(e);
        }
    }

    /**
     * autoPlaceArmies is the auto unit placement algorithm. it dynamically sets Territory Ownership and army numbers
     * for all players in a few grouped clusters.
     */
    public void autoPlaceArmies() {

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

        //invoke methods to automatically randomly fill assign territories
        // to players and assign armies to those territories
        fillTerritoriesWithOneArmy();
        addRemainingArmy();
    }

    /**
     * First fills territories with 1 army until every territory has 1 army on it.
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
     * Gives Territory tempTerr to Player tempPlay by setting Territory owner
     * and adding 1 army to the Territory, aslo decrements the Player's armies to place field.
     */
    private void giveTerritory(Territory tempTerr, Player tempPlay){
        tempTerr.setOwner(tempPlay);
        tempPlay.addTerritory(tempTerr);
        tempPlay.setArmiesToPlace(tempPlay.getArmiesToPlace() - 1);
        tempTerr.addArmies(1);
    }

    /**
     * Now that every territory has at least 1 army on it, adds the remaining
     * player armies randomly across the territories which they own.
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
     * Returns the current player from, players who is taking their turn based off currentPlayerIndex.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Calculates how many armies the player has to deploy then begins the DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO phase.
     */
    public void calculateArmiesToDeploy() {
        Player currPlayer = getCurrentPlayer();

        //1 army per 3 territories owned
        int armiesFromTerritoryOwnership = currPlayer.getTerritories().size() / 3;

        //armies for owning entire continents
        int armiesFromContinentOwnership = 0;
        for (Continent c : currPlayer.getContinents()) {
            armiesFromContinentOwnership += c.getValue();
        }

        //set totalDeployAmount, this value represents the total amount of armies the player has to deploy
        totalDeployAmount = armiesFromTerritoryOwnership + armiesFromContinentOwnership;

        //pity minimum amount of 3
        if (totalDeployAmount < MIN_ARMY_DEPLOY) {
            totalDeployAmount = MIN_ARMY_DEPLOY;
        }

        String message = currPlayer.getName() + " has " + totalDeployAmount + " armies to place \n" +
                armiesFromTerritoryOwnership + " from Territories and \n" + armiesFromContinentOwnership + " from Continents";
        notifyAllViews(new RiskEventMessage(this, TurnPhase.DEPLOY_CALCULATE_ARMIES_TO_PLACE,
                getCurrentPlayer(), message));

        //set phase to DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO to let player choose the territory(s) to deploy to
        phase = TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO;
        if (!getCurrentPlayer().isAI()) {
            notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO, getCurrentPlayer(), getCurrentPlayer().getTerritories()));
        }
        else {
            PlayerAI player = (PlayerAI) getCurrentPlayer();
            setDeployTerritory(player.getDeployTerritory());
        }
    }

    /**
     * Gives amountToDeploy armies to the territory the player selected.
     *
     * @param amountToDeploy The number of armies to be deployed.
     */
    public void giveDeployedArmies(int amountToDeploy){
        deployTerritory.addArmies(amountToDeploy);

        //subtract the amount deployed from totalDeployAmount
        //(will be used later to check if player has armies left to deploy)
        totalDeployAmount -= amountToDeploy;

        //update the army number of that territory on GUI
        phase = TurnPhase.DEPLOY_UPDATE_DEPLOYED_TERRITORY;
        notifyAllViews(new RiskEventSingleTerritory(this, TurnPhase.DEPLOY_UPDATE_DEPLOYED_TERRITORY, getCurrentPlayer(), deployTerritory));
        if (getCurrentPlayer().isAI()) {
            notifyAllViews(new RiskEventMessage(this, TurnPhase.AI_INFO, getCurrentPlayer(), "Deployed " + amountToDeploy + " to " + deployTerritory.getName()));
        }
        checkIfThereAreArmiesLeftToDeploy();
    }

    /**
     * Checks if the player still has Armies left to deploy, if yes then send them through another
     * deployment loop, else they have no more armies left to deploy so move onto the attack phase
     */
    public void checkIfThereAreArmiesLeftToDeploy(){
        //if player has armies left to deploy
        if(totalDeployAmount >= 1) {
            String message = totalDeployAmount + " armies remaining to deploy";
            notifyAllViews(new RiskEventMessage(this, TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO,
                    getCurrentPlayer(), message));

            //set phase to DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO to let player choose more territory(s) to deploy to
            phase = TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO;
            if (!getCurrentPlayer().isAI()) {
                notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO, getCurrentPlayer(), getCurrentPlayer().getTerritories()));
            }
            else {
                PlayerAI player = (PlayerAI) getCurrentPlayer();
                setDeployTerritory(player.getDeployTerritory());
            }
        }
        //else player has no armies left to deploy so send them to next phase
        else {
            phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
            if (!getCurrentPlayer().isAI()) {
                notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
            }
            else {
                PlayerAI player = (PlayerAI) getCurrentPlayer();
                if (player.hasFavorableAttacks()) {
                    setAttackerTerritory(player.getAttackingTerritory());
                }
                else {
                    phase = TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY;
                    chooseFortifyFrom();
                }
            }
        }
    }

    /**
     * Calculates the valid territories to fortify from and makes user select one of those options
     * note, at any time the player may decide to skip their fortify phase, in which case they
     * click the pass turn button and the turn is passed to the next player.
     */
    public void chooseFortifyFrom(){
        Player currPlayer = getCurrentPlayer();

        //valid fortify from territories are friendly territories that are connected to at least one other friendly territory and have at least 2 armies
        phase = TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY;
        if (!currPlayer.isAI()) {
            ArrayList<Territory> validChoices = new ArrayList<Territory>();
            for (Territory t : currPlayer.getTerritories()) {
                if (t.getAdjacentFriendlyTerritories().size() >= 1 && t.getArmies() >= 2) {
                    validChoices.add(t);
                }
            }
            notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY, currPlayer, validChoices));
        }
        else {
            PlayerAI player = (PlayerAI) currPlayer;
            setFortifierTerritory(player.getMovingTerritory());
        }
    }

    /**
     * Makes the player select the Territory to fortify/move armies to, note that this method
     * uses recursiveDepthFirstSearchOnFriendlyTerritories as a helper method to to determine all "connected" friendly territories
     * of the fromTerritory
     */
    private void chooseFortifyTo(){
        if (!getCurrentPlayer().isAI()) {
            ArrayList<Territory> validChoices = new ArrayList<Territory>();

            //after calling recursiveDepthFirstSearchOnFriendlyTerritories, validChoices contains all the visited Territories
            recursiveDepthFirstSearchOnFriendlyTerritories(fromTerritory, validChoices);

            //remove fromTerritory from choices since you can't move armies from one Territory to itself
            validChoices.remove(fromTerritory);

            notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.FORTIFY_CHOOSE_TO_TERRITORY, getCurrentPlayer(), validChoices));
        }
        else {
            PlayerAI player = (PlayerAI) getCurrentPlayer();
            setFortifiedTerritory(player.getTerritoryToFortify(fromTerritory));
        }
    }

    /**
     * Does a depth first search traversal starting at currentTerritory to determine all "connected" friendly territories.
     */

    //note I made this method public for testing it but usually it would be private
    public void recursiveDepthFirstSearchOnFriendlyTerritories(Territory currentTerritory, ArrayList<Territory> visitedTerritories){
        //mark currentTerritory as visited by adding it to visitedTerritories
        visitedTerritories.add(currentTerritory);

        //Traverse all the adjacent and unmarked Territories and call the recursive function with index of adjacent Territory.
        for(Territory t: currentTerritory.getAdjacentFriendlyTerritories()){
            if(!visitedTerritories.contains(t)){//if not visited
                recursiveDepthFirstSearchOnFriendlyTerritories(t, visitedTerritories);
            }
        }
    }

    /**
     * Moves armies from one of the owned territories to another owned territory.
     * @param fortifyArmyAmount The number of armies to move.
     */
    public void fortify(int fortifyArmyAmount){
        fromTerritory.setArmies(fromTerritory.getArmies() - fortifyArmyAmount);
        toTerritory.setArmies(toTerritory.getArmies() + fortifyArmyAmount);

        if (getCurrentPlayer().isAI()) {
            notifyAllViews(new RiskEventMessage(this, TurnPhase.AI_INFO, getCurrentPlayer(), "Moved " + fortifyArmyAmount + " from " + fromTerritory.getName() + " to " + toTerritory.getName()));
        }

        //update gui with changes
        phase = TurnPhase.FORTIFY_UPDATE_FORTIFIED_TERRITORIES;
        notifyAllViews(new RiskEventTerritories(this, TurnPhase.FORTIFY_UPDATE_FORTIFIED_TERRITORIES, getCurrentPlayer(), fromTerritory, toTerritory));

        //pass turn to next player
        passTurn();
    }

    public void setDeployTerritory(Territory territory) {
        deployTerritory = territory;

        //after user clicked on territory to deploy to, prompt the player for a Deploy Amount
        phase = TurnPhase.DEPLOY_CHOOSE_DEPLOY_AMOUNT;
        if (!getCurrentPlayer().isAI()) {
            notifyAllViews(new RiskEventBounds(this, TurnPhase.DEPLOY_CHOOSE_DEPLOY_AMOUNT,
                    getCurrentPlayer(), MIN_DEPLOY_AMOUNT, totalDeployAmount));
        }
        else {
            PlayerAI player = (PlayerAI) getCurrentPlayer();
            giveDeployedArmies(player.getDeployAmount());
        }
    }

    public void setAttackerTerritory(Territory territory) {
        fromTerritory = territory;
        phase = TurnPhase.ATTACK_CHOOSE_ENEMY;
        if (!getCurrentPlayer().isAI()) {
            notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ENEMY, getCurrentPlayer(), territory.getAdjacentEnemyTerritories()));
        }
        else {
            PlayerAI player = (PlayerAI) getCurrentPlayer();
            setEnemyTerritory(player.getTerritoryToAttack(fromTerritory));
        }
    }

    public void setEnemyTerritory(Territory territory) {
        toTerritory = territory;
        phase = TurnPhase.ATTACK_CHOOSE_DICE;
        if (!getCurrentPlayer().isAI()) {
            notifyAllViews(new RiskEventBounds(this, TurnPhase.ATTACK_CHOOSE_DICE, getCurrentPlayer(), 1, Math.min(fromTerritory.getArmies() - 1, MAX_ATTACK_DICE)));
        }
        else {
            PlayerAI player = (PlayerAI) getCurrentPlayer();
            setAttackDice(player.getAttackDiceNum(fromTerritory));
        }
    }

    public void setFortifierTerritory(Territory territory) {
        fromTerritory = territory;

        //after user clicked on territory to move armies from, make the
        // user select a connected territory to move the armies to
        phase = TurnPhase.FORTIFY_CHOOSE_TO_TERRITORY;
        chooseFortifyTo();
    }

    public void setFortifiedTerritory(Territory territory) {
        toTerritory = territory;

        //after user clicked on territory to fortify to, prompt the player for a fortify Amount
        phase = TurnPhase.DEPLOY_CHOOSE_DEPLOY_AMOUNT;
        if (!getCurrentPlayer().isAI()) {
            notifyAllViews(new RiskEventBounds(this, TurnPhase.FORTIFY_CHOOSE_FORTIFY_AMOUNT,
                    getCurrentPlayer(), MIN_FORTIFY_AMOUNT, fromTerritory.getArmies() - 1));
        }
        else {
            fortify(PlayerAI.getMoveNum(fromTerritory));
        }
    }

    /**
     * Passes the turn to the next player.
     */
    public void passTurn() {
        do {
            currentPlayerIndex++;
            if (currentPlayerIndex >= numPlayers) {
                currentPlayerIndex = 0;
            }
        } while (players.get(currentPlayerIndex).getGameStanding() != 0 );

        attackDiceNum = 0;
        defendDiceNum = 0;

        //show message saying its player's next turn
        notifyAllViews(new RiskEventMessage(this, TurnPhase.SHOW_NEXT_PLAYER_TURN,
                getCurrentPlayer(), getCurrentPlayer().getName() + "'s turn"));

        //start the next player's turn by calculating their armies to deploy
        phase = TurnPhase.DEPLOY_CALCULATE_ARMIES_TO_PLACE;
        calculateArmiesToDeploy();
    }

    /**
     * Sets the attack dice number.
     * @param num The dice number to be set.
     */
    public void setAttackDice(int num) {
        //should check bounds again
        attackDiceNum = num;
        phase = TurnPhase.DEFEND_CHOOSE_DICE;
        if (fromTerritory.getOwner().isAI()) {
            String message = getCurrentPlayer().getName() + " attacks " + toTerritory.getName() + " from " + fromTerritory.getName() + " with " + num + " dice";
            notifyAllViews(new RiskEventMessage(this, TurnPhase.AI_INFO, getCurrentPlayer(), message));
        }

        if (!toTerritory.getOwner().isAI()) {
            notifyAllViews(new RiskEventBounds(this, TurnPhase.DEFEND_CHOOSE_DICE, getCurrentPlayer(), 1, Math.min(toTerritory.getArmies(), MAX_DEFEND_DICE)));
        }
        else {
            setDefendDice(PlayerAI.getDefendDiceNum(toTerritory));
        }
    }

    /**
     * Sets the defend dice number.
     * @param num The dice number to be set.
     */
    public void setDefendDice(int num) {
        //should check bounds again
        defendDiceNum = num;
        if (toTerritory.getOwner().isAI()) {
            String message = getCurrentPlayer().getName() + " defends with " + num + " dice";
            notifyAllViews(new RiskEventMessage(this, TurnPhase.AI_INFO, getCurrentPlayer(), message));
        }
        rollDice();
    }

    /**
     *  Determines how many armies the attacking and defending territory lost and returns an array of ints.
     * @param attackDice List of Attacker's dice rolls.
     * @param defendDice List of Defender's dice rolls.
     * @return Array of number of armies the attacking and defending territory lost.
     */
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

    /**
     * Generates a list of dice rolls for the attacker and defender.
     */
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

    /**
     * Determines the result of the battle.
     * @param battleResults An array of the result of the battle containing the number of armies the attacker and defender lost.
     * @param attackDice A list of attacker's dice rolls.
     * @param defendDice A list of defender's dice rolls.
     */
    public void battleResults(int[] battleResults, List<Integer> attackDice, List<Integer> defendDice) {
        int attackArmyLoss = battleResults[0];
        int defendArmyLoss = battleResults[1];

        fromTerritory.subtractArmies(attackArmyLoss);
        Player defender = toTerritory.getOwner();
        if (toTerritory.getArmies() > defendArmyLoss) { //territory not conquered
            toTerritory.subtractArmies(defendArmyLoss);
            notifyAllViews(new RiskEventDiceResults(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), fromTerritory, toTerritory, attackDice, defendDice, attackArmyLoss, defendArmyLoss, defender));
            if (getCurrentPlayer().canAttack()) {
                phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
                if (!getCurrentPlayer().isAI()) {
                    notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
                }
                else {
                    PlayerAI player = (PlayerAI) getCurrentPlayer();
                    if (player.hasFavorableAttacks()) {
                        setAttackerTerritory(player.getAttackingTerritory());
                    }
                    else {
                        phase = TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY;
                        chooseFortifyFrom();
                    }
                }
            }
            else {
                phase = TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY;
                chooseFortifyFrom();//start fortify phase
            }
        }
        else { //territory conquered
            defender.removeTerritory(toTerritory);
            getCurrentPlayer().addTerritory(toTerritory);
            notifyAllViews(new RiskEventContinent(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), RiskMap.getContinentFromTerritory(toTerritory)));


            toTerritory.setArmies(0);
            phase = TurnPhase.ATTACK_CHOOSE_MOVE;
            notifyAllViews(new RiskEventDiceResults(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), fromTerritory, toTerritory, attackDice, defendDice, attackArmyLoss, -1, defender));
            if (!getCurrentPlayer().isAI()) {
                notifyAllViews(new RiskEventBounds(this, TurnPhase.ATTACK_CHOOSE_MOVE, getCurrentPlayer(), attackDiceNum, fromTerritory.getArmies() - 1));
            }
            else {
                move(PlayerAI.getMoveNum(fromTerritory));
            }
        }
    }

    /**
     * Moves the input number of armies from attacking territory to the territory attacked.
     * @param num The number of armies to be moved.
     */
    public void move(int num) {
        //should check bounds again
        toTerritory.addArmies(num);
        fromTerritory.subtractArmies(num);
        notifyAllViews(new RiskEventTerritories(this, TurnPhase.ATTACK_UPDATE_TERRITORIES, getCurrentPlayer(), fromTerritory, toTerritory));
        if (getCurrentPlayer().isAI()) {
            String message = "Moved " + num + " armies from " + fromTerritory.getName() + " to " + toTerritory.getName();
            notifyAllViews(new RiskEventMessage(this, TurnPhase.AI_INFO, getCurrentPlayer(), message));
        }

        if (getCurrentPlayer().canAttack()) {
            phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
            if (!getCurrentPlayer().isAI()) {
                notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
            }
            else {
                PlayerAI player = (PlayerAI) getCurrentPlayer();
                if (player.hasFavorableAttacks()) {
                    setAttackerTerritory(player.getAttackingTerritory());
                }
                else {
                    phase = TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY;
                    chooseFortifyFrom();
                }
            }
        }
        else {
            phase = TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY;
            chooseFortifyFrom();//start fortify phase
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
            notifyAllViews(new RiskEventEnd(this, TurnPhase.END, getCurrentPlayer(), players));
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
            notifyAllViews(new RiskEventPlayer(this, TurnPhase.ATTACK_RESULT, getCurrentPlayer(), player));
        }
    }

    /**
     * Returns a list of players in the game.
     * @return A list of players in the game.
     */
    public List<Player> getPlayers(){
        return this.players;
    }

    /**
     * Returns the number of players in the game.
     * @return The number of players in the game.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * Adds the input player to the list of players in the game.
     * @param player Player to be added.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Returns the TurnPhase of the player.
     * @return The TurnPhase of the player.
     */
    public TurnPhase getPhase() {
        return phase;
    }
    //below methods are for testing only

    /**
     * Adds the input player to the list of players in the game.
     * @param player Player to be added.
     */
    public void addPlayerTest(Player player) {
        if (testingMain || testingGame) {
            players.add(player);
            numPlayers++;
        }
    }



    /**
     * Sets the TurnPhase of the player.
     * @param phase The TurnPhase to be set.
     */
    public void setPhase(TurnPhase phase) {
        if (testingMain || testingGame) { //this shouldn't be used, use the RiskEvent
            this.phase = phase;
        }
    }

    /**
     * Return the attack dice number.
     * @return The attack dice number.
     */
    public int getAttackDiceNum() {
        //if (testing || testingGame) { //this shouldn't be used outside of testing
        return attackDiceNum;
        //}
    }

    /**
     * Return the defend dice number.
     * @return The defend dice number.
     */
    public int getDefendDiceNum() {
        //if (testing || testingGame) { //this shouldn't be used outside of testing
        return defendDiceNum;
        //}
    }

    /**
     * Sets fromTerritory (used in testing).
     * @param territory The input territory to set fromTerritory to.
     */
    public void setFromTerritory(Territory territory) {
        if (testingMain || testingGame) {
            fromTerritory = territory;
        }
    }

    /**
     * Sets toTerritory (used in testing).
     * @param territory The input territory to set toTerritory to.
     */
    public void setToTerritory(Territory territory) {
        if (testingMain || testingGame) {
            toTerritory = territory;
        }
    }

    /**
     * Sets deployTerritory (used in testing).
     * @param territory The input territory to set toTerritory to.
     */
    public void setDeployTerritoryTest(Territory territory) {
        if (testingMain || testingGame) {
            deployTerritory = territory;
        }
    }

    /**
     * returns totalDeployAmount.
     * @return totalDeployAmount.
     */
    public int getTotalDeployAmount() {
        return totalDeployAmount;
    }

    /**
     * Cancels an attack in progress.
     */
    public void cancelAttack() {
        phase = TurnPhase.ATTACK_CHOOSE_ATTACKERS;
        notifyAllViews(new RiskEventChooseTerritory(this, TurnPhase.ATTACK_CHOOSE_ATTACKERS, getCurrentPlayer(), getCurrentPlayer().getAttackableTerritories()));
    }
}
