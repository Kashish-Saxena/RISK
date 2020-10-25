import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {

    private Scanner reader;

    public InputParser() {
        reader = new Scanner(System.in);
    }

    public String getString() {
        String inputLine;
        String word = "";

        do {
            word = "";
            System.out.print("> ");
            inputLine = reader.nextLine();
            // Finding the word on the line.
            Scanner tokenizer = new Scanner(inputLine);
            while (tokenizer.hasNext()) {
                word += tokenizer.next() + " ";
            }
        } while (word.length() == 0);
        return word.substring(0, word.length() - 1);
    }

    public CommandWord getCommand(List<CommandWord> validCommands) {
        String inputLine;
        String word = "";

        ArrayList<String> validInputs = new ArrayList<>();
        for (CommandWord cw : validCommands) {
            validInputs.add(cw.toString());
        }

        String invalidWarning = "Input was not valid. Please choose an input from ";
        for (String s : validInputs) {
            invalidWarning += s + " ";
        }

        do {
            word = "";
            System.out.print("Enter a command ( ");
            for (String s : validInputs) {
                System.out.print(s + " ");
            }
            System.out.print(")> ");
            inputLine = reader.nextLine();
            // Finding the word on the line.
            Scanner tokenizer = new Scanner(inputLine);
            while (tokenizer.hasNext()) {
                word += tokenizer.next() + " ";
            }
            if (word.length() > 0)
                word = word.substring(0, word.length() - 1).toUpperCase();

            if (!validInputs.contains(word))
                System.out.println(invalidWarning);
        } while (!validInputs.contains(word));

        return CommandWord.valueOf(word);
    }

    public Territory getTerritory(List<Territory> validTerritoryInputs) {
        String inputLine;
        String word = "";

        ArrayList<String> validInputs = new ArrayList<>();
        for (Territory t : validTerritoryInputs) {
            validInputs.add(t.getName().toLowerCase());
        }

        String invalidWarning = "Input was not valid. Please choose an input from ";
        for (String s : validInputs) {
            invalidWarning += s + ", ";
        }
        invalidWarning = invalidWarning.substring(0, invalidWarning.length() - 2);

        do {
            word = "";
            System.out.print("> ");
            inputLine = reader.nextLine();
            // Finding the word on the line.
            Scanner tokenizer = new Scanner(inputLine);
            while (tokenizer.hasNext()) {
                word += tokenizer.next() + " ";
            }
            if (word.length() > 0)
                word = word.substring(0, word.length() - 1).toLowerCase();

            if (!validInputs.contains(word))
                System.out.println(invalidWarning);
        } while (!validInputs.contains(word));

        return Territory.getTerritoryFromString(word);
    }

    public int getInt(int bound1, int bound2) {
        int lowerBound, upperBound;
        if (bound1 < bound2) {
            lowerBound = bound1;
            upperBound = bound2;
        }
        else {
            lowerBound = bound2;
            upperBound = bound1;
        }

        int input = 0;

        System.out.print("> ");
        do {
            while (!reader.hasNextInt()) {
                reader.nextLine();
                System.out.println("Ensure a number is input.");
                System.out.print("> ");
            }
            //System.out.println("Please choose a number between (and including) " + lowerBound + " to " + upperBound + ".");
            input = reader.nextInt();
            reader.nextLine();
        } while (input < lowerBound || input > upperBound);

        return input;
    }
}