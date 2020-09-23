package ru.nsu.fit.cdrann;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IPTable {
    private static final Map<String, Long> currIpTable = new HashMap<>();

    static void printCurrentIPTable() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        System.out.println("Table update:" + formatter.format(date));
        currIpTable.forEach((key, value) -> System.out.println("IP:" + key));
        System.out.println();
    }

    static void checkIpState(String ipAndPort) {
        if (currIpTable.put(ipAndPort, System.currentTimeMillis()) == null) {
            printCurrentIPTable();
        }
    }

    static void checkCurrIpTable(Logger logger) {
//        currIpTable.entrySet().removeIf()
        for (Iterator<Map.Entry<String, Long>> iterator = currIpTable.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Long> ipAndPort = iterator.next();
            if (System.currentTimeMillis() - ipAndPort.getValue() > Constants.TABLE_TIMEOUT) {
                logger.log(Level.INFO, "Lost connection with: " + ipAndPort.getKey());

                iterator.remove();
                printCurrentIPTable();
            }
        }
    }

}
