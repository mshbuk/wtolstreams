package pgdp.stream;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Exception-fangende Stream Implementierung
 * <p>
 * Auf einer Stream-Instanz kann einmalig eine Stream-Methode aufgerufen werden,
 * danach ist diese Stream-Instanz an die entsprechende Operation gebunden bzw.
 * im fall einer terminalen Stream-Operation wird der Stream ausgewertet und
 * dabei konsumiert. Benutze Stream-Instanzen können daher nicht wiederverwendet
 * werden, es wird eine {@link IllegalStateException} geworfen.
 *
 * @param <T> der Typ der Elemente im Stream
 */
public interface Stream<T> {

    /**
     * Transformiert alle Elemente des Streams von T nach R mithilfe der übergeben
     * Function. Die Transformation hat auf fehlerhafte Elemente keinen Einfluss,
     * sie werden einfach weitergereicht.
     *
     * @param <R>    der Typ der Elemente des Streams nach map
     * @param mapper die Funktion, die die Elemente des Streams von Typ T (oder
     *               einer Oberklasse davon) zu Typ R (oder eine Unterklasse davon)
     *               umwandelt. Tritt dabei ein Fehler aus, so wird dieser als
     *               fehlerhaftes Element durch den Stream gereicht.
     * @return einen Stream vom Typ R
     * @throws NullPointerException falls mapper null ist
     */
    <R> Stream<R> map(Function<? super T, ? extends R> mapper);

    /**
     * Filtert die Elemente aus dem Stream, für die das übergebene Predicate false
     * zurückgibt. Der Filter hat auf fehlerhafte Elemente keinen Einfluss, sie
     * werden einfach weitergereicht.
     *
     //* @param mapper die Bedingung, nach der Elemente im Stream weitergereicht (test
     *               liefert true) werden oder herausgefiltert (test liefert false).
     *               Tritt dabei ein Fehler aus, so wird dieser als fehlerhaftes
     *               Element durch den Stream gereicht und nicht herausgefiltert.
     * @return einen Stream vom Typ T
     * @throws NullPointerException falls filter null ist
     */
    Stream<T> filter(Predicate<? super T> filter);

    /**
     * Siehe {@link #map(Function)}, jedoch kann mapper beliebige Exceptions werfen.
     * <p>
     * <b>Diese Stream Operation verwandelt den Stream in einen "Checked
     * Stream".</b>
     *
     * @throws NullPointerException falls mapper null ist
     * @see #map(Function)
     */
    <R> Stream<R> mapChecked(ThrowingFunction<? super T, ? extends R> mapper);

    /**
     * Siehe {@link #filter(Predicate)}, jedoch kann filter beliebige Exceptions
     * werfen.
     * <p>
     * <b>Diese Stream Operation verwandelt den Stream in einen "Checked
     * Stream".</b>
     *
     * @throws NullPointerException falls filter null ist
     * @see #filter(Predicate)
     */
    Stream<T> filterChecked(ThrowingPredicate<? super T> filter);

    /**
     * Eliminiert Duplikate aus dem Stream, fehlerhafte Elemente sind davon nicht
     * betroffen. Die Reihenfolge der Elemente verändert sich dabei nicht, bei
     * gleichen Elementen wird nur das erste behalten; fehlerhafte Elemente werden
     * einfach in weitergereicht. Enthält der Stream ein null-Elemente, so werden
     * diese nicht anders als normale Elemente behandelt, der Ergebnis-Stream
     * enthält maximal ein null Element.
     * <p>
     * Beispiel: aus
     *
     * <pre>
     * [1, 3, 2, 2, null, 1, {fehlerhaft}, 3, {fehlerhaft}, 4]
     * </pre>
     *
     * wird
     *
     * <pre>
     * [1, 3, 2, null, {fehlerhaft}, {fehlerhaft}, 4]
     * </pre>
     *
     * @return einen Stream selben Typs, der frei von Duplikaten ist
     */
    Stream<T> distinct();

    /**
     * Gibt die Anzahl an Elementen im Stream zurück. Ist die Größe des Streams
     * bekannt, wird das Ergebnis sofort zurückgegeben, ohne das Elemente den Stream
     * durchlaufen.
     * <p>
     * Ist die Größe nicht bekannt, werden alle Elemente im Stream gezählt. Falls
     * Elemente davon fehlerhaft sind, wird eine ErrorsAtTerminalOperationException
     * geworfen. (Da z.B. nicht herausgefunden werden kann, ob diese Elemente bei
     * einer filter-Operation gefiltert werden müssten).
     * <p>
     * <b>Dies ist eine terminale Stream-Operation.</b>
     *
     * @return die Anzahl der Elemente im Stream
     //* @throws ErrorsAtTerminalOperationException falls die exakte Anzahl unbekannt
     *                                            ist und ein fehlerhaftes Element
     *                                            verarbeitet werden müsste
     //* @throws CheckedStreamException             falls der Stream an dem Punkt ein
     *                                            "Checked Stream" ist
     */
    long count();

