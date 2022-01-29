package pgdp.stream;

import java.util.Iterator;

public class IntermediatePart<IN, OUT> extends AbstractStreamPart<IN, OUT> {
    public IntermediatePart(StreamIterator<IN> in) {
        AbstractStreamPart(Iterator);
    }
    @Override
    public SourcePart<?> getSource() {
        return null;
    }
}
