import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class DataSaver
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        ArrayList<String> records = new ArrayList<>();

        boolean moreRecords = true;


        // Collect records
        while(moreRecords)
        {
            String firstName =
                    SafeInput.getNonZeroLenString(
                            in,
                            "Enter first name");

            String lastName =
                    SafeInput.getNonZeroLenString(
                            in,
                            "Enter last name");


            int idNumber =
                    SafeInput.getRangedInt(
                            in,
                            "Enter ID number",
                            1,
                            999999);

            // Convert to 6 digits
            String id =
                    String.format("%06d", idNumber);


            String email =
                    SafeInput.getNonZeroLenString(
                            in,
                            "Enter email");


            int birthYear =
                    SafeInput.getRangedInt(
                            in,
                            "Enter four-digit year of birth",
                            1000,
                            9999);


            // Build CSV record
            String record =
                    firstName + "," +
                            lastName + "," +
                            id + "," +
                            email + "," +
                            birthYear;

            records.add(record);


            System.out.println();
            System.out.println("Record added:");
            System.out.println(record);


            moreRecords =
                    SafeInput.getYNConfirm(
                            in,
                            "Would you like to enter another record");
        }


        // Get output file name
        String fileName =
                SafeInput.getNonZeroLenString(
                        in,
                        "Enter the file name");


        // Add .csv extension
        if(!fileName.toLowerCase().endsWith(".csv"))
        {
            fileName = fileName + ".csv";
        }


        // Create path to src directory
        File workingDirectory =
                new File(System.getProperty("user.dir"));

        Path file =
                Paths.get(
                        workingDirectory.getPath(),
                        "src",
                        fileName);


        try
        {
            OutputStream out =
                    new BufferedOutputStream(
                            Files.newOutputStream(
                                    file,
                                    CREATE,
                                    TRUNCATE_EXISTING));

            BufferedWriter writer =
                    new BufferedWriter(
                            new OutputStreamWriter(out));


            // Write every CSV record
            for(String record : records)
            {
                writer.write(
                        record,
                        0,
                        record.length());

                writer.newLine();
            }


            writer.close();


            System.out.println();
            System.out.println("Data file written successfully!");

            System.out.println(
                    "File saved as: " + fileName);

            System.out.println(
                    "Number of records saved: "
                            + records.size());
        }
        catch(IOException e)
        {
            System.out.println(
                    "An error occurred while writing the file.");

            e.printStackTrace();
        }
    }
}