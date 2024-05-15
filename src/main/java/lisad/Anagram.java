package lisad;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

public class Anagram {

    public static int oneAnagramCounter = -1;
    public static int twoAnagramCounter;

    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/anagrammimang";
        String username = "postgres";
        String password = "password";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            String sql = "SELECT anagram from anagrams";
            resultSet = statement.executeQuery(sql);
            System.out.println(resultSet);

            while (resultSet.next()) {
                processAnagram(resultSet.getString("anagram"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            closeResources(resultSet, statement, connection);
        }

    }

    private static void processAnagram(String anagram) throws IOException {
        oneAnagramCounter = -1;
        twoAnagramCounter = 0;

        String fraas = processInput(anagram);

        if (fraas.isEmpty()) {
            System.exit(0);
        }

        Map<Character, Integer> sagedusmall = getFrequencyMap(fraas);
        String frsig = sortCharacters(fraas);
        //System.out.println(frsig);
        System.out.println(frsig);
        Map<String, String> sonastik = loadDictionary();

        //findSingleAnagrams(sonastik, frsig);

        //findDoubleAnagrams(sonastik, frsig);

        updateAnagramInDatabase(anagram, frsig);
    }

    private static void updateAnagramInDatabase(String originalAnagram, String frsig) {
        String url = "jdbc:postgresql://localhost:5432/anagrammimang";
        String username = "postgres";
        String password = "password";

        String selectSql = "SELECT anagram_id FROM anagrams WHERE anagram = ?";
        String updateSql = "UPDATE anagrams SET anagram = ?, anagram_key = ? WHERE anagram_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

            // Get the anagram_id for the given original anagram
            selectStatement.setString(1, originalAnagram);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int anagramId = resultSet.getInt("anagram_id");

                // Update the row with the sorted anagram
                updateStatement.setString(1, originalAnagram);
                updateStatement.setString(2, frsig);
                updateStatement.setInt(3, anagramId);
                System.out.println(originalAnagram + frsig + anagramId);

                int rowsAffected = updateStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String processInput(String anagram) {
        String fraas = String.join("", anagram);
        fraas = fraas.replaceAll("\\P{L}", "").toLowerCase();
        return fraas;
    }

    private static Map<String, String> loadDictionary() throws IOException {
        Map<String, String> sonastik = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("sonastik.txt"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\P{L}", "").toLowerCase();
                sonastik.putIfAbsent(sortCharacters(line), "");
            }
        }
        return sonastik;
    }

    private static void findSingleAnagrams(Map<String, String> sonastik, String frsig) {
        for (Map.Entry<String, String> entry : sonastik.entrySet()) {
            checkAndAddSingleAnagram(entry.getKey(), entry.getValue(), frsig);
        }
        System.out.println(oneAnagramCounter);
    }

    private static void checkAndAddSingleAnagram(String signature, String line, String frsig) {
        Map<Character, Integer> pisimall = new HashMap<>(getFrequencyMap(frsig));
        boolean valid = true;

        for (char c : line.toCharArray()) {
            if (pisimall.containsKey(c) && pisimall.get(c) > 0) {
                pisimall.put(c, pisimall.get(c) - 1);
            } else {
                valid = false;
                break;
            }
        }

        if (valid && frsig.equals(signature) && signature.length() > 1) {
            oneAnagramCounter++;
        }
    }

    private static void findDoubleAnagrams(Map<String, String> sonastik, String frsig) {
        List<String> kandidaadid = new ArrayList<>(sonastik.keySet());
        for (int i1 = 0; i1 < kandidaadid.size(); i1++) {
            for (int i2 = i1 + 1; i2 < kandidaadid.size(); i2++) {
                String sig = sortCharacters(kandidaadid.get(i1) + kandidaadid.get(i2));
                if (frsig.equals(sig)) {
                    twoAnagramCounter++;
                }
            }
        }
        System.out.println(twoAnagramCounter);
    }

    private static void insertAnagramToDatabase(String frsig) {
        String url = "jdbc:postgresql://localhost:5432/anagrammimang";
        String username = "postgres";
        String password = "password";

        String sql = "INSERT INTO anagrams (anagram_key) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, frsig);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) affected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeResources(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Map<Character, Integer> getFrequencyMap(String s) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    private static String sortCharacters(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
