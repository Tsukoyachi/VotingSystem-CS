package remote;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class Pitch implements Serializable {
    private String type;
    private String element;

    public Pitch(String type, String element) {
        this.type = type;
        this.element = element;
    }

    public String getType() {
        return this.type;
    }

    public String getTextElement() {
        return this.element;
    }

    public byte[] getVideoElement() throws IllegalAccessException, RemoteException {
        if(type.equals("text")) {
            throw new IllegalAccessException("The pitch isn't a video");
        }
        try {
            Path path = Paths.get(this.element);
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new RemoteException("Error downloading file", e);
        }
    }
}
