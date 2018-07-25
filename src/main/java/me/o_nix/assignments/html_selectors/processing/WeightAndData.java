package me.o_nix.assignments.html_selectors.processing;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class WeightAndData implements Comparable<WeightAndData> {
  private double weight;
  private HtmlOriginData data;

  @Override
  public int compareTo(WeightAndData o) {
    int weightsCompared = Double.compare(weight, o.weight);

    if (weightsCompared == 0) {
      if (!Objects.equals(data, o.getData())) {
        return 1;
      }
    }

    return weightsCompared;
  }
}
