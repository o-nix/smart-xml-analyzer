package me.o_nix.assignments.html_selectors.processing;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.o_nix.assignments.html_selectors.processing.split.ClassNamesSplitStrategy;
import me.o_nix.assignments.html_selectors.processing.split.DoNotSplitStrategy;
import me.o_nix.assignments.html_selectors.processing.split.JsCodeSplitStrategy;
import me.o_nix.assignments.html_selectors.processing.split.SplitStrategy;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

import static me.o_nix.assignments.html_selectors.utils.JsoupUtils.getElement;

@RequiredArgsConstructor
@Slf4j
public class HtmlInspectionService {
  public static final String DUMMY_STRATEGY_NAME = "none";

  private static final Map<String, SplitStrategy> splitStrategies = ImmutableMap.<String, SplitStrategy>builder()
      .put("class", new ClassNamesSplitStrategy())
      .put("onclick", new JsCodeSplitStrategy())
      .put("text", new ClassNamesSplitStrategy())
      .put("none", new DoNotSplitStrategy())
      .build();

  private final String selector;
  private final Map<String, OriginDataPieceComparator> comparators;

  public HtmlOriginData gatherData(Document origin) {
    Element initialElement = getElement(origin, selector);

    return extractOriginData(initialElement);
  }

  private static HtmlOriginData extractOriginData(Element element) {
    HtmlOriginData originData = new HtmlOriginData();

    originData.setTagName(element.tagName());

    for (Attribute attribute : element.attributes()) {
      String attributeName = attribute.getKey();
      String attributeValue = StringUtils.strip(attribute.getValue());

      SplitStrategy splitStrategy = Optional.ofNullable(splitStrategies.get(attributeName))
          .orElseGet(() -> splitStrategies.get(DUMMY_STRATEGY_NAME));

      List<String> values = splitStrategy.split(attributeValue);

      originData.getAttributes().putAll(attributeName, values);
    }

    String elementsText = StringUtils.strip(element.ownText());
    originData.setText(splitStrategies.get("text").split(elementsText));
    originData.setCssPath(element.cssSelector());

    Optional.ofNullable(element.parent())
        .ifPresent(parent -> originData.setParentData(extractOriginData(parent)));

    return originData;
  }

  public String findElementByFingerprints(Document otherDocument, HtmlOriginData originData) {
    TreeSet<WeightAndData> comparisonSet = Sets.newTreeSet();

    for (Element element : extractAllElements(otherDocument)) {
      HtmlOriginData currentElementData = extractOriginData(element);
      double weight = 0;

      weight += comparators.get("tag").compareAndGetWeight(Lists.newArrayList(currentElementData.getTagName()),
          Lists.newArrayList(originData.getTagName()));

      ListMultimap<String, String> originAttributes = originData.getAttributes();
      ListMultimap<String, String> currentAttributes = currentElementData.getAttributes();

      for (String attributeName : originAttributes.keys()) {
        weight += Optional.ofNullable(comparators.get(attributeName))
            .orElse(comparators.get(DUMMY_STRATEGY_NAME))
            .compareAndGetWeight(currentAttributes.get(attributeName), originAttributes.get(attributeName));
      }

      weight += comparators.get("text").compareAndGetWeight(currentElementData.getText(), originData.getText());

      comparisonSet.add(new WeightAndData(weight, currentElementData));
    }

    WeightAndData possiblyTargetElementData = comparisonSet.last();
    log.debug("Possible element data: {}", possiblyTargetElementData);

    return possiblyTargetElementData.getData().getCssPath();
  }

  private static Elements extractAllElements(Document document) {
    return document.body().select("*");
  }
}
