import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * RiskFrame is the JFrame that contains the GUI. This class implements the RiskView interface which contains a method
 * handleRiskUpdate(). This method updates the appropriate components on the map whenever the model changes.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version November 23, 2020
 */

public class RiskFrame extends JFrame implements RiskView {

    private RiskGame rg;
    private RiskMapPanel mapPanel;
    private JLabel turn;
    private JLabel info;
    private JButton passAndFortifyButton;

    /**
     * Constructor of the RiskFrame class. It initializes the field values
     * @param riskMap Map of the game.
     */
    public RiskFrame(RiskMap riskMap) {
        super("RISK");
        rg = new RiskGame(false, false);
        this.setLayout(new BorderLayout());

        //create a mapPanel and pass a reference to the RiskMap and RiskGame
        mapPanel = new RiskMapPanel(riskMap, rg);

        JPanel playerInputPanel = new JPanel();
        JPanel turnpanel = new JPanel();
        turn = new JLabel("Player's turn");
        info = new JLabel("Choose a territory to deploy to.");
        turnpanel.add(turn);
        turnpanel.add(info);
        JPanel buttonpanel = new JPanel();
        passAndFortifyButton = new JButton("END ATTACK AND FORTIFY");
        passAndFortifyButton.setActionCommand("fortify");
        passAndFortifyButton.setEnabled(false);

        buttonpanel.add(passAndFortifyButton);
        playerInputPanel.add(turnpanel);
        playerInputPanel.add(buttonpanel);

        RiskFrameController rfc = new RiskFrameController(rg);
        passAndFortifyButton.addActionListener(rfc);

        this.add(mapPanel,BorderLayout.CENTER);
        this.add(playerInputPanel, BorderLayout.SOUTH);

        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        //subscribe this frame (which implements RiskView) to the RiskGame model
        //this way the model can tell this frame to handle any updates
        rg.addView(this);

        //start the game by telling RiskGame to start off by calculating
        //the first player's amount of armies to deploy
        rg.calculateArmiesToDeploy();

    }

