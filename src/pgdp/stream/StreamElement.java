package pgdp.stream;

import java.util.List;

public class StreamElement<T> {
    public T element;
    public List<Exception> listOfExceptions;

    private T getElement() {
        return element;
    }

    private List<Exception> getExceptions() {
        return listOfExceptions;
    }

    private boolean hasExceptions() {
        if (getExceptions() != null) return true;
        else return false;
    }

    private <R> StreamElement<R> withExceptionAdded(Exception e) {
        return null;
    }

    private void tryAdapt() {

    }

    private <T> StreamElement<T> of(T t) {
        return null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean equals(Object obj) {
        return false;
    }

    public String toString() {
        return "";
    }


}
