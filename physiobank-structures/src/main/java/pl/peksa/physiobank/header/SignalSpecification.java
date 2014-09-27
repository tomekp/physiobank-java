package pl.peksa.physiobank.header;

import lombok.Data;

import javax.annotation.Nonnull;

/**
 * @see <a href="http://www.physionet.org/physiotools/wag/header-5.htm">Header file decription</a>
 */
@Data
public final class SignalSpecification {
    @Nonnull
    private final String fileName;
    @Nonnull
    private final SignalFormat signalFormat;
    private final double adcGain;
    private final int adcResolution;
    private final int adcZero;
    private final int initialValue;
    private final Short checksum;
    private final Integer blockSize;
    private final String description;

    public static class Builder {
        private static final double DEFAULT_ADC_GAIN = 200;
        private String fileName;
        private SignalFormat signalFormat;
        private Double adcGain;
        private Integer adcResolution;
        private Integer adcZero;
        private Integer initialValue;
        private Short checksum;
        private Integer blockSize;
        private String description;

        public static Builder create() {
            return new Builder();
        }

        public Builder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder withSignalFormat(SignalFormat signalFormat) {
            this.signalFormat = signalFormat;
            return this;
        }

        public Builder withAdcGain(Double adcGain) {
            this.adcGain = adcGain;
            return this;
        }

        public Builder withAdcResolution(Integer bits) {
            this.adcResolution = bits;
            return this;
        }

        public Builder withAdcZero(Integer zeroValue) {
            this.adcZero = zeroValue;
            return this;
        }

        public Builder withInitialValue(Integer initialValue) {
            this.initialValue = initialValue;
            return this;
        }

        public Builder withChecksum(Short checksum) {
            this.checksum = checksum;
            return this;
        }

        public Builder withBlockSize(Integer blockSize) {
            this.blockSize = blockSize;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SignalSpecification build() {
            if (adcGain == null) {
                adcGain = DEFAULT_ADC_GAIN;
            }
            if (adcResolution == null) {
                throw new IllegalArgumentException("Default value for ADC resolution is not implemented yet.");
            }
            if (adcZero == null) {
                adcZero = 0;
            }
            if (initialValue == null) {
                initialValue = adcZero;
            }
            return new SignalSpecification(fileName, signalFormat, adcGain, adcResolution, adcZero, initialValue, checksum, blockSize, description);
        }
    }
}
