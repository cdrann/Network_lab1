package ru.nsu.fit.cdrann;

import java.net.InetAddress;

public class AddressParser {
    public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result;
        result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
}
