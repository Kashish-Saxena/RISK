import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiskFrameController implements ActionListener {
    private RiskGame rg;

    public RiskFrameController(RiskGame rg) {
        this.rg = rg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("pass")) {
            rg.passTurn();
        }
        else if (e.getActionCommand().equals("fortify")){
            rg.chooseFortifyFrom();
        }
    }
}
