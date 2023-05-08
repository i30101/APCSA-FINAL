import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Writer {
    public static void writeLine(String filepath) {
        FileWriter writer = null;
        
        try {
            writer = new FileWriter(filepath);
            writer.append("Name, Age, Gender\n");
            
            System.out.println("CSV file written successfully");
        }catch (IOException e) {
            System.out.println("Error writing CSV file: " + e);
        }finally {
            try {
                writer.flush();
                writer.close();
            }catch (IOException e) {
                System.out.println("Error closing writer: " + e);
            }
        }

    }


    public static void main(String[] args) {
        String csvFile = "Temp.csv";
        String lineToRemove = "Name, Age, Gender"; // line to remove
        String tempFile = "Temper.csv";
        File inputFile = new File(csvFile);
        File tempOutputFile = new File(tempFile);
        BufferedReader reader = null;
        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new FileWriter(tempOutputFile);
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove)) {
                continue;
                }
                writer.write(currentLine + System.lineSeparator());
            }

            writer.close();
            reader.close();
            boolean successful = tempOutputFile.renameTo(inputFile);
            if (!successful) {
                throw new IOException("Failed to replace original file with updated file");
            }
            System.out.println("Line deleted successfully");
        } catch (IOException e) {
            System.out.println("Error deleting line from CSV file: " + e);
        } finally {
            try {
                if (writer != null) {
                writer.close();
                }
                if (reader != null) {
                reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file: " + e);
            }
        }
    }
}
