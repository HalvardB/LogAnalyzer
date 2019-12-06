package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        LogAnalyzer analyzer = new LogAnalyzer();

        // Print Coursera quiz solutions
        analyzer.readFile("data/weblog2_log");
        HashMap<String, ArrayList<String>> answer = analyzer.iPsForDays();
        analyzer.iPsWithMostVisitsOnDay(answer, "Sep 30");
    }
}
