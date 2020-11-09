import java.util.EventObject;
import java.util.List;

public class RiskEvent extends EventObject {
    private TurnPhase phase;
    private Player currentPlayer;

    /**
     * Constructs a prototypical Event.
     *
     * @param rg The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */

    public RiskEvent(RiskGame rg, TurnPhase phase, Player currentPlayer) {
        super(rg);
        this.phase = phase;
        this.currentPlayer = currentPlayer;
    }

    public TurnPhase getPhase() {
        return phase;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
