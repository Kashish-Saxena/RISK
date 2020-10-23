import java.util.Scanner;

public class InputParser {

    private Scanner reader;

    public InputParser() {
        reader = new Scanner(System.in);
    }

    public CommandWord getCommand() {
        String inputLine;
        String word = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Finding the word on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word = tokenizer.next();      // get word

        }
        return CommandWord.valueOf(word);
    }

    /*public Territory getTerritory() {

    }*/

    public int getInt() {

        String inputLine;

        inputLine = reader.nextLine();

        char[] chars = inputLine.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return Integer.parseInt(sb.toString());
    }
}