package me.o_nix.assignments.html_selectors.processing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OriginDataPieceComparator {
  @Getter
  private final double weight;

  public double compareAndGetWeight(List<String> v1, List<String> v2) {
    if (v1 == null || v2 == null || v1.size() == 0 || v2.size() == 0) {
      return 0;
    }

    int size1 = v1.size();
    int size2 = v2.size();

    int minSize = Math.min(size1, size2);
    double matchedPieceWeight = weight / minSize;
    List<String> targetList = size1 >= size2 ? v1 : v2;
    List<String> comparisonList = size1 >= size2 ? v2 : v1;

    return targetList.stream()
        .filter(comparisonList::contains)
        .count() * matchedPieceWeight;
  }
}
