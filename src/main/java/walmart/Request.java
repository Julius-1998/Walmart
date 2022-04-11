package walmart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Request {
    int seqNum;
    int guestNum;

    private Request(int seqNum, int guestNum) {
        this.seqNum = seqNum;
        this.guestNum = guestNum;
    }

    /**
     * Parse a request string
     *
     * @param requestString the string to be parsed
     * @throws IllegalArgumentException if the string could not be parsed
     */
    public Request(String requestString) throws IllegalArgumentException {
        String[] requestStrings = requestString.split("\\s+");
        if (requestStrings.length != 2) {
            throw new IllegalArgumentException("Illegal input:" + requestString);
        }
        if (requestStrings[0].charAt(0) != Constants.RESERVATION_IDENTIFIER) {
            throw new IllegalArgumentException("Illegal input:" + requestString);
        }
        try {
            this.seqNum = Integer.parseInt(requestStrings[0].substring(1));
            this.guestNum = Integer.parseInt(requestStrings[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal input:" + requestString);
        }
    }

    /**
     * Parse requests from inputStream
     *
     * @param inputStream the inputStream to be parsed
     * @return A list of requests
     * @throws IOException              if error occurs when reading from the inputStream
     * @throws IllegalArgumentException if the format of data is not correct
     */
    public static List<Request> parseFromStream(InputStream inputStream) throws IOException, IllegalArgumentException {
        List<Request> requestList = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (reader.ready()) {
            String requestString = reader.readLine();
            Request request = new Request(requestString);
            requestList.add(request);
        }
        return requestList;
    }


}
