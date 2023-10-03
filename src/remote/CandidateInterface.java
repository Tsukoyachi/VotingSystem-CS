package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CandidateInterface extends Remote {
    public void addVote(int number) throws RemoteException;
    public void deleteVote(int number) throws RemoteException;
    public int getScore() throws RemoteException;
    public int getRank() throws RemoteException;
    public Pitch getPitch() throws RemoteException;
    public String getPresentation() throws RemoteException;
}
