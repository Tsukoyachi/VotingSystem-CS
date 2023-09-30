package client;

import java.rmi.RemoteException;

public interface VoteInterface {
    public int getRank() throws RemoteException;
    public int getValue() throws RemoteException;
}
