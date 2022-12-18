package SmartWatch;

import java.time.Duration;
import java.time.Instant;

public class Timer extends Temporary{

    public Timer(Duration time) {
        active = false;
        end = Instant.now().plus(time);
    }

    @Override
    public void run() {
        active = true;
        start = Instant.now();

        var interval = Duration.between(start,end);
        var now = Duration.between(start,Instant.now());

        while (active && interval.minus(now).isPositive()) {
            now = Duration.between(start,Instant.now());
        }
    }

    @Override
    public void stop() {
        active = false;
        start = Instant.now();
    }

    @Override
    public void resume() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void check() {

    }
}
