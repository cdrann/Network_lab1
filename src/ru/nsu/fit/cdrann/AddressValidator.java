package ru.nsu.fit.cdrann;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressValidator {
    public static boolean isValidRange(String ipToCheck) {
        try {
            long ipLo = AddressParser.ipToLong(InetAddress.getByName(Constants.ipLower));
            long ipHi = AddressParser.ipToLong(InetAddress.getByName(Constants.ipUpper));
            long ipGiven = AddressParser.ipToLong(InetAddress.getByName(ipToCheck));

            return (ipGiven >= ipLo && ipGiven <= ipHi);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }
}
