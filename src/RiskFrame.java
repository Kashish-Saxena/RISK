import javax.swing.*;
import java.awt.*;
import java.util.*;

//RiskFrame is the JFrame that holds all the GUI, it implements RiskView and whenever a change is made
//to the model, this class handles that change with the handleRiskUpdate method which re-draws everything
public class RiskFrame extends JFrame implements RiskView {

    //RiskFrame has a reference to map so that it can fetch all the Territory names and x,y coordinates
    private RiskMap riskMap;
    private RiskGame rg;
    private RiskMapPanel mapPanel;

    private ArrayList<Shape> territoryCircles; // Create an ArrayList object

    //todo, move hardcoded values into finals here
    //todo, clean up the wording of comments once done everything

    //nested MapDrawer class extends a JPanel and is the JPanel that holds the board
    //when ever the repaint() method is invoked, the paintComponent method ran which traverses all Territories
    //in the Map, fetches their X, Y coordinates and draws them out as circles
    //paintComponent also draws all the "connections" between the Territories


    public RiskFrame(RiskMap riskMap) {
        super("RISK");
        /*RiskGame*/ rg = new RiskGame();
        this.riskMap = riskMap;

        this.setLayout(new BorderLayout());

        territoryCircles = new ArrayList<Shape>();
        mapPanel = new RiskMapPanel(riskMap, rg);

        JPanel playerInputPanel = new JPanel();
        JPanel turnpanel = new JPanel();
        JLabel turn = new JLabel("Player's turn");
        turnpanel.add(turn);
        turnpanel.add(info);
        JPanel buttonpanel = new JPanel();
        //JButton attack = new JButton("ATTACK");
        JButton pass = new JButton("PASS");
        buttonpanel.add(attack);
        buttonpanel.add(pass);
        playerInputPanel.add(turnpanel);
        playerInputPanel.add(buttonpanel);

        RiskFrameController rfc = new RiskFrameController(rg);
        pass.addActionListener(rfc);

        //this.add(mapPanel);
        this.add(mapPanel,BorderLayout.CENTER);
        this.add(playerInputPanel, BorderLayout.SOUTH);

        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        rg.addView(this);
    }

    //whenever a change to the model is made, the model will notify all Classes that implement the RiskView Interface
    //by invoking their handleRiskUpdate method, for RiskFrame, the handleRiskUpdate method redraws the updated map
    //by triggering the paint method of mapPanel
    @Override
    public void handleRiskUpdate(RiskEvent e) {
        mapPanel.handleRiskUpdate(e);
    }

    //main
    public static void main(String[] args) {
        RiskMap riskMap = new RiskMap();
        RiskFrame rf = new RiskFrame(riskMap);
    }

}