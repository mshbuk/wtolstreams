package pgdp.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StreamElement<T> {
    private final T element;
    private final List<Exception> listOfExceptions;

    private StreamElement(T element, List<Exception> listOfExceptions) {
        this.element = element;
        this.listOfExceptions = listOfExceptions;
    }

    private StreamElement(List<Exception> listOfExceptions) {
        this.listOfExceptions = listOfExceptions;
        this.element = null;
    }

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

    public <R> StreamElement <R> tryAdapt () throws Exception {
        if (!hasExceptions())
            return new StreamElement<>(null, listOfExceptions);
        else throw new Exception();
    }

    private static <T> StreamElement <T> of (T t){
        List<Exception> list = new ArrayList<>();
        return new StreamElement<>(t,list);
    }

    public int hashCode() {
        return Objects.hash(element, listOfExceptions);
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        if(this == obj) {
            return true;
        }
        StreamElement<?> that = (StreamElement<?>) obj;
        return Objects.equals(element, that.element) && Objects.equals(listOfExceptions,that.listOfExceptions);
    }

    @Override
    public String toString() {
        return "StreamElement element:" + element +
                ", listOfExceptions:" + listOfExceptions;
    }
}
