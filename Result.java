public class Result {
    private final int index;
    private final double number;

    Result(double number, int index) {
        this.number = number;
        this.index = index;
    }

    public double getNumber() {
        return this.number;
    }

    public int getIndex() {
        return this.index;
    }
}
