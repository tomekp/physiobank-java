package pl.peksa.physiobank.common;

import lombok.Data;

@Data
public final class Frequency {
    private final double hertzValue;

    private Frequency(double hertzValue) {
        this.hertzValue = hertzValue;
    }

    public static Frequency hz(double frequency) {
        return new Frequency(frequency);
    }
}
