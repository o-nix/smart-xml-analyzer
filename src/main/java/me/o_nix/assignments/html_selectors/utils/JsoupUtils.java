package me.o_nix.assignments.html_selectors.utils;

import org.jsoup.nodes.Element;

import java.util.Optional;

public class JsoupUtils {
  public static Element getElement(Element element, String selector) {
    return Optional.ofNullable(element.select(selector).first())
        .orElseThrow(() -> new IllegalArgumentException("No elements found with selector " + selector));
  }
}
