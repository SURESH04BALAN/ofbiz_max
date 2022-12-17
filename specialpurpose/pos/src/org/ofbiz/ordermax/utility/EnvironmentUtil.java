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

public class EnvironmentUtil {

    public enum OperatingSystem {

        WINDOWS, UNIX_LIKE
    };
    private static OperatingSystem operatingSystem;

    static {
        operatingSystem = (File.separatorChar == '\\') ? OperatingSystem.WINDOWS : OperatingSystem.UNIX_LIKE;
    }

    public static void setOperatingSystem(OperatingSystem aOperatingSystem) {
        operatingSystem = aOperatingSystem;
    }

    public static OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public static boolean isUnixLikeOperatingSystem() {
        return operatingSystem == OperatingSystem.UNIX_LIKE;
    }

    public static boolean isWindows() {
        return operatingSystem == OperatingSystem.WINDOWS;
    }

    public static boolean symlinksSupported() {
        return isUnixLikeOperatingSystem();
    }
}
