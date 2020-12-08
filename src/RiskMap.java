
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;


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
    private String mapPath;

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
                //first create the map
                createMap();

                //the validate it
                validateMap();
            }
            catch(Exception e){
                //need to put createMap() in a try catch block since it can throw exceptions
                System.out.println("Exception occurred while creating map");
                e.printStackTrace();
            }
        }
    }

    /**
     * Makes user select the json file to use for the custom map.
     */
    private void selectMapPath(){
        JOptionPane.showMessageDialog(null, "Please select a map from maps folder (included in zip submission)");
        while(mapPath.equals("")){
            // create an object of JFileChooser class
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            // invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION)
            {
                System.out.println(j.getSelectedFile().getAbsolutePath());
                mapPath = j.getSelectedFile().getAbsolutePath();
            }
            // if the user cancelled the operation
            else{
                JOptionPane.showMessageDialog(null, "Please select a map from maps folder (included in zip submission)");
            }
        }
    }


    /**
     * Instantiates territories, continents and set adjacent territories connections.
     */
    private void createMap() throws IOException, ParseException {

        //note that JSON parsing is done using an external java library
        //the java library was included within the "json external library" folder
        //jar file was downloaded from https://mvnrepository.com/artifact/org.json/json

        //first make user select the json file containing the custom map to load
        //custom maps are also located within the "maps" folder (included in zip submission)
        String mapPath = "";

        //todo, remove hard coded path before final submission
        //selectMapPath();
        mapPath = "maps/test_invalid_json";




        //parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader(mapPath));

        //typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        Iterator<Map.Entry> itr1;

        //todo, seperate this into multiple smaller methods

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
                } else if (pair.getKey().equals("yPos")) {
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
                //System.out.println(pair.getKey() + " : " + pair.getValue());

                if(pair.getKey().equals("name")){
                    tempName = (String)pair.getValue();
                }
                else if(pair.getKey().equals("xPos")){
                    tempXPos = (int)(long) pair.getValue();
                }
                else if(pair.getKey().equals("yPos")){
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

        //fetch territoriesInContinents from json file
        JSONArray territoriesInContinentsArray = (JSONArray) jo.get("territoriesInContinents");

        //traverse territoriesArray to create all territory objects
        Iterator territoryToContinentItr = territoriesInContinentsArray.iterator();

        while (territoryToContinentItr.hasNext()) {
            String tempContinentName = "";
            String tempTerritories = "";

            Continent tempContinent = null;
            List<Territory> territoriesInContinent = new ArrayList<Territory>();


            itr1 = ((Map) territoryToContinentItr.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                //System.out.println(pair.getKey() + " : " + pair.getValue());

                if (pair.getKey().equals("continent")) {
                    tempContinentName = (String) pair.getValue();
                } else if (pair.getKey().equals("territories")) {
                    tempTerritories = (String) pair.getValue();
                }

            }

            //find the continent object with matching name in continents
            for(Continent c: continents){
                if(c.toString().equals(tempContinentName)){
                    tempContinent = c;
                }
            }

            //find all territory objects with matching names in territories
            String[] parts = tempTerritories.split(",");
            for (String terrName : parts) {
                for (Territory t : territories) {
                    if (t.getName().equals(terrName)) {
                        territoriesInContinent.add(t);
                    }
                }

            }

            //finally add the territories to the continent
            tempContinent.setTerritories(territoriesInContinent);

            //also add all continent to territoryContinentMap hash map
            for(Territory t: tempContinent.getTerritories()){ territoryContinentMap.put(t, tempContinent); }
        }


        //==========================================
        //   STEP 4, link adjacent territories
        //==========================================
        //fetch territoriesInContinents from json file
        JSONArray territoryAdjacencyArray = (JSONArray) jo.get("territoryAdjacencies");

        //traverse territoriesArray to create all territory objects
        Iterator territoryToTerritoryItr = territoryAdjacencyArray.iterator();

        while (territoryToTerritoryItr.hasNext()) {
            String tempTerritoryName = "";
            String tempTerritories = "";

            Territory tempTerritory = null;
            List<Territory> territoriesInTerritory = new ArrayList<Territory>();


            itr1 = ((Map) territoryToTerritoryItr.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());

                if (pair.getKey().equals("territory")) {
                    tempTerritoryName = (String) pair.getValue();
                } else if (pair.getKey().equals("territories")) {
                    tempTerritories = (String) pair.getValue();
                }

            }

            //find the territory object with matching name in territories
            for(Territory t: territories){
                if(t.getName().equals(tempTerritoryName)){
                    tempTerritory = t;
                }
            }

            //find all territory objects with matching names in territories
            String[] parts = tempTerritories.split(",");
            for (String terrName : parts) {
                for (Territory t : territories) {
                    if (t.getName().equals(terrName)) {
                        territoriesInTerritory.add(t);
                    }
                }

            }

            //finally add the territories to the continent
            tempTerritory.setAdjacentTerritories(territoriesInTerritory);


        }
    }

    /**
     * Validates the custom map that was loaded from a json file by doing a depth first search
     * starting at the territory that was first added to territories in map creation.
     */
    private void validateMap(){
        ArrayList<Territory> visitedTerritories = new ArrayList<Territory>();
        recursiveDepthFirstSearchOnTerritories(territories.get(0), visitedTerritories);
        //after calling recursiveDepthFirstSearchOnFriendlyTerritories, validChoices contains all the visited Territories

        //if the visitedTerritories does not contain all the original territories exit program
        if(! territories.equals(visitedTerritories)){
            JOptionPane.showMessageDialog(null, "Selected custom map is not valid");
            System.exit(0);
        }else{
            System.out.println("map is valid");
        }
    }

    /**
     * Does a depth first search traversal starting at currentTerritory to determine all "connected" territories.
     */
    private void recursiveDepthFirstSearchOnTerritories(Territory currentTerritory, ArrayList<Territory> visitedTerritories){
        //mark currentTerritory as visited by adding it to visitedTerritories
        visitedTerritories.add(currentTerritory);

        //Traverse all the adjacent and unmarked Territories and call the recursive function with index of adjacent Territory.
        for(Territory t: currentTerritory.getAdjacentFriendlyTerritories()){
            if(!visitedTerritories.contains(t)){//if not visited
                recursiveDepthFirstSearchOnTerritories(t, visitedTerritories);
            }
        }
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

