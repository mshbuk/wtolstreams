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

    private T getElement() {
        return element;
    }

    private List<Exception> getExceptions() {
        LinkedList<Exception> listExceptions = new LinkedList<>(listOfExceptions);
        return listExceptions;
    }

    private boolean hasExceptions() {
        if (listOfExceptions.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private <R> StreamElement<R> withExceptionAdded(Exception e) {
        List<Exception> lastList = new ArrayList<>();
        StreamElement<R> myStreamElementWithR = new StreamElement<>((R)element,listOfExceptions,State.INCORRECT);
        myStreamElementWithR.listOfExceptions.add(e);
        return myStreamElementWithR;
    }

    public <R> StreamElement<R> tryAdapt() {
        StreamElement<R> newStreamElement = new StreamElement<>((R) element, listOfExceptions);
        return newStreamElement;
    }

    private static <T> StreamElement<T> of(T t) {
        List<Exception> list = new ArrayList<>();
        StreamElement<T> myStreamElementWithT = new StreamElement<>(t);
        return myStreamElementWithT;
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
        return Objects.equals(element, that.element) && Objects.equals(listOfExceptions, that.listOfExceptions);
    }

    @Override
    public String toString() {
        return "StreamElement element: " + element +
                "State of the element: " + state +
                "listOfExceptions: " + listOfExceptions;
    }
}
