public class RiskEventContinent extends RiskEvent{

    private Continent continentTo;

    public RiskEventContinent(RiskGame rg, TurnPhase phase, Player currentPlayer, Continent continentTo) {
        super(rg, phase, currentPlayer);
        this.continentTo = continentTo;
    }

    public Continent getContinentTo() {
        return continentTo;
    }
}
