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
public class CompetitivePlayer1 implements IPlayer
{
	// A reference to the model, which you can use to get information about
	// the state of the game. Do not use this model to make any moves!
	private IModel model;
	public byte[][] tempBoard;
	int score;
	int zeroCount;
	int AIPlayerCount;
	int otherPlayerCount;
	byte AIPlayer;
	byte otherPlayer;
	int centreCount;


	public void getBoard(){
		for (int i = 0; i < model.getGameSettings().nrRows; i++)
			for (int j = 0; j < model.getGameSettings().nrCols; j++) {
				tempBoard[i][j] = model.getPieceIn(i, j);
			}
	}

	public boolean isValidMove(byte[][] tempBoard, int column){
		return tempBoard[0][column] == 0;
	}

	public boolean notValidLocation(){
		int ret = 0;
		for (int i = 0; i < model.getGameSettings().nrCols; i++){
			if (isValidMove(tempBoard,i)){
				ret +=1;
			}
		}
		return ret ==0 ;
	}

	public int getRow(byte[][] tempBoard, int column){
		int row = -1;
		if(tempBoard[0][column] == 0){
			for (int i = model.getGameSettings().nrRows -1; i >= 0;i--){
				if (tempBoard[i][column]==0){
					row = i;
					break;
				}
			}
		}
		return row;
	}

	public void putPieceIn(byte[][] tempBoard, int row, int col, byte player){
		tempBoard[row][col] = player;
	}

	public byte playerSwap(byte player){
		if (player ==1){
			return  2;
		}
		else{
			return 1;
		}
	}

	public int scoreCount(int AIPlayerCount, int otherPlayerCount, int zeroCount){
		score = 0;

			if (AIPlayerCount == model.getGameSettings().minStreakLength) {
				score += 100;
			}
			if (AIPlayerCount == model.getGameSettings().minStreakLength - 1 && zeroCount == 1) {
				score += 5;
			}
			if (AIPlayerCount == model.getGameSettings().minStreakLength - 2 && zeroCount == 2) {
				score += 2;
			}
			if (otherPlayer == model.getGameSettings().minStreakLength -1 && zeroCount == 1){
				score -= 4;
			}


		return score;
	}

	public int winCount(int AIPlayerCount,int otherPlayerCount){
		if (AIPlayerCount == model.getGameSettings().minStreakLength) {
			return 1 ;
		}
		if (otherPlayerCount == model.getGameSettings().minStreakLength) {
			return 2 ;
		}

		return 0;
	}

	public boolean winningMove(byte[][] tempBoard, byte player){

		if ((winCount(AIPlayerCount,otherPlayerCount) == 1) || (winCount(AIPlayerCount,otherPlayerCount) ==2)){
			return true;
		}

	return false;
	}



	public int centreColCounter(byte[][] tempBoard, byte AIPlayer){
		score = 0;
		if (model.getGameSettings().nrCols%2 ==0){
			centreCount =0;
			for (int i = 0; i < model.getGameSettings().nrRows;i++){
				if (tempBoard[i][model.getGameSettings().nrCols/2 ] == AIPlayer){
					centreCount+=1;
				}
			}
			score = centreCount *3;
		}
		if (!(model.getGameSettings().nrCols%2 ==0)){
			centreCount = 0;
			for (int i = 0; i < model.getGameSettings().nrRows;i++){
				if (tempBoard[i][model.getGameSettings().nrCols/2 +1 ] == AIPlayer){
					centreCount+=1;
				}
			}
			score = centreCount *3;
		}
		return score;
	}

