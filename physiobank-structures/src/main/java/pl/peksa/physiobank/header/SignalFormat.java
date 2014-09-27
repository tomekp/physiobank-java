package pl.peksa.physiobank.header;

/**
 * @see <a href="http://www.physionet.org/physiotools/wag/signal-5.htm">WFDB signal file formats</a>
 */
public enum SignalFormat {
    Format8(8), Format16(16), Format24(24), Format32(32), Format61(61), Format80(80), Format160(160), Format212(212), Format310(310), Format311(311);

    private final int numberCode;

    SignalFormat(int numberCode) {
        this.numberCode = numberCode;
    }

    public static SignalFormat forNumber(int number) {
        for (SignalFormat format : SignalFormat.values()) {
            if (format.numberCode == number) {
                return format;
            }
        }
        return null;
    }
}
