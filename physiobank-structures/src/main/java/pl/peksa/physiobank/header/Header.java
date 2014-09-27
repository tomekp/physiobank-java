package pl.peksa.physiobank.header;

import lombok.Data;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.List;

/**
 * @see <a href="http://www.physionet.org/physiotools/wag/header-5.htm">Header file decription</a>
 */
@Data
public final class Header { //TODO Mutli-segment records are not supported
    @Nonnull
    @Valid
    private final Record record;
    @Nonnull
    @Valid
    private final List<SignalSpecification> signalSpecifications;
    @Nonnull
    private final List<String> infoLines;

    public Header(@Nonnull Record record, @Nonnull List<SignalSpecification> signalSpecifications, @Nonnull List<String> infoLines) {
        this.record = record;
        this.signalSpecifications = signalSpecifications;
        this.infoLines = infoLines;
    }

}
