package players;

import interfaces.IModel;
import interfaces.IPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementing this player is an advanced task.
 * See assignment instructions for what to do.
 * If not attempting it, just upload the file as it is.
 *
 * @author <s2157884>
 */
public class CompetitivePlayer implements IPlayer
{
    // A reference to the model, which you can use to get information about
    // the state of the game. Do not use this model to make any moves!
    private IModel model;
    private byte player;
    private byte opponent;
    // The constructor is called when the player is selected from the game menu.
    public CompetitivePlayer()
    {
        // You may (or may not) need to perform some initialisation here.
    }

    // This method is called when a new game is started or loaded.
    // You can use it to perform any setup that may be required before
    // the player is asked to make a move. The second argument tells
    // you if you are playing as player 1 or player 2.
    public void prepareForGameStart(IModel model, byte playerId)
    {
        this.model = model;
        player = playerId;
        //set player and opponent bytes
        if (player == (byte) 1){
            opponent = (byte) 2;
        }
        else{
            opponent = (byte) 1;
        }
    }

    // This method is called to ask the player to take their turn.
    // The move they choose should be returned from this method.
    public int chooseMove()
    {
        int move = 0;
        int[][] temp = tempBoard();
        move = minimax(temp, 8, true, -999999999, +999999999)[0];
        return move;
    }

    public int[] minimax(int[][] temp, int depth, boolean maximizingPlayer, int alpha, int beta)
    {

        if (depth == 0 || isWinner(player, temp) || isWinner(opponent, temp)) {

            if (isWinner(player, temp)) {
                int[] out = {0, 999999990};
                return out;
            } else if (isWinner(opponent, temp)) {
                int[] out = {0, -999999990};
                return out;
            } else if (NrZeroes(temp) == 0) {
                int[] out = {0, 0};
                return out;
            } else {
                int[] out = {0, scorePosition(temp, player)};
                return out;
            }
        }

        Random random = new Random();
        if (maximizingPlayer)
        {
            int scoreVal = -999999990;
            int playCol = random.nextInt(model.getGameSettings().nrCols);
            for (int i = 0; i < model.getGameSettings().nrCols; i++)
            {
                if (model.isMoveValid(i))
                {
                    int[][] tempc = tempCopy(temp);
                    tempMove(tempc, i, player);
                    int score = minimax(tempc, depth-1, false, alpha, beta)[1];
                    if (score > scoreVal)
                    {
                        scoreVal = score;
                        playCol = i;
                    }
                    alpha = Math.max(alpha, scoreVal);
                    if (beta<=alpha){break;}

                }
            }
            int[] out = {playCol,scoreVal};
            return out;
        }
        else
        {
            int scoreVal = 999999990;
            int playCol = random.nextInt(model.getGameSettings().nrCols);
            for (int i = 0; i < model.getGameSettings().nrCols; i++)
            {
                if (model.isMoveValid(i))
                {
                    int[][] tempc = tempCopy(temp);
                    tempMove(tempc, i, opponent);
                    int score = minimax(tempc, depth-1, true, alpha, beta)[1];
                    if (score < scoreVal)
                    {
                        scoreVal = score;
                        playCol = i;
                    }
                    beta = Math.min(beta, scoreVal);
                    if (beta<=alpha){break;}

                }
            }
            int[] out = {playCol,scoreVal};
            return out;
        }

    }

    public int scorePosition(int[][] temp, byte curr_player)
    {
        int nrRows = model.getGameSettings().nrRows;
        int nrCols = model.getGameSettings().nrCols;
        int streak = model.getGameSettings().minStreakLength;
        int score = 0;
        byte[] window = new byte[streak];

        //centre columns
        byte[] centreCol = new byte[nrRows];
        for (int i = 0; i < nrRows; i++)
        {
            centreCol[i] = (byte)temp[i][nrCols/2];
        }

        score += pieceCount(centreCol, curr_player)*10;

        //horizontal points
        for (int i = 0; i < nrRows; i++) {
            for (int j = 0; j < nrCols - streak + 1; j++) {
                for (int k = 0; k < streak; k++) {
                    window[k] = (byte) temp[i][j+k];
                }
                score += scoreWindow(window, curr_player);
            }
        }

        //vertical points
        for (int i = 0; i < nrRows - streak + 1; i++) {
            for (int j = 0; j < nrCols; j++) {
                for (int k = 0; k < streak; k++) {
                    window[k] = (byte) temp[i+k][j];
                }
                score += scoreWindow(window, curr_player);
            }
        }

        //down right diagonal points
        for (int i = 0; i < nrRows - streak + 1; i++) {
            for (int j = 0; j < nrCols - streak + 1; j++) {
                for (int k = 0; k < streak; k++) {
                    window[k] = (byte) temp[i+k][j+k];
                }
                score += scoreWindow(window, curr_player);
            }
        }
        //down left diagonal points
        for (int i = 0; i < nrRows - streak + 1; i++) {
            for (int j =  streak - 1; j < nrCols; j++) {
                for (int k = 0; k < streak; k++) {
                    window[k] = (byte) temp[i+k][j-k];
                }
                score += scoreWindow(window, curr_player);
            }
        }


        return score;
    }

