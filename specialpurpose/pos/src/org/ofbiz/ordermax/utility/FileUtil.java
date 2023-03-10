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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.security.MessageDigest;

/**
 * A file utility class
 * 
* @author Philipp C. Heckel <philipp.heckel@gmail.com>
 */
public class FileUtil {

    public static String getRelativePath(File base, File file) {
        return removeTrailingSlash(base.toURI().relativize(file.toURI()).getPath());
    }

    public static String getRelativeDatabasePath(File base, File file) {
        String relativeFilePath = getRelativePath(base, file);
        return getDatabasePath(relativeFilePath);
    }

    public static String getDatabasePath(String filePath) {
// Note: This is more important than it seems. Unix paths may contain backslashes
// so that 'black\white.jpg' is a perfectly valid file path. Windows file names
// may never contain backslashes, so that '\' can be safely transformed to the
// '/'-separated database path!
        if (EnvironmentUtil.isWindows()) {
            return filePath.toString().replaceAll("\\\\", "/");
        } else {
            return filePath;
        }
    }

    public static String removeTrailingSlash(String filename) {
        if (filename.endsWith("/")) {
            return filename.substring(0, filename.length() - 1);
        } else {
            return filename;
        }
    }

    public static File getCanonicalFile(File file) {
        try {
            return file.getCanonicalFile();
        } catch (IOException ex) {
            return file;
        }
    }

    public static void appendToOutputStream(InputStream inputStream, OutputStream outputStream, boolean closeOutputStream) throws IOException {
        appendToOutputStream(inputStream, outputStream);
        if (closeOutputStream) {
            outputStream.close();
        }
    }

    public static void appendToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buf = new byte[4096];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        inputStream.close();
    }

    public static byte[] createChecksum(File filename, String digestAlgorithm) throws Exception {
        FileInputStream fis = new FileInputStream(filename);
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance(digestAlgorithm);
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    public static boolean isFileLocked(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            return false;
        }
        if (isSymlink(file)) {
            return false;
        }
        RandomAccessFile randomAccessFile = null;
        boolean fileLocked = false;
        try {
// Test 1 missing permissions or locked file parts. If File is not readable == locked
            randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.close();
        } catch (Exception e) {
            fileLocked = true;
        }
        if (!fileLocked && file.canWrite()) {
            try {
// Test 2:Locked file parts
                randomAccessFile = new RandomAccessFile(file, "rw");
// Test 3: Set lock and release it again
                FileLock fileLock = randomAccessFile.getChannel().tryLock();
                if (fileLock == null) {
                    fileLocked = true;
                } else {
                    try {
                        fileLock.release();
                    } catch (Exception e) { /* Nothing */

                    }
                }
            } catch (Exception e) {
                fileLocked = true;
            }
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) { /* Nothing */

                }
            }
        }
        return fileLocked;
    }

    public static boolean isSymlink(File file) {
        return Files.isSymbolicLink(Paths.get(file.getAbsolutePath()));
    }

    public static String readSymlinkTarget(File file) {
        try {
            return Files.readSymbolicLink(Paths.get(file.getAbsolutePath())).toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static void createSymlink(String targetPathStr, File symlinkFile) throws Exception {
        Path targetPath = Paths.get(targetPathStr);
        Path symlinkPath = Paths.get(symlinkFile.getPath());
        Files.createSymbolicLink(symlinkPath, targetPath);
    }

    public static String dosAttrsToString(DosFileAttributes dosFileAttributes) {
        return LimitedDosFileAttributes.toString(dosFileAttributes);
    }

    public static LimitedDosFileAttributes dosAttrsFromString(String dosFileAttributes) {
        return new LimitedDosFileAttributes(dosFileAttributes);
    }

    /**
     * Replaces the <tt>exists()</tt> method in the <tt>File</tt> class by
     * taking symlinks into account. The method returns <tt>true</tt> if the
     * file exists, <tt>false</tt> otherwise.
     *     
* <p>
     * Note: The method returns <tt>true</tt>, if a symlink exists, but points
     * to a non-existing target. This behavior is different from the classic
     * {@link #exists(File) exists()}-method in the <tt>File</tt> class.
     *     
* @param file A file
     * @return Returns <tt>true</tt> if a file exists (even if its symlink
     * target does not), <tt>false</tt> otherwise
     */
    public static boolean exists(File file) {
        try {
            return Files.exists(Paths.get(file.getAbsolutePath()), LinkOption.NOFOLLOW_LINKS);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public static boolean isDirectory(File file) {
        try {
            return Files.isDirectory(Paths.get(file.getAbsolutePath()), LinkOption.NOFOLLOW_LINKS);
        } catch (InvalidPathException e) {
            return false;
        }
    }
    
}
