package pgdp.stream;

import java.util.List;
import java.util.Objects;

public class StreamElement<T> {
    public T element;
    public List<Exception> listOfExceptions;

    private StreamElement(T element){
        this.element = element;
    }

    private StreamElement(){
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

    private void tryAdapt() {

    }

    private <T> StreamElement<T> of(T t) {
        return null;
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