    public int bestMove(byte curr_player)
    {
        Random random = new Random();
        int maxScore = 0;
        int playCol = random.nextInt(model.getGameSettings().nrCols);
        for (int i = 0; i < model.getGameSettings().nrCols; i++)
        {
            if (model.isMoveValid(i))
            {
                int[][] temp = tempBoard();
                tempMove(temp, i, curr_player);
                int score = scorePosition(temp, curr_player);
                if (score > maxScore) {
                    maxScore = score;
                    playCol = i;
                }
            }
        }
        return playCol;
    }

    public int scoreWindow(byte[] window, byte curr_player)
    {
        int score = 0;
        int streak = model.getGameSettings().minStreakLength;
        if (pieceCount(window, curr_player) == streak){score += 1100;}
        else if (pieceCount(window, curr_player) == streak - 1 && pieceCount(window, (byte)0) == 1){score += 35;}
        else if (pieceCount(window, curr_player) == streak - 2 && pieceCount(window, (byte)0) == 2){score += 15;}
        if (pieceCount(window, opponent) == streak){score -= 990;}
        else if (pieceCount(window, opponent) == streak - 1 && pieceCount(window, (byte)0) == 1){score -= 30;}
        else if (pieceCount(window, opponent) == streak - 2 && pieceCount(window, (byte)0) == 2){score -= 10;}
        return score;
    }
    public int pieceCount(byte[] window, byte player)
    {
        int count = 0;
        for (byte piece : window)
        {
            if (piece == player) {count++;}
        }
        return count;
    }

    public int[][] tempBoard()
    {
        int nrRows = model.getGameSettings().nrRows;
        int nrCols = model.getGameSettings().nrCols;
        int[][] temp = new int[nrRows][nrCols];
        for (int i = 0; i < nrRows; i++) {
            for (int j = 0; j < nrCols; j++) {
                temp[i][j] = model.getPieceIn(i,j);
            }

        }
        return temp;
    }
    public int[][] tempCopy(int[][] temp)
    {
        int nrRows = model.getGameSettings().nrRows;
        int nrCols = model.getGameSettings().nrCols;
        int[][] tempc = new int[nrRows][nrCols];
        for (int i = 0; i < nrRows; i++) {
            for (int j = 0; j < nrCols; j++) {
                tempc[i][j] = temp[i][j];
            }

        }
        return tempc;
    }
    public void tempMove(int[][] board, int move, byte player)
    {
        for (int i = model.getGameSettings().nrRows - 1; i >= 0; i--) {
            if (board[i][move] == 0) {
                board[i][move] = player;
                break;
            }
        }
    }
    public boolean isWinner(int player, int[][] board)
    {
        boolean win = true;
        //vertical win
        for (int i = 0; i < model.getGameSettings().nrRows - model.getGameSettings().minStreakLength + 1; i++) {
            for (int j = 0; j < model.getGameSettings().nrCols; j++) {
                int count = 0;
                for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {

                    if (board[i + k][j] == player) {
                        count++;
                    }
                }
                if (count == model.getGameSettings().minStreakLength){
                    return win;
                }
            }
        }

        //horizontal win
        for (int i = 0; i < model.getGameSettings().nrRows; i++) {
            for (int j = 0; j < model.getGameSettings().nrCols - model.getGameSettings().minStreakLength + 1; j++) {
                int count = 0;
                for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {

                    if (board[i][j+k] == player) {
                        count++;
                    }
                }
                if (count == model.getGameSettings().minStreakLength){
                    return win;
                }
            }
        }

        //diagonal down right win
        for (int i = 0; i < model.getGameSettings().nrRows - model.getGameSettings().minStreakLength + 1; i++) {
            for (int j = 0; j < model.getGameSettings().nrCols - model.getGameSettings().minStreakLength + 1; j++) {
                int count = 0;
                for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {

                    if (board[i+k][j+k] == player) {
                        count++;
                    }
                }
                if (count == model.getGameSettings().minStreakLength){
                    return win;
                }
            }
        }

        //diagonal down left win
        for (int i = 0; i < model.getGameSettings().nrRows - model.getGameSettings().minStreakLength + 1; i++) {
            for (int j =  model.getGameSettings().minStreakLength - 1; j < model.getGameSettings().nrCols; j++) {
                int count = 0;
                for (int k = 0; k < model.getGameSettings().minStreakLength; k++) {

                    if (board[i+k][j-k] == player) {
                        count++;
                    }
                }
                if (count == model.getGameSettings().minStreakLength){
                    return win;
                }
            }
        }
        return false;
    }

    public int NrZeroes(int[][] board)
    {
        int c = 0;
        for (int i = 0; i < model.getGameSettings().nrRows; i++) {
            for (int j = 0; j < model.getGameSettings().nrCols; j++) {
                if (board[i][j] == 0){
                    c++;
                }
            }
        }
        return c;
    }


}