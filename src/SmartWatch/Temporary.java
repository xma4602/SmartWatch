package SmartWatch;


import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;

public abstract class Temporary implements Runnable, Cloneable, Serializable {

    protected Instant start;
    protected Instant end;
    protected boolean active;
    protected LinkedList<Instant> checkbox = new LinkedList<>();

    public abstract void run();

    public abstract void stop();

    public abstract void resume();

    public abstract void reset();

    public abstract void check();

    public void uncheck() {
        checkbox.removeLast();
    }

    public void uncheck(int index) throws IndexOutOfBoundsException {
        checkbox.remove(index);
    }

    public Collection<Instant> getCheckbox() {
        return checkbox;
    }

    public boolean isElapsed(Duration duration) {
        return Duration.between(start, end).compareTo(duration) > 0;
    }

    public Duration eapsed() {
        return Duration.between(start, end);
    }

    public boolean isActive() {
        return active;
    }
}
