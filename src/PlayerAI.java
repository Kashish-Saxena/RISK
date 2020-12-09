import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerAI extends Player implements Serializable {
    
    /**
     * Constructor of the PlayerAI class. It has no further purpose than the constructor for Player.
     */
    public PlayerAI(String name, RiskGame game) {
        super(name, game);
    }

    /**
     * Returns if this Player is a PlayerAI
     * @return true.
     */
    public boolean isAI () {
        return true;
    }

    /**
     * Returns the Territory to deploy to (adjacent to an enemy Territory and has the greatest disparity between armies).
     * @return the Territory to deploy armies to.
     */
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

    /**
     * Returns the number of troops to deploy to a territory.
     * @return the number of troops to deploy to a territory.
     */
    public int getDeployAmount() {
        return 1;
    }

    /**
     * Returns whether the PlayerAI has any Territories that are adjacent to any enemy Territories and has as many or more armies as the adjacent enemy Territory.
     * @return whether the PlayerAI should attack.
     */
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

    /**
     * Returns the Territory to attack with (adjacent to an enemy Territory and has the greatest disparity between armies).
     * @return the Territory to attack with.
     */
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

    /**
     * Returns the Territory to attack against (the enemy Territory adjacent to attackingTerritory that has the lowest number of armies).
     * @param attackingTerritory The Territory the PlayerAI is attacking with.
     * @return the Territory to attack against.
     */
    public Territory getTerritoryToAttack(Territory attackingTerritory) {
        int maxDifference = 0;
        Territory minTerritory = attackingTerritory.getAdjacentEnemyTerritories().get(0);
        for (Territory enemyTerritory : attackingTerritory.getAdjacentEnemyTerritories()) {
            if (attackingTerritory.getArmies() - enemyTerritory.getArmies() >= maxDifference) {
                maxDifference = attackingTerritory.getArmies() - enemyTerritory.getArmies();
                minTerritory = enemyTerritory;
            }
        }
        return minTerritory;
    }

    /**
     * Returns the greatest number of dice possible to attack with.
     * @param attackingTerritory The Territory the PlayerAI is attacking with.
     * @return the number of dice to attack with.
     */
    public static int getAttackDiceNum(Territory attackingTerritory) {
        return Math.max(1, Math.min(attackingTerritory.getArmies() - 1, RiskGame.MAX_ATTACK_DICE));
        //max number of dice possible
    }

    /**
     * Returns the greatest number of dice possible to defend with.
     * @param defendingTerritory The Territory the PlayerAI is defending with.
     * @return the number of dice to defend with.
     */
    public static int getDefendDiceNum(Territory attackingTerritory) {
        return Math.max(1, Math.min(attackingTerritory.getArmies(), RiskGame.MAX_DEFEND_DICE));
        //max number of dice possible
    }

    /**
     * Returns the Territory to move armies from (a Territory that has a path to a Territory that is adjacent to enemy Territories).
     * @return the Territory to move armies from.
     */
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

    /**
     * Returns the Territory to move armies to (a Territory with adjacent enemy Territories that has a path to movingTerritory).
     * @param movingTerritory the Territory armies are being moved from.
     * @return the Territory to move armies from.
     */
    public Territory getTerritoryToFortify(Territory movingTerritory) {
        return searchForConnectedAttackableTerritory(movingTerritory, new ArrayList<>());
    }

    /**
     * Returns the greatest number of armies possible to move.
     * @param fromTerritory The Territory the PlayerAI is moving from.
     * @return the number of armies to move.
     */
    public static int getMoveNum(Territory movingTerritory) {
        return movingTerritory.getArmies() - 1; //1 needs to be left on own territory
    }

    /**
     * Recursively depth-first searches for a Territory with adjacent enemy Territories and has a path to currentTerritory.
     * @param currentTerritory The Territory the PlayerAI is initially moving from.
     * @param visitedTerritories a List of Territories the search has already visited
     * @return the Territory that has a path to the original currentTerritory and has adjacent enemy Territories.
     */
    private Territory searchForConnectedAttackableTerritory(Territory currentTerritory, List<Territory> visitedTerritories){
        //mark currentTerritory as visited by adding it to visitedTerritories
        visitedTerritories.add(currentTerritory);

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
