import java.util.ArrayList;
import java.util.List;

public class PlayerAI extends Player {
    public PlayerAI(String name, RiskGame game) {
        super(name, game);
    }

    public boolean isAI () {
        return true;
    }

    public Territory getDeployTerritory() {
        //find the territory with the greatest difference between its army and the army of an attackable territory
        int maxDifference = 0;
        Territory maxTerritory = this.getTerritories().get(0);
        for (Territory t : this.getTerritories()) {
            for (Territory enemyTerritory : t.getAdjacentEnemyTerritories()) {
                if (enemyTerritory.getArmies() - t.getArmies() > maxDifference) {
                    maxDifference = enemyTerritory.getArmies() - t.getArmies();
                    maxTerritory = t;
                }
            }
        }
        return maxTerritory;
    }

    public int getDeployAmount() {
        return 1;
    }

    public boolean hasFavorableAttacks() {
        if (this.getAttackableTerritories().isEmpty()) {
            return false;
        }
        for (Territory t : this.getAttackableTerritories()) {
            for (Territory e : t.getAdjacentEnemyTerritories()) {
                if (t.getArmies() >= e.getArmies()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Territory getAttackingTerritory() {
        //find the territory with the greatest difference between its army and the army of an attackable territory
        int maxDifference = 0;
        Territory maxTerritory = this.getAttackableTerritories().get(0);
        for (Territory t : this.getAttackableTerritories()) {
            for (Territory enemyTerritory : t.getAdjacentEnemyTerritories()) {
                if (t.getArmies() - enemyTerritory.getArmies() >= maxDifference) {
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

    public static int getAttackDiceNum(Territory attackingTerritory) {
        return Math.max(1, Math.min(attackingTerritory.getArmies() - 1, RiskGame.MAX_ATTACK_DICE));
        //max number of dice possible
    }

    public static int getDefendDiceNum(Territory attackingTerritory) {
        return Math.max(1, Math.min(attackingTerritory.getArmies(), RiskGame.MAX_DEFEND_DICE));
        //max number of dice possible
    }

    public static int getAttackMoveNum(Territory fromTerritory) {
        return fromTerritory.getArmies() - 1;
        //max number of armies possible
    }

    public Territory getMovingTerritory() {
        Territory movingTerritory = this.getTerritories().get(0);
        for (Territory t : this.getTerritories()) {
            if (t.getAdjacentEnemyTerritories().isEmpty() && t.getArmies() > 1) {
                if (searchForConnectedAttackableTerritory(t, new ArrayList<>()) != null) {
                    return t;
                }
            }
        }
        return movingTerritory;
    }

    //TODO refactor to return a list with above?
    public Territory getTerritoryToFortify(Territory movingTerritory) {
        return searchForConnectedAttackableTerritory(movingTerritory, new ArrayList<>());
    }

    public static int getMoveNum(Territory movingTerritory) {
        return movingTerritory.getArmies() - 1; //1 needs to be left on own territory
    }

    private Territory searchForConnectedAttackableTerritory(Territory currentTerritory, List<Territory> visitedTerritories){
        //mark currentTerritory as visited by adding it to visitedTerritories
        visitedTerritories.add(currentTerritory);

        System.out.println("dfs visiting " + currentTerritory.getName());

        //Traverse all the adjacent and unmarked Territories and call the recursive function with index of adjacent Territory.
        for(Territory t: currentTerritory.getAdjacentFriendlyTerritories()){
            if (!t.getAdjacentEnemyTerritories().isEmpty()) {
                return t;
            }
            else if(!visitedTerritories.contains(t)){//if not visited
                return searchForConnectedAttackableTerritory(t, visitedTerritories);
            }
        }
        return null;
    }
}
