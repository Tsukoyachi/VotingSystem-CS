package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterface {
    private Scanner scanner;
    public Client() throws RemoteException {
        super();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String fetchStudentNumber() throws RemoteException {
        System.out.println("Enter your student number : ");
        return scanner.nextLine();
    }
}