    /**
     * Gibt das erste Element im Stream zurück.Ist der Stream leer, so wird
     * Optional.empty() zurückgegeben.
     * <p>
     * <b>Dies ist eine terminale Stream-Operation.</b>
     *
     * @return das erste Element im Stream, falls vorhanden.
     * @throws NullPointerException               falls das erste Element null ist
     //* @throws ErrorsAtTerminalOperationException falls das erste Element fehlerhaft
     *                                            ist
    // * @throws CheckedStreamException             falls der Stream an dem Punkt ein
     *                                            "Checked Stream" ist
     * @implSpec sobald ein Element gefunden wurde, werden keine weiteren Elemente
     *           von dem Stream verarbeitet.
     */
    Optional<T> findFirst();

    /**
     * Akkumuliert alle Elemente in dem Stream mit dem gegebenen accumulator. Ist
     * der Stream leer, so wird Optional.empty() zurückgegeben. Ist mindestens ein
     * Element vorhanden, so wird dieses als Startwert benutzt und alle darauf
     * folgenden werden mit dem Wert von davor kombiniert, woraus sich wieder ein
     * neuer Wert ergibt. Der akkumulierte Wert wird jeweils als erster Parameter
     * übergeben, der hinzuzufügende Wert an zweiter Stelle.
     * <p>
     * <b>Dies ist eine terminale Stream-Operation.</b>
     *
     * @param accumulator ein Akkumulator für Elemente vom Typ T
     * @return das Resultat aus der Akkumulation, falls vorhanden.
     * @throws NullPointerException               falls accumulator oder das
     *                                            Resultat null ist
     //* @throws ErrorsAtTerminalOperationException falls irgendein Element fehlerhaft
     *                                            ist
     //* @throws CheckedStreamException             falls der Stream an dem Punkt ein
     *                                            "Checked Stream" ist
     */
    Optional<T> reduce(BinaryOperator<T> accumulator);

    /**
     * Sammelt alle Elemente in einer Collection, die aus dem übergebenen
     * collectionGenerator neu erzeugt bzw. genommen wird.
     * <p>
     * <b>Dies ist eine terminale Stream-Operation.</b>
     *
     * @return das Resultat aus der Akkumulation, falls vorhanden.
     * @throws NullPointerException               falls der collectionGenerator oder
     *                                            die daraus kommende Collection
     *                                            null ist
     //* @throws ErrorsAtTerminalOperationException falls irgendein Element fehlerhaft
     *                                            ist
    // * @throws CheckedStreamException             falls der Stream an dem Punkt ein
     *                                            "Checked Stream" ist
     */
    Collection<T> toCollection(Supplier<? extends Collection<T>> collectionGenerator);

    /**
     * Transformiert alle fehlerhaften Elemente des Streams zu regulären, nicht
     * fehlerhaften Elementen mithilfe der übergebenen Function. Die Transformation
     * hat auf bereits nicht fehlerhafte Elemente keinen Einfluss, sie werden
     * einfach weitergereicht. Tritt bei errorMapper selbst ein Fehler auf, so
     * bleibt das Element fehlerhaft, und die aufgetretene Exception wird der Liste
     * hinzugefügt.
     * <p>
     * <b>Falls der Stream ein "Checked Stream" ist, ist es es danach nicht mehr</b>
     *
     * @param errorMapper die Funktion, die fehlerhafte Elemente des Streams zu
     *                    regulären umwandelt. Tritt bei errorMapper selbst ein
     *                    Fehler auf, so bleibt das Element fehlerhaft, und die neu
     *                    aufgetretene Exception wird dem Element hinzugefügt.
     * @return ein Stream selben Typs, garantiert kein "Checked Stream"
     * @throws NullPointerException falls errorMapper null ist
     */
    Stream<T> onErrorMap(Function<? super List<Exception>, ? extends T> errorMapper);

    /**
     * Siehe {@link #onErrorMap(Function)}, jedoch kann errorMapper beliebige
     * Exceptions werfen und falls der Stream ein "Checked Stream" ist, ändert sich
     * daran nichts.
     * <p>
     * <b>Diese Stream Operation verwandelt den Stream in einen "Checked
     * Stream".</b>
     *
     * @throws NullPointerException falls errorMapper null ist
     * @see #onErrorMap(Function)
     */
    Stream<T> onErrorMapChecked(ThrowingFunction<? super List<Exception>, ? extends T> errorMapper);

