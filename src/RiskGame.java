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
    private boolean gameInProgress;
    private InputParser parser;
    private int numPlayers;
    public static final int MIN_ARMY_ATTACK = 2;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    public static final int MAX_ATTACK_DICE = 3;
    public static final int MAX_DEFEND_DICE = 2;
    public static final int MIN_DICE_ROLL = 1;
    public static final int MAX_DICE_ROLL = 6;
    public static final int MIN_ARMY_TO_MOVE_FROM_ATTACK = 5;

    private ArrayList<RiskView> views;

    //private RiskMap riskMap;
    private RiskFrame riskFrame;

    /**
     * Constructor of the RiskGame class. It initializes the field values, sets up the initial game state
     */
    public RiskGame(){
        players = new ArrayList<>();
        gameInProgress = true;
        views = new ArrayList<>();
        //parser = new InputParser(riskMap);

        //create all Players, Territories and Continents
        setupOptions();

        //auto assign starting player armies to territories
        autoPlaceArmies();
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
        int numPlayers = 0;
        try
        {
            if(str != null)
                numPlayers = Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            numPlayers = 0;
        }
        while (!(numPlayers >= 2 && numPlayers < 7)) {
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
    }

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
    }

    /**
     * printStalemate prints a stalemate game ending
     */
    private void printStalemate(){
        System.out.println("");
        System.out.println("=============== STALEMATE =================");
        System.out.println("all remaining player don't have enough troops to mount an attack");
    }

    /**
     * processCommand executes a command once given an input.
     * @param player An input player object used by the methods.
     * @param command The command to be processed.
     */
    private void processCommand(Player player, CommandWord command){
        if(command == CommandWord.ATTACK){
            battle(player);

            //todo, for every class that implements the view interface, tell them to handle the change in map state
        }
        else if(command == CommandWord.STATUS){
            printMapState();
        }
        else if(command == CommandWord.PASS){
            player.setTurnPhase(TurnPhase.END);
        }
    }

    /**
     * battle determines the territories a player attacks and attacks from as well as the outcomes of a battle and their
     * placement in the game
     * @param player The player that starts the attack.
     */
    private void battle(Player player){
        String territoryString = "";
        for (Territory territory : player.getAttackableTerritories()) {
            territoryString += territory.toString() + ", ";
        }
        territoryString = territoryString.substring(0, territoryString.length() - 2);
        System.out.println("Territories to attack from: " + territoryString + "\n" +
                "Enter the name of the territory you wish to attack from.");
        Territory attackingTerritory = parser.getTerritory(player.getAttackableTerritories());

        territoryString = "";
        for (Territory territory : attackingTerritory.getAdjacentEnemyTerritories()) {
            territoryString += territory.toString() + ", ";
        }
        territoryString = territoryString.substring(0, territoryString.length() - 2);
        System.out.println("Choose a territory to attack: " + territoryString);
        Territory defendingTerritory = parser.getTerritory(attackingTerritory.getAdjacentEnemyTerritories());

        int attackingArmy = attackingTerritory.getArmies();
        int maxAttackDice = Math.min(attackingArmy - 1, MAX_ATTACK_DICE);
        String diceList = "";
        for (int i = 1; i <= maxAttackDice; i++) {
            diceList += i + " ";
        }
        System.out.println("Choose the number of dice to throw from: " + diceList);
        int attackDiceNum = parser.getInt(1, attackingArmy - 1);

        //defender will throw max dice possible (instead of letting them choose)
        int defendDiceNum = Math.min(defendingTerritory.getArmies(), MAX_DEFEND_DICE);
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

        String diceRolls = "";
        for (Integer i : attackDice) {
            diceRolls += i + ", ";
        }
        diceRolls = diceRolls.substring(0, diceRolls.length() - 2);
        System.out.println("Attacker rolls: " + diceRolls);

        diceRolls = "";
        for (Integer i : defendDice) {
            diceRolls += i + ", ";
        }
        diceRolls = diceRolls.substring(0, diceRolls.length() - 2);
        System.out.println("Defender rolls: " + diceRolls);

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

        attackingTerritory.subtractArmies(attackArmyLoss);
        System.out.println("Attackers (" + attackingTerritory.getName() + ") lost " + attackArmyLoss + " armies.");
        if (defendingTerritory.getArmies() > defendArmyLoss) {
            defendingTerritory.subtractArmies(defendArmyLoss);
            System.out.println("Defenders (" + defendingTerritory.getName() + ") lost " + defendArmyLoss + " armies.");
        }
        else {
            System.out.println(player.getName() + " took over " + defendingTerritory.getOwner().getName() + "'s " + defendingTerritory.getName());

            defendingTerritory.getOwner().removeTerritory(defendingTerritory);
            player.addTerritory(defendingTerritory);

            if (attackingTerritory.getArmies() < MIN_ARMY_TO_MOVE_FROM_ATTACK) {
                defendingTerritory.setArmies(attackingTerritory.getArmies() - 1);
                attackingTerritory.setArmies(1);
            }
            else {
                System.out.println("Choose the number of Army units to move to " + defendingTerritory.getName() + ": 3 to " + (attackingTerritory.getArmies() - 1));
                int armyToMove = parser.getInt(3, attackingTerritory.getArmies() - 1);
                defendingTerritory.setArmies(armyToMove);
                attackingTerritory.subtractArmies(armyToMove);
            }
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
        System.out.println("PLAYERS LEFT" + playersLeft);
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
        System.out.println(player.getName() + " was eliminated at " + (numPlayers - player.getGameStanding() + 1) + "th place.");
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    public void addRiskView (RiskView rview) {views.add(rview);}
    private void removeRiskView (RiskView rview){views.remove(rview);}

    public void play(){

        // variables used to detect stalemate
        boolean stalemateOccuredFlag = false;
        int playersWhoWereForcePassed = 0;

        //main game loop
        int currPlayerIndex = 0;
        while (this.gameInProgress) {

            //calculate amount of players left (used in stalemate check)
            int playersleft = 0;
            for (Player p : this.players){
                if (p.getGameStanding() == 0)
                    playersleft++;
            }

            System.out.println("");
            System.out.println("===========================================");

            Player currPlayer = this.players.get(currPlayerIndex);

            //only do player's turn if they are still alive
            if (currPlayer.getGameStanding() == 0) {
                System.out.println(currPlayer.getName() + "'s turn");
                currPlayer.setTurnPhase(TurnPhase.ATTACK);

                while(currPlayer.getTurnPhase() == TurnPhase.ATTACK && this.gameInProgress){

                    //check if the player is actually able to attack (since at this point there is no move or deploy phase)
                    if(currPlayer.canAttack()){
                        //CommandWord command = this.parser.getCommand(validCommands); //commented for now, uses parser
                        //this.processCommand(currPlayer, command);
                        battle(currPlayer);
                    }
                    //if the player couldn't attack, force pass their turn and increment playersWhoWereForcePassed (used to check stalemate)
                    else{
                        System.out.println("passing " + currPlayer.getName() + "'s turn since none of their territories have enough armies to attack");
                        currPlayer.setTurnPhase(TurnPhase.END);
                        playersWhoWereForcePassed ++;
                    }
                }
            }

            // todo, finish this after battle() is split into 2 methods one where it takes a territory to attack from another where it takes a territory to attack
            /*for (RiskView v: views){
                v.handleRiskUpdate(new RiskEvent(this,  ));
            }*/

            //if last while loop iteration before currPlayerIndex reset, check stalemate
            //(executes once per cycle after each player has taken their turn)
            if((currPlayerIndex + 1) % this.numPlayers == 0){
                if(playersleft == playersWhoWereForcePassed){
                    stalemateOccuredFlag = true;
                    break;
                }
                //also reset playersWhoWereForcePassed for next cycle
                playersWhoWereForcePassed = 0;
            }

            //increment currPlayerIndex to be index of Player who goes next
            currPlayerIndex = (currPlayerIndex + 1) % this.numPlayers;
        }

        //if game ended naturally without a stalemate print winner
        if(!stalemateOccuredFlag){
            this.printWinner();
        }
        //else print stalemate
        else{
            this.printStalemate();
        }
    }


//    public static void main(String[] args){
//        RiskGame game = new RiskGame();
//
//        //for now, there are no commands that can only happen at specific times, so all commands are always available
//        List<CommandWord> validCommands = new ArrayList<>();
//        validCommands.add(CommandWord.STATUS);
//        validCommands.add(CommandWord.ATTACK);
//        validCommands.add(CommandWord.PASS);
//
//        //various variables used to detect stalemate
//        boolean stalemateOccuredFlag = false;
//        int playersWhoWereForcePassed = 0;
//
//        //main game loop
//        int currPlayerIndex = 0;
//        while (game.gameInProgress) {
//
//            //calculate amount of players left (used in stalemate check)
//            int playersleft = 0;
//            for (Player p : game.players){
//                if (p.getGameStanding() == 0)
//                    playersleft++;
//            }
//
//            System.out.println("");
//            System.out.println("===========================================");
//
//            Player currPlayer = game.players.get(currPlayerIndex);
//
//            //only do player's turn if they are still alive
//            if (currPlayer.getGameStanding() == 0) {
//                System.out.println(currPlayer.getName() + "'s turn");
//                currPlayer.setTurnPhase(TurnPhase.ATTACK);
//
//                while(currPlayer.getTurnPhase() == TurnPhase.ATTACK && game.gameInProgress){
//
//                    //check if the player is actually able to attack (since at this point there is no move or deploy phase)
//                    if(currPlayer.canAttack()){
//                        CommandWord command = game.parser.getCommand(validCommands);
//                        game.processCommand(currPlayer, command);
//                    }
//                    //if the player couldn't attack, force pass their turn and increment playersWhoWereForcePassed (used to check stalemate)
//                    else{
//                        System.out.println("passing " + currPlayer.getName() + "'s turn since none of their territories have enough armies to attack");
//                        currPlayer.setTurnPhase(TurnPhase.END);
//                        playersWhoWereForcePassed ++;
//                    }
//                }
//            }
//
//            //if last while loop iteration before currPlayerIndex reset, check stalemate
//            //(executes once per cycle after each player has taken their turn)
//            if((currPlayerIndex + 1) % game.numPlayers == 0){
//                if(playersleft == playersWhoWereForcePassed){
//                    stalemateOccuredFlag = true;
//                    break;
//                }
//                //also reset playersWhoWereForcePassed for next cycle
//                playersWhoWereForcePassed = 0;
//            }
//
//            //increment currPlayerIndex to be index of Player who goes next
//            currPlayerIndex = (currPlayerIndex + 1) % game.numPlayers;
//        }
//
//        //if game ended naturally without a stalemate print winner
//        if(!stalemateOccuredFlag){
//            game.printWinner();
//        }
//        //else print stalemate
//        else{
//            game.printStalemate();
//        }
//    }

}
