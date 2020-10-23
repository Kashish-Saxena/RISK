import java.util.Scanner;

public class InputParser {

    private Scanner reader;

    public InputParser() {
        reader = new Scanner(System.in);
    }

    public String getCommand() {
        String inputLine;
        String word = null;

        System.out.print("> ");

        inputLine = reader.nextLine();

        // Finding the word on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word = tokenizer.next();
        }
        return word;
    }

    public int getInt() {

        String inputLine;

        System.out.print("> ");
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