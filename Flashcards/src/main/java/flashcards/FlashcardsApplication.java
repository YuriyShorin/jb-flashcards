package flashcards;

import java.util.Scanner;

public class FlashcardsApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final Log log = new Log();
    private Flashcards flashcards;
    private String exportFileName = "";

    private final String[] args;

    FlashcardsApplication(String[] args) {
        this.args = args;
    }

    public void run() {
        flashcards = new Flashcards();
        handlingArgs();
        handlingActions();
    }

    private void handlingArgs() {
        if (args.length == 0) {
            return;
        }
        String importFileName = "";
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-import")) {
                importFileName = args[i + 1];
            } else {
                exportFileName = args[i + 1];
            }
        }
        if (!importFileName.isEmpty()) {
            flashcards.loadToFile(importFileName);
        }
    }

    private void handlingActions() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String action = scanner.nextLine();
            log.add(action);
            flashcards.getLog().add(log);
            isExit = chooseAction(action);
        }
    }

    private boolean chooseAction(String action) {
        switch (action) {
            case "add" -> {
                addFlashcard();
                return false;
            }
            case "remove" -> {
                removeFlashcard();
                return false;
            }
            case "import" -> {
                importFlashcards();
                return false;
            }
            case "export" -> {
                exportFlashcards();
                return false;
            }
            case "ask" -> {
                askRandomFlashcard();
                return false;
            }
            case "exit" -> {
                exitProgram();
                return true;
            }
            case "log" -> {
                printLog();
                return false;
            }
            case "hardest card" -> {
                printHardestFlashCard();
                return false;
            }
            case "reset stats" -> {
                resetStats();
                return false;
            }
            default -> {
                System.out.println("Not correct action. Please, try again!");
                return false;
            }
        }
    }

    private void addFlashcard() {
        flashcards.add();
    }

    private void removeFlashcard() {
        flashcards.remove();
    }

    private void importFlashcards() {
        flashcards.load();
    }

    private void exportFlashcards() {
        flashcards.export();
    }

    private void askRandomFlashcard() {
        flashcards.askRandom();
    }

    private void exitProgram() {
        if (!exportFileName.isEmpty()) {
            flashcards.exportToFile(exportFileName);
        }
        System.out.println("Bye bye!");
    }

    private void printLog() {
        flashcards.getLog().print();
    }

    private void printHardestFlashCard() {
        flashcards.printHardest();
    }

    private void resetStats() {
        flashcards.resetStats();
    }
}
