import java.util.*;


//Map class does 2 things
//1. it deals with the initial Territory/Continent instanitation with the createMap() and autoPlaceArmies() methods
//2. it holds the state of all the Territories and continents, more specifically it holds them in 2 hash maps:
// territoryMap (String to Territory) and territoryContinentMap (Territory to Continent)
public class RiskMap {

    private static java.util.Map<String, Territory> territoryMap;
    private static java.util.Map<Territory, Continent> territoryContinentMap;

    public RiskMap() {
        territoryMap = new HashMap<>();
        territoryContinentMap = new HashMap<>();

        createMap();
    }

    /**
     * Instantiates territories, continents and set adjacent territories connections.
     */
    public void createMap(){

        //instantiate all Territory objects
        //northAmerica
        Territory alaska = new Territory("Alaska",80,100);
        Territory alberta = new Territory("Alberta", 175, 150);
        Territory centralAmerica = new Territory("Cnt America",170,225);
        Territory easternUnitedStates = new Territory("Eastern US", 280, 265);
        Territory greenland = new Territory("Greenland", 400, 40);
        Territory northwestTerritory = new Territory("NW Territory", 200, 100);
        Territory ontario = new Territory("Ontario",270,190);
        Territory quebec = new Territory("Quebec", 350, 140);
        Territory westernUnitedStates = new Territory("Western US", 190, 310);

        //southAmerica
        Territory argentina = new Territory("Argentina", 300, 550);
        Territory brazil = new Territory("Brazil", 400, 460);
        Territory peru = new Territory("Peru", 270, 460);
        Territory venezuela = new Territory("Venezuela",280, 370);

        //europe
        Territory greatBritain = new Territory("Great Britain", 450, 190);
        Territory iceland = new Territory("Iceland", 480, 130);
        Territory northernEurope = new Territory("Northern EU", 580, 200);
        Territory scandinavia = new Territory("Scandinavia", 600, 110);
        Territory southernEurope = new Territory("Southern EU", 600, 255);
        Territory ukraine = new Territory("Ukraine" ,700, 170);
        Territory westernEurope = new Territory("Western EU", 510, 300);

        //africa
        Territory congo = new Territory("Congo", 635, 505);
        Territory eastAfrica = new Territory("East Africa", 700, 460);
        Territory egypt = new Territory("Egypt", 635, 370);
        Territory madagascar = new Territory("Madagascar", 755, 585);
        Territory northAfrica = new Territory("North Africa", 510, 390);
        Territory southAfrica = new Territory("South Africa", 635, 570);

        //asia
        Territory afghanistan = new Territory("Afghanistan", 750, 225);
        Territory china = new Territory("China", 915, 300);
        Territory india = new Territory("India", 850, 370);
        Territory irkutsk = new Territory("Irkutsk", 950 ,170);
        Territory japan = new Territory("Japan", 1100, 225);
        Territory kamchatka = new Territory("Kamchatka", 1050, 90);
        Territory middleEast = new Territory("Middle East", 700, 310);
        Territory mongolia = new Territory("Mongolia", 925, 225);
        Territory siam = new Territory("Siam", 980, 385);
        Territory siberia = new Territory("Siberia", 870, 90);
        Territory ural = new Territory("Ural", 825, 170);
        Territory yakutsk = new Territory("Yakutsk", 950, 50);

        //australia
        Territory easternAustralia = new Territory("Eastern AUS", 1125, 570);
        Territory indonesia = new Territory("Indonesia", 945, 485);
        Territory newGuinea = new Territory("New Guinea", 1100, 460);
        Territory westernAustralia = new Territory("Western AUS", 1000, 570);

        //add all Territories to territoryMap hash map
        territoryMap.put(alaska.getName().toLowerCase(), alaska);
        territoryMap.put(alberta.getName().toLowerCase(), alberta);
        territoryMap.put(centralAmerica.getName().toLowerCase(), centralAmerica);
        territoryMap.put(easternUnitedStates.getName().toLowerCase(), easternUnitedStates);
        territoryMap.put(greenland.getName().toLowerCase(), greenland);
        territoryMap.put(northwestTerritory.getName().toLowerCase(), northwestTerritory);
        territoryMap.put(ontario.getName().toLowerCase(), ontario);
        territoryMap.put(quebec.getName().toLowerCase(), quebec);
        territoryMap.put(westernUnitedStates.getName().toLowerCase(), westernUnitedStates);

        territoryMap.put(argentina.getName().toLowerCase(), argentina);
        territoryMap.put(brazil.getName().toLowerCase(), brazil);
        territoryMap.put(peru.getName().toLowerCase(), peru);
        territoryMap.put(venezuela.getName().toLowerCase(), venezuela);

        territoryMap.put(greatBritain.getName().toLowerCase(), greatBritain);
        territoryMap.put(iceland.getName().toLowerCase(), iceland);
        territoryMap.put(northernEurope.getName().toLowerCase(), northernEurope);
        territoryMap.put(scandinavia.getName().toLowerCase(), scandinavia);
        territoryMap.put(southernEurope.getName().toLowerCase(), southernEurope);
        territoryMap.put(ukraine.getName().toLowerCase(), ukraine);
        territoryMap.put(westernEurope.getName().toLowerCase(), westernEurope);

        territoryMap.put(congo.getName().toLowerCase(), congo);
        territoryMap.put(eastAfrica.getName().toLowerCase(), eastAfrica);
        territoryMap.put(egypt.getName().toLowerCase(), egypt);
        territoryMap.put(madagascar.getName().toLowerCase(), madagascar);
        territoryMap.put(northAfrica.getName().toLowerCase(), northAfrica);
        territoryMap.put(southAfrica.getName().toLowerCase(), southAfrica);

        territoryMap.put(afghanistan.getName().toLowerCase(), afghanistan);
        territoryMap.put(china.getName().toLowerCase(), china);
        territoryMap.put(india.getName().toLowerCase(), india);
        territoryMap.put(irkutsk.getName().toLowerCase(), irkutsk);
        territoryMap.put(japan.getName().toLowerCase(), japan);
        territoryMap.put(kamchatka.getName().toLowerCase(), kamchatka);
        territoryMap.put(middleEast.getName().toLowerCase(), middleEast);
        territoryMap.put(mongolia.getName().toLowerCase(), mongolia);
        territoryMap.put(siam.getName().toLowerCase(), siam);
        territoryMap.put(siberia.getName().toLowerCase(), siberia);
        territoryMap.put(ural.getName().toLowerCase(), ural);
        territoryMap.put(yakutsk.getName().toLowerCase(), yakutsk);

        territoryMap.put(easternAustralia.getName().toLowerCase(), easternAustralia);
        territoryMap.put(indonesia.getName().toLowerCase(), indonesia);
        territoryMap.put(newGuinea.getName().toLowerCase(), newGuinea);
        territoryMap.put(westernAustralia.getName().toLowerCase(), westernAustralia);

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

        //add all continents to territoryContinentMap hash map
        for(Territory t: northAmerica.getTerritories()){ territoryContinentMap.put(t, northAmerica); }
        for(Territory t: southAmerica.getTerritories()){ territoryContinentMap.put(t, southAmerica); }
        for(Territory t: europe.getTerritories()){ territoryContinentMap.put(t, europe); }
        for(Territory t: africa.getTerritories()){ territoryContinentMap.put(t, africa); }
        for(Territory t: asia.getTerritories()){ territoryContinentMap.put(t, asia); }
        for(Territory t: australia.getTerritories()){ territoryContinentMap.put(t, australia); }

        //set adjacent territories "connections"
        alaska.setAdjacentTerritories(Arrays.asList(alberta,northwestTerritory,kamchatka));
        alberta.setAdjacentTerritories(Arrays.asList(alaska,northwestTerritory,ontario,centralAmerica));
        centralAmerica.setAdjacentTerritories(Arrays.asList(alberta,easternUnitedStates,westernUnitedStates));
        easternUnitedStates.setAdjacentTerritories(Arrays.asList(centralAmerica,westernUnitedStates,ontario,quebec));
        greenland.setAdjacentTerritories(Arrays.asList(northwestTerritory,ontario,quebec,iceland));
        northwestTerritory.setAdjacentTerritories(Arrays.asList(alaska,alberta,ontario,greenland));
        ontario.setAdjacentTerritories(Arrays.asList(alberta,northwestTerritory,alberta,easternUnitedStates,quebec,greenland));
        quebec.setAdjacentTerritories(Arrays.asList(greenland,ontario,easternUnitedStates));
        westernUnitedStates.setAdjacentTerritories(Arrays.asList(easternUnitedStates,centralAmerica,venezuela));
        argentina.setAdjacentTerritories(Arrays.asList(brazil,peru));
        brazil.setAdjacentTerritories(Arrays.asList(venezuela,peru,argentina,northAfrica));
        peru.setAdjacentTerritories(Arrays.asList(venezuela,brazil,argentina));
        venezuela.setAdjacentTerritories(Arrays.asList(brazil,peru,westernUnitedStates));
        greatBritain.setAdjacentTerritories(Arrays.asList(iceland,scandinavia,northernEurope,westernEurope));
        iceland.setAdjacentTerritories(Arrays.asList(greenland,scandinavia,greatBritain));
        northernEurope.setAdjacentTerritories(Arrays.asList(scandinavia,greatBritain,westernEurope,southernEurope,ukraine));
        scandinavia.setAdjacentTerritories(Arrays.asList(iceland,greatBritain,northernEurope,ukraine));
        southernEurope.setAdjacentTerritories(Arrays.asList(northernEurope,westernEurope,ukraine,northAfrica,egypt,middleEast));
        ukraine.setAdjacentTerritories(Arrays.asList(scandinavia,northernEurope,southernEurope,middleEast,afghanistan,ural));
        westernEurope.setAdjacentTerritories(Arrays.asList(greatBritain,northernEurope,southernEurope,northAfrica));
        congo.setAdjacentTerritories(Arrays.asList(southAfrica,eastAfrica,northAfrica));
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
        newGuinea.setAdjacentTerritories(Arrays.asList(easternAustralia,indonesia,westernAustralia));
        westernAustralia.setAdjacentTerritories(Arrays.asList(indonesia,easternAustralia,newGuinea));
    }

    /**
     * Returns a territory from an input string representation of a territory.
     * @param input A string representation of a territory.
     * @return A territory associated with the input territory.
     */
    public static Territory getTerritoryFromString(String input) {
        return territoryMap.get(input);
    }

    /**
     *  Returns a territory from the territory map given an index.
     * @param index The input index required to get a territory from the territory map.
     * @return A territory from the territory map.
     */
    public static Territory getTerritoryFromIndex(int index) {
        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(territoryMap.keySet());
        return territoryMap.get(keys.get(index));
    }

    /**
     * Returns a continent from the territory-continent map given an input territory.
     * @param childTerritory The input territory to get the associated continent.
     * @return The continent associated with the input territory.
     */
    public static Continent getContinentFromTerritory(Territory childTerritory) {
        return territoryContinentMap.get(childTerritory);
    }

    /**
     * Returns the number of territories in the territory map.
     * @return The size of the territory map.
     */
    public static int numTerritories() {
        return territoryMap.size();
    }

    public static java.util.Map<String, Territory> getTerritoryMap(){
        return territoryMap;
    }
}

