package remote;

import java.rmi.Remote;

public interface CandidateInterface extends Remote {
    public String toString();
    public void addVote(int number);
    public void deleteVote(int number);
    public int getScore();
    public int getRank();
}
