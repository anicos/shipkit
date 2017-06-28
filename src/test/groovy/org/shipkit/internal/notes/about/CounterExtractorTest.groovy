package org.shipkit.internal.notes.about

import spock.lang.Specification
import spock.lang.Unroll

class CounterExtractorTest extends Specification {

    final
    static String INFORMATION_ABOUT_COMMENTED = InformationAboutProvider.COMMENT_START + InformationAboutProvider.INFORMATION_ABOUT + InformationAboutProvider.COMMENT_END;

    private final CounterExtractor testObj = new CounterExtractor()

    @Unroll
    def "should extract correct couter from first line od realese note"() {
        expect:
        testObj.getCounter(infoAbout) == result
        where:
        infoAbout                                                     || result
        String.format(InformationAboutProvider.INFORMATION_ABOUT, 2)  || 2
        String.format(InformationAboutProvider.INFORMATION_ABOUT, 10) || 10
        String.format(INFORMATION_ABOUT_COMMENTED, 123)               || 123
        String.format(INFORMATION_ABOUT_COMMENTED, 4)                 || 4
        ""                                                            || 0
        "some content"                                                || 0
        "some content with number 43 and 2323"                        || 0
    }


    def "about info pattern should match to information about shipkit"() {
        when:
        String infoAboutShipkit = String.format(InformationAboutProvider.INFORMATION_ABOUT, 300)
        then:
        infoAboutShipkit.matches(CounterExtractor.ABOUT_INFO_PATTERN)
    }
}
