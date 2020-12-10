import javax.swing.*;
import java.io.Serializable;

public class RiskInitializer implements RiskView, Serializable {
    private RiskGame rg;

    public RiskInitializer(RiskGame rg) {
        this.rg = rg;
        rg.addView(this);
    }

    @Override
    public void handleRiskUpdate(RiskEvent e) {
        if (e.getPhase() == TurnPhase.INITIAL_SETUP) {
            String str = "";
            while (!(rg.getNumPlayers() >= RiskGame.MIN_PLAYERS && rg.getNumPlayers() <= RiskGame.MAX_PLAYERS)) {
                try {
                    str = JOptionPane.showInputDialog("Enter Number of Players (2-6):");
                    if (str != null) { //cannot cancel a player number choice
                        rg.setNumPlayers(Integer.parseInt(str));
                    }
                } catch (NumberFormatException excp) {
                    rg.setNumPlayers(0);
                }
            }

            String name = "";
            int isAI = 0;
            for (int i = 0; i < rg.getNumPlayers(); i++) {
                name = "";
                while (name == null || name.equals("")) {
                    name = JOptionPane.showInputDialog("Enter Player " + (i + 1) + "'s name:");
                }

                isAI = JOptionPane.showConfirmDialog(null, "Is " + name + " an AI?");
                //0 corresponds to yes button, 1 corresponds to no button
                if (isAI == 0) {
                    Player player = new PlayerAI(name + " (ai)", rg);
                    rg.addPlayer(player);
                } else {
                    Player player = new Player(name, rg);
                    rg.addPlayer(player);
                }
            }
            rg.autoPlaceArmies();
        }
    }


    public static void main(String[] args) {
        RiskMap riskMap = new RiskMap(false, false);
        RiskGame rg = new RiskGame(false, false);
        RiskInitializer ri = new RiskInitializer(rg);
        RiskFrame rf = new RiskFrame(riskMap, rg);
    }
}
