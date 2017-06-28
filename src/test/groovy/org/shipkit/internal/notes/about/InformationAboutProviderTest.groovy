package org.shipkit.internal.notes.about

import spock.lang.Specification
import spock.lang.Unroll

class InformationAboutProviderTest extends Specification {

    public static
    final String INFORMATION_ABOUT_ENABLED = "<sup><sup>*This release notes was automatically generated by [shipkit](http://shipkit.org/) %d times.*</sup></sup>"
    public static
    final String INFORMATION_ABOUT_DISABLED = "<!---<sup><sup>*This release notes was automatically generated by [shipkit](http://shipkit.org/) %d times.*</sup></sup>--->"

    public static
    final String INFORMATION_ABOUT_ENABLED_WITH_CONTENT = INFORMATION_ABOUT_ENABLED + "/n new content line"
    public static
    final String INFORMATION_ABOUT_DISABLED_WITH_CONTENT = INFORMATION_ABOUT_DISABLED + "/n new content line"

    private final InformationAboutProvider testObj = new InformationAboutProvider();

    @Unroll
    def "should generate correct info about shipkit when release note file not exist"() {
        given:
        File releaseNoteFile = new File("notExistFile.neg")

        expect:
        "should generate correct info about shipkit when information about is " + informationAbout + " and release not file not exist"
        testObj.getInfoText(releaseNoteFile, informationAbout) == result
        where:
        informationAbout || result
        true             || String.format(INFORMATION_ABOUT_ENABLED, 1)
        false            || String.format(INFORMATION_ABOUT_DISABLED, 1)
    }

    @Unroll
    def "should generate correct info about shipkit when release note file is empty"() {
        given:
        File releaseNoteFile = File.createTempFile("temp", ".tmp")

        expect:
        "should generate correct info about shipkit when information about is " + informationAbout + " and release not file not exist"
        testObj.getInfoText(releaseNoteFile, informationAbout) == result
        where:
        informationAbout || result
        true             || String.format(INFORMATION_ABOUT_ENABLED, 1)
        false            || String.format(INFORMATION_ABOUT_DISABLED, 1)
    }

    @Unroll
    def "should generate correct info about shipkit when release note file exist"() {
        given:
        File releaseNoteFile = File.createTempFile("temp", ".tmp");
        releaseNoteFile.with {
            write content
        }

        expect:
        "should generate correct info about shipkit when information about is " + informationAbout + " and content is " + "'" + content + "'"
        testObj.getInfoText(releaseNoteFile, informationAbout) == result
        where:
        informationAbout | content                                                   || result
        true             | "content"                                                 || String.format(INFORMATION_ABOUT_ENABLED, 1)
        false            | "content"                                                 || String.format(INFORMATION_ABOUT_DISABLED, 1)
        true             | String.format(INFORMATION_ABOUT_ENABLED_WITH_CONTENT, 1)  || String.format(INFORMATION_ABOUT_ENABLED, 2)
        true             | String.format(INFORMATION_ABOUT_DISABLED_WITH_CONTENT, 1) || String.format(INFORMATION_ABOUT_ENABLED, 2)
        false            | String.format(INFORMATION_ABOUT_ENABLED_WITH_CONTENT, 1)  || String.format(INFORMATION_ABOUT_DISABLED, 2)
        false            | String.format(INFORMATION_ABOUT_DISABLED_WITH_CONTENT, 1) || String.format(INFORMATION_ABOUT_DISABLED, 2)
    }
}
