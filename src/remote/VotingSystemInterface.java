package remote;

import client.ClientInterface;
import client.VoteInterface;
import exception.BadCredentialsException;
import exception.HaveAlreadyAskedOTP;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VotingSystemInterface extends Remote {
    public List<String> askListOfCandidate() throws RemoteException;
    public String askUserOTP(ClientInterface client) throws RemoteException, BadCredentialsException, HaveAlreadyAskedOTP;
    public Boolean emitVote(String studentNumber, String otp, List<VoteInterface> votes) throws RemoteException, BadCredentialsException;
    public String checkResultOfElection() throws RemoteException;
}
