import java.util.List;

public class RiskEventDiceResults extends RiskEventTerritories {

    private List<Integer> attackDice;
    private List<Integer> defendDice;
    private int attackLoss;
    private int defendLoss;
    private Player defender;

    public RiskEventDiceResults(RiskGame rg, TurnPhase phase, Player currentPlayer, Territory territoryFrom, Territory territoryTo, List<Integer> attackDice, List<Integer> defendDice, int attackLoss, int defendLoss, Player defender) {
        super(rg, phase, currentPlayer, territoryFrom, territoryTo);
        this.attackDice = attackDice;
        this.defendDice = defendDice;
        this.attackLoss = attackLoss;
        this.defendLoss = defendLoss;
        this.defender = defender;
    }

    public List<Integer> getAttackDice() {
        return attackDice;
    }

    public List<Integer> getDefendDice() {
        return defendDice;
    }

    public int getAttackLoss() {
        return attackLoss;
    }

    public int getDefendLoss() {
        return defendLoss;
    }

    public Player getDefender() {
        return defender;
    }
}
