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

    private StreamCharacteristics() {
        streamSize = null;
        distinct = false;
        checked = false;
    }

    public OptionalLong getStreamSize() {
        return streamSize;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public boolean isChecked () {
        return checked;
    }

    public OptionalLong withStreamSize(OptionalLong ol) {
        return streamSize;
    }

    public StreamCharacteristics regular () {
        StreamCharacteristics sc = new StreamCharacteristics(null, false, false);
        return sc;
    }

    public StreamCharacteristics withStreamSize(long streamSize) {
        StreamCharacteristics sc = new StreamCharacteristics(OptionalLong.of(streamSize), distinct, checked);
        return sc;
    }

    public StreamCharacteristics withDistinct(boolean distinct1) {
        StreamCharacteristics sc = new StreamCharacteristics(streamSize, distinct1, checked);
        return sc;
    }

    public StreamCharacteristics withChecked(boolean checked1) {
        StreamCharacteristics sc = new StreamCharacteristics(streamSize, distinct, checked1);
        return sc;
    }
}
