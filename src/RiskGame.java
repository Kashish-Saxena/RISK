import java.util.*;

public class RiskGame implements Observer {

    private List<Player> players;
    private boolean gameInProgress;
    private InputParser parser;
    private int numPlayers;

    //Constructor
    public RiskGame(){
        //initialize field values
        players = new ArrayList<>();
        gameInProgress = true;
        parser = new InputParser();

        setupOptions();
        autoPlaceArmies();
    }

    //todo, add javadoc comments
    private void setupOptions(){
        System.out.println("===========================================");
        System.out.println("==           RISK MILESTONE 1            ==");
        System.out.println("==                  By                   ==");
        System.out.println("==         David Sciola 101082459        ==");
        System.out.println("==         Kevin Quach 101115704         ==");
        System.out.println("==         Kashish Saxena 101107204      ==");
        System.out.println("===========================================");

        //prompt user for the number of players playing
        System.out.print("How many players will be playing? (2-6) :");
        int numPlayers = parser.getInt(2, 6); //TODO: define constants for player bounds

        //instantiate Player objects
        for(int i = 1; i <= numPlayers; i++){
            System.out.print("Player " + i + " what is your name? :");
            String playerName = parser.getString();

            //create Player object and add it to players
            Player player = new Player(parser.getName());
            players.add(player);
        }

        //instantiate all Territory objects
        //northAmerica
        Territory alaska = new Territory("Alaska");
        Territory alberta = new Territory("Alberta");
        Territory centralAmerica = new Territory("Central America");
        Territory easternUnitedStates = new Territory("Alaska");
        Territory greenland = new Territory("Greenland");
        Territory northwestTerritory = new Territory("Northwest Territory");
        Territory ontario = new Territory("Ontario");
        Territory quebec = new Territory("Quebec");
        Territory westernUnitedStates = new Territory("Western United States");

        //southAmerica
        Territory argentina = new Territory("Argentina");
        Territory brazil = new Territory("Brazil");
        Territory peru = new Territory("Peru");
        Territory venezuela = new Territory("Venezuela");

        //europe
        Territory greatBritain = new Territory("Great Britain");
        Territory iceland = new Territory("Iceland");
        Territory northernEurope = new Territory("Northern Europe");
        Territory scandinavia = new Territory("Scandinavia");
        Territory southernEurope = new Territory("Southern Europe");
        Territory ukraine = new Territory("Ukraine");
        Territory westernEurope = new Territory("Western Europe");

        //africa
        Territory congo = new Territory("Congo");
        Territory eastAfrica = new Territory("East Africa");
        Territory egypt = new Territory("Egypt");
        Territory madagascar = new Territory("Madagascar");
        Territory northAfrica = new Territory("North Africa");
        Territory southAfrica = new Territory("South Africa");

        //asia
        Territory afghanistan = new Territory("Afghanistan");
        Territory china = new Territory("China");
        Territory india = new Territory("India");
        Territory irkutsk = new Territory("Irkutsk");
        Territory japan = new Territory("Japan");
        Territory kamchatka = new Territory("Kamchatka");
        Territory middleEast = new Territory("Middle East");
        Territory mongolia = new Territory("Mongolia");
        Territory siam = new Territory("Siam");
        Territory siberia = new Territory("Siberia");
        Territory ural = new Territory("Ural");
        Territory yakutsk = new Territory("Yakutsk");

        //australia
        Territory easternAustralia = new Territory("Eastern Australia");
        Territory indonesia = new Territory("Indonesia");
        Territory newGuinea = new Territory("New Guinea");
        Territory westernAustralia = new Territory("Western Australia");

        //instantiate continents
        Continent northAmerica = new Continent("North America", Arrays.asList(alaska,alberta,centralAmerica,easternUnitedStates,
                greenland,northwestTerritory,ontario,quebec,westernUnitedStates));
        Continent southAmerica = new Continent("South America", Arrays.asList(argentina,brazil,peru,venezuela));
        Continent europe = new Continent("Europe", Arrays.asList(greatBritain,iceland,northernEurope,scandinavia,
                southernEurope,ukraine,westernEurope));
        Continent africa = new Continent("Africa", Arrays.asList(congo,eastAfrica,egypt,madagascar,northAfrica,southAfrica));
        Continent asia = new Continent("Asia", Arrays.asList(afghanistan,china,india,irkutsk,japan,kamchatka,middleEast,
                mongolia,siam,siberia,ural,yakutsk));
        Continent australia = new Continent("Australia", Arrays.asList(easternAustralia,indonesia,newGuinea,westernAustralia));

        //set adjacent territories "connections"
        alaska.setAdjacentTerritories(Arrays.asList(alberta,northwestTerritory,kamchatka));
        alberta.setAdjacentTerritories(Arrays.asList(alaska,northwestTerritory,ontario,westernUnitedStates));
        centralAmerica.setAdjacentTerritories(Arrays.asList(easternUnitedStates,westernUnitedStates,venezuela));
        easternUnitedStates.setAdjacentTerritories(Arrays.asList(centralAmerica,westernUnitedStates,ontario,quebec));
        greenland.setAdjacentTerritories(Arrays.asList(northwestTerritory,ontario,quebec,iceland));
        northwestTerritory.setAdjacentTerritories(Arrays.asList(alaska,alberta,ontario,greenland));
        ontario.setAdjacentTerritories(Arrays.asList(alberta,northwestTerritory,alberta,westernUnitedStates,easternUnitedStates,quebec,greenland));
        quebec.setAdjacentTerritories(Arrays.asList(greenland,ontario,easternUnitedStates));
        westernUnitedStates.setAdjacentTerritories(Arrays.asList(alberta,ontario,easternUnitedStates,centralAmerica));
        argentina.setAdjacentTerritories(Arrays.asList(brazil,peru));
        brazil.setAdjacentTerritories(Arrays.asList(venezuela,peru,argentina,northAfrica));
        peru.setAdjacentTerritories(Arrays.asList(venezuela,brazil,argentina));
        venezuela.setAdjacentTerritories(Arrays.asList(brazil,peru,centralAmerica));
        greatBritain.setAdjacentTerritories(Arrays.asList(iceland,scandinavia,northernEurope,westernEurope));
        iceland.setAdjacentTerritories(Arrays.asList(greenland,scandinavia,greatBritain));
        northernEurope.setAdjacentTerritories(Arrays.asList(scandinavia,greatBritain,westernEurope,southernEurope,ukraine));
        scandinavia.setAdjacentTerritories(Arrays.asList(iceland,greatBritain,northernEurope,ukraine));
        southernEurope.setAdjacentTerritories(Arrays.asList(northernEurope,westernEurope,ukraine,northAfrica,egypt,middleEast));
        ukraine.setAdjacentTerritories(Arrays.asList(scandinavia,northernEurope,southernEurope,middleEast,afghanistan,ural));
        westernEurope.setAdjacentTerritories(Arrays.asList(greatBritain,northernEurope,southernEurope,northAfrica));
        congo.setAdjacentTerritories(Arrays.asList(southAfrica,madagascar,eastAfrica,northAfrica));
        eastAfrica.setAdjacentTerritories(Arrays.asList(congo,southAfrica,madagascar,northAfrica,egypt,middleEast));
        egypt.setAdjacentTerritories(Arrays.asList(northAfrica,eastAfrica,middleEast,southernEurope));
        madagascar.setAdjacentTerritories(Arrays.asList(eastAfrica,southAfrica));
        northAfrica.setAdjacentTerritories(Arrays.asList(brazil,westernEurope,southernEurope,egypt,eastAfrica,congo));
        southAfrica.setAdjacentTerritories(Arrays.asList(congo,eastAfrica,madagascar));
        afghanistan.setAdjacentTerritories(Arrays.asList(ukraine,ural,china,india,middleEast));
        china.setAdjacentTerritories(Arrays.asList(ural,afghanistan,india,siam,mongolia,siberia));
        india.setAdjacentTerritories(Arrays.asList(siam,china,afghanistan,middleEast));
        irkutsk.setAdjacentTerritories(Arrays.asList(kamchatka,yakutsk,siberia,mongolia));
        japan.setAdjacentTerritories(Arrays.asList(kamchatka,mongolia));
        kamchatka.setAdjacentTerritories(Arrays.asList(alaska,yakutsk,irkutsk,mongolia,japan));
        middleEast.setAdjacentTerritories(Arrays.asList(india,afghanistan,ukraine,southernEurope,egypt,eastAfrica));
        mongolia.setAdjacentTerritories(Arrays.asList(japan,irkutsk,kamchatka,siberia,china));
        siam.setAdjacentTerritories(Arrays.asList(china,india,indonesia));
        siberia.setAdjacentTerritories(Arrays.asList(yakutsk,irkutsk,mongolia,china,ural));
        ural.setAdjacentTerritories(Arrays.asList(siberia,china,afghanistan,ukraine));
        yakutsk.setAdjacentTerritories(Arrays.asList(kamchatka,irkutsk,siberia));
        easternAustralia.setAdjacentTerritories(Arrays.asList(newGuinea,westernAustralia));
        indonesia.setAdjacentTerritories(Arrays.asList(siam,newGuinea,westernAustralia));
        newGuinea.setAdjacentTerritories(Arrays.asList(easternAustralia,indonesia));
        westernAustralia.setAdjacentTerritories(Arrays.asList(indonesia,easternAustralia));
    }

