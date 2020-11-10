/**
 * The RiskView interface contains a signature for the handleRiskUpdate() method which is responsible for changing the
 * map state in the GUI whenever the model changes.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version November 9, 2020
 */
public interface RiskView {
    public void handleRiskUpdate(RiskEvent e);
}