    /**
     * Filtert alle fehlerhaften Elemente aus dem Stream heraus.
     * <p>
     * <b>Falls der Stream ein "Checked Stream" ist, ist es es danach nicht mehr</b>
     *
     * @return ein Stream selben Typs, ohne fehlerhafte Elemente und garantiert kein
     *         "Checked Stream"
     */
    Stream<T> onErrorFilter();

    /**
     * Erzeugt einen neuen Stream, der den übergebenen Stream nutzt
     */
    static <T> Stream<T> of(java.util.stream.Stream<T> javaStream) {
        StreamIterator<T> iterator = StreamIterator.of(javaStream);
        Stream<T> stream = new SourcePart<>(iterator, StreamCharacteristics.regular().withStreamSize(javaStream.count()));
        return stream;
    }

    /**
     * Erzeugt einen neuen Stream, der die Elemente der Collection enthält
     *
     * @throws NullPointerException falls die Collection null ist
     * @implSpec die Größe des erzeugten Streams ist bekannt und davon abhängige
     *           Operationen nutzten diese Information zur Optimierung
     */
    static <T> Stream<T> of(Collection<T> col) {
        StreamIterator<T> iterator = StreamIterator.of(col);
        Stream<T> stream = new SourcePart<>(iterator, StreamCharacteristics.regular().withStreamSize(col.size()));
        return stream;
    }

    /**
     * Erzeugt einen neuen Stream, der die Elemente des Sets enthält
     *
     * @throws NullPointerException falls das Set null ist
     * @implSpec die Größe des erzeugten Streams und dessen Duplikat-Freiheit ist
     *           bekannt und davon abhängige Operationen nutzten diese Information
     *           zur Optimierung
     */
    static <T> Stream<T> of(Set<T> col) {
        StreamIterator<T> iterator = StreamIterator.of(col);
        Stream<T> stream = new SourcePart<>(iterator, StreamCharacteristics.regular().withStreamSize(col.size()).withDistinct(true));
        return stream;
    }

    /**
     * Erzeugt einen neuen Stream, der die übergebenen Elemente enthält
     *
     * @throws NullPointerException falls das Array null ist
     * @implSpec die Größe des erzeugten Streams ist bekannt und davon abhängige
     *           Operationen nutzten diese Information zur Optimierung
     */
    @SafeVarargs
    static <T> Stream<T> of(T... elements) {
       StreamIterator<T> iterator = StreamIterator.of(elements);
       Stream<T> stream = new SourcePart<>(iterator, StreamCharacteristics.regular().withStreamSize(elements.length));
       return stream;
    }

    //Translation into English:
    /**
     * Exception catching stream implementation
     * <p>
     * A stream method can be called once on a stream instance,
     * After this, this stream instance is bound to the corresponding operation or
     * in the case of a terminal stream operation, the stream is evaluated and
     * consumed in the process. Therefore, used stream instances cannot be reused
     * will throw an {@link IllegalStateException}.
     *
     * @param <T> the type of elements in the stream
     */


    /**
     * Transforms all elements of the stream from T to R using the pass
     * function. The transformation has no influence on faulty elements,
     * they are simply passed on.
     *
     * @param <R> the type of the elements of the stream after map
     * @param mapper the function that maps the elements of the stream of type T (or
     * a superclass thereof) to type R (or a subclass thereof)
     * converts. If an error occurs, it is displayed as a
     * erroneous element passed through the stream.
     * @return a stream of type R
     * @throws NullPointerException if mapper is null
     */

    /**
     * Filters the elements from the stream for which the given predicate is false
     * returns. The filter has no effect on erroneous elements, they
     * are simply passed on.
     *
     //* @param mapper the condition on which elements are passed in the stream (test
     * returns true) or filtered out (test returns false).
     * If an error occurs, it is marked as an error
     * Element passed through the stream and not filtered out.
     * @return a stream of type T
     * @throws NullPointerException if filter is null
     */

/**
 * See {@link #map(Function)}, but mapper can throw arbitrary exceptions.
 * <p>
 * <b>This stream operation turns the stream into a "Checked
 *stream".</b>
 *
 * @throws NullPointerException if mapper is null
 * @see #map(function)
 */


/**
 * See {@link #filter(Predicate)}, but filter can throw any exception
 * throw.
 * <p>
 * <b>This stream operation turns the stream into a "Checked
 *stream".</b>
 *
 * @throws NullPointerException if filter is null
 * @see #filter(Predicate)
 */

