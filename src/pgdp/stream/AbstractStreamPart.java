package pgdp.stream;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractStreamPart<IN, OUT> implements StreamOperable<IN>, Stream<OUT>  {
    private final StreamIterator<OUT> iterator;
    private StreamOperable<OUT> next;

    public AbstractStreamPart(StreamIterator<OUT> iterator) {
        this.iterator = iterator;
    }

    public void setNext(StreamOperable<OUT> next){
        if(this.next == null){
            this.next = next;
        } else {
            throw new IllegalStateException("it already exists");
        }
    }

    public abstract SourcePart<?> getSource();

    @Override
    public <R> Stream<R> map(Function<? super OUT, ? extends R> mapper) {
        return null;
    }

    @Override
    public Stream<OUT> filter(Predicate<? super OUT> filter) {
        return null;
    }

    @Override
    public <R> Stream<R> mapChecked(ThrowingFunction<? super OUT, ? extends R> mapper) {
        return null;
    }

    @Override
    public Stream<OUT> filterChecked(ThrowingPredicate<? super OUT> filter) {
        return null;
    }

    @Override
    public Stream<OUT> distinct() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Optional<OUT> findFirst() {
        return Optional.empty();
    }

    @Override
    public Optional<OUT> reduce(BinaryOperator<OUT> accumulator) {
        return Optional.empty();
    }

    @Override
    public Collection<OUT> toCollection(Supplier<? extends Collection<OUT>> collectionGenerator) {
        return null;
    }

    @Override
    public Stream<OUT> onErrorMap(Function<? super List<Exception>, ? extends OUT> errorMapper) {
        return null;
    }

    @Override
    public Stream<OUT> onErrorMapChecked(ThrowingFunction<? super List<Exception>, ? extends OUT> errorMapper) {
        return null;
    }

    @Override
    public Stream<OUT> onErrorFilter() {
        return null;
    }

    @Override
    public StreamOperation<IN> getStreamOperation() {
        return null;
    }
}
