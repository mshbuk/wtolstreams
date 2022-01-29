package pgdp.stream;

import java.util.NoSuchElementException;
import java.util.OptionalLong;

public interface StreamIterator<T> {
    boolean hasNext ();
    StreamElement <T> next () throws NoSuchElementException;
    OptionalLong getSize ();
}

