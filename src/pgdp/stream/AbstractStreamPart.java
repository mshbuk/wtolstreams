package pgdp.stream;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractStreamPart <IN, OUT> implements Stream<OUT>, StreamOperable<IN>{

    private final StreamIterator<OUT> iterator;
    private StreamOperable<OUT> next;

    public AbstractStreamPart(StreamIterator<OUT> iterator){
        this.iterator = iterator;
    }

    public void setNext(StreamOperable<OUT> next){
        if(this.next != null){
            throw new IllegalStateException("Next ukve aris!");
        }

        this.next = next;
    }

    public abstract SourcePart<?> getSource();

    @Override
    public <R> Stream<R> map(Function<? super OUT, ? extends R> mapper) {
        List<R> result = new ArrayList<>();

        if (iterator.hasNext()) {
            do {
                result.add(mapper.apply(iterator.next().getElement()));
            } while (iterator.hasNext());
        }
       Stream<R> stream =  new IntermediatePart<R, R>(StreamIterator.of(result));
        return stream;
    }

    @Override
    public Stream<OUT> filter(Predicate<? super OUT> filter) {
        List<OUT> result = new ArrayList<>();

        if (iterator.hasNext()) {
            do {
                OUT element = iterator.next().getElement();
                if (filter.test(element)) {
                    result.add(element);
                }
            } while (iterator.hasNext());
        }
        Stream<OUT> stream = new IntermediatePart<OUT, OUT>(StreamIterator.of(result));
        return stream;
    }

    @Override
    public <R> Stream<R> mapChecked(ThrowingFunction<? super OUT, ? extends R> mapper) {
        List<R> result = new ArrayList<>();

        do {
            if (!iterator.hasNext()) break;
            StreamElement<OUT> element = iterator.next();
            try {
                result.add(mapper.apply(element.getElement()));
            } catch (Exception e) {
                element = element.withExceptionAdded(e);
            }
        } while (true);
        Stream<R>stream = new IntermediatePart<R, R>(StreamIterator.of(result))
        return stream;
    }

    @Override
    public Stream<OUT> filterChecked(ThrowingPredicate<? super OUT> filter) {
        List<OUT> result = new ArrayList<>();

        do {
            if (!iterator.hasNext()) break;

            StreamElement<OUT> element = iterator.next();
            try {
                if (filter.test(element.getElement()))
                    result.add(element.getElement());
            } catch (Exception e) {
                element = element.withExceptionAdded(e);
            }
        } while (true);
       Stream<OUT> stream = new IntermediatePart<OUT, OUT>(StreamIterator.of(result))
        return stream;
    }



    @Override
    public Optional<OUT> reduce(BinaryOperator<OUT> accumulator) {
        if (iterator.hasNext()) {
            OUT element = iterator.next().getElement();

            while (iterator.hasNext()) {
                element = accumulator.apply(element, iterator.next().getElement());
            }

            return Optional.of(element);
        } else {
            return Optional.empty();
        }

    }

    @Override
    public Collection<OUT> toCollection(Supplier<? extends Collection<OUT>> collectionGenerator) {
        Collection<OUT> collection = collectionGenerator.get();

        if (iterator.hasNext()) {
            do {
                collection.add(iterator.next().getElement());
            } while (iterator.hasNext());
        }

        return collection;
    }

    @Override
    public Stream<OUT> distinct() {
        Set<OUT> result = new LinkedHashSet<>();

        if (iterator.hasNext()) {
            do {
                result.add(iterator.next().getElement());
            } while (iterator.hasNext());
        }
        Stream<OUT> stream = new IntermediatePart<OUT, OUT>(StreamIterator.of(result));
        return stream;
    }

    @Override
    public long count() {
        return getSource().count();
    }

    @Override
    public Optional<OUT> findFirst() {
        return iterator.hasNext() ? Optional.of(iterator.next().getElement()) : Optional.empty();
    }
}