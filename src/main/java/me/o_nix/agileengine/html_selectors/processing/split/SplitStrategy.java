package me.o_nix.agileengine.html_selectors.processing.split;

import java.util.List;

public interface SplitStrategy {
  List<String> split(String value);
}
