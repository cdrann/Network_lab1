package ru.nsu.fit.cdrann;

import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;

public class CopyFinder {
    private final static Logger logger = Logger.getLogger(CopyFinder.class.getName());

    private InetAddress multicastGroup;
    private MulticastSocket multicastSocket;
    private DatagramSocket datagramSocket;

    public CopyFinder(String multicastGroupIpAddr) {
        try {
            multicastGroup = InetAddress.getByName(multicastGroupIpAddr);

            multicastSocket = new MulticastSocket(Constants.PORT);
            multicastSocket.joinGroup(multicastGroup);
            multicastSocket.setSoTimeout(Constants.RECEIVE_TIMEOUT);

            datagramSocket = new DatagramSocket();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        logger.log(Level.INFO, "New host joins multicast group");

        while (true) {
            try {
                datagramSocket.send(new DatagramPacket(Constants.MSG.getBytes(), Constants.MSG.getBytes().length,
                        multicastGroup, Constants.PORT));
                long end = System.currentTimeMillis() + Constants.RECEIVE_TIMEOUT;

                while (System.currentTimeMillis() < end) {
                    byte[] buffer = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                    try {
                        multicastSocket.receive(packet);
                        long packetPort = packet.getPort();

                        logger.log(Level.INFO, "Received packet from port:" + packetPort);
                    } catch (SocketTimeoutException ex) {
                        logger.log(Level.INFO, "Receive timeout exception");
                        break;
                    }

                    IPTable.checkIpState(packet.getAddress().getHostAddress() + " port:" + packet.getPort());
                }

                IPTable.checkCurrIpTable(logger);
            } catch (IOException ex) {
                multicastSocket.close(); // should we?
                datagramSocket.close();

                ex.printStackTrace();
                break;
            }
        }

        multicastSocket.close();
        datagramSocket.close();
    }
}
