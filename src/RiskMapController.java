import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The RiskMapController class passes the territory to the processTerritory() method in the model (RiskGame) which sets
 * it as a territory to attack from or a territory to be attacked and finally updates the model.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version November 23, 2020
 */
public class RiskMapController implements ActionListener {

    private RiskGame rg;

    /**
     * Constructor of the RiskMapController class. It initializes the field values.
     * @param rg The RiskGame class.
     */
    public RiskMapController(RiskGame rg) {
        this.rg = rg;
    }

    /**
     * Invokes processTerritory() method from RiskGame and sends the territory clicked as a parameter.
     * @param e ActionEvent caused by the territory.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Territory territory = RiskMap.getTerritoryFromString(e.getActionCommand());
        if (rg.getPhase() == TurnPhase.DEPLOY_CHOOSE_TERRITORY_TO_DEPLOY_TO) {
            rg.setDeployTerritory(territory);
        }
        else if (rg.getPhase() == TurnPhase.ATTACK_CHOOSE_ATTACKERS) {
            rg.setAttackerTerritory(territory);
        }
        else if (rg.getPhase() == TurnPhase.ATTACK_CHOOSE_ENEMY) {
            rg.setEnemyTerritory(territory);
        }
        else if (rg.getPhase() == TurnPhase.FORTIFY_CHOOSE_FROM_TERRITORY) {
            rg.setFortifierTerritory(territory);
        }
        else if (rg.getPhase() == TurnPhase.FORTIFY_CHOOSE_TO_TERRITORY) {
            rg.setFortifiedTerritory(territory);
        }
    }
}
