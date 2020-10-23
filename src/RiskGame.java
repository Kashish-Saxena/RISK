import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class RiskGame implements Observer {

    private List<Player> players;
    private boolean gameInProgress;
    private List<Continent> continents;

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
