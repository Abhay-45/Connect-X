package players;

import interfaces.IModel;
import interfaces.IPlayer;

/**
 * Implementing this player is an advanced task.
 * See assignment instructions for what to do.
 * If not attempting it, just upload the file as it is.
 *
 * @author <s2029730>
 */
public class WinDetectingPlayer implements IPlayer
{
	// A reference to the model, which you can use to get information about
	// the state of the game. Do not use this model to make any moves!
	private IModel model;


	public byte tempBoard [][];

	// The constructor is called when the player is selected from the game menu.
	public WinDetectingPlayer()
	{
		// You may (or may not) need to perform some initialisation here.
	}

	public void accessBoard()
	{
		for(int i = 0; i < model.getGameSettings().nrRows;i++){
			for (int j = 0; j < model.getGameSettings().nrCols;j++){
				tempBoard[i][j] = model.getPieceIn(i,j);

			}
		}
	}
	public byte playerSwap(byte player){
		if (player ==1){
			return  2;
		}
		else{
			return 1;
		}
	}

	public int placePiece(int column, byte player){
		int row = -1;
		if(tempBoard[0][column] == 0){
			for (int i = model.getGameSettings().nrRows -1; i >= 0;i--){
				if (tempBoard[i][column]==0){
					row = i;
					tempBoard[i][column] = player;
					break;
				}
			}
		}
		return row;
	}



	public int rowWinningCatcher(byte player){

		for (int i = 0; i < model.getGameSettings().nrRows;i++ ){
			for (int j = 0; j <= model.getGameSettings().nrCols - model.getGameSettings().minStreakLength ;j++){
				int rowCount1 = 0;

				for (int k = 0; k < model.getGameSettings().minStreakLength;k++){
					if (tempBoard[i][j+k] == player){
						rowCount1 +=1;
					}
				}
				if (rowCount1 == model.getGameSettings().minStreakLength){
					return 1;
				}

			}
		}
		return 0;
	}

	public int colWinningCatcher(byte player){

		for (int i = 0; i < model.getGameSettings().nrCols;i++ ){
			for (int j = 0; j< model.getGameSettings().nrRows - model.getGameSettings().minStreakLength +1;j++){
				int colCount1 = 0;
				for (int k = 0; k < model.getGameSettings().minStreakLength;k++){
					if (tempBoard[j+k][i] == player){
						colCount1 +=1;
					}
				}
				if (colCount1 == model.getGameSettings().minStreakLength){
					return 1;
				}
			}
		}
		return 0;
	}

	public int upperDiagonalWinCatcher(byte player){

		for (int i = 0; i < model.getGameSettings().nrRows; i++){
			if (i- model.getGameSettings().minStreakLength +1 >= 0) {

				for (int j = 0; j < model.getGameSettings().nrCols; j++) {
					if (j < model.getGameSettings().nrCols - model.getGameSettings().minStreakLength + 1) {
						int uppDiagonalCount1 = 0;
						for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {
							if (tempBoard[i-k][j+k] == player) {
								uppDiagonalCount1 += 1;
							}
						}
						if (uppDiagonalCount1 == model.getGameSettings().minStreakLength) {
							return 1;
						}
					}
				}
			}
		}
		return 0;
	}

	public int lowerDiagonalWinCatcher(byte player){

		for (int i = 0; i < model.getGameSettings().nrRows; i++){
			if (i- model.getGameSettings().nrRows + model.getGameSettings().minStreakLength <= 0) {

				for (int j = 0; j <= model.getGameSettings().nrCols - model.getGameSettings().minStreakLength; j++) {

					int lowDiagonalCount1 = 0;
					for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {
						if (tempBoard[i+k][j+k] ==  player) {
							lowDiagonalCount1 += 1;
						}
					}
					if (lowDiagonalCount1 == model.getGameSettings().minStreakLength) {
						return 1;
					}
				}
			}
		}
		return 0;

	}


	
	// This method is called when a new game is started or loaded.
	// You can use it to perform any setup that may be required before
	// the player is asked to make a move. The second argument tells
	// you if you are playing as player 1 or player 2.
	byte WinDetectingPlayerId;
	public void prepareForGameStart(IModel model, byte playerId)
	{
		this.model = model;
		tempBoard = new byte[model.getGameSettings().nrRows][model.getGameSettings().nrCols];




	}


	
	// This method is called to ask the player to take their turn.
	// The move they choose should be returned from this method.
	public int chooseMove()
	{

		accessBoard();


		int winMove = -1;
		boolean winStatus = false;
		byte WinDetectingPlayerId = model.getActivePlayer();
		byte otherPlayer = playerSwap(model.getActivePlayer());


		for(int i = 0; i< model.getGameSettings().nrCols;i++){
			int winRow = placePiece(i,WinDetectingPlayerId);

			boolean winConditionBot = rowWinningCatcher(WinDetectingPlayerId) ==1 || colWinningCatcher(WinDetectingPlayerId) == 1 ||upperDiagonalWinCatcher(WinDetectingPlayerId) == 1 || lowerDiagonalWinCatcher(WinDetectingPlayerId) ==1 ;

			if (winConditionBot){
				winStatus = true;
				winMove = i;
				break;
			}
			if (winRow != -1){
				tempBoard[winRow][i] = 0;
			}
		}

		if (!winStatus){
			for (int i = 0; i < model.getGameSettings().nrCols;i++) {

				boolean youWin = false;
				int winRow = placePiece(i, WinDetectingPlayerId);

				for (int j = 0; j < model.getGameSettings().nrCols; j++) {
					int thisRow = placePiece(j, otherPlayer);

					boolean oppWinConditionTrue = rowWinningCatcher(otherPlayer) == 1 || colWinningCatcher(otherPlayer) == 1 || upperDiagonalWinCatcher(otherPlayer) == 1 || lowerDiagonalWinCatcher(otherPlayer) == 1 ;

					if (oppWinConditionTrue) {

						youWin = true;
					}
					if (thisRow != -1) {
						tempBoard[thisRow][j] = 0;
					}
				}
				if (!youWin && winRow != -1) {

					winMove = i;
					break;
				}
				if (winRow != -1) {
					tempBoard[winRow][i] = 0;
				}
			}
		}



		return winMove;



	}


}
