package SmartWatch;

import java.time.Duration;
import java.time.Instant;

public class Stopwatch extends Temporary {

    public Stopwatch() {
        active = false;
        reset();
    }

    public Stopwatch(Duration time) {
        this();
        start = Instant.now().minus(time);
    }

    @Override
    public void run() {
        while (active) {
            end = Instant.now();
        }
    }

    @Override
    public void stop() {
        active = false;
    }

    @Override
    public void resume() {
        active = true;
        run();
    }

    @Override
    public void reset() {
        start = Instant.now();
        end = Instant.MAX;
    }

    @Override
    public void check() {
        checkbox.addLast(Instant.now());
    }
}
