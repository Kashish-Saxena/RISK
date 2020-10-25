import java.util.EventObject;

/**
 * BattleEvent class is Event class of the battle in the game.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version October 25, 2020
 */
public class BattleEvent extends EventObject {
    private Player attacker;
    private Player defender;
    private Territory territory;

    /**
     * Constructor of the BattleEvent class. It initializes all the field values to the input values.
     */
    public BattleEvent(RiskGame game, Player attacker, Player defender, Territory territory) {
        super(game);
        this.attacker = attacker;
        this.defender = defender;
        this.territory = territory;
    }

    /**
     * Returns the attacking player of the battle.
     * @return the attacking player.
     */
    public Player getAttacker() {
        return attacker;
    }

    /**
     * Returns the defending player of the battle.
     * @return the defending player.
     */
    public Player getDefender() {
        return defender;
    }

    /**
     * Returns the territory to be defended.
     * @return the defending territory.
     */
    public Territory getTerritory() {
        return territory;
    }
}
