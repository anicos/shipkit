package org.shipkit.internal.notes.about;

import java.io.*;

public class InfoAboutRemover {

    private final ReleaseNoteFileReader releaseNoteFileReader = new ReleaseNoteFileReader();

    public void removeAboutInfoIfExist(File releseNoteFile) {


        try {
            File tempFile = File.createTempFile("tmp", "shipkit");

            BufferedReader reader = new BufferedReader(new FileReader(releseNoteFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            boolean firstLine = true;
            while ((currentLine = reader.readLine()) != null) {

                if (currentLine.matches(CounterExtractor.ABOUT_INFO_PATTERN.pattern())) continue;
                writer.write(currentLine);
                if (!firstLine) {
                    writer.newLine();
                    firstLine = false;
                }
            }
            writer.close();
            reader.close();
            tempFile.renameTo(releseNoteFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
