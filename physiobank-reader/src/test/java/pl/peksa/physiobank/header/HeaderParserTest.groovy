package pl.peksa.physiobank.header

import pl.peksa.physiobank.common.Frequency
import spock.lang.Specification

class HeaderParserTest extends Specification {
    final HeaderParser headerParser = new HeaderParser()

    def "should parse example header"() {
        given:
            String headerFile =
                    """
                    100 2 360 650000 0:0:0 0/0/0
                    100.dat 212 200 11 1024 995 -22131 0 MLII
                    100.dat 212 200 11 1024 1011 20052 0 V5

                    # 69 M 1085 1629 x1
                    # Aldomet, Inderal
                    """.stripIndent().trim()
            List<String> headerFileLines = Arrays.asList(headerFile.split("\\n"))
        when:
            Header header = headerParser.parse(headerFileLines)
        then:
            header.record.recordName == "100"
            header.record.numberOfSignals == 2
            header.record.samplingFrequency == Frequency.hz(360)
            header.record.numberOfSamplesPerSignal == 650000
            header.signalSpecifications.size() == 2
            header.signalSpecifications[0].fileName == "100.dat"
            header.signalSpecifications[0].signalFormat == SignalFormat.Format212
            header.signalSpecifications[0].adcResolution == 11
            header.signalSpecifications[0].adcZero == 1024
            header.signalSpecifications[0].initialValue == 995
            header.signalSpecifications[0].checksum == Short.valueOf((short)-22131)
            header.signalSpecifications[0].blockSize == 0
            header.signalSpecifications[0].description == "MLII"
            header.infoLines[0] == "69 M 1085 1629 x1"
            header.infoLines[1] == "Aldomet, Inderal"
    }

}
