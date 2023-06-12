package org.example;
import com.google.gson.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error :: Invalid count of console arguments. Expected: " + 2 + ", got: " + args.length + ".");
            System.out.println("Info  :: Usage of console program: java -jar JsonParser.jar <function name> testData.json");
            System.out.println("         <function name> must be one of the following values: avg, max, values.");
            System.exit(-1);
        }

        // Read command line arguments
        String commandType  = args[0];
        Path jsonFilePath = Paths.get(args[1]);

        // Extract array of JSON objects from input JSON file ...
        // ... Read input JSON file
        Reader reader;
        try {
            reader = Files.newBufferedReader(jsonFilePath);
        } catch (Exception ex) {
            System.out.println("Error :: Failed to load input JSON file at: \"" + jsonFilePath.toAbsolutePath() + "\".");
            System.out.println("Info  :: " + ex + ".");
            return;
        }

        // ... Parse input JSON file
        JsonArray jsonArray;
        try {
            jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            reader.close();
        } catch (Exception ex) {
            System.out.println("Error :: Failed to parse input JSON file at: \"" + jsonFilePath.toAbsolutePath() + "\".");
            System.out.println("Info  :: " + ex.getMessage() + ".");
            return;
        }

        // Process read data depending on command type
        try {
            switch (commandType) {
                case "avg"    -> { System.out.println("Average of \"ups_adv_battery_run_time_remaining\" field values: " + StatisticFetcher.avg(jsonArray, "ups_adv_battery_run_time_remaining") + "."); }
                case "max"    -> { System.out.println("Maximum of \"ups_adv_output_voltage\" field values: " + StatisticFetcher.max(jsonArray, "ups_adv_output_voltage") + "."); }
                case "values" -> { System.out.println("Unique values of \"host\" field: " + StatisticFetcher.values(jsonArray, "host") + "."); }
                default -> {
                    System.out.println("Error :: Unknown command: " + commandType + ".");
                    System.out.println("Info  :: Command must be one of the following values: avg, max, values.");
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Error :: " + ex.getMessage());
        }
    }
}