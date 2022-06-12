package server;

import java.io.IOException;

public class myServer {
    public static void main(String[] args) throws IOException {
        server s = new server(); // to invoke gui
        s.waitingForClient(); // to ait for the client
        s.setIoStream(); // to set the streams in order to transfer the data

    }
}
