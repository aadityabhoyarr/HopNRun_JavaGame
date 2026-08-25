/**
 * Utility methods for the HopNRun game.
 *
 * This class contains reusable helper methods that can be used
 * by different parts of the game in future development.
 */
public class GameUtils {

    /**
     * Checks whether a game object has moved completely
     * outside the left side of the screen.
     *
     * @param x X-coordinate of the object
     * @param width Width of the object
     * @return true if the object is outside the screen
     */
    public static boolean isOutsideLeft(int x, int width) {
        return x + width < 0;
    }

    /**
     * Calculates a safe starting position for an object
     * appearing after the right side of the screen.
     *
     * @param screenWidth Current screen width
     * @param minimumGap Minimum distance from the screen edge
     * @return X-coordinate for the new object
     */
    public static int getSpawnX(int screenWidth, int minimumGap) {
        return screenWidth + minimumGap;
    }

    /**
     * Keeps a value within a specified range.
     *
     * @param value Value to check
     * @param minimum Minimum allowed value
     * @param maximum Maximum allowed value
     * @return Value restricted to the given range
     */
    public static int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(value, maximum));
    }
}