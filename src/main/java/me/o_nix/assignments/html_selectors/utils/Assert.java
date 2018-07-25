package me.o_nix.assignments.html_selectors.utils;

public class Assert {
  public static void assertTrue(boolean value, String reason) {
    if (!value) {
      throw new IllegalStateException(reason);
    }
  }

  public static void notNull(Object value, String reason) {
    if (value == null) {
      throw new IllegalArgumentException(reason);
    }
  }
}
