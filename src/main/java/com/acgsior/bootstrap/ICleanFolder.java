package com.acgsior.bootstrap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * Created by mqin on 8/3/16.
 */
public interface ICleanFolder {

    default void cleanFolder(Path dir) throws IOException {
        Files.walk(dir).map(Path::toFile).sorted(Comparator.comparing(File::isDirectory)).forEach(File::delete);
    }
}
