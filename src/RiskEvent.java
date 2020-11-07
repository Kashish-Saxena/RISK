import java.util.EventObject;

public class RiskEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public RiskEvent(Object source) {
        super(source);
    }
}