	public int rowScoreCounter(byte[][] tempBoard, byte AIPlayer, byte otherPlayer){
		score = 0;
		for(int i = 0; i < model.getGameSettings().nrRows; i++){
			for( int j = 0 ; j <= model.getGameSettings().nrCols- model.getGameSettings().minStreakLength;j++){
				zeroCount = 0;
				AIPlayerCount = 0;
				otherPlayerCount = 0;
				for (int k = 0; k < model.getGameSettings().minStreakLength; k++){
					if (tempBoard[i][j+k] == AIPlayer){
						AIPlayerCount +=1;
					}
					if (tempBoard[i][j+k] == otherPlayer){
						otherPlayerCount +=1;
					}
					if (tempBoard[i][j+k]== 0){
						zeroCount += 1;
					}
				}
				score += scoreCount(AIPlayerCount,otherPlayerCount,zeroCount);
			}
		}
	return score;
	}

	public int colScoreCount(byte[][] tempBoard, byte AIPlayer, byte otherPlayer){
		score = 0;
		for (int i = 0; i < model.getGameSettings().nrCols;i++ ) {
			for (int j = 0; j < model.getGameSettings().nrRows - model.getGameSettings().minStreakLength + 1; j++) {
				zeroCount = 0;
				AIPlayerCount = 0;
				otherPlayerCount = 0;
				for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {
					if (tempBoard[j + k][i] == AIPlayer) {
						AIPlayerCount += 1;
					}
					if (tempBoard[j + k][i] == 0) {
						zeroCount += 1;
					}
					if (tempBoard[j + k][i] == otherPlayer) {
						otherPlayerCount += 1;
					}
				}
				score += scoreCount(AIPlayerCount, otherPlayerCount, zeroCount);
			}

		}
		return score;
	}

	public int uppDiagonalScoreCount(byte[][] tempBoard, byte AIPlayer, byte otherPlayer){
		score = 0;
		for (int i = 0; i < model.getGameSettings().nrRows; i++){
			if (i- model.getGameSettings().minStreakLength +1 >= 0) {

				for (int j = 0; j < model.getGameSettings().nrCols; j++) {
					if (j < model.getGameSettings().nrCols - model.getGameSettings().minStreakLength + 1) {
						zeroCount = 0;
						AIPlayerCount = 0;
						otherPlayerCount = 0;
						for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {
							if (tempBoard[i-k][j+k] == AIPlayer) {
								AIPlayerCount += 1;
							}
							if (tempBoard[i-k][j+k] == 0) {
								zeroCount += 1;
							}
							if (tempBoard[i-k][j+k] == otherPlayer) {
								otherPlayerCount += 1;
							}

						}
						score += scoreCount(AIPlayerCount, otherPlayerCount, zeroCount);
					}
				}
			}
		}
		return score;
	}

	public int lowDiagonalScoreCount(byte[][] tempBoard, byte AIPlayer, byte otherPlayer){
		score = 0;
		for (int i = 0; i < model.getGameSettings().nrRows; i++){
			if (i- model.getGameSettings().nrRows + model.getGameSettings().minStreakLength <= 0) {

				for (int j = 0; j <= model.getGameSettings().nrCols - model.getGameSettings().minStreakLength; j++) {

					zeroCount = 0;
					AIPlayerCount = 0;
					otherPlayerCount = 0;
					for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {
						if (tempBoard[i+k][j+k] == AIPlayer) {
							 AIPlayerCount += 1;
						}
						if (tempBoard[i+k][j+k] ==  0) {
							zeroCount += 1;
						}
						if (tempBoard[i+k][j+k] == otherPlayer) {
							otherPlayerCount += 1;
						}
					}
					score += scoreCount(AIPlayerCount, otherPlayerCount, zeroCount);
				}
			}
		}
		return score;
	}

	public boolean IsTerminalNode(byte[][] tempBoard){
		boolean condition = winningMove(tempBoard,AIPlayer) || winningMove(tempBoard,otherPlayer) || notValidLocation();

		return condition;
	}

