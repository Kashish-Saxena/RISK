import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map;

public class RiskFrame extends JFrame implements RiskView {

    //RiskFrame has a reference to map so that it can fetch all the Territory names and x,y coordinates
    private RiskMap riskMap;
    private MapDrawerJPanel mapPanel;
    private BufferedImage image;

    //todo, move hardcoded values into finals here

    //nested MapDrawer class extends a JPanel and is the JPanel that holds the board
    //when ever the repaint() method is invoked, the paintComponent method ran which traverses all Territories
    //in the Map, fetches their X, Y coordinates and draws them out as circles
    //paintComponent also draws all the "connections" between the Territories
    public class MapDrawerJPanel extends JPanel{

        @Override
        public void paint(Graphics g) {
            System.out.println("entering paint");//todo, remove this later, just using it for debugging
            Graphics2D g2 = (Graphics2D) g;

            //draw world background image
            try {
                image = ImageIO.read(new File("res/world_map.png"));
            } catch (IOException ignored) { }
            g.drawImage(image, 0,0,1200,700,null);

            //for each Territory in territoryMap hash map, draw a circle at the Territory's x,y coordinates
            g2.setColor(Color.red);
            Iterator hmIterator = riskMap.getTerritoryMap().entrySet().iterator();
            while (hmIterator.hasNext()) {
                //draw circle at Territory x,y coordinates
                g2.setColor(Color.red);
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                Territory tempTerritory = (Territory) mapElement.getValue();
                g2.fillOval(tempTerritory.getXPos(), tempTerritory.getYPos(), 20, 20);

                //draw Territory name
                g2.setColor(Color.black);
                g2.drawString(tempTerritory.getName(), tempTerritory.getXPos()+20, tempTerritory.getYPos()-10);
                g2.drawString(String.valueOf(tempTerritory.getArmies()), tempTerritory.getXPos(), tempTerritory.getYPos());
            }
        }
    }

    public RiskFrame(RiskMap riskMap) {
        super("RISK");
        this.riskMap = riskMap;
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        mapPanel = new MapDrawerJPanel();
        //mapPanel.repaint();
        this.add(mapPanel);

        JPanel turnpanel = new JPanel();
        JLabel turn = new JLabel("Player's turn");
        turnpanel.add(turn);

        JPanel buttonpanel = new JPanel();
        JButton attack = new JButton("ATTACK");
        JButton pass = new JButton("PASS");
        buttonpanel.add(attack);
        buttonpanel.add(pass);
        this.add(turnpanel);
        this.add(buttonpanel,BorderLayout.SOUTH);
        this.setResizable(false);
    }

    @Override
    public void handleRiskUpdate(RiskEvent e) {
        mapPanel.repaint();
    }

    public static void main(String[] args) {
        RiskMap riskMap = new RiskMap();
        RiskFrame rf = new RiskFrame(riskMap);
    }
}