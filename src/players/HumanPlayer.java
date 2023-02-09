package players;

import interfaces.IModel;
import interfaces.IPlayer;
import util.InputUtil;

/**
 * Implementing this player is a basic task.
 * See assignment instructions for what to do.
 * There are also helpful comments in this file (which you can remove).
 *
 * @author <s2029730>
 */
public class HumanPlayer implements IPlayer
{


	// The constructor is called when the player is selected from the game menu.
	public HumanPlayer()
	{
		// You can leave this empty.
	}
	
	// This method is called when a new game is started or loaded.
	public void prepareForGameStart(IModel model, byte playerId)
	{
		// You can leave this empty.
	}
	
	// This method is called to ask the player to take their turn.
	// The move they choose should be returned from this method.
	public int chooseMove()
	{
		// Until you have implemented this player, it will always concede.
		System.out.println("Please enter your move: ");
		int userInput = InputUtil.readIntFromUser();
		return userInput;
		
		// Remove the above return statement, ask the user to enter a move and return that instead.
		// The user should enter the index of the column into which they want to put their next piece.
		// They can also enter -1 if they really do want to concede.
	}
}
