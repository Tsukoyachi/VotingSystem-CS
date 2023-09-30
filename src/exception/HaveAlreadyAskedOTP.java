package exception;

public class HaveAlreadyAskedOTP extends Exception{
    public HaveAlreadyAskedOTP(String msg) {
        super(msg);
    }
}
