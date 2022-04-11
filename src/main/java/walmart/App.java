package walmart;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public class App {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Input error!");
            return;
        }
        String inputFilePath = args[0];
        File inputFile = new File(inputFilePath);
        String outputFilePath = getOutputFilePath(inputFile);
        File outputFile = new File(outputFilePath);
        List<Request> requestList = getRequestsFromInputFile(inputFile);
        Seating seating = handleRequests(requestList);
        writeResponseToFile(outputFile, seating.getSeatAssignmentString());
        System.out.println(outputFile.getAbsolutePath());
    }


    public static String getOutputFilePath(File inputFile) {
        String inputFileName = inputFile.getName();
        String directoryName = inputFile.getParent();
        int dotIndex = inputFileName.lastIndexOf('.');
        String outputFilename = directoryName + "\\" + inputFileName.substring(0, dotIndex) + "_output.txt";
        return outputFilename;
    }


    public static List<Request> getRequestsFromInputFile(File inputFile) {
        List<Request> requestList = null;
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            requestList = Request.parseFromStream(inputStream);
        } catch (FileNotFoundException e) {
            System.err.print("File not found! Please check the filename.");
            System.exit(-1);
        } catch (IOException e) {
            System.err.print("Error occurred when reading from file. Please check the file");
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            System.err.print("Input is not valid!" + e.getMessage());
            System.exit(-1);
        }
        return requestList;
    }

    public static void writeResponseToFile(File outputFile, String outputString) {
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write(outputString);
            fileWriter.flush();
            fileWriter.close();
        } catch (FileAlreadyExistsException e) {
            System.err.print(e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.err.print("Error occurred when generating output file!");
            System.exit(-1);
        }
    }

    public static Seating handleRequests(List<Request> requestList) {
        Seating seating = new Seating();
        seating.handleRequests(requestList);
        seating.optimize();
        return seating;
    }
}
