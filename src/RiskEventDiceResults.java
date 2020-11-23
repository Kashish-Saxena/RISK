import java.util.List;

/**
 * The RiskEventDiceResults class extends the RiskEventTerritories class and gets the updated dice results from
 * the model (from the battle() method) that updates the RiskFrame GUI state.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 23, 2020
 */

public class RiskEventDiceResults extends RiskEventTerritories {

    private List<Integer> attackDice;
    private List<Integer> defendDice;
    private int attackLoss;
    private int defendLoss;
    private Player defender;

    /**
     * Constructor of the RiskEventDiceResults class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param territoryFrom The Territory to attack from.
     * @param territoryTo The territory to attack.
     * @param attackDice The list of attackers dice numbers.
     * @param defendDice The list of defenders dice numbers.
     * @param attackLoss Number of territories the attacking territory lost.
     * @param defendLoss Number of territories the defending territory lost.
     * @param defender The defending player.
     */
    public RiskEventDiceResults(RiskGame rg, TurnPhase phase, Player currentPlayer, Territory territoryFrom, Territory territoryTo, List<Integer> attackDice, List<Integer> defendDice, int attackLoss, int defendLoss, Player defender) {
        super(rg, phase, currentPlayer, territoryFrom, territoryTo);
        this.attackDice = attackDice;
        this.defendDice = defendDice;
        this.attackLoss = attackLoss;
        this.defendLoss = defendLoss;
        this.defender = defender;
    }

    /**
     * Returns the List of attackers dice numbers.
     * @return the List of attackers dice numbers.
     */
    public List<Integer> getAttackDice() {
        return attackDice;
    }

    /**
     * Returns the List of defenders dice numbers.
     * @return the List of defenders dice numbers.
     */
    public List<Integer> getDefendDice() {
        return defendDice;
    }

    /**
     * Return the number of armies the attacking territory lost.
     * @return the number of armies the attacking territory lost.
     */
    public int getAttackLoss() {
        return attackLoss;
    }

    /**
     * Return the number of armies the defending territory lost.
     * @return the number of armies the defending territory lost.
     */
    public int getDefendLoss() {
        return defendLoss;
    }

    /**
     * Returns the defending player.
     * @return the defending player.
     */
    public Player getDefender() {
        return defender;
    }
}
