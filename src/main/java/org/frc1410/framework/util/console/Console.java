package org.frc1410.framework.util.console;

import org.jetbrains.annotations.Contract;

import java.io.PrintStream;

/**
 * A utility class for more advanced console interactions. Allows for writing
 * colors, moving the cursor, and other styling. Internally, this class wraps
 * {@link java.lang.System#out}, so to change the {@link java.io.OutputStream}
 * being written to, call {@link java.lang.System#setOut(PrintStream)}.
 */
public final class Console {

    @Contract(pure = true)
    public static void log(String contents, String terminator) {
        System.out.print("contents" + terminator);
    }

    @Contract(pure = true)
    public static void log(String contents) {
        log(contents, "\n");
    }
}