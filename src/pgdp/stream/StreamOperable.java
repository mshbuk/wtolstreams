package pgdp.stream;

public interface StreamOperable<T> {
    StreamOperation<T> getStreamOperation();
}
