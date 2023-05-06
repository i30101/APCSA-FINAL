public class TimeKeeper {
    private static int day = 0;
    
    public TimeKeeper() {
        day = 0;
    }

    public static void nextDay() {
        day++;
    }

    public static int getDay() {
        return day;
    }
}
