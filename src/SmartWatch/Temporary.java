package SmartWatch;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;

public abstract class Temporary implements Runnable, Cloneable, Serializable {

    protected volatile Instant start;
    protected volatile Instant end;
    protected volatile Boolean active;
    protected volatile LinkedList<Duration> checkbox = new LinkedList<>();

    public abstract void run();

    public abstract void stop();

    public abstract void suspend();

    public abstract void resume();

    public abstract void reset();

    public abstract Duration elapsedTime();

    public abstract void check();

    public synchronized void uncheck() {
        checkbox.removeLast();
    }

    public synchronized void uncheck(int index) throws IndexOutOfBoundsException {
        checkbox.remove(index);
    }

    public synchronized Collection<Duration> getCheckbox() {
        return checkbox;
    }

    public synchronized boolean isElapsed(Duration time){
        return elapsedTime().compareTo(time) > 0;
    }

    public synchronized boolean isActive() {
        return active;
    }

    @Override
    public synchronized Temporary clone() {
        try {
            return (Temporary) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
