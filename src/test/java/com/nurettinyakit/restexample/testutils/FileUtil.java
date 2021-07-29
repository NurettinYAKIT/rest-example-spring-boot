package com.nurettinyakit.restexample.testutils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtil {

    public static String readMessageFromFile(final String fileName) throws IOException {

        return Files.readString(readFile(fileName).toPath(), StandardCharsets.UTF_8);
    }

    public static File readFile(final String fileName) throws IOException {

        return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + fileName);
    }
}
