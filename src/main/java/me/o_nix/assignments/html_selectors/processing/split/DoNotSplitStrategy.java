package me.o_nix.assignments.html_selectors.processing.split;

import com.google.common.collect.Lists;

import java.util.List;

public class DoNotSplitStrategy implements SplitStrategy {
  @Override
  public List<String> split(String value) {
    return Lists.newArrayList(value);
  }
}
