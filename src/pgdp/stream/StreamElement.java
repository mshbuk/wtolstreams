package pgdp.stream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class StreamElement<T> {
    private final T element;
    private final List<Exception> listOfExceptions;
    private final State state;

    private StreamElement(T element, List<Exception> listOfExceptions, State state) {
        this.state = state;
        this.listOfExceptions = new LinkedList<>(listOfExceptions);
        this.element = element;
    }

    private StreamElement(T element, List<Exception> listOfExceptions) {
        state = State.REGULAR;
        this.element = element;
        this.listOfExceptions = new LinkedList<>(listOfExceptions);

    }

    private StreamElement(T t) {
        state = State.REGULAR;
        this.listOfExceptions = new LinkedList<>();
        this.element = t;
    }

    public T getElement() {
        return element;
    }

    public List<Exception> getExceptions() {
        LinkedList<Exception> listExceptions = new LinkedList<>(listOfExceptions);
        return listExceptions;
    }

    public boolean hasExceptions() {
        if (listOfExceptions.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public <R> StreamElement<R> withExceptionAdded(Exception e) {
        List<Exception> lastList = new ArrayList<>();
        lastList.add(e);
        return new StreamElement<>(null, lastList);
    }

    public <R> StreamElement<R> tryAdapt() {
        StreamElement<R> newStreamElement = new StreamElement<>((R) element, listOfExceptions);
        return newStreamElement;
    }

    public static <T> StreamElement<T> of(T t) {
        List<Exception> list = new ArrayList<>();
        return new StreamElement<>(t, list);
    }

    public int hashCode() {
        return Objects.hash(element, listOfExceptions, state);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        StreamElement<?> that = (StreamElement<?>) obj;
        return Objects.equals(element, that.element) && Objects.equals(listOfExceptions, that.listOfExceptions)&& state==that.state;
    }

    @Override
    public String toString() {
        return "StreamElement element: " + element +
                "State of the element: " + state +
                "listOfExceptions: " + listOfExceptions;
    }
}
