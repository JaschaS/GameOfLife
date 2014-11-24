package gameoflife;

import java.awt.Dimension;
import java.awt.Point;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.HashSet;
//nur vorl�ufig drinnen
import java.util.regex.Pattern;

public interface IGameOfLifeGUI extends Remote {
	/*
	 * @return: The id of the user.
	 */
	public String getUserID();

	/*
	 * @return: The id of the game.
	 */
	public String getGameID();

	/*
	 * @return: The dimension of the game field.
	 */
	public Dimension getGameFieldSize();

	/*
	 *  Reg Expressions represented by String??
	 */
	public HashSet<String> getRegExpressions();

	/*
	 *  @return: A set with the user defined patterns. If the boolean is true,
	 *  the cell will live, else the cell will die by this pattern.
	 */
	public HashMap<Pattern, Boolean> getPatterns();

	/*
	 *  String??
	 */
	public String getEdgeOverflow();

	/*
	 *  @return: Returns a set with all points which was set by the user.
	 */
	public HashSet<Point> getStartConfiguration();

}
