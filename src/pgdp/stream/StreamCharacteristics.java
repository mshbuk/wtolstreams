package pgdp.stream;

import java.util.OptionalLong;

public final class StreamCharacteristics {
    private final OptionalLong streamSize;
    private final boolean distinct;
    private final boolean checked;

    private StreamCharacteristics(OptionalLong streamSize, boolean distinct, boolean checked) {
        this.streamSize = streamSize;
        this.distinct = distinct;
        this.checked = checked;
    }

    private OptionalLong getStreamSize() {
        return streamSize;
    }

    private boolean isDistinct() {
        return distinct;
    }

    private boolean isChecked () {
        return checked;
    }

    private StreamCharacteristics regular () {
        return new StreamCharacteristics(OptionalLong.empty(), false, false);
    }


}