    //todo, descriptive javadoc
    //auto unit placement algorithm
    //the following algorithm will dynamically set Territory ownership and army numbers for all players
    //note that instead of placing them randomly, this algorithm places Player armies in a few grouped clusters
    private void autoPlaceArmies() {

        //first calculate the number of armies they have to place
        int numArmiesToPlace;
        if(numPlayers == 2){ numArmiesToPlace = 50; }
        else{numArmiesToPlace = 50 - (5 * numPlayers); }

        //set armiesToPlace for each Player
        for(Player p: players){ p.setArmiesToPlace(numArmiesToPlace); }

        //first fill territories with 1 army until every territory has 1 army on it
        Random ran = new Random();
        int initialNumArmiesToBePlaced = Territory.numTerritories();
        int playerIndex = 0;
        int randomTerritoryIndex;
        Player tempPlay = players.get(0);
        Territory tempTerr;

        while(initialNumArmiesToBePlaced > 0){
            //point tempTerr towards a territory to add army
            //(20% to be a random un-owned territory, 80% to be an un-owned neighbor territory)

            //default 20% random territory option
            do {
                randomTerritoryIndex = ran.nextInt(Territory.numTerritories());
                tempTerr = Territory.getTerritoryFromIndex(randomTerritoryIndex);
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
            tempPlay.addTerritory(tempTerr, Continent.getContinentFromTerritory(tempTerr));
            tempPlay.setArmiesToPlace(tempPlay.getArmiesToPlace() - 1);
            tempTerr.addArmies(1);

            //increment playerIndex and update tempPlay
            if(playerIndex == players.size()-1){ playerIndex = 0; }
            else{playerIndex += 1; }
            tempPlay = players.get(playerIndex);

            initialNumArmiesToBePlaced -= 1;
        }

        //now that every territory has at least 1 army on it, add the remaining
        //player armies randomly across the territories which they own
        int randTerrIndex;
        Territory randTerr;
        for(Player p: players){
            for(int i = p.getArmiesToPlace(); i > 0; i--){
                randTerrIndex = ran.nextInt(p.getTerritories().size()-1);
                randTerr = p.getTerritories().get(randTerrIndex);
                randTerr.addArmies(1);
            }
            p.setArmiesToPlace(0);
        }

        //todo, remove this later on (im just using for debugging)
        printMapState();
    }

    //todo, make prettier
    private void printMapState(){
        for(Player p: players){
            System.out.println("======= "+ p.getName() +" =======");

            if(p.getGameStanding() == 0) {//if they aren't dead

                //print owned continents
                System.out.println("owned continents: ");
                if (p.getContinents().size() == 0) {
                    System.out.print("none");
                } else {
                    for (Continent c : p.getContinents()) {

                        System.out.println(c.getName());
                    }
                }
                System.out.println("");

                //print owned territories
                System.out.println("owned territories: ");
                for (Territory t : p.getTerritories()) {
                    System.out.println(t.getName() + ":" + t.getArmies());
                }
                System.out.println("");
            }
            else{
                System.out.println("dead");
                System.out.println("");
            }
        }
    }

    private void processCommand(Player player, CommandWord command){

    }
  
    private void battle(Player player){
        //TODO: condense below 2 sections into a method accepting a List<Territory> param
        String territoryString = "";
        for (Territory territory : player.getAttackableTerritories()) {
            territoryString += territory.toString() + ", ";
        }
        territoryString.substring(0, territoryString.length() - 2);
        System.out.println("Territories to attack from: " + territoryString + "\n" +
                "Enter the name of the territory you wish to attack from.");
        Territory attackingTerritory = parser.getTerritory(player.getAttackableTerritories());

        territoryString = "";
        for (Territory territory : attackingTerritory.getAdjacentEnemyTerritories()) {
            territoryString += territory.toString() + ", ";
        }
        territoryString.substring(0, territoryString.length() - 2);
        System.out.println("Choose a territory to attack: " + territoryString);
        Territory defendingTerritory = parser.getTerritory(attackingTerritory.getAdjacentEnemyTerritories());

        int attackingArmy = attackingTerritory.getArmies();
        int maxAttackDice = Math.min(attackingArmy - 1, 3); //TODO: define constants instead of using 3
        String diceList = "";
        for (int i = 1; i <= maxAttackDice; i++) {
            diceList += i + " ";
        }
        System.out.println("Choose the number of dice to throw from: " + diceList);
        int attackDiceNum = parser.getInt(1, attackingArmy - 1);

        //defender will throw max dice possible (instead of letting them choose)
        int defendDiceNum = Math.min(defendingTerritory.getArmies(), 2); //TODO: define constants instead of using 2
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
            System.out.println("Attackers roll: " + attackDice.get(i));
            System.out.println("Defenders roll: " + defendDice.get(i));
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
            Continent continent = Continent.getContinentFromTerritory(defendingTerritory);

            for (Player p : players) {
                p.handleBattle(new BattleEvent(this, player, defendingTerritory.getOwner(), defendingTerritory, continent));
            }

            if (attackingTerritory.getArmies() <= 4) { //TODO: define constants (need 5 or more army to choose a number to move)
                defendingTerritory.setArmies(attackingTerritory.getArmies() - 1);
                attackingTerritory.setArmies(1);
            }
            else {
                System.out.println("Choose the number of Army units to move to " + defendingTerritory.getName() + ": 3 to " + (attackingTerritory.getArmies() - 1));
                int armyToMove = parser.getInt(attackingTerritory.getArmies() - 1, 3); //TODO: ensure this is within bounds
                defendingTerritory.setArmies(armyToMove);
                attackingTerritory.subtractArmies(armyToMove);
            }
        }

    }

