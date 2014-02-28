package com.gdit.capture.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MacAddress {

    public static void getMacAddress(String ip) {
        try {
            //InetAddress address = InetAddress.getLocalHost();
           InetAddress address = InetAddress.getByName(ip);

            /*
16.             * Get NetworkInterface for the current host and then read the
17.             * hardware address.
18.             */
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    /*
24.                     * Extract each array of mac address and convert it to hexa with the
25.                     * following format 08-00-27-DC-4A-9E.
26.                     */
                    String s = "";
                    for (int i = 0; i < mac.length; i++) {
                       s+= s.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
                        
                    }
                    System.out.println(s);
               } else {
                    System.out.println("Address doesn't exist or is not accessible.");
               }
            } else {
                System.out.println("Network Interface for the specified address is not found.");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}