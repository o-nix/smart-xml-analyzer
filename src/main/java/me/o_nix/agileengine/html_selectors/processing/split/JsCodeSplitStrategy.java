package me.o_nix.agileengine.html_selectors.processing.split;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JsCodeSplitStrategy implements SplitStrategy {
  @Override
  public List<String> split(String value) {
    return Arrays.stream(value.split(";\\s+?"))
        .map(v -> v.replaceAll(";", ""))
        .collect(toList());
  }
}
