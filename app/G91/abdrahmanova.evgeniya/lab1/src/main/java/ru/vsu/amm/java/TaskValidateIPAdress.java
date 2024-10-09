package ru.vsu.amm.java;
import java.util.Scanner;

public class TaskValidateIPAdress {

    private static boolean isValidIpv4(String ipAdress) {

        String[] parts = ipAdress.split("\\.");

        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            if (part.isEmpty() || part.length() > 3)
                return false;
            if (part.charAt(0) == '0' && part.length() > 1)
                return false;
            int num = Integer.parseInt(part);
            if (num < 0 || num > 255)
                return false;
        }
            return true;
    }

    private static boolean isValidIpv6(String ipAdress) {
        String[] parts = ipAdress.split(":");

        if (parts.length != 8) {
            return false;
        }

        for (String part : parts) {
            if (part.isEmpty() || part.length() > 4)
                return false;

            /*for (char c : part.toCharArray()) {
                if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                    return false;
                }
            }*/

            if (!part.matches("^[0-9a-fA-F]+$"))
                return false;
        }
        return true;
    }

    public static void main (String [] args) /*throws UnsupportedEncodingException*/ {
        //System.setOut(new PrintStream(System.out, true, "cp866"));
        //System.setErr(new PrintStream(System.err, true, "cp866"));
        System.out.println("Input Ip --> ");

        Scanner scanner = new Scanner(System.in);
        String queryIP = scanner.nextLine();
        System.out.println("Ip: ");
        System.out.println(queryIP);

        if (isValidIpv4(queryIP))
            System.out.println("Ipv4");
        else if (isValidIpv6(queryIP))
            System.out.println("Ipv6");
        else
            System.out.println("Neither");

            scanner.close();
    }
}