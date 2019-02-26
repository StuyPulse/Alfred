package frc.util;

import java.io.File;
import java.io.PrintWriter;

public class Logger {
    PrintWriter writer;
    StringBuilder sb;
    Boolean cannotLog;

    public Logger(File writeFile, PrintWriter writer) {
        this.writer = writer;
        sb = new StringBuilder();
        sb.append("Time Stamp, Command, Relevant Value");
    }

    public Logger() {
    }

    public void write(String command, String value) {
        if (!cannotLog) {
            String currentTime = getTime();
            sb.append(currentTime + ", " + command + ", " + value);
            writer.write(sb.toString());
        }
    }

    public String getTime() {
        long time = System.currentTimeMillis();
        long second =(time / 1000) % 60;
        long minute =(time / (1000 * 60)) % 60;
        long hour = ((time / (1000 * 60 * 60)) % 24) - 5;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public void setCannotLog() {
        cannotLog = true;
    }
}