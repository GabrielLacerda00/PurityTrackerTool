package org.example.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class clearTempDirs {

    public static void main(String[] args) {

    }

    public static void runClearTempDirs(){
        String projectPath = System.getProperty("user.dir");
        String relativePath = "tmp/.";

        Path fullPath = Paths.get(projectPath, relativePath);
        String absolutePath = fullPath.toString();

        try {
            deleteDirs(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDirs(Path directory) throws IOException
    {
        Files.walk(directory)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }



}
