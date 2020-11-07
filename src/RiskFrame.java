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
    private Image backgroundImage;
    private BufferedImage image;


    //nested MapDrawer class extends a JPanel and is the JPanel that holds the board
    //when ever the repaint() method is invoked, the paintComponent method ran which traverses all Territories
    //in the Map, fetches their X, Y coordinates and draws them out as circles
    //paintComponent also draws all the "connections" between the Territories
    public class MapDrawerJPanel extends JPanel{

        public MapDrawerJPanel(){
            super();

        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            //draw world background image
            try {
                image = ImageIO.read(new File("res/world_map.png"));
            } catch (IOException ex) { }
            g.drawImage(image, 0,0,1200,700,null);

            //for each Territory in territoryMap hash map, draw a circle at the Territory's x,y coordinates
            g2.setColor(Color.red);
            Iterator hmIterator = riskMap.getTerritoryMap().entrySet().iterator();
            while (hmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                Territory tempTerritory = (Territory) mapElement.getValue();
                System.out.println(tempTerritory.getXPos());
                g2.fillOval(tempTerritory.getXPos(), tempTerritory.getYPos(), 20, 20);
            }
        }
    }

    public RiskFrame(RiskMap riskMap) {
        super("RISK");
        this.riskMap = riskMap;
        this.setSize(1200, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        mapPanel = new MapDrawerJPanel();
        //mapPanel.repaint();

        this.add(mapPanel);
    }

    @Override
    public void handleRiskUpdate(RiskEvent e) {
        mapPanel.repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.red);
        g.fillOval(getWidth()/4, getHeight()/4,
                getWidth()/2, getHeight()/2);
    }

    /*public static void main(String[] args) {

    }*/
}