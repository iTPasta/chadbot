package fr.itpasta.chadbot.generic;

import net.dv8tion.jda.internal.entities.SystemMessage;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public final class StringUtils {

    public static String cutStringUntil(String strToCut, String word, boolean takeFirstPart, boolean deleteWord) {
        String[] cutStr = strToCut.split(word);
        if (takeFirstPart) {
            if (deleteWord) return cutStr[0];
            else return cutStr[0] + word;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < cutStr.length; i++) {
                if (!deleteWord) sb.append(word);
                sb.append(cutStr[i]);
            }
            return sb.toString();
        }
    }

    public static String cutDelimitedPart(String str, String startDelimiter, String endDelimiter) {
        String[] cutStr = str.split(startDelimiter);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < cutStr.length; i++) {
            sb.append(cutStr[i]);
            if (i != cutStr.length - 1) sb.append(startDelimiter);
        }
        // TODO: Possible erreur.
        return sb.toString().split(endDelimiter)[0];
    }

    public static String[][] createTabFromString(String str, char lineDelimiter, char columnDelimiter) {
        ArrayList<ArrayList<String>> tab = new ArrayList<>();
        ArrayList<String> to_add = new ArrayList<>();
        StringBuilder currentStr = new StringBuilder();
        boolean quote = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\'') quote = true;
            if (lineDelimiter == str.charAt(i)) {
                to_add.add(currentStr.toString());
                currentStr = new StringBuilder();
                tab.add(new ArrayList<String>(to_add));
                to_add.clear();
            } else if (
                    (quote && columnDelimiter == str.charAt(i) && str.charAt(i-1) == '\'') ||
                    (!quote && columnDelimiter == str.charAt(i))
            ) {
                to_add.add(currentStr.toString());
                currentStr = new StringBuilder();
                quote = false;
            } else {
                currentStr.append(str.charAt(i));
            }
        }

        return tab.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }

    public static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static char byteToChar(byte b) {
        return b < 0 ? (char) (256 + b) : (char) b;
    }

    private static char[] byteArrayToCharArray(byte[] bytes) {
        int lenght = bytes.length;
        char[] chars = new char[lenght];
        for (int i = 0; i < lenght; i++) {
            chars[i] = byteToChar(bytes[i]);
        }
        return chars;
    }

    private static char[] readChars(URL url) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (InputStream is = url.openStream()) {
            byte[] byteChunk = new byte[4096];
            int n;

            while ((n = is.read(byteChunk)) > 0) {
                baos.write(byteChunk, 0, n);
            }
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
            e.printStackTrace();
        }

        return byteArrayToCharArray(baos.toByteArray());
    }

    public static String getHTML(String urlStr) throws IOException {
        char[] chars = readChars(new URL(urlStr));
        return String.valueOf(chars);
    }
}
