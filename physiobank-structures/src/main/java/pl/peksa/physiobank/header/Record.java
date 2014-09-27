package pl.peksa.physiobank.header;

import lombok.Data;
import pl.peksa.physiobank.common.Frequency;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @see <a href="http://www.physionet.org/physiotools/wag/header-5.htm">Header file decription</a>
 */
@Data
public final class Record {
    @Nonnull
    private final String recordName;
    @Min(0)
    private final int numberOfSegments;
    @Min(0)
    private final int numberOfSignals;
    @Valid
    @Nonnull
    private final Frequency samplingFrequency;
    @Valid
    @Nonnull
    private final Frequency counterFrequency;
    private final double baseCounterValue;
    @Nullable
    private final Integer numberOfSamplesPerSignal;
    @Nullable
    private final LocalTime baseTime;
    @Nullable
    private final LocalDate baseDate;


    public static class Builder {
        private static final Frequency DEFAULT_FREQUENCY = Frequency.hz(250.0);
        public static final DateTimeFormatter BASE_DATE_FORMAT = DateTimeFormatter.ofPattern("DD/MM/YYYY");
        public static final DateTimeFormatter BASE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
        private String recordName;
        private int numberOfSegments = 1;
        private int numberOfSignals;
        private Frequency samplingFrequency = DEFAULT_FREQUENCY;
        private Frequency counterFrequency = null; // if not set (equals null) then defaults to samplingFrequency
        private double baseCounterValue = 0;
        private Integer numberOfSamplesPerSignal;
        private LocalDate baseDate;
        private LocalTime baseTime;

        public static Builder create() {
            return new Builder();
        }

        public Builder withRecordName(String recordName) {
            this.recordName = recordName;
            return this;
        }

        public Builder withNumberOfSegments(int numberOfSegments) {
            this.numberOfSegments = numberOfSegments;
            return this;
        }

        public Builder withNumberOfSignals(int numberOfSignals) {
            this.numberOfSignals = numberOfSignals;
            return this;
        }

        public Builder withSamplingFrequency(Frequency samplingFrequency) {
            this.samplingFrequency = samplingFrequency;
            return this;
        }

        public Builder withCounterFrequency(Frequency counterFrequency) {
            this.counterFrequency = counterFrequency;
            return this;
        }

        public Builder withBaseCounterValue(double baseCounterValue) {
            this.baseCounterValue = baseCounterValue;
            return this;
        }

        public Builder withNumberOfSamplesPerSignal(Integer numberOfSamplesPerSignal) {
            this.numberOfSamplesPerSignal = numberOfSamplesPerSignal;
            return this;
        }

        public Builder withBaseDate(LocalDate baseDate) {
            this.baseDate = baseDate;
            return this;
        }

        public Builder withBaseTime(LocalTime baseTime) {
            this.baseTime = baseTime;
            return this;
        }

        public Record build() {
            if (counterFrequency == null) {
                counterFrequency = samplingFrequency;
            }
            return new Record(
                    recordName,
                    numberOfSegments,
                    numberOfSignals,
                    samplingFrequency,
                    counterFrequency,
                    baseCounterValue,
                    numberOfSamplesPerSignal,
                    baseTime,
                    baseDate
            );
        }
    }
}
