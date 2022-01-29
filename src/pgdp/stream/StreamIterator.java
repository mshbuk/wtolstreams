package pgdp.stream;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.OptionalLong;

public interface StreamIterator<T> {
    boolean hasNext();

    StreamElement<T> next() throws NoSuchElementException;

    OptionalLong getSize();

    static <T> StreamIterator<T> of(Collection<T> collection) {
        return null;
    }

    static <T> StreamIterator<T> of(Iterable<T> iterable) {
        return null;
    }

    static <T> StreamIterator<T> of(T[] t) {
        return null;
    }

    static <T> StreamIterator<T> of(java.util.stream.Stream<T> stream) {
        return new StreamIterator<T>() {
            @Override
            public boolean hasNext() {
                return stream.iterator().hasNext();
            }

            @Override
            public StreamElement<T> next() throws NoSuchElementException {
                return (StreamElement<T>) stream.iterator().next();
            }

            @Override
            public OptionalLong getSize() {
                return OptionalLong.of(stream.count());
            }
        };
    }
}