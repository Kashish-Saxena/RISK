import java.util.List;

public class Continent {

    private String name;
    private List<Territory> territories;

    //Constructor
    public Continent(String name, List<Territory> territories){
        this.name = name;
        this.territories = territories;
    }

    public String getName(){
        return this.name;
    }

    public List<Territory> getTerritories(){
        return this.territories;
    }

}
