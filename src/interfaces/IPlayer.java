package interfaces;

/**
 * This interface defines which methods a player must implement.
 * Do not change this code in any way.
 *
 * @author David Symons
 */
public interface IPlayer
{
	// This method is called when a new game is started or loaded.
	// You can use it to perform any setup that may be required before
	// the player is asked to make a move. The second argument tells
	// you if you are playing as player 1 or player 2.
	public void prepareForGameStart(IModel model, byte playerId);
	
	// This method is called to ask the player to take their turn.
	// The move they choose should be returned from this method.
	public int chooseMove();
}
