package SmartWatch;

import java.time.Duration;
import java.time.Instant;

public class Stopwatch extends Temporary implements Comparable<Stopwatch>{

    public Stopwatch() {
        active = false;
        reset();
    }

    @Override
    public synchronized void run() {
        if (active && start.equals(end)) throw new RuntimeException("The stopwatch is already running");

        start = Instant.now();
        active = true;
        try {
            active.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO:проверить двойную остановку
    //TODO:остановка во время паузы
    @Override
    public synchronized void stop() {
        if (!active) throw new RuntimeException("The stopwatch has already been stopped");
        end = Instant.now();
        active = false;
        active.notifyAll();
    }

    @Override
    public synchronized void suspend() {
        if (active) {
            end = Instant.now();
            active = false;
        }
    }

    @Override
    public synchronized void resume() {
        if (!active) {
            var downtime = Duration.between(end, Instant.now());
            start = start.plus(downtime);
            checkbox.forEach(check -> check.plus(downtime));
            active = true;
        }
    }

    @Override
    public synchronized void reset() {
        start = Instant.now();
        end = start;
        checkbox.clear();
    }

    @Override
    public synchronized Duration elapsedTime() {
        if (active) {
            return Duration.between(start, Instant.now());
        } else {
            return Duration.between(start, end);
        }
    }

    @Override
    public synchronized void check() {
        checkbox.addLast(elapsedTime());
    }

    @Override
    public int compareTo(Stopwatch o) {
        return elapsedTime().compareTo(o.elapsedTime());
    }
}
