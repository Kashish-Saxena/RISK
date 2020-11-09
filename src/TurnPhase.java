/**
 * Representation for all the phases of a player's turn.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version October 25, 2020
 */
public enum TurnPhase {
    ATTACK_CHOOSE_ATTACKERS,
    ATTACK_CHOOSE_ENEMY,
    ATTACK_CHOOSE_DICE,
    DEFEND_CHOOSE_DICE, //this is actually the attacker's state after they choose the dice to attack with, not the defender's state
    ATTACK_RESULT,
    ATTACK_CHOOSE_MOVE,
    END
}
