import javax.swing.*;
import java.awt.*;
import java.util.List;

//RiskFrame is the JFrame that holds all the GUI, it implements RiskView and whenever a change is made
//to the model, this class handles that change with the handleRiskUpdate method which re-draws everything
public class RiskFrame extends JFrame implements RiskView {

    private RiskGame rg;
    private RiskMapPanel mapPanel;
    private JLabel turn;
    private JLabel info;

    //todo, move hardcoded values into finals here
    //todo, clean up the wording of comments once done everything

    //nested MapDrawer class extends a JPanel and is the JPanel that holds the board
    //when ever the repaint() method is invoked, the paintComponent method ran which traverses all Territories
    //in the Map, fetches their X, Y coordinates and draws them out as circles
    //paintComponent also draws all the "connections" between the Territories

    public RiskFrame(RiskMap riskMap) {
        super("RISK");
        rg = new RiskGame(false, false);
        this.setLayout(new BorderLayout());

        //create a mapPanel and pass a reference to the RiskMap and RiskGame
        mapPanel = new RiskMapPanel(riskMap, rg);

        JPanel playerInputPanel = new JPanel();
        JPanel turnpanel = new JPanel();
        turn = new JLabel("Player's turn");
        info = new JLabel("Choose a territory to deploy armies to.");
        turnpanel.add(turn);
        turnpanel.add(info);
        JPanel buttonpanel = new JPanel();
        JButton pass = new JButton("PASS");
        pass.setActionCommand("pass");

        buttonpanel.add(pass);
        playerInputPanel.add(turnpanel);
        playerInputPanel.add(buttonpanel);

        RiskFrameController rfc = new RiskFrameController(rg);
        pass.addActionListener(rfc);

        this.add(mapPanel,BorderLayout.CENTER);
        this.add(playerInputPanel, BorderLayout.SOUTH);

        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        rg.addView(this);

        //start the game by telling RiskGame to start off by calculating
        //the first player's amount of armies to deploy
        rg.calculateArmiesToDeploy();

    }

