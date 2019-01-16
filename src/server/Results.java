package server;

import java.util.List;

public class Results {
    List<String[]> itemsets;
    List<Integer> itemsetOccurrence;

    Results(List<String[]> itemsets, List<Integer> itemsetOccurrence) {
        this.itemsets = itemsets;
        this.itemsetOccurrence = itemsetOccurrence;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Itemsets:\n");

        for (int row = 0; row < itemsets.size(); row++) {
            Integer occurrence = itemsetOccurrence.get(row);
            for (int col = 0; col < itemsets.get(row).length; col++) {
                stringBuilder.append(itemsets.get(row)[col] + ", ");
            }
            stringBuilder.append(occurrence + " appearences\n");
        }

        return stringBuilder.toString();
    }
}