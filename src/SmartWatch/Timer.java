package SmartWatch;

import java.time.Duration;
import java.time.Instant;

//TODO:концепция "как таймер считает время?"
public class Timer extends Temporary{

    protected final Duration interval;
    protected volatile Boolean suspended;
    public Timer(Duration time) {
        interval = time;
        active = false;
        suspended = false;
    }

    @Override
    public void run() {
        if (active && !suspended) throw new RuntimeException("The stopwatch is already running");

        start = Instant.now();
        end = start.plus(interval);
        active = true;
        Duration duration;
        do {
            if (suspended) {
                try {
                    suspended.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            duration = Duration.between(start,Instant.now());
        }
        while (active && interval.compareTo(duration) > 0);
    }

    //TODO:что делаем во время прямой остановки и оставновки во время паузы
    @Override
    public void stop() {
        if (suspended){
            var downtime = Duration.between(end, Instant.now());
            start = start.plus(downtime);
            checkbox.forEach(check -> check.plus(downtime));
            suspended = false;

        }
        active = false;
        suspended.notifyAll();
    }

    @Override
    public void suspend() {
        if (active) {
            end = Instant.now();
            active = false;
            suspended = true;
        }
    }

    @Override
    public void resume() {
        if (!active) {
            var downtime = Duration.between(end, Instant.now());
            start = start.plus(downtime);
            checkbox.forEach(check -> check.plus(downtime));
            active = true;
            suspended = false;
            suspended.notifyAll();
        }
    }

    @Override
    public void reset() {
        start = Instant.now();
        end = start.plus(interval);
    }

    //TODO:вероятно неправильно считает
    @Override
    public Duration elapsedTime() {
        if (active) {
            return Duration.between(start, Instant.now());
        } else {
            return Duration.between(start, end);
        }
    }

    @Override
    public void check() {

    }
}
