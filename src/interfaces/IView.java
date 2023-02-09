package interfaces;

import util.GameSettings;

/**
 * This interface defines which methods a view must implement.
 * Do not change this code in any way.
 *
 * @author David Symons
 */
public interface IView
{
	// Methods to display a variety of information.
	public void displayWelcomeMessage();
	public void displayChosenMove(int move);
	public void displayMoveRejectedMessage(int move);
	public void displayActivePlayer(byte playerID);
	public void displayGameStatus(byte gameStatus);
	public void displayBoard(IModel model);
	
	// Methods to request user input.
	public char requestMenuSelection();
	public GameSettings requestGameSettings();
	public IPlayer requestPlayerSelection(byte playerId);
	public String requestSaveFileName();
}
