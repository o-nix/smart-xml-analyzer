package me.o_nix.agileengine.html_selectors.processing;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude = "parentData")
class HtmlOriginData {
  private String tagName;
  private List<String> text;
  private String cssPath;
  private HtmlOriginData parentData;
  private ListMultimap<String, String> attributes = LinkedListMultimap.create();
}
