package com.cnebrera.uc3.tech.lesson2.tcp;

import com.cnebrera.uc3.tech.lesson2.util.VariableSizeMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * TCP server that send messages of variable size to a client
 */
public class TCPVarSizeServer {
    
    private static int SIZE = 8;

    public static void main(String argv[]) throws Exception {
        // Configure the message size
        if (argv.length > 0) {
            SIZE = Integer.parseInt(argv[0]);
        }
        System.out.println("Using message size: " + SIZE);

        // Open socket with resource
        try (ServerSocket soc = new ServerSocket(16000)) {
            // Get a client connection
            try (Socket con = soc.accept()) {
                sendMessagesToClient(con);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send messages to a client given the client connection socket
     *
     * @param connectionSocket the client connection socket
     * @throws IOException          exception thrown if there is an input-output problem
     * @throws InterruptedException exception thrown if interrupted while sleeping
     */
    private static void sendMessagesToClient(final Socket connectionSocket) throws IOException, InterruptedException {
        // Get the output stream
        try (OutputStream outputStream = connectionSocket.getOutputStream()) {


            // The buffer to write the msg size header, the message size will be an integer (4 bytes)
            final ByteBuffer headerBuffer = ByteBuffer.allocate(4);

            while (true) {
                // Clear the buffer
                headerBuffer.clear();

                // Generate random message
                final VariableSizeMessage rndMsg = VariableSizeMessage.generateRandomMsg(SIZE);

                // Convert to binary
                final ByteBuffer binaryMessage = rndMsg.toBinary();

                // Serialize the msg size
                headerBuffer.putInt(binaryMessage.position());

                // Flip the binary message prior to writing to adjust position to 0 and limit to the end of the buffer
                binaryMessage.flip();

                System.out.println("About to send msg of size " + (binaryMessage.limit() + headerBuffer.capacity()));

                // Write the header with the message size
                outputStream.write(headerBuffer.array());

                // Write the contents
                outputStream.write(binaryMessage.array(), 0, binaryMessage.limit());

                // Force it to be sent without batching
                outputStream.flush();

                System.out.println("Message sent: " + rndMsg.toString());

                // Wait a bit
                Thread.sleep(1000);
            }
        }
    }
}
