package game;

import interfaces.IModel;
import interfaces.IPlayer;
import interfaces.IView;
import players.HumanPlayer;
import players.RoundRobinPlayer;
import util.GameSettings;
import java.io.*;
import java.util.Scanner;
import util.InputUtil;

/**
 * This class represents the state of the game.
 * It has been partially implemented, but needs to be completed by you.
 *
 * @author <s2029730>
 */
public class Model implements IModel
{

	public byte[][] state;
	int mademove = 0;
	int moves = 0;

	int rows;
	int cols;
	int streak;
	int activePlayer;

	private Scanner sc;

	// A reference to the game settings from which you can retrieve the number
	// of rows and columns the board has and how long the win streak is.
	private GameSettings settings;
	
	// The default constructor.
	public Model()
	{
		// You probably won't need this.
	}


	// A constructor that takes another instance of the same type as its parameter.
	// This is called a copy constructor.
	public Model(IModel model)
	{
		// You may (or may not) find this useful for advanced tasks.
	}


	// Called when a new game is started on an empty board.
	public void initNewGame(GameSettings settings)
	{
		mademove =0;
		this.settings = settings;
		state = new byte[settings.nrRows][settings.nrCols];

		// This method still needs to be extended.

	}



	// Called when a game state should be loaded from the given file.
	public void initSavedGame(String fileName)
	{
		openFile(fileName);
		readFile();
		closeFile();

		// This is an advanced feature. If not attempting it, you can ignore this method.
	}

	public void openFile(String fileName){
		try {
			File file = new File("saves/" + fileName );
			sc = new Scanner(file);
		}
		catch (FileNotFoundException e){
			System.out.println("Couldn't find the file");
		}
	}

	public void readFile(){

		rows = Integer.parseInt(sc.next());
		cols = Integer.parseInt(sc.next());
		streak = Integer.parseInt(sc.next());
		activePlayer = Integer.parseInt(sc.next());
		if (activePlayer ==1){
			mademove = 0;
		}
		if (activePlayer ==2 ){
			mademove = 1;
		}
		this.settings = new GameSettings(rows,cols,streak);

		state = new byte[rows][cols];

		for(int i = 0; i < rows;i++){
			char[] thisRow = sc.next().toCharArray();
			for (int j = 0; j< cols; j++ ){
				String element = String.valueOf(thisRow[j]);
				state[i][j] = Byte.parseByte(element);
			}
		}
	}

	public void closeFile(){
		sc.close();
	}
	
	// Returns whether or not the passed in move is valid at this time.
	public boolean isMoveValid(int move)
	{
		// Assuming all moves are valid.
		if ((move<-1) || (move>= settings.nrCols)){
			return false;
		}
		return true;
	}
	
	// Actions the given move if it is valid. Otherwise, does nothing.
	public void makeMove(int move)
	{
		// Not doing anything here yet.
		for(int i = settings.nrRows -1; i>=0;i--){
			moves = move;
			if (move == -1){
				break;
			}
			if (getPieceIn(i,move)==0){
				state[i][move] = getActivePlayer();
				mademove++;
				break;
			}
		}
	}


	public int rowWinningDetection(){

		for (int i = 0; i < settings.nrRows;i++ ){
			for (int j = 0; j <= settings.nrCols - settings.minStreakLength ;j++){
				int rowCount1 = 0;
				int rowCount2 = 0;
				for (int k = 0; k < settings.minStreakLength;k++){
					if (getPieceIn(i,j+k) == 1){
						rowCount1 +=1;
					}
					if (getPieceIn(i,j+k) == 2){
						rowCount2 +=1;
					}
				}
				if (rowCount1 == settings.minStreakLength){
					return 1;
				}
				if (rowCount2 == settings.minStreakLength){
					return 2;
				}

			}
		}
		return 0;
	}


	public int colWinningDetection(){

		for (int i = 0; i < settings.nrCols;i++ ){
			for (int j = 0; j< settings.nrRows - settings.minStreakLength +1;j++){
				int colCount1 = 0;
				int colCount2 = 0;
				for (int k = 0; k < settings.minStreakLength;k++){
					if (getPieceIn(j+k,i) == 1){
						colCount1 +=1;
					}
					if (getPieceIn(j+k,i) == 2){
						colCount2 +=1;
					}
				}
				if (colCount1 == settings.minStreakLength){
					return 1;
				}
				if (colCount2 == settings.minStreakLength){
					return 2;
				}

			}
		}
		return 0;
	}


