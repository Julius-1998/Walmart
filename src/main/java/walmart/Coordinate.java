package walmart;

public class Coordinate implements Comparable<Coordinate> {
    int row;
    int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Convert coordinate to string. For example, (0,0) -> (A,1);
     * @return
     */
    @Override
    public String toString() {
        return Character.toString((char) ('A' + row)) + (col + 1);
    }

    @Override
    public int compareTo(Coordinate o) {
        if(this.row != o.row){
            return this.row > o.row ? 1 : -1;
        }
        if(this.col != o.col){
            return this.col > o.col ? 1 : -1;
        }
        return 0;

    }
}
