package edu.byu.cs.tweeter.model.net.response;

public abstract class CountResponse extends Response{
    private int count;

    public CountResponse(boolean success, int count) {
        super(success);
        this.count = count;
    }

    public CountResponse(boolean success, String message, int count) {
        super(success, message);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
