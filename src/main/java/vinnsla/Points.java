package vinnsla;

public class Points {
    private static int points = 0;
    private static int totalPoints = 0;

    public static void setPoints(int i) {
        points = i;
    }

    public static void addPoints(int i) {
        points += i;
    }

    public static void resetPoints() {
        totalPoints += points;
        points = 0;
    }

    public static void resetAll() {
        totalPoints = 0;
        points = 0;
    }

    public static int getPoints() {
        return points;
    }
}
