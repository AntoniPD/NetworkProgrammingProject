package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import ca.pfv.spmf.algorithms.frequentpatterns.fin_prepost.PrePost;;

public class FrequentItemsetMiner {
    public static Results mine(List<String[]> matrix) throws IOException {
        Pair<String, Integer> itemMap = new Pair<>();
        int numColumns = matrix.get(1).length;
        int uniqueItemCount = 0;
        int[][] asIntegers = new int[matrix.size()][numColumns];

        System.out.println("Processing file...");
        for (int rowIndex = 1; rowIndex < matrix.size(); rowIndex++) {

            String[] row = matrix.get(rowIndex);
            for (int colIndex = 0; colIndex < numColumns; colIndex++) {
                String itemString = row[colIndex];
                if (!itemMap.isDefinedForDomainValue(itemString)) {
                    itemMap.define(itemString, uniqueItemCount);
                    uniqueItemCount++;
                }

                asIntegers[rowIndex][colIndex] = itemMap.getValueByKey(itemString);
            }

            Arrays.sort(asIntegers[rowIndex]);
        }

        File inputFile = File.createTempFile("input", ".tmp");
        inputFile.deleteOnExit();
        File outputFile = File.createTempFile("output", ".tmp");
        outputFile.deleteOnExit();
        createAlgorithmInputFile(inputFile, asIntegers);
        PrePost prePost = new PrePost();
        prePost.runAlgorithm(inputFile.getAbsolutePath(), .2, outputFile.getAbsolutePath());

        return parseResultsFromFile(outputFile, itemMap);
    }

    private static Results parseResultsFromFile(File file, Pair<String, Integer> itemMap) throws IOException {
        BufferedReader resultsFile = new BufferedReader(new FileReader(file));

        List<String[]> itemsets = new LinkedList<>();
        List<Integer> occurrences = new LinkedList<>();

        String resultLine;
        while ((resultLine = resultsFile.readLine()) != null) {
            String[] splitted = resultLine.split("\\s+#SUP:\\s+");
            String[] itemsetStrings = splitted[0].split("\\s+");

            String[] itemset = new String[itemsetStrings.length];
            for (int i = 0; i < itemsetStrings.length; i++) {
                itemset[i] = itemMap.getKeyByValue(Integer.parseInt(itemsetStrings[i]));
            }

            Integer count = Integer.parseInt(splitted[1]);

            itemsets.add(itemset);
            occurrences.add(count);
        }

        return new Results(itemsets, occurrences);
    }

    private static void createAlgorithmInputFile(File file, int[][] data) throws IOException {
        PrintWriter fileWriter = new PrintWriter(new FileWriter(file));

        for (int row = 0; row < data.length; row++) {
            fileWriter.println(StringUtils.join(ArrayUtils.toObject(data[row]), " "));
        }

        fileWriter.close();
    }
}
