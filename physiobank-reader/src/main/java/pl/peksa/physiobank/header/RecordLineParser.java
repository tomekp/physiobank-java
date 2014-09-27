package pl.peksa.physiobank.header;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import pl.peksa.physiobank.common.Frequency;
import pl.peksa.physiobank.parsing.ParseException;
import pl.peksa.physiobank.parsing.StringParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalQueries;
import java.util.List;


/**
 * @see <a href="http://www.physionet.org/physiotools/wag/header-5.htm">Header file decription</a>
 */
@Slf4j
public class RecordLineParser implements StringParser<Record> {
    @Override
    public Record apply(String line) {

        StatefulParser parser = new StatefulParser();
        parser.parse(line);
        Record record = parser.get();
        log.debug("Record line for \"{}\" is \"{}\".", line, record);
        return record;
    }

    protected static class StatefulParser {
        private final List<String> givenLines;
        private final Record.Builder builder;
        private State state = State.START;

        public StatefulParser() {
            builder = Record.Builder.create();
            givenLines = Lists.newArrayList();
        }

        public void parse(String line) {
            givenLines.add(line);
            String[] tokens = line.split(" ");
            for (String token : tokens) {
                log.trace("State: {}, accepting: \"{}\"", state, token);
                accept(token);
            }
            log.trace("State: {}, finished.", state);
        }

        private void accept(String token) {
            switch (state) {
                case START:
                    builder.withRecordName(token);
                    state = State.AFTER_NAME;
                    break;
                case AFTER_NAME:
                    builder.withNumberOfSignals(Integer.valueOf(token));
                    state = State.AFTER_NUMBER_OF_SIGNALS;
                    break;
                case AFTER_NUMBER_OF_SIGNALS:
                    builder.withSamplingFrequency(Frequency.hz(Double.valueOf(token)));
                    state = State.AFTER_FREQUENCY;
                    break;
                case AFTER_FREQUENCY:
                    builder.withNumberOfSamplesPerSignal(Integer.valueOf(token));
                    state = State.AFTER_NUMBER_OF_SAMPLES;
                    break;
                case AFTER_NUMBER_OF_SAMPLES:
                    builder.withBaseTime(parseBaseTime(token));
                    state = State.AFTER_BASE_TIME;
                    break;
                case AFTER_BASE_TIME:
                    builder.withBaseDate(parseBaseDate(token));
                    state = State.AFTER_BASE_DATE;
                    break;
                case AFTER_BASE_DATE:
                    state = State.END;
                    break;
                case END:
                default:
                    throw new ParseException(
                            "Unexpected token (\"" + token + "\" given in state \"" + state + "\"!",
                            Joiner.on('\n').join(givenLines)
                    );
            }
        }

        private static LocalDate parseBaseDate(String token) {
            if ("0/0/0".equals(token)) {
                return null;
            }
            return Record.Builder.BASE_DATE_FORMAT.parse(token, TemporalQueries.localDate());
        }

        private static LocalTime parseBaseTime(String token) {
            if ("0:0:0".equals(token)) {
                return null;
            }
            return Record.Builder.BASE_TIME_FORMAT.parse(token, TemporalQueries.localTime());
        }

        private static enum State {
            START, AFTER_NAME, AFTER_NUMBER_OF_SIGNALS, AFTER_FREQUENCY, AFTER_NUMBER_OF_SAMPLES, AFTER_BASE_TIME, AFTER_BASE_DATE, END
        }

        public Record get() {
            return builder.build();
        }
    }
}
