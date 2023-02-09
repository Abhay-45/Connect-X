package game;

import interfaces.*;
import players.HumanPlayer;
import util.GameSettings;

/**
 * This class controls the flow of the game.
 * It has been completed for you and must not be changed.
 *
 * @author David Symons
 */
public class Controller
{
	// References to facilitate communication between model and view.
	private final IModel model;
	private final IView view;
	
	// The current game settings.
	private GameSettings settings;
	
	// The current players.
	private IPlayer player1;
	private IPlayer player2;
	
	// The constructor creates model and view and loads default settings and players.
	public Controller()
	{
		// Create model and vew.
		this.model = new Model();
		this.view = new TextView();
		
		// Load default game settings.
		settings = new GameSettings();
		
		// Players are both human by default.
		player1 = new HumanPlayer();
		player2 = new HumanPlayer();
	}
	
	// Starts a session, which can consist of multiple matches.
	public void startSession()
	{
		view.displayWelcomeMessage();
		
		do
		{
			switch(view.requestMenuSelection())
			{
				case '1': startNewGame(); break;
				case '2': resumeSavedGame(); break;
				case '3': changeGameSettings(); break;
				case '4': changePlayers(); break;
				default: return;
			}
		}
		while(true);
	}
	
	// Starts a new game with an empty board.
	private void startNewGame()
	{
		model.initNewGame(settings);
		startMatchLoop();
	}
	
	// Resumes a game loaded from a save file.
	private void resumeSavedGame()
	{
		String fileName = view.requestSaveFileName();
		model.initSavedGame(fileName);
		startMatchLoop();
	}
	
	// Allows the default game settings to be changed by the user.
	private void changeGameSettings()
	{
		settings = view.requestGameSettings();
	}
	
	// Allows the default players to be changed by the user.
	private void changePlayers()
	{
		player1 = view.requestPlayerSelection((byte)1);
		player2 = view.requestPlayerSelection((byte)2);
	}
	
	// Starts a match between the selected players.
	// Players are asked to take turns until the game ends.
	// The state of the board is displayed to represent the moves made.
	private void startMatchLoop()
	{
		// Inform the players that a new game is about to begin.
		player1.prepareForGameStart(model, (byte)1);
		player2.prepareForGameStart(model, (byte)2);
		
		// Display the initial state of the board.
		view.displayBoard(model);
		
		byte gameStatus;
		while((gameStatus = model.getGameStatus()) == IModel.GAME_STATUS_ONGOING)
		{
			// Select the player whose turn it is.
			IPlayer activePlayer = getActivePlayerInstance();
			
			// Say whose turn it is.
			view.displayActivePlayer(model.getActivePlayer());
			
			// Ask the active player to make a (valid) move.
			int chosenMove = askForValidMove(activePlayer);
			
			// State which move the active player chose to make.
			view.displayChosenMove(chosenMove);
			
			// Perform the chosen move.
			model.makeMove(chosenMove);
			
			// Show the updated state of the board.
			view.displayBoard(model);
		}
		
		// The game has ended. Announce the final game status i.e. the outcome.
		view.displayGameStatus(gameStatus);
	}
	
	// Returns the player whose turn it is.
	private IPlayer getActivePlayerInstance()
	{
		if(model.getActivePlayer() == 1)
			return player1;
		return player2;
	}
	
	// Ask the given player to submit a move command.
	// They will be asked repeatedly until they produce a move accepted by the model.
	private int askForValidMove(IPlayer activePlayer)
	{
		int chosenMove;
		boolean moveRejected;
		
		// Ask for a move until a valid move is submitted by the given player.
		do
		{
			// Ask the given/active player to submit a move.
			chosenMove = activePlayer.chooseMove();
			
			// Check the move's validity.
			moveRejected = !model.isMoveValid(chosenMove);
			
			// If rejected, display an error message.
			if(moveRejected)
				view.displayMoveRejectedMessage(chosenMove);
		}
		while(moveRejected);
		
		return chosenMove;
	}
}
