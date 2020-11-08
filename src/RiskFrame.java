import javax.imageio.ImageIO;
import javax.swing.*;
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
public class RiskFrame extends JFrame implements RiskView, MouseListener {

    //RiskFrame has a reference to map so that it can fetch all the Territory names and x,y coordinates
    private RiskMap riskMap;

    private MapDrawerJPanel mapPanel;
    private BufferedImage image;

    ArrayList<Shape> territoryCircles; // Create an ArrayList object

    //todo, move hardcoded values into finals here

    //todo, clean up the wording of comments once done everything

    //nested MapDrawer class extends a JPanel and is the JPanel that holds the board
    //when ever the repaint() method is invoked, the paintComponent method ran which traverses all Territories
    //in the Map, fetches their X, Y coordinates and draws them out as circles
    //paintComponent also draws all the "connections" between the Territories
    public class MapDrawerJPanel extends JPanel{

        @Override
        public void paint(Graphics g) {
            this.setBackground(Color.white);

            System.out.println("entering paint");//todo, remove this later, just using it for debugging
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));

            //draw world background image
            try {
                image = ImageIO.read(new File("res/world_map.png"));
            } catch (IOException ignored) { }
            g.drawImage(image, 0,0,1200,700,null);

            //draw the special case "connection between Alaska and Kamchatka"
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(80,100,20,100);
            g2.drawLine(1050, 90,1200,90);

            //first draw each of the "connections" between Territories
            //todo, right now this approach actually draws each connection twice, is there a better way???
            g2.setColor(Color.black);
            Iterator hmIterator = riskMap.getTerritoryMap().entrySet().iterator();
            while (hmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                Territory tempTerritory = (Territory) mapElement.getValue();

                //for each of the adjacent Territories of tempTerritory, draw the connection
                for(Territory t: tempTerritory.getAdjacentTerritories()){
                    if(t.getName() != "Alaska" && t.getName() != "Kamchatka"){
                        g2.drawLine(tempTerritory.getXPos(),tempTerritory.getYPos(),t.getXPos(),t.getYPos());
                    }
                }
            }

            //then for each Territory in territoryMap hash map, draw a circle at the Territory's x,y coordinates and draw the Territory name
            g2.setColor(Color.red);
            hmIterator = riskMap.getTerritoryMap().entrySet().iterator(); //reset iterator
            while (hmIterator.hasNext()) {

                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                Territory tempTerritory = (Territory) mapElement.getValue();

                //draw mini background rectangle for Territory name and number of armies
                g2.setColor(Color.black);
                g2.drawRect(tempTerritory.getXPos(), tempTerritory.getYPos()-10, 90,26);
                g2.setColor(Color.cyan);
                g2.fillRect(tempTerritory.getXPos(), tempTerritory.getYPos()-10, 90,26);

                //draw Territory name and owner
                g2.setColor(Color.black);
                g2.drawString(tempTerritory.getName(), tempTerritory.getXPos()+19, tempTerritory.getYPos()+2);
                //todo, print actual name once initial setup is implemented
                //g2.drawString(tempTerritory.getOwner().getName(), tempTerritory.getXPos()+19, tempTerritory.getYPos()+14);
                //for now Jeff Bezos owns the world
                g2.drawString("Jeff Bezos", tempTerritory.getXPos()+18, tempTerritory.getYPos()+14);

                //draw Territory black circle outline
                g2.setColor(Color.black);
                g2.fillOval(tempTerritory.getXPos()-18, tempTerritory.getYPos()-18, 36, 36);

                //draw Territory inner circle
                g2.setColor(Color.green);
                g2.fillOval(tempTerritory.getXPos()-14, tempTerritory.getYPos()-14, 28, 28);

                //draw number of armies on the Territory
                g2.setColor(Color.black);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
                g2.drawString( String.valueOf(tempTerritory.getArmies()), tempTerritory.getXPos()-7, tempTerritory.getYPos()+5);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
            }
        }
    }

    public RiskFrame(RiskMap riskMap) {
        super("RISK");
        this.riskMap = riskMap;
        this.setSize(1300, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        territoryCircles = new ArrayList<Shape>();
        mapPanel = new MapDrawerJPanel();
        this.add(mapPanel);

        //add mouse mouse listener to frame to listen for mouse clicks
        addMouseListener(this);
    }

    //whenever a change to the model is made, the model will notify all Classes that implement the RiskView Interface
    //by invoking their handleRiskUpdate method, for RiskFrame, the handleRiskUpdate method redraws the updated map
    //by triggering the paint method of mapPanel
    @Override
    public void handleRiskUpdate(RiskEvent e) {
        mapPanel.repaint();
    }

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

    //need these other methods since this class implements MouseListener
    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}