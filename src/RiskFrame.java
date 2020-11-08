import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map;

//RiskFrame is the JFrame that holds all the GUI, it implements RiskView and whenever a change is made
//to the model, this class handles that change with the handleRiskUpdate method which re-draws everything
public class RiskFrame extends JFrame implements RiskView/*, MouseListener*/ {

    //RiskFrame has a reference to map so that it can fetch all the Territory names and x,y coordinates
    private RiskMap riskMap;
    private RiskMapPanel mapPanel;
    private BufferedImage image;

    private ArrayList<Shape> territoryCircles; // Create an ArrayList object

    //todo, move hardcoded values into finals here
    //todo, clean up the wording of comments once done everything

    //nested MapDrawer class extends a JPanel and is the JPanel that holds the board
    //when ever the repaint() method is invoked, the paintComponent method ran which traverses all Territories
    //in the Map, fetches their X, Y coordinates and draws them out as circles
    //paintComponent also draws all the "connections" between the Territories


    public RiskFrame(RiskMap riskMap) {
        super("RISK");
        this.riskMap = riskMap;
        this.setSize(1250, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        //this.setLayout(new BorderLayout());
        //this.setLayout(null);

        territoryCircles = new ArrayList<Shape>();
        mapPanel = new RiskMapPanel(riskMap);

        //add mouse mouse listener to frame to listen for mouse clicks
        //addMouseListener(this);

        JPanel playerInputPanel = new JPanel();
        JPanel turnpanel = new JPanel();
        JLabel turn = new JLabel("Player's turn");
        turnpanel.add(turn);
        JPanel buttonpanel = new JPanel();
        JButton attack = new JButton("ATTACK");
        JButton pass = new JButton("PASS");
        buttonpanel.add(attack);
        buttonpanel.add(pass);
        playerInputPanel.add(turnpanel);
        playerInputPanel.add(buttonpanel);

        this.add(mapPanel);
        //this.add(mapPanel,BorderLayout.CENTER);
        //this.add(playerInputPanel, BorderLayout.SOUTH);
    }

    //whenever a change to the model is made, the model will notify all Classes that implement the RiskView Interface
    //by invoking their handleRiskUpdate method, for RiskFrame, the handleRiskUpdate method redraws the updated map
    //by triggering the paint method of mapPanel

    //public void handleRiskUpdate(RiskEvent e) {
    @Override
    public void handleRiskUpdate() {
        System.out.println("repainting");
        mapPanel.repaint();
        this.repaint();
    }
/*
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse click detected");

        //fetch and store the x,y coordinates of the click
        Point p = e.getPoint();
        int xClk = p.x - 7;//offset of 7
        int yClk = p.y - 30;//offset of 30
        System.out.println(p.x + "," +  p.y);

        //now go through all Territories and check if the x,y coordinates match (with a give or take amount of 15)
        Iterator hmIterator = riskMap.getTerritoryMap().entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            Territory tempTerritory = (Territory) mapElement.getValue();

            //if the x,y coordinates match (with a give or take amount of 15) do something ... (todo, finish this comment once we know what we doing)
            //use pythag to compute distance between 2 points
            if(Math.sqrt((tempTerritory.getYPos() - yClk) * (tempTerritory.getYPos() - yClk) + (tempTerritory.getXPos() - xClk) * (tempTerritory.getXPos() - xClk)) <= 15 ){

                //for now just print the name of the Territory
                System.out.println(tempTerritory.getName());//todo, hook this up
            }
        }
    }
    */

/*
    //need these other methods since this class implements MouseListener
    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
  */
    //main
    public static void main(String[] args) {
        RiskMap riskMap = new RiskMap();
        RiskGame rg = new RiskGame();
        RiskFrame rf = new RiskFrame(riskMap);
    }

}