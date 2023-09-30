import client.Client;
import client.ClientInterface;
import client.Vote;
import client.VoteInterface;
import exception.BadCredentialsException;
import exception.HaveAlreadyAskedOTP;
import remote.VotingSystemInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws RemoteException, NotBoundException, BadCredentialsException, HaveAlreadyAskedOTP {
        Scanner scanner = new Scanner(System.in);
        Registry registry = LocateRegistry.getRegistry(1099);
        VotingSystemInterface votingSystem = (VotingSystemInterface) registry.lookup("votingSystem");
        ClientInterface client = new Client();

        // Check if the user need his password or if he already know it
        System.out.println("Hello, we'll proceed into the vote, but before that do you know your password ? (Y for yes and N for no) : ");
        String choice = scanner.nextLine();
        while (!choice.equals("Y") && !choice.equals("N")) {
            System.out.println("You need to enter either Y or N in order to get or not your password :");
            choice = scanner.nextLine();
        }

        String studentNumber = null;
        String password = null;

        if(choice.equals("Y")) {
            password = votingSystem.askUserOTP(client);
            System.out.println("Here is your password : " + password);
            System.out.println("Please memorize it well, because it won't be provided anymore");
        }

        studentNumber = client.fetchStudentNumber();
        if(password == null) {
            System.out.println("Please enter your password :");
            password = scanner.nextLine();
        }

        System.out.println("Here are the candidate for this election : ");
        List<String> candidates = votingSystem.askListOfCandidate();
        for (String tmp : candidates) {
            System.out.println(" - " + tmp);
        }

        List<VoteInterface> votes = new ArrayList<>();
        for(int i=0; i<candidates.size(); i++){
            System.out.println("Which value between 0-3 (with 0 the worst and 3 the best) do you want to give to " + candidates.get(i) + "?");
            int candidateVoteValue = scanner.nextInt();
            if(candidateVoteValue < 0 || candidateVoteValue > 3){
                System.out.println("Please enter a value between 0-3");
                i--;
                continue;
            }
            votes.add(new Vote(i+1, candidateVoteValue));
        }

        Boolean didVoteSucceded = votingSystem.emitVote(studentNumber,password,votes);
        if(didVoteSucceded) {
            System.out.println("Your vote has been acknowledge");
        } else {
            System.out.println("An error occured with the vote");
            System.out.println(" - It can already be ended\n - Or a problem happened with the vote processing");
        }

        System.out.println("Let's see if the election is finished : ");
        System.out.print(votingSystem.checkResultOfElection());

        System.exit(0);
    }
}