	public int upperDiagonalWinDetection(){

		for (int i = 0; i < settings.nrRows; i++){
			if (i- settings.minStreakLength +1 >= 0) {

				for (int j = 0; j < settings.nrCols; j++) {
					if (j < settings.nrCols - settings.minStreakLength + 1) {

						int uppDiagonalCount1 = 0;
						int uppDiagonalCount2 = 0;
						for (int k = 0; k < settings.minStreakLength; k++) {
							if (getPieceIn(i - k, j + k) == 1) {
								uppDiagonalCount1 += 1;
							}
							if (getPieceIn(i - k, j + k) == 2) {
								uppDiagonalCount2 += 1;
							}
						}
						if (uppDiagonalCount1 == settings.minStreakLength) {
							return 1;
						}
						if (uppDiagonalCount2 == settings.minStreakLength) {
							return 2;
						}
					}
				}
			}
		}
		return 0;
    }


	public int lowerDiagonalWinDetection(){

		for (int i = 0; i < settings.nrRows; i++){
			if (i- settings.nrRows + settings.minStreakLength <= 0) {

				for (int j = 0; j <= settings.nrCols - settings.minStreakLength; j++) {

						int lowDiagonalCount1 = 0;
						int lowDiagonalCount2 = 0;
						for (int k = 0; k < settings.minStreakLength; k++) {
							if (getPieceIn(i + k, j + k) == 1) {
								lowDiagonalCount1 += 1;
							}
							if (getPieceIn(i + k, j + k) == 2) {
								lowDiagonalCount2 += 1;
							}
						}
						if (lowDiagonalCount1 == settings.minStreakLength) {
							return 1;
						}
						if (lowDiagonalCount2 == settings.minStreakLength) {
							return 2;
						}
				}
			}
		}
		return 0;

	}

	
	// Returns one of the following codes to indicate the game's current status.
	// IModel.java in the "interfaces" package defines constants you can use for this.
	// 0 = Game in progress
	// 1 = Player 1 has won
	// 2 = Player 2 has won
	// 3 = Tie (board is full and there is no winner)

	public byte getGameStatus()
	{
		boolean player1WinCondition = rowWinningDetection()==1 || colWinningDetection() == 1 || upperDiagonalWinDetection() ==1 || lowerDiagonalWinDetection() ==1 ;

		boolean player2WinCondition = rowWinningDetection() == 2 || colWinningDetection() == 2 || upperDiagonalWinDetection() ==2 || lowerDiagonalWinDetection() ==2;


		if (player1WinCondition){

			return IModel.GAME_STATUS_WIN_1;
		}
		if (player2WinCondition){
			return IModel.GAME_STATUS_WIN_2;
		}



		int zeroCount = 0;
		for (int j = 0; j<settings.nrCols; j++){
			int i = 0;
			if (state[i][j]==0) {
				zeroCount += 1; }
			}
		if (zeroCount == 0){
			return IModel.GAME_STATUS_TIE;
		}

		if (getActivePlayer()==1 & (moves == CONCEDE_MOVE)){
			moves = 0;
		    return IModel.GAME_STATUS_WIN_2;

		}

		if (getActivePlayer()==2 & (moves == CONCEDE_MOVE)){
			moves = 0;
			return IModel.GAME_STATUS_WIN_1;
		}


		return IModel.GAME_STATUS_ONGOING;

		// Assuming the game is never ending.

	}


	// Returns the number of the player whose turn it is.
	public byte getActivePlayer()
	{
		// Assuming it is always the turn of player 1.

		if((mademove % 2)==0 ){
			return 1;
		}
		else {
			return 2;
		}
	}
	
	// Returns the owner of the piece in the given row and column on the board.
	// Return 1 or 2 for players 1 and 2 respectively or 0 for empty cells.
	public byte getPieceIn(int row, int column)
	{
		// Assuming all cells are empty for now.
		byte piece = state[row][column];

		return piece;
	}
	
	// Returns a reference to the game settings, from which you can retrieve the
	// number of rows and columns the board has and how long the win streak is.
	public GameSettings getGameSettings()
	{
		return settings;
	}
	
	// =========================================================================
	// ================================ HELPERS ================================
	// =========================================================================
	
	// You may find it useful to define some helper methods here.
	
}