    /**
     * Eliminates duplicates from the stream, erroneous elements are not included
     * affected. The order of the elements does not change
     * same elements only the first one is kept; become defective items
     * simply passed in. If the stream contains a null element, then
     * these are not treated differently than normal elements of the result stream
     * contains at most one null element.
     * <p>
     * Example: off
     *
     * <pre>
     * [1, 3, 2, 2, null, 1, {faulty}, 3, {faulty}, 4]
     * </pre>
     *
     * will
     *
     * <pre>
     * [1, 3, 2, null, {faulty}, {faulty}, 4]
     * </pre>
     *
     * @return a stream of the same type that is free of duplicates
     */


    /**
     * Returns the number of elements in the stream. Is the size of the stream
     * Known, the result is returned immediately without breaking the stream
     * run through.
     * <p>
     * If the size is not known, all elements in the stream are counted. If
     * Elements of it are in error, an ErrorsAtTerminalOperationException
     * thrown. (E.g. since it cannot be determined whether these elements are
     * would have to be filtered by a filter operation).
     * <p>
     * <b>This is a terminal stream operation.</b>
     *
     * @return the number of elements in the stream
    //* @throws ErrorsAtTerminalOperationException if the exact number is unknown
     * is and an erroneous element
     * would have to be processed
    //* @throws CheckedStreamException if the stream comes in at that point
     * "Checked Stream" is
     */

/**
 * Returns the first element in the stream. If the stream is empty, then
 * Optional.empty() returned.
 * <p>
 * <b>This is a terminal stream operation.</b>
 *
 * @return the first element in the stream, if any.
 * @throws NullPointerException if the first element is null
//* @throws ErrorsAtTerminalOperationException if the first element has an error
 *                                            is
// * @throws CheckedStreamException if the stream comes in at that point
 * "Checked Stream" is
 * @implSpec once an item is found, no more items are found
 * processed by the stream.
 */


    /**
     * Accumulates all elements in the stream with the given accumulator. is
     * the stream is empty, so Optional.empty() is returned. Is at least one
     * Element available, this will be used as start value and all on it
     * following are combined with the value from before, resulting in a again
     * gives new value. The accumulated value is given as the first parameter
     * passed, the value to add second.
     * <p>
     * <b>This is a terminal stream operation.</b>
     *
     * @param accumulator an accumulator for elements of type T
     * @return the result from the accumulation, if any.
     * @throws NullPointerException if accumulator or that
     * Result is zero
    //* @throws ErrorsAtTerminalOperationException if any element is in error
     *                                            is
    //* @throws CheckedStreamException if the stream comes in at that point
     * "Checked Stream" is
     */



    /**
     * Gathers all items in a Collection from the passed
     * collectionGenerator is newly created or taken.
     * <p>
     * <b>This is a terminal stream operation.</b>
     *
     * @return the result from the accumulation, if any.
     * @throws NullPointerException if the collectionGenerator or
     * the resulting Collection
     * is zero
    //* @throws ErrorsAtTerminalOperationException if any element is in error
     *                                            is
    // * @throws CheckedStreamException if the stream comes in at that point
     * "Checked Stream" is
     */



    /**
     * Transforms all bad elements of the stream to regular ones, not
     * erroneous elements using the passed function. The transformation
     * does not affect elements that are already non-faulty, they will
     * simply passed on. If an error occurs with errorMapper itself, so
     * the element remains erroneous and the exception that occurred is added to the list
     * added.
     * <p>
     * <b>If the stream is a "Checked Stream", it isn't afterwards</b>
     *
     * @param errorMapper the function that maps the erroneous elements of the stream
     * regular converts. Occurs at errorMapper itself
     * Errors on, the item remains defective, and the new
     * the exception that occurred is added to the element.
     * @return a stream of the same type, guaranteed no "checked stream"
     * @throws NullPointerException if errorMapper is null
     */


    /**
     * See {@link #onErrorMap(Function)}, but errorMapper can use any
     * Throw exceptions and if the stream is a checked stream, changes
     * nothing about that.
     * <p>
     * <b>This stream operation turns the stream into a "Checked
     *stream".</b>
     *
     * @throws NullPointerException if errorMapper is null
     * @see #onErrorMap(Function)
     */



    /**
     * Filters out all erroneous elements from the stream.
     * <p>
     * <b>If the stream is a "Checked Stream", it isn't afterwards</b>
     *
     * @return a stream of the same type, with no bad elements and guaranteed none
     * "Checked Stream"
     */


    /**
     * Creates a new stream using the passed stream
     */


}
