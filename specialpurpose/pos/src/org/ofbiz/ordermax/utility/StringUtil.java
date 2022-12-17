/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.utility;

/**
 *
 * @author BBS Auctions
 */
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;
/**
* Utility class for common application string functions.
*
* @author Philipp C. Heckel <philipp.heckel@gmail.com>
*/
public class StringUtil {
private static final String MACHINE_NAME_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
private static final int MACHINE_NAME_LENGTH = 20;
private static Random random = new Random();
/**
* Transforms a string to a camel case representation, including the
* first character.
*
* <p>Examples:
* <ul>
* <li><tt>toCamelCase("hello world") -&gt; "HelloWorld"</tt></li>
* <li><tt>toCamelCase("hello_world") -&gt; "HelloWorld"</tt></li>
* </ul>
*/
public static String toCamelCase(String str) {
StringBuilder sb = new StringBuilder();
for (String s : str.split("[-_ ]")) {
if (s.length() > 0) {
sb.append(Character.toUpperCase(s.charAt(0)));
if (s.length() > 1) {
sb.append(s.substring(1, s.length()).toLowerCase());
}
}
}
return sb.toString();
}
/**
* Converts a byte array to a lower case hex representation.
* If the given byte array is <tt>null</tt>, an empty string is returned.
*/
public static String toHex(byte[] bytes) {
if (bytes == null) {
return "";
}
else {
return DatatypeConverter.printHexBinary(bytes).toLowerCase();
}
}
/**
* Creates byte array from a hex represented string.
*/
public static byte[] fromHex(String s) {
return DatatypeConverter.parseHexBinary(s); // fast!
}
public static byte[] toBytesUTF8(String s) {
try {
return s.getBytes("UTF-8");
}
catch (UnsupportedEncodingException e) {
throw new RuntimeException("JVM does not support UTF-8 encoding.", e);
}
}
/**
* Generates a random machine name of length 20. Only uses characters
* A-Z/a-z (in order to always create valid serialized vector clock representations)
*/
public static String createRandomMachineName() {
StringBuilder sb = new StringBuilder(MACHINE_NAME_LENGTH);
for (int i = 0; i < MACHINE_NAME_LENGTH; i++) {
sb.append(MACHINE_NAME_CHARS.charAt(random.nextInt(MACHINE_NAME_CHARS.length())));
}
return sb.toString();
}
public static <T> String join(List<T> objects, String delimiter, StringJoinListener<T> listener) {
StringBuilder objectsStr = new StringBuilder();
for (int i=0; i<objects.size(); i++) {
if (listener != null) {
objectsStr.append(listener.getString(objects.get(i)));
}
else {
objectsStr.append(objects.get(i).toString());
}
if (i < objects.size()-1) {
objectsStr.append(delimiter);
}	
}
return objectsStr.toString();
}
public static <T> String join(List<T> objects, String delimiter) {
return join(objects, delimiter, null);
}
public static <T> String join(T[] objects, String delimiter, StringJoinListener<T> listener) {
return join(Arrays.asList(objects), delimiter, listener);
}
public static <T> String join(T[] objects, String delimiter) {
return join(Arrays.asList(objects), delimiter, null);
}
public static interface StringJoinListener<T> {
public String getString(T object);
}	
}


