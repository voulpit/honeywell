package com.honeywell.atm.console.model.cmd;

import java.util.Arrays;
import java.util.Optional;

public enum VisitorCommand implements Command {
    LOGIN(1, "[1] Login user", "Card number = ", "Pin = "),
    INFORMATION(9, "[9] Display all options");

    private final int shortcut;
    private final String description;
    private final String[] paramPrompts;

    VisitorCommand(int shortcut, String description, String...paramPrompts) {
        this.shortcut = shortcut;
        this.description = description;
        this.paramPrompts = paramPrompts;
    }

    public String getDescription() {
        return description;
    }

    public int getShortcut() {
        return shortcut;
    }

    @Override
    public String[] getParamPrompts() {
        return paramPrompts;
    }

    public static VisitorCommand shortcutToCommand(String shortcutStr) {
        try {
            int shortcut = Integer.parseInt(shortcutStr);
            Optional<VisitorCommand> visitorCommand = Arrays.stream(VisitorCommand.values()).filter(cmd -> cmd.shortcut == shortcut).findFirst();
            return visitorCommand.orElse(VisitorCommand.INFORMATION);
        } catch (NumberFormatException e) {
            return VisitorCommand.INFORMATION;
        }
    }

    public static String getAllCommandsDescription() {
        return Arrays.stream(VisitorCommand.values())
                .map(VisitorCommand::getDescription)
                .reduce((a, b) -> a.concat("\n").concat(b)).get();
    }
}
