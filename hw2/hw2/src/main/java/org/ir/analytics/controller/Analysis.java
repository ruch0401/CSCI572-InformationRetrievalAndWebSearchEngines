package org.ir.analytics.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface Analysis {

    String analyze(Path filepath, Path outputPath);

}
