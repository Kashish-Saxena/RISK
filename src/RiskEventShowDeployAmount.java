
public class RiskEventShowDeployAmount extends RiskEvent{

    private int totalDeployAmount;
    private int AmountFromTerritories;
    private int AmountFromContinents;

    public RiskEventShowDeployAmount(RiskGame rg, TurnPhase phase, Player currentPlayer, int totalDeployAmount, int AmountFromTerritories, int AmountFromContinents) {
        super(rg, phase, currentPlayer);
        this.totalDeployAmount = totalDeployAmount;
        this.AmountFromTerritories = AmountFromTerritories;
        this.AmountFromContinents = AmountFromContinents;
    }

    public int getTotalDeployAmount(){
        return this.totalDeployAmount;
    }

    public int getTerritoryAmount(){
        return this.AmountFromTerritories;
    }

    public int getContinentAmount(){
        return this.AmountFromContinents;
    }
}
