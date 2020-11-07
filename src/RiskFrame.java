import javax.swing.*;
import java.awt.*;

public class RiskFrame extends JFrame implements RiskView {

    public RiskFrame() {
        super("RISK");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void handleRiskUpdate(RiskEvent e) {

    }

    /*public static void main(String[] args) {

    }*/
}