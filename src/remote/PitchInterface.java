package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PitchInterface extends Remote {
    public String getType() throws RemoteException;
    public String getTextElement() throws RemoteException;
    public byte[] getVideoElement() throws IllegalAccessException, RemoteException;
}
