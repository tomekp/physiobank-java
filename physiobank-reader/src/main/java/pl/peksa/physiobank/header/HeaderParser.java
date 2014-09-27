package pl.peksa.physiobank.header;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * @see <a href="http://www.physionet.org/physiotools/wag/header-5.htm">Header file decription</a>
 */
@Slf4j
public class HeaderParser {
    public Header parse(Iterable<String> headerFileLines) {
        Record record = null;
        List<SignalSpecification> signalSpecifications = Lists.newArrayList();
        List<String> infoLines = Lists.newArrayList();

        Iterator<String> iterator = headerFileLines.iterator();
        boolean seenRecordLine = false;
        boolean lastSignalSpecificationLineSeen = false;
        int lineNumber = 0;
        while (iterator.hasNext()) {
            String currentLine = iterator.next();
            lineNumber += 1;
            if (isEmpty(currentLine)) {
                logLine("Ignoring empty line", lineNumber, currentLine);
            } else if (isComment(currentLine)) {
                if (isInfoLine(currentLine, lastSignalSpecificationLineSeen)) {
                    logLine("Found info line", lineNumber, currentLine);
                    infoLines.add(extractInfoString(currentLine));
                } else {
                    logLine("Ignoring comment", lineNumber, currentLine);
                }
            } else if (!seenRecordLine) {
                logLine("Found record line", lineNumber, currentLine);
                seenRecordLine = true;
                record = getRecordLineParser().parse(currentLine);
            } else {
                logLine("Found signal definition line", lineNumber, currentLine);
                signalSpecifications.add(getSignalSpecificationLineParser().parse(currentLine));
                lastSignalSpecificationLineSeen = signalSpecifications.size() == record.getNumberOfSignals();
            }
        }
        Preconditions.checkNotNull(record, "No record found!");
        return new Header(record, unmodifiableList(signalSpecifications), unmodifiableList(infoLines));
    }

    protected String extractInfoString(String line) {
        return StringUtils.stripStart(line, "# ");
    }

    protected boolean isInfoLine(String line, boolean isAfterLastSignalLine) {
        return isAfterLastSignalLine && line.startsWith("#");
    }

    protected boolean isComment(String line) {
        return line.trim().startsWith("#");
    }

    protected boolean isEmpty(String line) {
        return StringUtils.isBlank(line);
    }

    protected RecordLineParser getRecordLineParser() {
        return new RecordLineParser();
    }

    protected SignalSpecificationLineParser getSignalSpecificationLineParser() {
        return new SignalSpecificationLineParser();
    }

    private static void logLine(String message, int lineNumber, String lineText) {
        if (log.isTraceEnabled()) {
            log.trace(String.format("%-35s %03d# %s", message, lineNumber, lineText));
        }
    }

}