    @Override
    public void handleRiskUpdate(RiskEvent e) {
        //propagate the event to the map panel as well
        mapPanel.handleRiskUpdate(e);

        turn.setText(e.getCurrentPlayer().getName() + "'s turn"); //include the phase

        //Deploy phases
        if (e.getPhase() == TurnPhase.AI_INFO) {}
        else if (e.getPhase() == TurnPhase.DEPLOY_CALCULATE_ARMIES_TO_PLACE){
            //disable the fortify button for deploy phase
            passAndFortifyButton.setEnabled(false);
        }

        else if (e.getPhase() == TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO) {
            info.setText("Choose a territory to deploy to.");
        }
        else if (e.getPhase() == TurnPhase.DEPLOY_CHOOSE_DEPLOY_AMOUNT) {
            RiskEventBounds r = (RiskEventBounds)e;
            info.setText("Choose an amount of armies to deploy.");
            int armyNum = 0;
            String str = "";
            boolean cancel = false;
            while (!cancel && ((armyNum > ((RiskEventBounds)e).getMaxChoice()) || (armyNum < ((RiskEventBounds)e).getMinChoice()))) {
                try {
                    str = JOptionPane.showInputDialog("Choose a number of armies to deploy ( "+r.getMinChoice()+" - " + r.getMaxChoice() + ")"); //list options
                    if (str == null) {
                        cancel = 0 == JOptionPane.showConfirmDialog(null, "Cancel deploy?");
                    }
                    else {
                        armyNum = Integer.parseInt(str);
                    }
                } catch (NumberFormatException excp) {
                    armyNum = 0;
                }
            }
            if (cancel) {
                rg.checkIfThereAreArmiesLeftToDeploy(); //note! some controller logic in here
            }
            else {
                rg.giveDeployedArmies(armyNum);
            }
        }
        else if (e.getPhase() == TurnPhase.DEPLOY_UPDATE_DEPLOYED_TERRITORY) {
            RiskEventSingleTerritory r = (RiskEventSingleTerritory)e;
            info.setText("deployed armies to "+r.getTerritory().getName()+".");
        }

        //attack phases
        else if (e.getPhase() == TurnPhase.ATTACK_CHOOSE_ATTACKERS) {
            info.setText("Choose a Territory to attack with.");
            //also re-enable the fortify button that was disabled from deploy phase
            passAndFortifyButton.setEnabled(true);
        }
        else if (e.getPhase() == TurnPhase.ATTACK_CHOOSE_ENEMY) {
            info.setText("Choose a Territory to attack.");
        }
        else if (e.getPhase() == TurnPhase.ATTACK_CHOOSE_DICE) { //update this to use new phases
            info.setText("Choose a number of dice to attack with."); //list options
            int diceNum = 0;
            String str = "";
            boolean cancel = false;
            while (!cancel && ((diceNum > ((RiskEventBounds)e).getMaxChoice()) || (diceNum < ((RiskEventBounds)e).getMinChoice()))) {
                try {
                    str = JOptionPane.showInputDialog("Choose a number of dice to attack with (1 - " + ((RiskEventBounds) e).getMaxChoice() + ")"); //list options
                    if (str == null) {
                        cancel = 0 == JOptionPane.showConfirmDialog(null, "Cancel attack?");
                    }
                    else {
                        diceNum = Integer.parseInt(str);
                    }
                } catch (NumberFormatException excp) {
                    diceNum = 0;
                }
            }
            if (cancel) {
                rg.cancelAttack(); //note! some controller logic in here
            }
            else {
                rg.setAttackDice(diceNum);
            }
        }
        else if (e.getPhase() == TurnPhase.DEFEND_CHOOSE_DICE) {
            info.setText("Choose a number of dice to defend with.");
            int diceNum = 0;
            String str = "";
            while (diceNum > ((RiskEventBounds)e).getMaxChoice() || diceNum < ((RiskEventBounds)e).getMinChoice()) {
                try {
                    str = JOptionPane.showInputDialog("Choose a number of dice to defend with (1 - " + ((RiskEventBounds)e).getMaxChoice() + ")"); //list options
                    if (str != null) { //player cannot cancel a defend choice
                        diceNum = Integer.parseInt(str);
                    }
                } catch (NumberFormatException excp) {
                    diceNum = 0;
                }
            }
            rg.setDefendDice(diceNum); //note! some controller logic in here
        }
        else if (e.getPhase() == TurnPhase.ATTACK_CHOOSE_MOVE) {
            info.setText("Choose a number of army to move.");
            int armyNum = 0;
            String str = "";
            while (armyNum > ((RiskEventBounds)e).getMaxChoice() || armyNum < ((RiskEventBounds)e).getMinChoice()) {
                try {
                    str = JOptionPane.showInputDialog("Choose a number of army to move ("+ ((RiskEventBounds)e).getMinChoice() + " - " + ((RiskEventBounds)e).getMaxChoice() + ")"); //list options
                    if (str != null) { //player cannot cancel an attack move choice
                        armyNum = Integer.parseInt(str);
                    }
                } catch (NumberFormatException excp) {
                    armyNum = 0;
                }
            }
            rg.move(armyNum); //note! some controller logic in here
        }
        else if (e.getPhase() == TurnPhase.ATTACK_RESULT) {
            info.setText("Attack over!");
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

        //fortify phases
        else if(e.getPhase() == TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY){
            passAndFortifyButton.setText("PASS TURN");
            passAndFortifyButton.setActionCommand("pass");
            info.setText("Choose a Territory to move armies from.");
        }
        else if(e.getPhase() == TurnPhase.FORTIFY_CHOOSE_TO_TERRITORY){
            info.setText("Choose a Territory to move armies to.");
        }
        else if(e.getPhase() == TurnPhase.FORTIFY_CHOOSE_FORTIFY_AMOUNT){
            info.setText("Choose an amount of armies to move.");
            RiskEventBounds r = (RiskEventBounds)e;
            int armyNum = 0;
            String str = "";
            boolean cancel = false;
            while (!cancel && ((armyNum > ((RiskEventBounds)e).getMaxChoice()) || (armyNum < ((RiskEventBounds)e).getMinChoice()))) {
                try {
                    str = JOptionPane.showInputDialog("Choose a number of armies to move ( "+r.getMinChoice()+" - " + r.getMaxChoice() + ")"); //list options
                    if (str == null) {
                        cancel = 0 == JOptionPane.showConfirmDialog(null, "Cancel fortify?");
                    }
                    else {
                        armyNum = Integer.parseInt(str);
                    }
                } catch (NumberFormatException excp) {
                    armyNum = 0;
                }
            }
            if (cancel) {
                rg.chooseFortifyFrom(); //note! some controller logic in here
            }
            else {
                rg.fortify(armyNum);
            }
        }

        else if (e.getPhase() == TurnPhase.END) {
            info.setText("Game over!");
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
