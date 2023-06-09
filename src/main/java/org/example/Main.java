package org.example;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("ERROR :: Invalid count of console arguments! Expected: " + 2 + ", got: " + args.length);
            System.out.println("INFO  :: Usage of console program: java -jar json_parse <function name> testData.json");
            System.out.println("        <function name> must be one of the following values: avg, max, values.");
            System.exit(-1);
        }

        System.out.println("FUNCTION NAME: " + args[0] + ", JSON FILE NAME: " + args[1]);
    }
}