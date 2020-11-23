import java.util.ArrayList;

public class PlayerAI extends Player {
    public PlayerAI(String name, RiskGame game) {
        super(name, game);
    }

    public boolean isAI () {
        return true;
    }

    public Territory getAttackingTerritory() {
        //find the territory with the greatest difference between its army and the army of an attackable territory
        int maxDifference = 0;
        Territory maxTerritory = this.getAttackableTerritories().get(0);
        for (Territory t : this.getAttackableTerritories()) {
            for (Territory enemyTerritory : t.getAdjacentEnemyTerritories()) {
                if (t.getArmies() - enemyTerritory.getArmies() > maxDifference) {
                    maxDifference = t.getArmies() - enemyTerritory.getArmies();
                    maxTerritory = t;
                }
            }
        }
        return maxTerritory;
    }

    //TODO refactor to return a list with above?
    public Territory getTerritoryToAttack(Territory attackingTerritory) {
        int maxDifference = 0;
        Territory minTerritory = attackingTerritory.getAdjacentEnemyTerritories().get(0);
        for (Territory enemyTerritory : attackingTerritory.getAdjacentEnemyTerritories()) {
            if (attackingTerritory.getArmies() - enemyTerritory.getArmies() > maxDifference) {
                maxDifference = attackingTerritory.getArmies() - enemyTerritory.getArmies();
                minTerritory = enemyTerritory;
            }
        }
        return minTerritory;
    }
    public Territory getMovingTerritory() {
        Territory movingTerritory = this.getTerritories().get(0);
        for (Territory t : this.getTerritories()) {
            if (t.getAdjacentEnemyTerritories().isEmpty() && t.getArmies() > 1) {
                if (t.)
            }
        }
        return null;
    }

    //TODO refactor to return a list with above?
    public Territory getTerritoryToFortify(Territory attackingTerritory) {
        int maxDifference = 0;
        Territory minTerritory = attackingTerritory.getAdjacentEnemyTerritories().get(0);
        for (Territory enemyTerritory : attackingTerritory.getAdjacentEnemyTerritories()) {
            if (attackingTerritory.getArmies() - enemyTerritory.getArmies() > maxDifference) {
                maxDifference = attackingTerritory.getArmies() - enemyTerritory.getArmies();
                minTerritory = enemyTerritory;
            }
        }
        return minTerritory;
    }

    private Territory searchForAttackableTerritory(Territory currentTerritory, ArrayList<Territory> visitedTerritories){
        //mark currentTerritory as visited by adding it to visitedTerritories
        visitedTerritories.add(currentTerritory);

        System.out.println("dfs visiting " + currentTerritory.getName());

        //Traverse all the adjacent and unmarked Territories and call the recursive function with index of adjacent Territory.
        for(Territory t: currentTerritory.getAdjacentFriendlyTerritories()){
            if(!visitedTerritories.contains(t)){//if not visited
                searchForAttackableTerritory(t, visitedTerritories);
            }
            if (!t.getAdjacentEnemyTerritories().isEmpty()) {
                return t;
            }
        }
    }
}
