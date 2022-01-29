package pgdp.stream;

public interface StreamOperation<T> {
    void start(StreamCharacteristics sc);
    void acceptElement(StreamElement<T> t);
    void finish();
    boolean needsMoreElements();
}
