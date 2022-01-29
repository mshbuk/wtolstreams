package pgdp.stream;

import java.lang.management.OperatingSystemMXBean;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.OptionalLong;

public interface StreamIterator<T> {
    boolean hasNext();

    StreamElement<T> next();

    OptionalLong getSize();

    static <T> StreamIterator<T> of(Collection<T> collection) {
        return new StreamIterator<T>() {

            @Override
            public boolean hasNext() {
                if(collection.iterator().hasNext() == true){
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public StreamElement<T> next() throws NoSuchElementException {
                StreamElement<T> nextStream = StreamElement.of(collection.iterator().next());
                /*if(StreamElement.of(collection.iterator().next()) == null) { // I tried to make it work
                    throw new NoSuchElementException e;
                }*/
                return nextStream;
            }

            @Override
            public OptionalLong getSize() {
                OptionalLong size = OptionalLong.of(collection.size());
                return size;
            }
        };
    }

    static <T> StreamIterator<T> of(Iterable<T> iterable) {
        return new StreamIterator<T>() {
            @Override
            public boolean hasNext() {
                if(iterable.iterator().hasNext() == true){
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public StreamElement<T> next() throws NoSuchElementException {
                StreamElement<T> nextStream = StreamElement.of(iterable.iterator().next());
                /*if(StreamElement.of(iterable.iterator().next()) == null) { // I tried to make it work
                    throw new NoSuchElementException e;
                }*/
                return nextStream;
            }

            @Override
            public OptionalLong getSize() {
                OptionalLong size = OptionalLong.of(iterable.spliterator().characteristics());
                return size;
            }
        };
    }

    static <T> StreamIterator<T> of(T[] t) {
        StreamIterator<T> streamIterator = StreamIterator.of(Arrays.asList(t));
        return streamIterator;
    }

    static <T> StreamIterator<T> of(java.util.stream.Stream<T> stream) {
        return new StreamIterator<T>() {
            @Override
            public boolean hasNext() {
                if(stream.iterator().next() == null) return false;
                else return true;
            }

            @Override
            public StreamElement<T> next() throws NoSuchElementException {
                return (StreamElement<T>) stream.iterator().next();
            }

            @Override
            public OptionalLong getSize() {
                if(OptionalLong.of(stream.count()) != null) {
                    return OptionalLong.of(stream.count());
                } else {
                    return OptionalLong.empty();
                }
            }
        };
    }
}