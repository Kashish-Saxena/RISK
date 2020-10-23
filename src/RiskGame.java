import java.util.*;

public class RiskGame implements Observer {

    private List<Player> players;
    private boolean gameInProgress;
    private Map<Territory, Continent> territoryContinentMap;
    private InputParser parser;

    //Constructor
    public RiskGame(){

    }

    public void update(Observable o, Object arg) {

    }

    private void setupOptions(){

    }

    private void printMapState(){

    }

    private void processCommand(Player player, CommandWord command){

    }

    private void battle(Player player){
        String territoryList = "";
        for (Territory territory : player.getAttackableTerritories()) {
            territoryList += territory.toString() + " ";
        }
        System.out.println("Territories to attack from: " + territoryList + "\n" +
                "Enter the name of the territory you wish to attack from.");
        Territory attackingTerritory = parser.getTerritory(); //TODO: ensure input is valid choice

        territoryList = "";
        for (Territory territory : attackingTerritory.getAdjacentEnemyTerritories()) {
            territoryList += territory.toString() + " ";
        }
        System.out.println("Choose a territory to attack: " + territoryList);
        Territory defendingTerritory = parser.getTerritory(); //TODO: ensure input is valid choice

        int attackingArmy = attackingTerritory.getArmies();
        int maxAttackDice = attackingArmy - 1 < 3? attackingArmy - 1 : 3; //TODO: define constants instead of using 3
        String diceList = "";
        for (int i = 1; i <= maxAttackDice; i++) {
            diceList += i + " ";
        }
        System.out.println("Choose the number of dice to throw from: " + diceList);
        int attackDiceNum = parser.getInt();  //TODO: ensure input is valid choice

        //defender will throw max dice possible (instead of letting them choose)
        int defendDiceNum = defendingTerritory.getArmies() >= 2? 2 : defendingTerritory.getArmies(); //TODO: define constants instead of using 2
        Random rand = new Random();

        ArrayList<Integer> attackDice = new ArrayList<>();
        for (int i = 0; i < attackDiceNum; i++) {
            attackDice.add(rand.nextInt(6) + 1); //TODO: define constants instead of using 1 and 6
        }
        Collections.sort(attackDice, Collections.reverseOrder());

        ArrayList<Integer> defendDice = new ArrayList<>();
        for (int i = 0; i < defendDiceNum; i++) {
            defendDice.add(rand.nextInt(6) + 1); //TODO: define constants instead of using 1 and 6
        }
        Collections.sort(defendDice, Collections.reverseOrder());

        int attackArmyLoss = 0;
        int defendArmyLoss = 0;

        for (int i = 0; i < defendDiceNum; i++) {
            if (attackDice.get(i) < defendDice.get(i)) {
                attackArmyLoss++;
            }
            else {
                defendArmyLoss++;
            }
        }

        attackingTerritory.subtractArmies(attackArmyLoss);
        if (defendingTerritory.getArmies() > defendArmyLoss) {
            defendingTerritory.subtractArmies(defendArmyLoss);
        }
        else {
            defendingTerritory.setOwner(player);
            Continent continent = territoryContinentMap.get(defendingTerritory);

            for (Player p : players) {
                p.handleBattle(new BattleEvent(this, player, defendingTerritory.getOwner(), defendingTerritory, continent));
            }

            if (attackingTerritory.getArmies() <= 4) { //TODO: define constants (need 5 or more army to choose a number to move)
                defendingTerritory.setArmies(attackingTerritory.getArmies() - 1);
                attackingTerritory.setArmies(1);
            }
            else {
                System.out.println("Choose the number of Army units to move to " + defendingTerritory.getName() + ": 3 to " + (attackingTerritory.getArmies() - 1));
                int armyToMove = parser.getInt(); //TODO: ensure this is within bounds
                defendingTerritory.setArmies(armyToMove);
                attackingTerritory.subtractArmies(armyToMove);
            }
        }

    }

    private void updateGameInProgress(){

    }

    private void updatePlayerGameStanding(Player player){

    }

    //main
    public static void main(String[] args){
        System.out.println("hello world");
    }



}
