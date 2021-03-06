package me.o_nix.assignments.html_selectors.processing.split;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClassNamesSplitStrategy implements SplitStrategy {
  @Override
  public List<String> split(String value) {
    return Arrays.stream(value.split("\\s+"))
        .collect(toList());
  }
}
