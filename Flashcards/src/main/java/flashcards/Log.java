package flashcards;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Log {
    private final LinkedList<String> log = new LinkedList<>();

    public void add(String logToAdd) {
        log.add(logToAdd);
    }

    public void add(Log logsToAdd) {
        this.log.addAll(logsToAdd.getLog());
    }

    public void print() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("File name:");
        log.add("File name:");
        String fileName = scanner.nextLine();
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String line : log) {
                writer.write(line + "\n");
            }
            System.out.println("The log has been saved.");
        } catch (IOException e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    public LinkedList<String> getLog() {
        return this.log;
    }
}
