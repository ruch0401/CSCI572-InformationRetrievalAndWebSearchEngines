package org.ir.analytics.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface Analysis {

    void analyze(Path filepath, Path outputPath);

    default void outputAnalysisToFile(Path filepath, String data) {
        try {
            Files.writeString(filepath, data, StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
}
