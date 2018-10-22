package com.cnebrera.uc3.tech.lesson2.tcp;

import com.cnebrera.uc3.tech.lesson2.util.FixSizeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * TCP client that read messages of fixed size from a server
 */
public class TCPFixSizeClient {
    public static void main(String argv[]) throws Exception {
        // Create the server connection
        try (Socket connection = new Socket(InetAddress.getLocalHost(), 16000)) {
            // Get the input stream
            try (InputStream istream = connection.getInputStream()) {
                // Read messages non stop
                while (true) {
                    readMessage(istream);
                }
            }
        }
    }

    /**
     * Read a message from the given input Stream
     *
     * @param inputStream the input stream
     * @throws IOException exception thrown if there is a problem
     */
    private static void readMessage(final InputStream inputStream) throws IOException {
        // The buffer to read the message bytes
        final byte[] msgBytes = new byte[FixSizeMessage.MSG_BINARY_SIZE];

        // Wait until message size is enough, then read it
        while (inputStream.available() < msgBytes.length) ;
        inputStream.read(msgBytes);

        // Create the message
        final FixSizeMessage msg = FixSizeMessage.readMsgFromBinary(ByteBuffer.wrap(msgBytes));

        System.out.println("Received message: " + msg);
    }
}
