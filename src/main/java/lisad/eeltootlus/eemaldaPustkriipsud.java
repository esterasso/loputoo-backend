package lisad.eeltootlus;

import java.io.*;

public class eemaldaPustkriipsud {
    public static void main(String[] args) {
        try {
            String input = "src/main/java/lisad/eeltootlus/anagrammisonastik.txt"; // EKI anagrammigeneraatori sõnaloend
            String output = "eemaldatud_pustkriipsud.txt";

            // Loeme vormiloendi sisse
            BufferedReader reader = new BufferedReader(new FileReader(input));
            BufferedWriter writer = new BufferedWriter(new FileWriter(output));

            String word;
            while ((word = reader.readLine()) != null) {
                String newWord = word.replace("|", ""); // eemaldame "|"
                writer.write(newWord); // kirjutame uue sõna output faili
                writer.newLine(); // liigume järgmise sõna juurde
            }

            reader.close();
            writer.close();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
