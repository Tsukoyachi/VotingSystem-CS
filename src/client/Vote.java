package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Vote extends UnicastRemoteObject implements VoteInterface {
    private int rank;
    private int score;
    public Vote(int rank, int score) throws RemoteException {
        super();
        this.rank = rank;
        this.score = score;
    }

    public int getRank() throws RemoteException {
        return this.rank;
    }

    public int getValue() throws RemoteException {
        return this.score;
    }
}
