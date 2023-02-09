package players;

import interfaces.IModel;
import interfaces.IPlayer;
import util.GameSettings;

/**
 * Implementing this player is an intermediate task.
 * See assignment instructions for what to do.
 * If not attempting it, just upload the file as it is.
 *
 * @author <s2029730>
 */
public class RoundRobinPlayer implements IPlayer
{
	// A reference to the model, which you can use to get information about
	// the state of the game. Do not use this model to make any moves!
	private IModel model;
	
	// The constructor is called when the player is selected from the game menu.
	public RoundRobinPlayer()
	{
		// You can leave this empty.
	}
	
	// This method is called when a new game is started or loaded.
	// You can use it to perform any setup that may be required before
	// the player is asked to make a move. The second argument tells
	// you if you are playing as player 1 or player 2.
	public void prepareForGameStart(IModel model, byte playerId)
	{
		this.model = model;
		starter = -1;


		// Extend this method if required.
	}


	public boolean emptyColumnChecker(int col) {

		if (!(model.getPieceIn(0,col)==0)){
			return false;
		}
		return true;
	}


	// This method is called to ask the player to take their turn.
	// The move they choose should be returned from this method.
	public int starter = -1;
	public int chooseMove()
	{
		int ret ;
		while (true){

			starter++;
			int totCols = model.getGameSettings().nrCols;

			if (emptyColumnChecker(starter%totCols)){
				ret = starter%totCols;
				break;
			}

		}
		return ret;


		// Until you have implemented this player, it will always concede.

	}
}
