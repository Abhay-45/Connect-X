package util;

import java.util.Scanner;

/**
 * This class provides a simple means of getting user input.
 * You should not change this class.
 *
 * @author David Symons
 */
public final class InputUtil
{
	private static final Scanner sc = new Scanner(System.in);
	
	/**
	 * Waits for the user to type an integer on standard input.
	 * The integer they entered is returned when they press enter.
	 * If the input is not an integer, the user is asked to try again.
	 *
	 * @return An integer entered by the user.
	 */
	public static final int readIntFromUser()
	{
		// At least once and until valid input is received:
		do
		{
			// Wait for new input and check if it is an integer.
			if(sc.hasNextInt())
			{
				// Obtain the integer value and return it after
				// consuming the rest of the input line (if any).
				int input = sc.nextInt();
				sc.nextLine();
				return input;
			}
			
			// The input was not an integer, print an error and repeat.
			System.out.print("Please enter an integer: ");
			
			// Consume the rest of the line.
			sc.nextLine();
		}
		while(true);
	}
	
	/**
	 * Waits for the user to type a single character on standard input.
	 * The character they entered is returned when they press enter.
	 * If the input is not a single char, the user is asked to try again.
	 *
	 * @return A single char entered by the user.
	 */
	public static final char readCharFromUser()
	{
		// At least once and until valid input is received:
		do
		{
			// Wait for a new line of text to be entered.
			if(sc.hasNextLine())
			{
				// Obtain the user input.
				String input = sc.nextLine();
				
				// If a single char was entered, return it.
				if(input.length() == 1)
					return input.charAt(0);
			}
			
			// Invalid input length, print an error and repeat.
			System.out.print("Please enter a single character: ");
		}
		while(true);
	}
	
	/**
	 * Waits for the user to type on standard input.
	 * The String they entered is returned when they press enter.
	 *
	 * @return A String entered by the user.
	 */
	public static final String readStringFromUser()
	{
		return sc.nextLine();
	}
}
