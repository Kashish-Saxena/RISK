import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiskMapController implements ActionListener {

    private RiskGame rg;

    public RiskMapController(RiskGame rg) {
        this.rg = rg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Territory territory = RiskMap.getTerritoryFromString(e.getActionCommand());
        rg.processTerritory(territory);
    }
}