    //whenever a change to the model is made, the model will notify all Classes that implement the RiskView Interface
    //by invoking their handleRiskUpdate method, for RiskFrame, the handleRiskUpdate method redraws the updated map
    //by triggering the handleRiskUpdate method of RiskMapPanel
    @Override
    public void handleRiskUpdate(RiskEvent e) {
        //propagate the event to the map panel as well
        mapPanel.handleRiskUpdate(e);
        turn.setText(e.getCurrentPlayer().getName() + "'s turn"); //include the phase

        //Deploy phase
        if (e.getPhase() == TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO) {
            info.setText("Choose a Territory to deploy to.");
        }

        if (e.getPhase() == TurnPhase.ATTACK_CHOOSE_ATTACKERS) {
            info.setText("Choose a Territory to attack with.");
        }
        else if (e.getPhase() == TurnPhase.ATTACK_CHOOSE_ENEMY) {
            info.setText("Choose a Territory to attack.");
        }
        else if (e.getPhase() == TurnPhase.ATTACK_CHOOSE_DICE) { //update this to use new phases
            info.setText("Choose a number of dice to attack with."); //list options
            //TODO: wrap JOptionPane in the try catch, or implement a spinner
            String str = JOptionPane.showInputDialog("Choose a number of dice to attack with (1 - " + ((RiskEventBounds)e).getMaxChoice() + ")"); //list options
            int diceNum = 0;
            try
            {
                if(str != null)
                    diceNum = Integer.parseInt(str);
            }
            catch (NumberFormatException excp)
            {
                diceNum = 0;
            }
            while (((diceNum > ((RiskEventBounds)e).getMaxChoice()) || (diceNum < ((RiskEventBounds)e).getMinChoice()))) {
                diceNum = Integer.parseInt(JOptionPane.showInputDialog("Invalid. Please enter number of dice between (1 - " + ((RiskEventBounds)e).getMaxChoice() + ")"));
            }
            rg.setAttackDice(diceNum); //note! some controller logic in here
        }
        else if (e.getPhase() == TurnPhase.DEFEND_CHOOSE_DICE) {
            info.setText("Choose a number of dice to defend with.");
            //TODO: wrap JOptionPane in the try catch, or implement a spinner
            String str = JOptionPane.showInputDialog("Choose a number of dice to defend with (1 - " + ((RiskEventBounds)e).getMaxChoice() + ")"); //list options
            int diceNum = 0;
            try
            {
                if(str != null)
                    diceNum = Integer.parseInt(str);
            }
            catch (NumberFormatException excp)
            {
                diceNum = 0;
            }
            while (((diceNum > ((RiskEventBounds)e).getMaxChoice()) || (diceNum < ((RiskEventBounds)e).getMinChoice()))) {
                diceNum = Integer.parseInt(JOptionPane.showInputDialog("Invalid. Please enter number of dice between (1 - " + ((RiskEventBounds)e).getMaxChoice() + ")"));
            }
            rg.setDefendDice(diceNum); //note! some controller logic in here
        }
        else if (e.getPhase() == TurnPhase.ATTACK_CHOOSE_MOVE) {
            info.setText("Choose a number of army to move.");
            //TODO: wrap JOptionPane in the try catch, or implement a spinner
            String str = JOptionPane.showInputDialog("Choose a number of army to move ("+ ((RiskEventBounds)e).getMinChoice() + " - " + ((RiskEventBounds)e).getMaxChoice() + ")"); //list options
            int armyNum = 0;
            try
            {
                if(str != null)
                    armyNum = Integer.parseInt(str);
            }
            catch (NumberFormatException excp)
            {
                armyNum = 0;
            }
            while (((armyNum > ((RiskEventBounds)e).getMaxChoice()) || (armyNum < ((RiskEventBounds)e).getMinChoice()))) {
                armyNum = Integer.parseInt(JOptionPane.showInputDialog("Invalid. Please enter number of armies between (" + ((RiskEventBounds)e).getMinChoice() + " - " + ((RiskEventBounds)e).getMaxChoice() + ")"));
            }
            rg.move(armyNum); //note! some controller logic in here
        }
        else if (e.getPhase() == TurnPhase.ATTACK_RESULT) {
            info.setText("Game over!");
            if (e instanceof RiskEventDiceResults) {
                RiskEventDiceResults diceResults = (RiskEventDiceResults) e;

                String message = "";
                for (Integer i : diceResults.getAttackDice()) {
                    message += i + ", ";
                }
                message = "Attacker rolls: " + message.substring(0, message.length() - 2) + "\n";

                String diceRolls = "";
                for (Integer i : diceResults.getDefendDice()) {
                    diceRolls += i + ", ";
                }
                diceRolls = diceRolls.substring(0, diceRolls.length() - 2);
                message += "Defender rolls: " + diceRolls + "\n"
                        + "Attackers (" + diceResults.getTerritoryFrom().getName() + ") lost " + diceResults.getAttackLoss() + " armies.\n";

                if (diceResults.getDefendLoss() != -1) {
                    message += "Defenders (" + diceResults.getTerritoryTo().getName() + ") lost " + diceResults.getDefendLoss() + " armies.";
                } else {
                    message += diceResults.getCurrentPlayer().getName() + " took over " + diceResults.getDefender().getName() + "'s " + diceResults.getTerritoryTo().getName();
                }
                JOptionPane.showMessageDialog(this, message);
            }
            else if (e instanceof RiskEventPlayer) {
                RiskEventPlayer playerEvent = (RiskEventPlayer)e;
                JOptionPane.showMessageDialog(this, playerEvent.getPlayer().getName() + " was eliminated at " + (rg.getNumPlayers() - playerEvent.getPlayer().getGameStanding() + 1) + "th place.");
            }
            else if (e instanceof RiskEventContinent){
                //do nothing since mapPanel.handleRiskUpdate(e); handles ev
                //do nothing
            }
        }
        else if (e.getPhase() == TurnPhase.END) {
            RiskEventEnd endEvent = (RiskEventEnd)e;
            List<Player> players = endEvent.getPlayers();
            String message = "================ GAME OVER ================\n";
            for(int i = 0; i <= players.size()-1; i++){
                Player p = players.get(i);
                if(p.getGameStanding() == 0){
                    if(i == 0){
                        message += p.getName() + " wins!\n";
                    }
                    else{
                        message += p.getName() + " had a standing of " + (rg.getNumPlayers() - p.getGameStanding() + 1) + "th place\n";
                    }
                }
            }
            JOptionPane.showMessageDialog(this, message);
        }
    }

    public static void main(String[] args) {
        RiskMap riskMap = new RiskMap(false);
        RiskFrame rf = new RiskFrame(riskMap);
    }
}