	public int[] miniMax(byte[][] tempBoard,int depth, int alpha, int beta, boolean maximizingPlayer){

		if (depth == 0 || IsTerminalNode(tempBoard)){
			if (IsTerminalNode(tempBoard)){
				if (winningMove(tempBoard,AIPlayer)) {
					int[] ret = {0,1000000};
					return ret;
				}
				else if ( winningMove(tempBoard,otherPlayer)){
					int[] ret = {0,-1000000};
					return ret;
				}
				else {
					int[] ret = {0, 0};
					return ret;
				}
			}
			else{
				int[] ret = {0,scoreCount(AIPlayerCount,otherPlayerCount, zeroCount)};
				return ret;
			}
		}

		if(maximizingPlayer){
			int value = -1000000000;
			int column = -1;

			for (int i = 0; i < model.getGameSettings().nrCols; i++){

				if (isValidMove(tempBoard,i )){
					column = i;
					int row = getRow(tempBoard,column);
					byte[][] boardCopy = new byte[model.getGameSettings().nrRows][model.getGameSettings().nrCols];
					for (int k = 0; k < model.getGameSettings().nrRows;k++){
						for (int j= 0;j< model.getGameSettings().nrCols;j++){
							boardCopy[k][j] = tempBoard[k][j];
						}
					}
					putPieceIn(boardCopy,row,column,AIPlayer);
					int newScore = miniMax(boardCopy, depth - 1, alpha, beta, false)[1];
					if (newScore> value){
						value = newScore;
						column = i;
						alpha = Math.max(alpha,value);
					}
					if(alpha >= beta){
						break;
					}

				}
			}
			int[] ret = {column,value};
			return ret;

		}

		else{
			int value = 1000000000;
			int column = -1;

			for (int i = 0; i < model.getGameSettings().nrCols; i++){

				if (isValidMove(tempBoard,i )){
					column = i;
					int row = getRow(tempBoard,column);
					byte[][] boardCopy = new byte[model.getGameSettings().nrRows][model.getGameSettings().nrCols];
					for (int k = 0; k < model.getGameSettings().nrRows;k++){
						for (int j= 0;j< model.getGameSettings().nrCols;j++){
							boardCopy[k][j] = tempBoard[k][j];
						}
					}
					putPieceIn(boardCopy,row,column,otherPlayer);
					int newScore = miniMax(boardCopy, depth - 1, alpha, beta, true)[1];
					if (newScore< value){
						value = newScore;
						column = i;
					beta = Math.min(alpha,value);
					}
					if(alpha >= beta){
						break;
					}

				}
			}
			int[] ret = {column,value};
			return ret;
		}


	}


	
	// The constructor is called when the player is selected from the game menu.
	public CompetitivePlayer1()
	{
		// You may (or may not) need to perform some initialisation here.
	}
	
	// This method is called when a new game is started or loaded.
	// You can use it to perform any setup that may be required before
	// the player is asked to make a move. The second argument tells
	// you if you are playing as player 1 or player 2.
	public void prepareForGameStart(IModel model, byte playerId)
	{
		AIPlayer = playerId;
		this.model = model;
		 tempBoard = new byte[model.getGameSettings().nrRows][model.getGameSettings().nrCols];

	}
	
	// This method is called to ask the player to take their turn.
	// The move they choose should be returned from this method.
	public int chooseMove()
	{
		getBoard();

		AIPlayer = model.getActivePlayer();
		otherPlayer = playerSwap(AIPlayer);

		centreColCounter(tempBoard,AIPlayer);
		rowScoreCounter(tempBoard,AIPlayer,otherPlayer);
		colScoreCount(tempBoard,AIPlayer,otherPlayer);
		uppDiagonalScoreCount(tempBoard,AIPlayer,otherPlayer);
		lowDiagonalScoreCount(tempBoard,AIPlayer,otherPlayer);


		int ret = miniMax(tempBoard,2, -Integer.MAX_VALUE,Integer.MAX_VALUE, true)[0];




		// Until you have implemented this player, it will always concede.
		return ret;
	}
}
