package frc.util;

import java.io.File;
import java.io.PrintWriter;

public class Logger {
    PrintWriter writer;
    StringBuilder sb;

    public Logger(File writeFile, PrintWriter writer) {
        this.writer = writer;
        sb = new StringBuilder();
        sb.append("Time Stamp, Command, Relevant Value");
    }

    public void write(String command, String value) {
        long time = System.currentTimeMillis();
        long second =(time / 1000) % 60;
        long minute =(time / (1000 * 60)) % 60;
        long hour = ((time / (1000 * 60 * 60)) % 24) - 5;
        String currentTime = String.format("%02d:%02d:%02d", hour, minute, second);
        sb.append(currentTime + ", " + command + ", " + value);
        writer.write(sb.toString());
    }
}