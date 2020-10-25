import java.util.EventObject;

public class BattleEvent extends EventObject {
    private Player attacker;
    private Player defender;
    private Territory territory;

    public BattleEvent(RiskGame game, Player attacker, Player defender, Territory territory) {
        super(game);
        this.attacker = attacker;
        this.defender = defender;
        this.territory = territory;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public Territory getTerritory() {
        return territory;
    }
}
