package me.o_nix.agileengine.html_selectors;

import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import lombok.extern.slf4j.Slf4j;
import me.o_nix.agileengine.html_selectors.processing.HtmlInspectionService;
import me.o_nix.agileengine.html_selectors.processing.OriginDataPieceComparator;
import me.o_nix.agileengine.html_selectors.processing.Processor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static me.o_nix.agileengine.html_selectors.processing.HtmlInspectionService.DUMMY_STRATEGY_NAME;
import static me.o_nix.agileengine.html_selectors.utils.Assert.assertTrue;

@Slf4j
public class Application {

  public static final String WEIGHTS_CONFIG_KEY = "weights";

  public static void main(String[] args) {
    createProcessor(args).run();
  }

  private static Processor createProcessor(String[] args) {
    assertTrue(args.length >= 2, "No input file arguments provided");

    Config config = ConfigFactory.load();
    String selector = config.getString("selector");

    Path originPath = Paths.get(args[0]);
    assertTrue(Files.exists(originPath) && Files.isRegularFile(originPath), "Origin file path is wrong");

    Path otherPath = Paths.get(args[1]);
    assertTrue(Files.exists(otherPath) && Files.isRegularFile(otherPath), "Other file path is wrong");

    log.debug("Selector is: {}", selector);
    log.debug("Original path is: {}", originPath);
    log.debug("Other path is: {}", otherPath);

    ImmutableMap.Builder<String, OriginDataPieceComparator> comparatorsBuilder =
        ImmutableMap.<String, OriginDataPieceComparator>builder()
            .put(DUMMY_STRATEGY_NAME, new OriginDataPieceComparator(1));

    ConfigObject weightsMap = config.getObject(WEIGHTS_CONFIG_KEY);

    for (String weightKey : weightsMap.keySet()) {
      double weight = config.getDouble("weights." + weightKey);
      comparatorsBuilder.put(weightKey, new OriginDataPieceComparator(weight));
    }

    HtmlInspectionService inspectionService = new HtmlInspectionService(selector, comparatorsBuilder.build());

    return new Processor(inspectionService, originPath, otherPath);
  }
}
