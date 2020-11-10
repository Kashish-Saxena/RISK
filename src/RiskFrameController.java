import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * RiskFrameController class controls the Pass JButton on the JFrame.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version November 9, 2020
 */
public class RiskFrameController implements ActionListener {
    private RiskGame rg;

    /**
     * Constructor of the RiskFrameController class. It initializes all the field values.
     * @param rg The RiskGame class.
     */
    public RiskFrameController(RiskGame rg) {
        this.rg = rg;
    }

    /**
     * Invokes the passTurn() method in the RiskGame class when a player clicks on the pass button.
     * @param e The ActionEvent caused by the pass button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("pass")) {
            rg.passTurn();
        }
    }
}
