package org.shipkit.internal.notes.about

import spock.lang.Specification

class InfoAboutRemoverTest extends Specification {

    InfoAboutRemover infoAboutRemover = new InfoAboutRemover();

    def "should remove first line if match"() {
        given:
        File releaseNoteFile = File.createTempFile("temp", ".tmp");
        releaseNoteFile.with {
            write String.format(InformationAboutProvider.INFORMATION_ABOUT, 400)
            write "old content"
        }
        when:
        infoAboutRemover.removeAboutInfoIfExist(releaseNoteFile);

        then:
        releaseNoteFile.text == "old content"
    }
}