    public void update(Observable o, Object arg) {
        updatePlayerGameStanding((Player) o);
        updateGameInProgress();
    }

    private void updateGameInProgress(){
        int playersleft = 0;
        for (Player p : players){
            if (p.getGameStanding() == 0)
                playersleft++;
        }
        gameInProgress = (playersleft >= 2);
    }

    private void updatePlayerGameStanding(Player player){
        int maxstanding = players.get(0).getGameStanding();
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getGameStanding() > maxstanding)
                maxstanding = players.get(i).getGameStanding();
        }
        player.setGameStanding(maxstanding + 1);
    }

    //main
    public static void main(String[] args){
        RiskGame game = new RiskGame();

        while (game.gameInProgress) {
            for (int i = 0; i < game.players.size(); i++)
            {
                Player p = game.players.get(i);
                if (p.getGameStanding() == 0) {
                    System.out.println(p.getName() + " starts their attack!");
                    game.battle(p);
                }
            }
        }

        /*Player p1 = new Player("Bob");
        Player p2 = new Player("nob");

        Territory t1 = new Territory("t1");
        Territory t2 = new Territory("t2");
        ArrayList<Territory> adj = new ArrayList<Territory>();
        adj.add(t2);
        t1.setAdjacentTerritories(adj);
        adj.remove(t2);
        adj.add(t1);
        t2.setAdjacentTerritories(adj);

        t1.setOwner(p1);
        t1.setArmies(4);
        t2.setOwner(p2);
        t2.setArmies(2);

        game.battle(p1);*/
    }

}
