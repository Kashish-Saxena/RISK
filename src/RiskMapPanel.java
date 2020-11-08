import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class RiskMapPanel extends JPanel {
    private RiskMap riskMap;
    private BufferedImage image;

    public RiskMapPanel(RiskMap riskMap) {
        this.riskMap = riskMap;

        //then for each Territory in territoryMap hash map, draw a circle at the Territory's x,y coordinates and draw the Territory name
        //g2.setColor(Color.red);
        this.setLayout(null);
        Iterator hmIterator = riskMap.getTerritoryMap().entrySet().iterator(); //reset iterator
        while (hmIterator.hasNext()) {

            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            Territory tempTerritory = (Territory) mapElement.getValue();

            JButton buttonTerritory = new JButton("" + tempTerritory.getArmies());
            buttonTerritory.setBounds(tempTerritory.getXPos()-14, tempTerritory.getYPos()-14, 28, 28);
            buttonTerritory.setBorderPainted(false);
            buttonTerritory.setBackground(Color.GREEN);
            buttonTerritory.setForeground(Color.BLACK);
            buttonTerritory.setFont(new Font("Arial", Font.BOLD, 14));
            buttonTerritory.setMargin(new Insets(0, 0, 0, 0));
            buttonTerritory.setOpaque(true);
            this.add(buttonTerritory);

            //draw Territory name and owner
            JLabel labelTerritoryName = new JLabel(tempTerritory.getName());
            labelTerritoryName.setBounds(tempTerritory.getXPos()+19, tempTerritory.getYPos()-28, 80, 40);
            this.add(labelTerritoryName);

            JLabel labelTerritoryOwner = new JLabel(tempTerritory.getOwner().getName());
            labelTerritoryOwner.setBounds(tempTerritory.getXPos()+19, tempTerritory.getYPos()-14, 80, 40);
            this.add(labelTerritoryOwner);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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

        hmIterator = riskMap.getTerritoryMap().entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            Territory tempTerritory = (Territory) mapElement.getValue();

            //draw mini background rectangle for Territory name and number of armies
            g2.setColor(Color.black);
            g2.drawRect(tempTerritory.getXPos(), tempTerritory.getYPos()-14, 90,26);
            g2.setColor(Color.cyan);
            g2.fillRect(tempTerritory.getXPos(), tempTerritory.getYPos()-14, 90,26);
        }
    }
}
