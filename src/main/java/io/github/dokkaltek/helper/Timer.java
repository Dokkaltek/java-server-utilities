package io.github.dokkaltek.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Timer to do rudimentary timing of code blocks.
 */
public class Timer {
    private long startTime;
    private long endTime;
    private List<Long> suspendedTimes;
    private Map<Long, Long> suspensionTimes;

    /**
     * Start the timer.
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Stops the timer and returns the elapsed time in milliseconds.
     * @return elapsed time in milliseconds
     */
    public long stop() {
        if (startTime == 0) {
            return 0;
        }
        endTime = System.currentTimeMillis();
        return get();
    }

    /**
     * Returns the elapsed time between start and end.
     * @return elapsed time in milliseconds.
     */
    public long get() {
        return endTime - startTime - calculateSuspendedTime();
    }

    /**
     * Reset the timer.
     */
    public void reset() {
        startTime = 0;
        endTime = 0;
        suspendedTimes = null;
        suspensionTimes = null;
    }

    /**
     * Suspends the timer.
     */
    public void suspend() {
        Long suspensionTime = System.currentTimeMillis();
        if (suspendedTimes == null) {
            suspendedTimes = new ArrayList<>();
        }
        if (suspensionTimes == null) {
            suspensionTimes = new HashMap<>();
        }

        // If the last suspension was not resumed we ignore the suspension
        if (suspendedTimes.size() > 1 && suspensionTimes.get(suspendedTimes.get(suspendedTimes.size() - 1)) == null) {
            return;
        }

        suspendedTimes.add(suspensionTime);

        suspensionTimes.put(suspensionTime, null);
    }

    /**
     * Resumes the timer.
     */
    public void resume() {
        Long suspensionEndTime = System.currentTimeMillis();
        if (suspendedTimes == null) {
            return;
        }

        suspensionTimes.put(suspendedTimes.get(suspendedTimes.size() - 1), suspensionEndTime);
    }

    /**
     * Calculates the total suspended time.
     * @return The total suspended time.
     */
    private long calculateSuspendedTime() {
        long totalSuspension = 0;
        if (suspensionTimes == null || endTime == 0) {
            return 0;
        }
        for (Map.Entry<Long, Long> suspension : suspensionTimes.entrySet()) {
            if (suspension.getValue() != null) {
                totalSuspension += suspension.getValue() - suspension.getKey();
            } else {
                totalSuspension += endTime - suspension.getKey();
            }
        }
        return totalSuspension;
    }
}
