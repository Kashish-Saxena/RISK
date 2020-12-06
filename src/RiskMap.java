
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


/**
 *  The RiskMap class does the initial instantiation of territories and continents and
 *  holds the state of all the Territories and continents in two hash maps:
 *  territoryMap (String to Territory) and territoryContinentMap (Territory to Continent). An instance of this
 *  class is created in the first line of the main.
 *
 *  * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 *  * @version November 23, 2020
 *
 */

public class RiskMap {

    private static java.util.Map<String, Territory> territoryMap;
    private static java.util.Map<Territory, Continent> territoryContinentMap;
    private static ArrayList<Territory> territories;
    private static ArrayList<Continent> continents;

    /**
     * Constructor of the RiskMap class. It initializes all the field values and invokes the createMap() method that initializes
     * the Risk Map.
     * @param testing Boolean that restricts initialization of the normal Risk map when testing, allows otherwise.
     */
    public RiskMap(boolean testing) {
        territoryMap = new HashMap<>();
        territoryContinentMap = new HashMap<>();
        continents = new ArrayList<Continent>();
        territories = new ArrayList<Territory>();
        if (!testing) {
            try{
                createMap();
            }
            catch(Exception e){
                //need to put createMap() in a try catch block since it can throw exceptions
                System.out.println("Exception occurred while creating map");
                e.printStackTrace();
            }
        }
    }

    /**
     * Instantiates territories, continents and set adjacent territories connections.
     */
    public void createMap() throws IOException, ParseException {
//        ArrayList<Territory> tempTerritories = new ArrayList<Territory>();
//        ArrayList<Continent> tempContinents = new ArrayList<Continent>();


        //note that JSON parsing is done using an external java library
        //the java library was included within the "json external library" folder
        //jar file was downloaded from https://mvnrepository.com/artifact/org.json/json

        //custom maps are also located within the "maps" folder

        //parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader("maps/test_json"));

        //typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        Iterator<Map.Entry> itr1;

        //==========================================
        //   STEP 1, create territory objects
        //==========================================

        //fetch territories from json file
        JSONArray territoriesArray = (JSONArray) jo.get("territories");

        //traverse territoriesArray to create all territory objects
        Iterator territoryItr = territoriesArray.iterator();

        while (territoryItr.hasNext()) {
            String tempName = "";
            int tempXPos = 0;
            int tempYPos = 0;

            itr1 = ((Map) territoryItr.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                //System.out.println(pair.getKey() + " : " + pair.getValue());

                if (pair.getKey().equals("name")) {
                    tempName = (String) pair.getValue();
                } else if (pair.getKey().equals("xPos")) {
                    tempXPos = (int)(long) pair.getValue();
                } else if (pair.getKey().equals("YPos")) {
                    tempYPos = (int)(long) pair.getValue();
                }
            }

            Territory tempTer = new Territory(tempName,tempXPos,tempYPos);
            territories.add(tempTer);

        }

        //add all Territories to territoryMap hash map
        for(Territory t: territories){
            territoryMap.put(t.getName().toLowerCase(), t);
        }

        //==========================================
        //   STEP 2, create continent objects
        //==========================================

        //fetch territories from json file
        JSONArray continentArray = (JSONArray) jo.get("continents");

        //traverse territoriesArray to create all territory objects
        Iterator continentItr = continentArray.iterator();

        while (continentItr.hasNext()) {
            String tempName = "";
            int tempXPos = 0;
            int tempYPos = 0;
            int tempRed = 0;
            int tempGreen = 0;
            int tempBlue = 0;
            int tempVal = 0;

            itr1 = ((Map) continentItr.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());

                if(pair.getKey().equals("name")){
                    tempName = (String)pair.getValue();
                }
                else if(pair.getKey().equals("xPos")){
                    tempXPos = (int)(long) pair.getValue();
                }
                else if(pair.getKey().equals("YPos")){
                    tempYPos = (int)(long) pair.getValue();
                }
                else if(pair.getKey().equals("red")){
                    tempRed = (int)(long) pair.getValue();
                }
                else if(pair.getKey().equals("green")){
                    tempGreen = (int)(long) pair.getValue();
                }
                else if(pair.getKey().equals("blue")){
                    tempBlue = (int)(long) pair.getValue();
                }
                else if(pair.getKey().equals("value")){
                    tempVal = (int)(long) pair.getValue();
                }
            }

            Continent tempContinent = new Continent(tempName, tempXPos, tempYPos, new Color(tempRed,tempGreen,tempBlue), tempVal);
            continents.add(tempContinent);
        }


