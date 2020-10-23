public enum CommandWord {
    STATUS("status"), ATTACK("attack"), PASS("pass");

    // The command string
    private String commandString;

    /**
     * Initialising with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
