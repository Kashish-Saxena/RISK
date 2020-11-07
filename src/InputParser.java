import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This parser reads user input and tries to interpret it as a command word,
 * a territory, a number or a string. It also checks if an input is valid and
 * gives options of valid inputs that the user can select from.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version October 25, 2020
 */
public class InputParser {

    private Scanner reader;
    private RiskMap riskMap;

    /**
     * Constructor of the InputParser class. It creates the parser that reads from the terminal window.
     */
    public InputParser(RiskMap riskMap) {
        reader = new Scanner(System.in);
        this.riskMap = riskMap;
    }

    /**
     * Reads user input and returns a string.
     * @return String A string input from the user.
     */
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

    /**
     * Reads user input and returns a command word.
     * @param validCommands A list of valid commands.
     * @return The command word associated with the input command.
     */
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

    /**
     * Reads user input and returns a territory.
     * @param validTerritoryInputs A list of valid territories.
     * @return The territory associated with the input territory.
     */
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

        return riskMap.getTerritoryFromString(word);
    }

    /**
     * Reads user input and returns a number within a given range.
     * @param bound1 The first bound
     * @param bound2 The second bound
     * @return The number associated with the input number.
     */
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
            input = reader.nextInt();
            reader.nextLine();
        } while (input < lowerBound || input > upperBound);

        return input;
    }
}