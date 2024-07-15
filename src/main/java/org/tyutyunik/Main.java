package org.tyutyunik;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Characteristics of output document
        final boolean marginIsExist = false;
        final String columnDelimiterSymbol = ",";
        final String rowDelimiterSymbol = ";";
        String pathSource;
        String pathOutput;
        String reportName;

        // File paths
        switch (args.length) {
            case 0:
                // Getting path source
                Scanner scanner = new Scanner(System.in);
                System.out.print("[ACT] Enter value pathSource: ");

                while (!scanner.hasNextLine()) {
                    // Wait for user input. Do nothing while waiting for input
                }
                pathSource = scanner.nextLine();

                // Try to read
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(pathSource));
                } catch (IOException e) {
                    System.out.println("[EXCEPTION] There is no way to read the file.");
                    e.printStackTrace();
                    throw new RuntimeException();
                }

                // Getting path output
                scanner = new Scanner(System.in);
                System.out.print("[ACT] Enter value pathOutput: ");

                while (!scanner.hasNextLine()) {
                    // Wait for user input. Do nothing while waiting for input
                }
                pathOutput = scanner.nextLine();

                // Getting report name
                scanner = new Scanner(System.in);
                System.out.print("[ACT] Enter value reportName: ");
                while (!scanner.hasNextLine()) {
                    // Wait for user input. Do nothing while waiting for input
                }
                reportName = scanner.nextLine();

                System.out.printf("[INFO] Input three arguments: csvFilePath [%s] and htmlFilePath [%s] with reportName [%s]\n", pathSource, pathOutput, reportName);

                // Payload
                convertCsvToHtml(pathSource, pathOutput, reportName, marginIsExist, columnDelimiterSymbol, rowDelimiterSymbol);
                break;
            case 3:
                // Args path. Path must be without special symbols
                pathSource = args[0];
                pathOutput = args[1];
                reportName = args[2];

                // Try to read
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(pathSource));
                } catch (IOException e) {
                    System.out.println("[EXCEPTION] There is no way to read the file.");
                    e.printStackTrace();
                    throw new RuntimeException();
                }

                System.out.printf("[INFO] Input three arguments: csvFilePath [%s] and htmlFilePath [%s] with reportName [%s]\n", pathSource, pathOutput, reportName);

                // Payload
                convertCsvToHtml(pathSource, pathOutput, reportName, marginIsExist, columnDelimiterSymbol, rowDelimiterSymbol);
                break;
            default:
                System.out.println("[ERROR] Wrong count of arguments");
        }
    }

    public static void convertCsvToHtml(String csvFilePath, String htmlFilePath, String reportName, boolean marginIsExist, String columnDelimiterSymbol, String rowDelimiterSymbol) {

        // Read csv file
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
             // Create html file
             FileWriter writer = new FileWriter(htmlFilePath)) {

            // Create head
            writer.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "<title>" + reportName + "</title>\n" +
                    "<link rel=\"stylesheet\" href=\"style.css\">" +
                    "</head>\n" +
                    "<body>");

            // H1
            writer.write("<h1>" + reportName + "</h1>");

            // Create table
            writer.write("<table>");

            // Caption
            writer.write("<caption>текст вверху таблицы</caption>");

            // HTML code colgroup
            writer.write("<colgroup>\n" +
                    "<col span=\"2\">\n" +
                    "<col style=\"border: 2px solid black\">\n" +
                    "<col span=\"9\">\n" +
                    "</colgroup>");

            // The first row - head of a document
            writer.write("<thead><tr>");

            // Margin
            if (marginIsExist) {
                writer.write("<th colspan=\"2\"></th>");
            }

            // Data of first line
            String line = reader.readLine(); // Read the first line (header)
            String[] values = line.split(columnDelimiterSymbol);
            for (String value : values) {
                String newValue = value.replace(rowDelimiterSymbol, ""); // remove end of row symbol
                writer.write("<th scope=\"col\">" + newValue + "</th>");
            }
            writer.write("</tr></thead>");

            // Other rows - body of a document
            writer.write("<tbody>");

            // Data of other lines
            while ((line = reader.readLine()) != null) {
                writer.write("<tr>");
                values = line.split(columnDelimiterSymbol);
                if (marginIsExist) {
                    writer.write("<th scope=\"rowgroup\" rowspan=\"1\" colspan=\"2\">Terrestrial planets</th>");
                }
                for (String value : values) {
                    String newValue = value.replace(rowDelimiterSymbol, ""); // remove end of row symbol
                    writer.write("<th scope=\"row\">" + newValue + "</h>");
                }

                writer.write("</tr>");
            }

            // End of file
            writer.write("\n</table>\n</body>\n</html>");

            System.out.printf("[SUCCESS] File was created. Path [%s]\n", htmlFilePath);
        } catch (IOException e) {
            System.out.println("[EXCEPTION] There is no way to read the file.");
            e.printStackTrace();
        }
    }
}
