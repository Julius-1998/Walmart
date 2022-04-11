package walmart;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Handles requests and arrange seats.
 * The column number and row number may be specified, or it will use numbers stored in default setting.
 */
public class Seating {
    int[][] seats;
    int capacity;
    Map<Integer, Set<Coordinate>> givenSeats;
    int seatsTaken;
    int rowNum;
    int colNum;
    Map<Integer, Integer> remainingSeats;


    public Seating() {
        this(Constants.ROW, Constants.COL);
    }

    public Seating(int rowNum, int colNum) {
        //If an empty row is required, discard the last row.
        if (Constants.SAFETY_ROW_EMPTY) {
            rowNum = rowNum - 1;
        }
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.seats = new int[rowNum][colNum];
        this.capacity = rowNum * colNum;
        this.givenSeats = new TreeMap<>();
        this.seatsTaken = 0;
        this.remainingSeats = new HashMap<>();
        for (int row = 0; row < rowNum; row++) {
            remainingSeats.put(row, colNum);
        }
        //If seats are required to be empty, reduce the capacity by empty seats number.
        if (Constants.SAFETY_SEATS_EMPTY) {
            capacity = capacity - Constants.EMPTY_SEATS;
        }
    }

    /**
     * Handle requests until reaches maximum capacity
     *
     * @param requestList the requests to be handled
     */
    public void handleRequests(List<Request> requestList) {
        for (Request request : requestList) {
            //If the guests number exceeds the limit, reject the rest of requests
            if (seatsTaken + request.guestNum > capacity) {
                break;
            }
            seatsTaken = seatsTaken + request.guestNum;
            assignSeats(request.seqNum, request.guestNum);
        }
    }

    /**
     * Assign seats, try assigning consecutive seats at first, then assign scattered seats
     * fill taken seats with seqNum, add guestNum to seatsTaken
     *
     * @param seqNum   seqNum of request
     * @param guestNum guestNum of request
     */
    private void assignSeats(int seqNum, int guestNum) {
        boolean hasAssigned = assignConsecutiveSeats(seqNum, guestNum);
        if (!hasAssigned) {
            assignScatteredSeats(seqNum, guestNum);
        }
    }

    /**
     * Loop through every row, assign consecutive empty seats
     *
     * @param seqNum   seqNum of request
     * @param guestNum guestNum of request
     * @return true if seats have been assigned
     */
    private boolean assignConsecutiveSeats(int seqNum, int guestNum) {
        for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
            int remainingSeat = remainingSeats.get(rowIndex);
            if (remainingSeat >= guestNum) {
                int colIndex = Constants.COL - remainingSeat;
                Arrays.fill(seats[rowIndex], colIndex, colIndex + guestNum, seqNum);
                remainingSeats.put(rowIndex, remainingSeat - guestNum);
                Set<Coordinate> coordinateSet = new TreeSet<>();
                for (int seatIndex = 0; seatIndex < guestNum; seatIndex++) {
                    Coordinate coordinate = new Coordinate(rowIndex, colIndex + seatIndex);
                    coordinateSet.add(coordinate);
                }
                givenSeats.put(seqNum, coordinateSet);
                return true;
            }
        }
        return false;
    }

    /**
     * Loop through every row, if this row has empty seats, then assign
     *
     * @param seqNum   seqNum of request
     * @param guestNum guestNum of request
     */
    private void assignScatteredSeats(int seqNum, int guestNum) {
        int rowIndex = 0;
        Set<Coordinate> coordinateSet = new TreeSet<>();
        while (guestNum > 0) {
            int remainingSeat = remainingSeats.get(rowIndex);
            if (remainingSeat > 0) {
                int seatsAssignedThisRow = Math.min(remainingSeat, guestNum);
                int colIndex = Constants.COL - remainingSeat;
                remainingSeat = remainingSeat > guestNum ? remainingSeat - guestNum : 0;
                remainingSeats.put(rowIndex, remainingSeat);
                guestNum = guestNum - seatsAssignedThisRow;
                Arrays.fill(seats[rowIndex], colIndex, colIndex + seatsAssignedThisRow, seqNum);
                for (int seatIndex = 0; seatIndex < seatsAssignedThisRow; seatIndex++) {
                    Coordinate coordinate = new Coordinate(rowIndex, colIndex + seatIndex);
                    coordinateSet.add(coordinate);
                }

            }
            rowIndex++;
        }
        givenSeats.put(seqNum, coordinateSet);
    }

    /**
     * Get the seat assignments in the format of string
     */
    public String getSeatAssignmentString() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat(Constants.RESERVATION_NUMBER_FORMAT);
        for (Map.Entry<Integer, Set<Coordinate>> entry : givenSeats.entrySet()) {
            sb.append(decimalFormat.format(entry.getKey()));
            sb.append(" ");
            Set<Coordinate> coordinateSet = entry.getValue();
            String prefix = "";
            for (Coordinate coordinate : coordinateSet) {
                sb.append(prefix);
                prefix = ",";
                sb.append(coordinate.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void optimize() {

    }

}
