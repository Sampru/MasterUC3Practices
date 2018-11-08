package com.cnebrera.uc3.tech.lesson2.tcp;

import com.cnebrera.uc3.tech.lesson2.util.FixSizeMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * TCP server that send messages of fixed size to a client
 */
public class TCPFixSizeServer {
    public static void main(String argv[]) {
        TCPFixSizeServer server = new TCPFixSizeServer();
        server.acceptConnections();

    }

    private void acceptConnections() {
        // Open socket with resource
        try (ServerSocket socket = new ServerSocket(16000)) {
            // Get a client connection
            try (Socket connection = socket.accept()) {
                sendMessagesToClient(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send fix size messages to a connected socket client
     *
     * @param connectionSocket the client connection socket
     * @throws IOException          exception thrown if there is an IO problem
     * @throws InterruptedException exception thrown if interrupted
     */
    private static void sendMessagesToClient(final Socket connectionSocket) throws IOException, InterruptedException {
        try (OutputStream outputStream = connectionSocket.getOutputStream()) {

            // The buffer to write the
            ByteBuffer sendBuffer = ByteBuffer.allocate(FixSizeMessage.MSG_BINARY_SIZE);

            while (true) {
                // Clear the buffer
                sendBuffer.clear();

                // Generate random message
                final FixSizeMessage rndMsg = FixSizeMessage.generateRandomMsg();

                // Convert to binary
                rndMsg.toBinary(sendBuffer);

                outputStream.write(sendBuffer.array());

                outputStream.flush();

                System.out.println("Message sent: " + rndMsg.toString());

                // Wait a bit before sending the next message
                Thread.sleep(1000);
            }
        }
    }
}
