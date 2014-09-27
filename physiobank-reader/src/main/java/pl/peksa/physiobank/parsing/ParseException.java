package pl.peksa.physiobank.parsing;

public final class ParseException extends RuntimeException {
    private final String parsingSubject;

    public ParseException(String message, String parsingSubject) {
        super(message);
        this.parsingSubject = parsingSubject;
    }

    public ParseException(String message, String parsingSubject, Throwable cause) {
        super(message, cause);
        this.parsingSubject = parsingSubject;
    }

    public String getParsingSubject() {
        return parsingSubject;
    }
}
