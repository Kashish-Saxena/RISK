import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Continent {

    private String name;
    private List<Territory> territories;
    private static Map<Territory, Continent> territoryContinentMap = new HashMap<>();

    //Constructor
    public Continent(String name, List<Territory> territories){
        this.name = name;
        this.territories = territories;
        for (Territory t : territories) {
            territoryContinentMap.put(t, this);
        }
    }

    public String toString(){
        return this.name;
    }

    public List<Territory> getTerritories(){
        return this.territories;
    }

    public static Continent getContinentFromTerritory(Territory childTerritory) {
        return territoryContinentMap.get(childTerritory);
    }
}
