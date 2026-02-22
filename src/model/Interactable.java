package model;

/**
 * Interactable 
 * 
 * Purpose: Interactable is an interface that specifies which method all
 * entities that the player can interact with must implement.
 * 
 * @author Manas Paranjape
 */

public interface Interactable {

	void whenInteract(Player player, GameModel model);

}
