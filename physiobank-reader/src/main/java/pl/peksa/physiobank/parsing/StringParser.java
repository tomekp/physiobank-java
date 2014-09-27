package pl.peksa.physiobank.parsing;

import java.util.Objects;
import java.util.function.Function;

public interface StringParser<T> extends Function<String, T> {
    default T parse(String input) {
        Objects.requireNonNull(input);
        return apply(input);
    }
}
