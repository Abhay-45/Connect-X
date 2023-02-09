package interfaces;

import util.GameSettings;

/**
 * This interface defines which methods a model must implement.
 * Do not change this code in any way.
 *
 * @author David Symons
 */
public interface IModel
{
	// This constant is used to represent a player conceding.
	public static final byte CONCEDE_MOVE = -1;
	
	// Constants for the game status.
	public static final byte GAME_STATUS_ONGOING = 0;
	public static final byte GAME_STATUS_WIN_1 = 1;
	public static final byte GAME_STATUS_WIN_2 = 2;
	public static final byte GAME_STATUS_TIE = 3;
	
	// Constants for the minimum and maximum dimensions of the board.
	public static final int MIN_ROWS = 3;
	public static final int MAX_ROWS = 10;
	public static final int MIN_COLS = 3;
	public static final int MAX_COLS = 10;
	
	// Called when a new game is started on an empty board.
	public void initNewGame(GameSettings settings);
	
	// Called when a game state should be loaded from the given file.
	public void initSavedGame(String fileName);
	
	// Returns whether or not the passed in move is valid at this time.
	public boolean isMoveValid(int move);
	
	// Actions the given move if it is valid. Otherwise, does nothing.
	public void makeMove(int move);
	
	// Returns a status indicating if this game is ongoing or has ended.
	// If it has ended, the returned code indicates the outcome.
	// See above game status constants.
	public byte getGameStatus();
	
	// Returns the number of the player whose turn it is.
	public byte getActivePlayer();
	
	// Returns the owner of the piece in the given row and column on the board.
	// Return 1 or 2 for players 1 and 2 respectively or 0 for empty cells.
	public byte getPieceIn(int row, int column);
	
	// Returns a reference to the game settings, from which you can retrieve the
	// number of rows and columns the board has and how long the win streak is.
	public GameSettings getGameSettings();
}
