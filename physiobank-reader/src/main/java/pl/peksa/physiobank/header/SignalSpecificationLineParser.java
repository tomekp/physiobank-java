package pl.peksa.physiobank.header;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import pl.peksa.physiobank.parsing.StringParser;

/**
 * @see <a href="http://www.physionet.org/physiotools/wag/header-5.htm">Header file decription</a>
 */
@Slf4j
public class SignalSpecificationLineParser implements StringParser<SignalSpecification> {
    @Override
    public SignalSpecification apply(String line) {
        StrTokenizer tokenizer = new StrTokenizer(line, ' ');
        SignalSpecification.Builder builder = SignalSpecification.Builder.create()
                .withFileName(tokenizer.next())
                .withSignalFormat(SignalFormat.forNumber(Integer.valueOf(tokenizer.next())));

        if (tokenizer.hasNext()) {
            builder.withAdcGain(Double.valueOf(tokenizer.next()));
        }
        if (tokenizer.hasNext()) {
            builder.withAdcResolution(Integer.valueOf(tokenizer.next()));
        }
        if (tokenizer.hasNext()) {
            builder.withAdcZero(Integer.valueOf(tokenizer.next()));
        }
        if (tokenizer.hasNext()) {
            builder.withInitialValue(Integer.valueOf(tokenizer.next()));
        }
        if (tokenizer.hasNext()) {
            builder.withChecksum(Short.valueOf(tokenizer.next()));
        }
        if (tokenizer.hasNext()) {
            builder.withBlockSize(Integer.valueOf(tokenizer.next()));
        }
        if (tokenizer.hasNext()) {
            builder.withDescription(StringUtils.join(tokenizer, ' '));
        }
        return builder.build();
    }
}
