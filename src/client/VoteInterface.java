package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VoteInterface extends Remote {
    public int getRank() throws RemoteException;
    public int getValue() throws RemoteException;
}
