package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Flashcards {

    Scanner scanner = new Scanner(System.in);
    private final HashMap<String, String> flashcards = new HashMap<>();
    private final HashMap<String, Integer> mistakes = new HashMap<>();
    Log log = new Log();

    public void add() {
        System.out.println("The card:");
        log.add("The card");
        String term = scanner.nextLine();
        log.add(term);
        if (flashcards.containsKey(term)) {
            System.out.println("The card \"" + term + "\" already exists.");
            log.add("The card \"" + term + "\" already exists.");
            return;
        }
        System.out.println("The definition of the card");
        log.add("The definition of the card");
        String definition = scanner.nextLine();
        log.add(definition);
        if (flashcards.containsValue(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            log.add("The definition \"" + definition + "\" already exists.");
            return;
        }
        flashcards.put(term, definition);
        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
        log.add("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }

    public void remove() {
        System.out.println("Which card?");
        log.add("Which card?");
        String termToRemove = scanner.nextLine();
        log.add(termToRemove);
        String resultOfRemoving = flashcards.remove(termToRemove);
        mistakes.remove(termToRemove);
        if (resultOfRemoving == null) {
            System.out.println("Can't remove \"" + termToRemove + "\": there is no such card.");
            log.add("Can't remove \"" + termToRemove + "\": there is no such card.");
        } else {
            System.out.println("The card has been removed.");
            log.add("The card has been removed.");
        }
    }


    public void load() {
        String fileName = getFileName();
        loadToFile(fileName);
    }

    public void loadToFile(String fileName) {
        try {
            Scanner reader = new Scanner(new File(fileName));
            int importedCounter = 0;
            while (reader.hasNext()) {
                String flashcard = reader.nextLine();
                String[] flashcardSplit = flashcard.split(":");
                flashcards.put(flashcardSplit[0], flashcardSplit[1]);
                if (Integer.parseInt(flashcardSplit[2]) > 0) {
                    mistakes.put(flashcardSplit[0], Integer.parseInt(flashcardSplit[2]));
                }
                importedCounter++;
            }
            System.out.println(importedCounter + " cards have been loaded.");
            log.add(importedCounter + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    public void export() {
        String fileName = getFileName();
        exportToFile(fileName);
    }

    public void exportToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Map.Entry<String, String> flashcard : flashcards.entrySet()) {
                writer.write(flashcard.getKey() + ":" + flashcard.getValue() + ":"
                        + mistakes.getOrDefault(flashcard.getKey(), 0) + "\n");
            }
            System.out.println(flashcards.size() + " cards have been saved.");
            log.add(flashcards.size() + " cards have been saved.");
        } catch (IOException e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    private String getFileName() {
        System.out.println("File name:");
        log.add("File name:");
        String fileName = scanner.nextLine();
        log.add(fileName);
        return fileName;
    }

    public void askRandom() {
        Random random = new Random();
        ArrayList<String> terms = new ArrayList<>(flashcards.keySet());
        System.out.println("How many times to ask?");
        log.add("How many times to ask?");
        int questionsNumber = scanner.nextInt();
        scanner.nextLine();
        log.add(String.valueOf(questionsNumber));
        for (int i = 0; i < questionsNumber; ++i) {
            String randomTerm = terms.get(random.nextInt(flashcards.size()));
            System.out.println("Print the definition of \"" + randomTerm + "\":");
            log.add("Print the definition of \"" + randomTerm + "\":");
            String userAnswer = scanner.nextLine();
            log.add(userAnswer);
            if (userAnswer.equals(flashcards.get(randomTerm))) {
                System.out.println("Correct!");
                log.add("Correct!");
            } else if (flashcards.containsValue(userAnswer)) {
                System.out.println("Wrong. The right answer is \"" + flashcards.get(randomTerm) +
                        "\", but your definition is correct for \""
                        + MapUtils.getKey(flashcards, userAnswer) + "\".");
                log.add("Wrong. The right answer is \"" + flashcards.get(randomTerm) +
                        "\", but your definition is correct for \""
                        + MapUtils.getKey(flashcards, userAnswer) + "\".");
                mistakes.put(randomTerm, mistakes.getOrDefault(randomTerm, 0) + 1);
            } else {
                System.out.println("Wrong. The right answer is \"" + flashcards.get(randomTerm) + "\".");
                log.add("Wrong. The right answer is \"" + flashcards.get(randomTerm) + "\".");
                mistakes.put(randomTerm, mistakes.getOrDefault(randomTerm, 0) + 1);
            }
        }
    }

    public void printHardest() {
        if (mistakes.isEmpty()) {
            System.out.println("There are no cards with errors.");
            log.add("There are no cards with errors.");
            return;
        }
        int maxNumberOfMistakes = MapUtils.getMaxValue(mistakes);
        ArrayList<String> maxMistakes = MapUtils.getKeysOfMaxValue(mistakes, maxNumberOfMistakes);
        if (maxMistakes.size() == 1) {
            System.out.println("The hardest card is \"" + maxMistakes.get(0)
                    + "\". You have " + maxNumberOfMistakes + " errors answering it");
            log.add("The hardest card is \"" + maxMistakes.get(0)
                    + "\". You have " + maxNumberOfMistakes + " errors answering it");
        } else {
            StringBuilder hardestCards = new StringBuilder("The hardest cards are");
            for (int i = 0; i < maxMistakes.size(); ++i) {
                if (i != maxMistakes.size() - 1) {
                    hardestCards.append(" \"").append(maxMistakes.get(i)).append("\",");
                } else {
                    hardestCards.append(" \"").append(maxMistakes.get(i)).append("\". ");
                }
            }
            hardestCards.append("You have ").append(maxNumberOfMistakes).append(" errors answering them");
            System.out.println(hardestCards);
            log.add(hardestCards.toString());
        }
    }

    public void resetStats() {
        mistakes.clear();
        System.out.println("Card statistics have been reset.");
        log.add("Card statistics have been reset.");
    }

    public Log getLog() {
        return log;
    }
}
