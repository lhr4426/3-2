package pratice;

import java.io.*;
import java.net.*;

public class EncoderTest {
    public static void main(String[] args) {
        try {
            System.out.println(URLEncoder.encode("This string has spaces",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This*string*has*asterisks",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This%string%has%percent%signs",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This+string+has+pluses",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This/string/has/slashes",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This\"string\"has\"quote\"marks",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This:string:has:colons",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This~string~has~tildes",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This(string)has(parentheses)",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This.string.has.periods",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This=string=has=equals=signs",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("This&string&has&ampersands",
                    "UTF-8"));
            System.out.println(URLEncoder.encode("Thiséstringéhasé non - ASCII characters", "utf-8"));
            System.out.println(URLEncoder.encode("http://www.google.com/search?q=한밭대학교", "utf-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }
}