package com.vkuzmenko.tmdbapi.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonPlaceholderReader {

  public String read(String fileName) {
    File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
    StringBuilder contentBuilder = new StringBuilder();

    try (Stream<String> stream = Files.lines(Paths.get(file.getPath()), StandardCharsets.UTF_8)) {
      stream.forEach(contentBuilder::append);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return contentBuilder.toString();
  }
}
