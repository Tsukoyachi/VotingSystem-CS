package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterface {
    private Scanner scanner;
    private String studentNumber;
    public Client() throws RemoteException {
        super();
        this.scanner = new Scanner(System.in);
        this.studentNumber = null;
    }

    @Override
    public String fetchStudentNumber() throws RemoteException {
        if(studentNumber != null) {
            return studentNumber;
        }
        System.out.println("Enter your student number : ");
        studentNumber = scanner.nextLine();
        return studentNumber;
    }
}
