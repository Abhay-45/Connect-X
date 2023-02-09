package main;

import game.Controller;

/**
 * This class creates a controller to start the game.
 * You should not change this class.
 *
 * @author David Symons
 */
public class ConnectFour
{
	public static void main(String[] args)
	{
		Controller controller = new Controller();
		controller.startSession();
	}
}
