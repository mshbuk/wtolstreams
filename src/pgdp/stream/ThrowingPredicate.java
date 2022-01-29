package pgdp.stream;

@FunctionalInterface
public interface ThrowingPredicate<T> {
    boolean test() throws Exception;

}
