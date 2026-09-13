package main.service;

public class ScheduleService {
    private final int MONDAY = 1;
    private final int TUESDAY = 1 << 1;
    private final int WEDNESDAY = 1 << 2;
    private final int THURSDAY = 1 << 3;
    private final int FRIDAY = 1 << 4;
    private final int SATURDAY = 1 << 5;
    private final int SUNDAY = 1 << 6;

    private final int[] departuresByDay = {4, 7, 3, 8, 12, 6, 2};

    public int createMask(boolean monday, boolean tuesday, boolean wednesday,
                          boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        boolean[] days = {
                monday, tuesday, wednesday, thursday,
                friday, saturday, sunday
        };

        int mask = 0;

        for (int i = 0; i < days.length; i++) {
            if (days[i]) {
                mask |= 1 << i;
            }
        }

        return mask;
    }

    public boolean runsOnFriday(int mask) {
        return (mask & FRIDAY) != 0;
    }

    public int getDepartures(int day) {
        try {
            return departuresByDay[day];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return 0;
        }
    }
}