        //==========================================
        // STEP 3, link continents to territories
        //==========================================

        //add all continents to territoryContinentMap hash map
        // for(Territory t: northAmerica.getTerritories()){ territoryContinentMap.put(t, northAmerica); }

        //==========================================
        //   STEP 4, link adjacent territories
        //==========================================






//        //instantiate all Territory objects
//        //northAmerica
//        Territory alaska = new Territory("Alaska",80,100);
//        Territory alberta = new Territory("Alberta", 175, 150);
//        Territory centralAmerica = new Territory("Cnt America",170,225);
//        Territory easternUnitedStates = new Territory("Eastern US", 280, 265);
//        Territory greenland = new Territory("Greenland", 400, 40);
//        Territory northwestTerritory = new Territory("NW Territory", 200, 100);
//        Territory ontario = new Territory("Ontario",270,190);
//        Territory quebec = new Territory("Quebec", 350, 140);
//        Territory westernUnitedStates = new Territory("Western US", 190, 310);
//
//        //southAmerica
//        Territory argentina = new Territory("Argentina", 300, 550);
//        Territory brazil = new Territory("Brazil", 400, 460);
//        Territory peru = new Territory("Peru", 270, 460);
//        Territory venezuela = new Territory("Venezuela",280, 370);
//
//        //europe
//        Territory greatBritain = new Territory("Great Britain", 450, 190);
//        Territory iceland = new Territory("Iceland", 480, 130);
//        Territory northernEurope = new Territory("Northern EU", 580, 200);
//        Territory scandinavia = new Territory("Scandinavia", 600, 110);
//        Territory southernEurope = new Territory("Southern EU", 600, 255);
//        Territory ukraine = new Territory("Ukraine" ,700, 170);
//        Territory westernEurope = new Territory("Western EU", 510, 300);
//
//        //africa
//        Territory congo = new Territory("Congo", 635, 505);
//        Territory eastAfrica = new Territory("East Africa", 700, 460);
//        Territory egypt = new Territory("Egypt", 635, 370);
//        Territory madagascar = new Territory("Madagascar", 755, 585);
//        Territory northAfrica = new Territory("North Africa", 510, 390);
//        Territory southAfrica = new Territory("South Africa", 635, 570);
//
//        //asia
//        Territory afghanistan = new Territory("Afghanistan", 750, 225);
//        Territory china = new Territory("China", 915, 300);
//        Territory india = new Territory("India", 850, 370);
//        Territory irkutsk = new Territory("Irkutsk", 950 ,170);
//        Territory japan = new Territory("Japan", 1100, 225);
//        Territory kamchatka = new Territory("Kamchatka", 1050, 90);
//        Territory middleEast = new Territory("Middle East", 700, 310);
//        Territory mongolia = new Territory("Mongolia", 925, 225);
//        Territory siam = new Territory("Siam", 980, 385);
//        Territory siberia = new Territory("Siberia", 870, 90);
//        Territory ural = new Territory("Ural", 825, 170);
//        Territory yakutsk = new Territory("Yakutsk", 950, 50);
//
//        //australia
//        Territory easternAustralia = new Territory("Eastern AUS", 1125, 570);
//        Territory indonesia = new Territory("Indonesia", 945, 485);
//        Territory newGuinea = new Territory("New Guinea", 1100, 460);
//        Territory westernAustralia = new Territory("Western AUS", 1000, 570);
//
//        //add all Territories to territoryMap hash map
//        territoryMap.put(alaska.getName().toLowerCase(), alaska);
//        territoryMap.put(alberta.getName().toLowerCase(), alberta);
//        territoryMap.put(centralAmerica.getName().toLowerCase(), centralAmerica);
//        territoryMap.put(easternUnitedStates.getName().toLowerCase(), easternUnitedStates);
//        territoryMap.put(greenland.getName().toLowerCase(), greenland);
//        territoryMap.put(northwestTerritory.getName().toLowerCase(), northwestTerritory);
//        territoryMap.put(ontario.getName().toLowerCase(), ontario);
//        territoryMap.put(quebec.getName().toLowerCase(), quebec);
//        territoryMap.put(westernUnitedStates.getName().toLowerCase(), westernUnitedStates);
//
//        territoryMap.put(argentina.getName().toLowerCase(), argentina);
//        territoryMap.put(brazil.getName().toLowerCase(), brazil);
//        territoryMap.put(peru.getName().toLowerCase(), peru);
//        territoryMap.put(venezuela.getName().toLowerCase(), venezuela);
//
//        territoryMap.put(greatBritain.getName().toLowerCase(), greatBritain);
//        territoryMap.put(iceland.getName().toLowerCase(), iceland);
//        territoryMap.put(northernEurope.getName().toLowerCase(), northernEurope);
//        territoryMap.put(scandinavia.getName().toLowerCase(), scandinavia);
//        territoryMap.put(southernEurope.getName().toLowerCase(), southernEurope);
//        territoryMap.put(ukraine.getName().toLowerCase(), ukraine);
//        territoryMap.put(westernEurope.getName().toLowerCase(), westernEurope);
//
//        territoryMap.put(congo.getName().toLowerCase(), congo);
//        territoryMap.put(eastAfrica.getName().toLowerCase(), eastAfrica);
//        territoryMap.put(egypt.getName().toLowerCase(), egypt);
//        territoryMap.put(madagascar.getName().toLowerCase(), madagascar);
//        territoryMap.put(northAfrica.getName().toLowerCase(), northAfrica);
//        territoryMap.put(southAfrica.getName().toLowerCase(), southAfrica);
//
//        territoryMap.put(afghanistan.getName().toLowerCase(), afghanistan);
//        territoryMap.put(china.getName().toLowerCase(), china);
//        territoryMap.put(india.getName().toLowerCase(), india);
//        territoryMap.put(irkutsk.getName().toLowerCase(), irkutsk);
//        territoryMap.put(japan.getName().toLowerCase(), japan);
//        territoryMap.put(kamchatka.getName().toLowerCase(), kamchatka);
//        territoryMap.put(middleEast.getName().toLowerCase(), middleEast);
//        territoryMap.put(mongolia.getName().toLowerCase(), mongolia);
//        territoryMap.put(siam.getName().toLowerCase(), siam);
//        territoryMap.put(siberia.getName().toLowerCase(), siberia);
//        territoryMap.put(ural.getName().toLowerCase(), ural);
//        territoryMap.put(yakutsk.getName().toLowerCase(), yakutsk);
//
//        territoryMap.put(easternAustralia.getName().toLowerCase(), easternAustralia);
//        territoryMap.put(indonesia.getName().toLowerCase(), indonesia);
//        territoryMap.put(newGuinea.getName().toLowerCase(), newGuinea);
//        territoryMap.put(westernAustralia.getName().toLowerCase(), westernAustralia);
//
//        //instantiate continents
//        Continent northAmerica = new Continent("North America", Arrays.asList(alaska,alberta,centralAmerica,easternUnitedStates,
//                greenland,northwestTerritory,ontario,quebec,westernUnitedStates), 20, 225, Color.yellow, 5);
//        Continent southAmerica = new Continent("South America", Arrays.asList(argentina,brazil,peru,venezuela), 100, 460, Color.red, 2);
//        Continent europe = new Continent("Europe", Arrays.asList(greatBritain,iceland,northernEurope,scandinavia,
//                southernEurope,ukraine,westernEurope), 600, 30, new Color(50,150,250), 5);
//        Continent africa = new Continent("Africa", Arrays.asList(congo,eastAfrica,egypt,madagascar,northAfrica,southAfrica),
//                495, 505, new Color(150,100,0), 3);
//        Continent asia = new Continent("Asia", Arrays.asList(afghanistan,china,india,irkutsk,japan,kamchatka,middleEast,
//                mongolia,siam,siberia,ural,yakutsk), 1100, 300, new Color(100,250,100), 7);
//        Continent australia = new Continent("Australia", Arrays.asList(easternAustralia,indonesia,newGuinea,westernAustralia), 1100, 385, new Color(230
//                ,0,250), 2);
//
//        //add all continents to territoryContinentMap hash map
//        for(Territory t: northAmerica.getTerritories()){ territoryContinentMap.put(t, northAmerica); }
//        for(Territory t: southAmerica.getTerritories()){ territoryContinentMap.put(t, southAmerica); }
//        for(Territory t: europe.getTerritories()){ territoryContinentMap.put(t, europe); }
//        for(Territory t: africa.getTerritories()){ territoryContinentMap.put(t, africa); }
//        for(Territory t: asia.getTerritories()){ territoryContinentMap.put(t, asia); }
//        for(Territory t: australia.getTerritories()){ territoryContinentMap.put(t, australia); }
//
//        //add all continents to Continent ArrayList
//        continents.add(northAmerica);
//        continents.add(southAmerica);
//        continents.add(europe);
//        continents.add(africa);
//        continents.add(asia);
//        continents.add(australia);
//
//        //set adjacent territories "connections"
//        alaska.setAdjacentTerritories(Arrays.asList(alberta,northwestTerritory,kamchatka));
//        alberta.setAdjacentTerritories(Arrays.asList(alaska,northwestTerritory,ontario,centralAmerica));
//        centralAmerica.setAdjacentTerritories(Arrays.asList(alberta,easternUnitedStates,westernUnitedStates));
//        easternUnitedStates.setAdjacentTerritories(Arrays.asList(centralAmerica,westernUnitedStates,ontario,quebec));
//        greenland.setAdjacentTerritories(Arrays.asList(northwestTerritory,ontario,quebec,iceland));
//        northwestTerritory.setAdjacentTerritories(Arrays.asList(alaska,alberta,ontario,greenland));
//        ontario.setAdjacentTerritories(Arrays.asList(alberta,northwestTerritory,alberta,easternUnitedStates,quebec,greenland));
//        quebec.setAdjacentTerritories(Arrays.asList(greenland,ontario,easternUnitedStates));
//        westernUnitedStates.setAdjacentTerritories(Arrays.asList(easternUnitedStates,centralAmerica,venezuela));
//        argentina.setAdjacentTerritories(Arrays.asList(brazil,peru));
//        brazil.setAdjacentTerritories(Arrays.asList(venezuela,peru,argentina,northAfrica));
//        peru.setAdjacentTerritories(Arrays.asList(venezuela,brazil,argentina));
//        venezuela.setAdjacentTerritories(Arrays.asList(brazil,peru,westernUnitedStates));
//        greatBritain.setAdjacentTerritories(Arrays.asList(iceland,scandinavia,northernEurope,westernEurope));
//        iceland.setAdjacentTerritories(Arrays.asList(greenland,scandinavia,greatBritain));
//        northernEurope.setAdjacentTerritories(Arrays.asList(scandinavia,greatBritain,westernEurope,southernEurope,ukraine));
//        scandinavia.setAdjacentTerritories(Arrays.asList(iceland,greatBritain,northernEurope,ukraine));
//        southernEurope.setAdjacentTerritories(Arrays.asList(northernEurope,westernEurope,ukraine,northAfrica,egypt,middleEast));
//        ukraine.setAdjacentTerritories(Arrays.asList(scandinavia,northernEurope,southernEurope,middleEast,afghanistan,ural));
//        westernEurope.setAdjacentTerritories(Arrays.asList(greatBritain,northernEurope,southernEurope,northAfrica));
//        congo.setAdjacentTerritories(Arrays.asList(southAfrica,eastAfrica,northAfrica));
//        eastAfrica.setAdjacentTerritories(Arrays.asList(congo,southAfrica,madagascar,northAfrica,egypt,middleEast));
//        egypt.setAdjacentTerritories(Arrays.asList(northAfrica,eastAfrica,middleEast,southernEurope));
//        madagascar.setAdjacentTerritories(Arrays.asList(eastAfrica,southAfrica));
//        northAfrica.setAdjacentTerritories(Arrays.asList(brazil,westernEurope,southernEurope,egypt,eastAfrica,congo));
//        southAfrica.setAdjacentTerritories(Arrays.asList(congo,eastAfrica,madagascar));
//        afghanistan.setAdjacentTerritories(Arrays.asList(ukraine,ural,china,india,middleEast));
//        china.setAdjacentTerritories(Arrays.asList(ural,afghanistan,india,siam,mongolia,siberia));
//        india.setAdjacentTerritories(Arrays.asList(siam,china,afghanistan,middleEast));
//        irkutsk.setAdjacentTerritories(Arrays.asList(kamchatka,yakutsk,siberia,mongolia));
//        japan.setAdjacentTerritories(Arrays.asList(kamchatka,mongolia));
//        kamchatka.setAdjacentTerritories(Arrays.asList(alaska,yakutsk,irkutsk,mongolia,japan));
//        middleEast.setAdjacentTerritories(Arrays.asList(india,afghanistan,ukraine,southernEurope,egypt,eastAfrica));
//        mongolia.setAdjacentTerritories(Arrays.asList(japan,irkutsk,kamchatka,siberia,china));
//        siam.setAdjacentTerritories(Arrays.asList(china,india,indonesia));
//        siberia.setAdjacentTerritories(Arrays.asList(yakutsk,irkutsk,mongolia,china,ural));
//        ural.setAdjacentTerritories(Arrays.asList(siberia,china,afghanistan,ukraine));
//        yakutsk.setAdjacentTerritories(Arrays.asList(kamchatka,irkutsk,siberia));
//        easternAustralia.setAdjacentTerritories(Arrays.asList(newGuinea,westernAustralia));
//        indonesia.setAdjacentTerritories(Arrays.asList(siam,newGuinea,westernAustralia));
//        newGuinea.setAdjacentTerritories(Arrays.asList(easternAustralia,indonesia,westernAustralia));
//        westernAustralia.setAdjacentTerritories(Arrays.asList(indonesia,easternAustralia,newGuinea));
    }

    /**
     * Returns a territory from an input string representation of a territory.
     * @param input A string representation of a territory.
     * @return A territory associated with the input territory.
     */
    public static Territory getTerritoryFromString(String input) {
        return territoryMap.get(input.toLowerCase());
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

    /**
     * Returns the territory Map of the game.
     * @return The Territory Map.
     */
    public static java.util.Map<String, Territory> getTerritoryMap(){
        return territoryMap;
    }

    /**
     * Returns an ArrayList of Continents.
     * @return An ArrayList of Continents.
     */
    public static ArrayList<Continent> getContinentsArrayList(){
        return continents;
    }

    /**
     * Adds the input Territory to the Territory Map.
     * @param territory The territory to be added to the Territory Map.
     */
    public static void addTerritory(Territory territory) {
        territoryMap.put(territory.getName().toLowerCase(), territory);
    }

    /**
     * Adds the input continent and territory to the territoryContinentMap and the continent to the ArrayList of Continents.
     * @param territory Territory to be associated with the continent.
     * @param continent Continent to be added to the Map and ArrayLit.
     */
    public static void addContinent(Territory territory, Continent continent) {
        territoryContinentMap.put(territory, continent);
        continents.add(continent);
    }
}

