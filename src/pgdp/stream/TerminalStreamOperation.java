package pgdp.stream;

import java.util.function.Supplier;

public interface TerminalStreamOperation<T, R> extends StreamOperation<T>, Supplier<R> {
}
