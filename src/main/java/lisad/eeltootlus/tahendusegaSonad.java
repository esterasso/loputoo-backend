package lisad.eeltootlus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class tahendusegaSonad {

    public static void main(String[] args) {
        String inputFile = "src/main/java/lisad/eeltootlus/morfanaluusitud.txt";
        String outputFile = "loplik_sonastik.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String currentLine = reader.readLine();  // loeme rida
            while (currentLine != null) {
                String nextLine = reader.readLine(); // vaatame ette mis on järgmine rida

                // kontrollime, et rida oleks tähendusega
                if (!currentLine.contains("+") && !currentLine.trim().contains("#") &&
                        (nextLine == null || !nextLine.trim().contains("#"))) {
                    writer.write(currentLine.trim());
                    writer.newLine();
                }

                currentLine = nextLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
