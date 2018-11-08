package com.cnebrera.uc3.tech.lesson2.tcp;

import com.cnebrera.uc3.tech.lesson2.util.VariableSizeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * TCP client that read messages of variable size from a server
 */
public class TCPVarSizeClient {
    public static void main(String argv[]) throws Exception {
        // Create the server connection
        try (Socket connection = new Socket(InetAddress.getLocalHost(), 16000)) {
            // Get the input stream
            try (InputStream istream = connection.getInputStream()) {
                // Read messages non stop
                while (true) {
                    readMessages(istream);
                }
            }
        }
    }

    /**
     * Send messages into the input stream
     *
     * @param inputStream the input stream connected to the socket
     * @throws IOException exception if there is an input output problem
     */
    private static void readMessages(final InputStream inputStream) throws IOException {
        // The buffer to read the header
        final byte[] header = new byte[4];

        // Wait to have at least the header
        while (inputStream.available() < 4) ;

        // Read the header
        inputStream.read(header);

        final int msgSize = ByteBuffer.wrap(header).getInt();

        System.out.println("Read MsgSize " + msgSize);

        // The buffer to read the message bytes
        final byte[] msgBytes = new byte[msgSize];

        // Wait for the whole message to be ready
        while (inputStream.available() < msgSize) ;

        // Read the message bytes
        inputStream.read(msgBytes);

        // Create the message
        final VariableSizeMessage msg = VariableSizeMessage.readMsgFromBinary(msgSize, ByteBuffer.wrap(msgBytes));

        System.out.println("Msg received " + msg);
    }
}
