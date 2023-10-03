import client.Client;
import client.ClientInterface;
import client.Vote;
import client.VoteInterface;
import exception.BadCredentialsException;
import exception.HaveAlreadyAskedOTP;
import remote.CandidateInterface;
import remote.Pitch;
import remote.VotingSystemInterface;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, NotBoundException, BadCredentialsException, HaveAlreadyAskedOTP, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        Registry registry = LocateRegistry.getRegistry(1099);
        VotingSystemInterface votingSystem = (VotingSystemInterface) registry.lookup("votingSystem");
        ClientInterface client = new Client();

        // Check if the user need his password or if he already knows it
        System.out.println("Hello, we'll proceed into the vote, but before that do you want to know your password ? (Y for yes and N for no) : ");
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
        List<CandidateInterface> candidates = votingSystem.askListOfCandidate();
        for (CandidateInterface tmp : candidates) {
            System.out.println(" - " + tmp.getPresentation());
        }
        System.out.println("What do you want to do ? (V for vote and P to view pitch) : ");
        choice = scanner.nextLine();
        while (!choice.equals("V") && !choice.equals("P")) {
            System.out.println("You need to enter either V or P in order to vote or see the pitch :");
            choice = scanner.nextLine();
        }
        if(choice.equals("P")) {
            System.out.println("Which candidate do you want to see the pitch ? (Enter the rank number of the candidate) : ");
            int candidateNumber = scanner.nextInt();
            while (candidateNumber < 1 || candidateNumber > candidates.size()) {
                System.out.println("You need to enter a number between 1 and " + candidates.size() + " :");
                candidateNumber = scanner.nextInt();
            }
            CandidateInterface tmp = candidates.get(candidateNumber - 1);
            System.out.println("Here is the pitch of the candidate " + tmp.getPresentation() + " : ");
            Pitch pitch = tmp.getPitch();
            if(pitch.getType().equals("text")) {
                System.out.println("Text pitch: " +pitch.getTextElement());
            } else if(pitch.getType().equals("video")) {
                Files.write(Paths.get(pitch.getTextElement()), pitch.getVideoElement());
                File videoFile = new File(pitch.getTextElement());
                System.out.println("Video downloaded to " + videoFile.getAbsoluteFile());
                Desktop.getDesktop().open(videoFile);
                System.out.println("The pitch is a video, you can find it in the ressource folder");
            }
            System.exit(0);
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
            System.out.println("An error occurred with the vote");
            System.out.println(" - It can already be ended\n - Or a problem happened with the vote processing");
        }

        System.out.println("Let's see if the election is finished : ");
        System.out.print(votingSystem.checkResultOfElection());

        System.exit(0);
    }
}
