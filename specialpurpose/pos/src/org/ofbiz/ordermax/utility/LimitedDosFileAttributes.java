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
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
/**
* @author Philipp C. Heckel <philipp.heckel@gmail.com>
*/
public class LimitedDosFileAttributes implements DosFileAttributes {
private String dosAttributes;
public LimitedDosFileAttributes(String dosAttributes) {
if (dosAttributes == null || dosAttributes.length() != 4) {
throw new IllegalArgumentException("Given DOS attribute string is invalid: " + dosAttributes);
}
this.dosAttributes = dosAttributes.toLowerCase();	
}
@Override
public boolean isReadOnly() {
return dosAttributes.charAt(0) == 'r';
}
@Override
public boolean isHidden() {
return dosAttributes.charAt(1) == 'h';
}
@Override
public boolean isArchive() {
return dosAttributes.charAt(2) == 'a';
}
@Override
public boolean isSystem() {
return dosAttributes.charAt(3) == 's';
}
@Override
public String toString() {
return toString(this);
}
public static String toString(DosFileAttributes dosFileAttributes) {
StringBuilder sb = new StringBuilder();
sb.append(dosFileAttributes.isReadOnly() ? "r" : "-");
sb.append(dosFileAttributes.isHidden() ? "h" : "-");
sb.append(dosFileAttributes.isArchive() ? "a" : "-");
sb.append(dosFileAttributes.isSystem() ? "s" : "-");
return sb.toString();	
}
@Override
public long size() {
throw new RuntimeException("Not supported.");
}
@Override
public FileTime lastModifiedTime() {
throw new RuntimeException("Not supported.");
}
@Override
public FileTime lastAccessTime() {
throw new RuntimeException("Not supported.");
}
@Override
public boolean isSymbolicLink() {
throw new RuntimeException("Not supported.");
}
@Override
public boolean isRegularFile() {
throw new RuntimeException("Not supported.");
}
@Override
public boolean isOther() {
throw new RuntimeException("Not supported.");
}
@Override
public boolean isDirectory() {
throw new RuntimeException("Not supported.");
}
@Override
public Object fileKey() {
throw new RuntimeException("Not supported.");
}
@Override
public FileTime creationTime() {
throw new RuntimeException("Not supported.");
}
}
