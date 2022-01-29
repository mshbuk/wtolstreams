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

    public boolean withDistinct(boolean b) {
        return distinct;
    }

    public boolean withChecked (boolean b) {
        return checked;
    }

    public StreamCharacteristics regular () {
        return new StreamCharacteristics(OptionalLong.empty(), false, false);
    }


}
