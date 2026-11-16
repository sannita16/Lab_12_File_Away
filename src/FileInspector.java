import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInspector
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();

        File selectedFile;
        String rec = "";

        int lineCount = 0;
        int wordCount = 0;
        int characterCount = 0;

        try
        {
            // Open JFileChooser in this project's src directory
            File workingDirectory =
                    new File(System.getProperty("user.dir"), "src");

            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null)
                    == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();

                Path file = selectedFile.toPath();

                InputStream in =
                        new BufferedInputStream(
                                Files.newInputStream(file));

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(in));


                // Read file one line at a time
                while((rec = reader.readLine()) != null)
                {
                    lineCount++;

                    // Echo the line to the screen
                    System.out.printf(
                            "Line %4d: %s%n",
                            lineCount,
                            rec);


                    // Count characters
                    characterCount =
                            characterCount + rec.length();


                    // Count words
                    String trimmedLine = rec.trim();

                    if(!trimmedLine.isEmpty())
                    {
                        String[] words =
                                trimmedLine.split("\\s+");

                        wordCount =
                                wordCount + words.length;
                    }
                }

                reader.close();


                // Summary
                System.out.println();
                System.out.println("File Summary");
                System.out.println("----------------------------");

                System.out.println(
                        "File Name: " + selectedFile.getName());

                System.out.println(
                        "Number of Lines: " + lineCount);

                System.out.println(
                        "Number of Words: " + wordCount);

                System.out.println(
                        "Number of Characters: "
                                + characterCount);
            }
            else
            {
                System.out.println(
                        "No file selected. Program ended.");
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch(IOException e)
        {
            System.out.println(
                    "An error occurred while reading the file.");

            e.printStackTrace();
        }
    }
}