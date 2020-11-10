import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

/** 
 * RiskMapPanel is a class that extends a JPanel and also implements the RiskView interface and is essentially the main JPanel 
 * which contains the actual game board GUI. More specifically it contains all the Territory JButtons of the board GUI and all
 * the code to display everything in a visually appealing manner to provide the best user experience. By implementing RiskView, 
 * RiskMapPanel also has a handleRiskUpdate() method that handles RiskEvents and changes to the model and reflects any of those 
 * changes to the state in the various GUI JComponents
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704
 * @version November 9, 2020
 */
public class RiskMapPanel extends JPanel implements RiskView {
    private RiskMap riskMap;
    private BufferedImage image;
    private List<JButton> territoryButtons;
    private List<JLabel> territoryOwnerLabels;
    private List<JLabel> continentOwnerLabels;

    /**
     * Constructor of the RiskMap class. It initializes all the field values and then draws a circle at a Territory's X
     * and Y coordinates along with the Territory's name.
     * @param riskMap The Risk Map containing territories and continents.
     * @param rg The Risk Game class.
     */
    public RiskMapPanel(RiskMap riskMap, RiskGame rg) {
        this.riskMap = riskMap;
        territoryButtons = new ArrayList<>();
        territoryOwnerLabels = new ArrayList<>();
        continentOwnerLabels = new ArrayList<>();
        RiskMapController rmc = new RiskMapController(rg);

        //then for each Territory in territoryMap hash map, draw a circle at the Territory's x,y coordinates and draw the Territory name
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
            buttonTerritory.addActionListener(rmc);
            buttonTerritory.setActionCommand(tempTerritory.getName());
            territoryButtons.add(buttonTerritory);
            this.add(buttonTerritory);

            //draw Territory name and owner
            //TODO: refactor into a multiline text box
            JLabel labelTerritoryName = new JLabel(tempTerritory.getName());
            labelTerritoryName.setBounds(tempTerritory.getXPos()+19, tempTerritory.getYPos()-28, 80, 40);
            this.add(labelTerritoryName);

            JLabel labelTerritoryOwner = new JLabel(tempTerritory.getOwner().getName());
            labelTerritoryOwner.setBounds(tempTerritory.getXPos()+19, tempTerritory.getYPos()-14, 80, 40);
            labelTerritoryOwner.setName(tempTerritory.getName());
            territoryOwnerLabels.add(labelTerritoryOwner);
            this.add(labelTerritoryOwner);
        }

        //also add continent name and owner JLabels to continentOwnerLabels
        for(Continent tempContinent: RiskMap.getContinentsArrayList()) {

            //add continent name JLabel
            JLabel labelContinentName = new JLabel(tempContinent.toString());
            labelContinentName.setBounds(tempContinent.getXPos()+9, tempContinent.getYPos()-28, 80, 40);
            this.add(labelContinentName);

            //then add owner (just get the owner of the first territory in continent and if check if he owns the continent)
            Player ownerOfFirst = tempContinent.getTerritories().get(0).getOwner();

            JLabel labelContinentOwner = new JLabel(tempContinent.toString());
            labelContinentOwner.setBounds(tempContinent.getXPos()+9, tempContinent.getYPos()-14, 80, 40);
            labelContinentOwner.setName(tempContinent.toString());


            if(ownerOfFirst.getContinents().contains(tempContinent)){
                labelContinentOwner.setText(ownerOfFirst.getName());
            }
            else{
                labelContinentOwner.setText("unowned");
            }

            continentOwnerLabels.add(labelContinentOwner);
            this.add(labelContinentOwner);
        }
    }

    /**
     *  Draws the Map on the Panel including the background image, territory connections, continents and
     *  ownership etc.
     * @param g the Graphics object to protect.
     */
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

        //draw each of the "connections" between Territories
        //todo, right now this approach actually draws each connection twice, is there a better way???
        g2.setColor(Color.black);
        Iterator hmIterator = riskMap.getTerritoryMap().entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            Territory tempTerritory = (Territory) mapElement.getValue();

            //for each of the adjacent Territories of tempTerritory, draw the connection
            for(Territory t: tempTerritory.getAdjacentTerritories()){
                //special case, don't draw the connection between Alaska and Kamchatka
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

        //draw continent name and ownership boxes
        for(Continent tempContinent: RiskMap.getContinentsArrayList()){
            //draw mini background rectangle for Territory name and number of armies
            g2.setColor(Color.black);
            g2.drawRect(tempContinent.getXPos(), tempContinent.getYPos()-14, 90,26);
            g2.setColor(tempContinent.getColor());
            g2.fillRect(tempContinent.getXPos(), tempContinent.getYPos()-14, 90,26);
        }
    }

    /**
     * Handles the update of the game depending on the event that occured.
     * @param e A riskEvent that occurred.
     */
    @Override
    public void handleRiskUpdate(RiskEvent e) {
        for (JButton buttonTerritory : territoryButtons) {
            if (e instanceof RiskEventTerritories) {
                RiskEventTerritories territoryEvent = (RiskEventTerritories)e;

                if (buttonTerritory.getActionCommand().equals(territoryEvent.getTerritoryFrom().getName())) {
                    buttonTerritory.setText("" + territoryEvent.getTerritoryFrom().getArmies());
                }
                else if (buttonTerritory.getActionCommand().equals(territoryEvent.getTerritoryTo().getName())) {
                    buttonTerritory.setText("" + territoryEvent.getTerritoryTo().getArmies());
                }
            }
            else if (e instanceof RiskEventChooseTerritory) {
                RiskEventChooseTerritory chooseTerritoryEvent = (RiskEventChooseTerritory)e;
                if (chooseTerritoryEvent.getEnabledTerritories().contains(RiskMap.getTerritoryFromString(buttonTerritory.getActionCommand()))) {
                    buttonTerritory.setEnabled(true);
                }
                else {
                    buttonTerritory.setEnabled(false);
                }
            }
            else if (e instanceof RiskEventEnd) {
                buttonTerritory.setEnabled(false);
            }
        }

        if (e instanceof RiskEventTerritories) {
            RiskEventTerritories territoryEvent = (RiskEventTerritories)e;
            for (JLabel labelOwner : territoryOwnerLabels) {
                if (labelOwner.getName().equals(territoryEvent.getTerritoryTo().getName()) && !labelOwner.getName().equals(territoryEvent.getTerritoryTo().getOwner().getName())) {
                    labelOwner.setText(territoryEvent.getTerritoryTo().getOwner().getName());
                }
            }
        }

        if(e instanceof RiskEventContinent) {
            Continent c = ((RiskEventContinent) e).getContinentTo();

            //update the owner label of the continent that just got attacked
            for (JLabel labelOwner : continentOwnerLabels) {
                if(c.toString().equals( labelOwner.getName())){
                    Player ownerOfFirst = c.getTerritories().get(0).getOwner();
                    if(ownerOfFirst.getContinents().contains(c)){
                        labelOwner.setText(ownerOfFirst.getName());
                    }
                    else{
                        labelOwner.setText("unowned");
                    }
                }
            }
        }
    }
}
