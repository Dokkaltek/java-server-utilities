package io.github.dokkaltek.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests the Timer class.
 */
class TimerTest {

    /**
     * Tests the Timer class.
     */
    @Test
    @DisplayName("Tests the Timer class methods")
    void testTimer() {
        Timer timer = new Timer();
        assertDoesNotThrow(() -> {
            timer.start();
            timer.stop();
            timer.reset();
            timer.start();
            timer.suspend();
            timer.resume();
            timer.stop();
        });
    }
}
