/**
 * The PlayerFactory class is responsible for creating instances of different player types.
 * It provides a method to build a player based on the specified player type.
 * Supported player types include "human," "whatever," "clever," and "genius."
 *
 * @author Yotam
 */
public class PlayerFactory {

    /**
     * Builds a player based on the specified player type.
     *
     * @param playerType The type of player to be created ("human," "whatever," "clever," or "genius").
     * @return A player instance of the specified type, or null if the type is not recognized.
     */
    Player buildPlayer(String playerType){
        Player player = null;
        switch (playerType){
            case "human":
                player = new HumanPlayer();
                break;
            case "whatever":
                player = new WhateverPlayer();
                break;
            case "clever":
                player = new CleverPlayer();
                break;
            case "genius":
                player = new GeniusPlayer();
                break;
        }
        return player;
    }
}
