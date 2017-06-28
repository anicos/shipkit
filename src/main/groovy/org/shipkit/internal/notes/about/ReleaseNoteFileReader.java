package org.shipkit.internal.notes.about;

import java.io.*;

class ReleaseNoteFileReader {
    String getFirstLine(File releaseNoteFile) {
        BufferedReader br = createBufferedReader(releaseNoteFile);
        String firstLine = readFirstLine(br);
        closeBuffedReader(br);
        return firstLine;
    }

    private BufferedReader createBufferedReader(File releaseNoteFile) {
        try {
            return new BufferedReader(new FileReader(releaseNoteFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't create FileReader for release note file", e);
        }
    }

    private String readFirstLine(BufferedReader bufferedReader) {
        try {
            String firstLine = bufferedReader.readLine();
            return (firstLine == null) ? "" : firstLine;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from release note file", e);
        }
    }

    private void closeBuffedReader(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException("Can't close file reader from release note file", e);
        }
    }
}
