package me.o_nix.assignments.html_selectors.processing;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class Processor {
  private final HtmlInspectionService inspectionService;
  private final Path originPath;
  private final Path otherPath;

  public Processor(HtmlInspectionService inspectionService, Path originPath, Path otherPath) {
    this.inspectionService = inspectionService;
    this.originPath = originPath;
    this.otherPath = otherPath;
  }

  @SneakyThrows
  public void run() {
    Document originDocument = Jsoup.parse(Files.newInputStream(originPath), StandardCharsets.UTF_8.name(), "");
    Document otherDocument = Jsoup.parse(Files.newInputStream(otherPath), StandardCharsets.UTF_8.name(), "");

    HtmlOriginData htmlOriginData = inspectionService.gatherData(originDocument);
    log.debug("Data gathered from initial document: {}", htmlOriginData);

    String path = inspectionService.findElementByFingerprints(otherDocument, htmlOriginData);

    System.out.println(path);
  }
}
