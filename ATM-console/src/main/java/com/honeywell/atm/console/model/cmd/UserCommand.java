package com.honeywell.atm.console.model.cmd;

import java.util.Arrays;
import java.util.Optional;

public enum UserCommand implements Command {
    VIEW_BALANCE(1, "[1] View balance"),
    WITHDRAW(2, "[2] Withdraw amount", "Amount = "),
    DEPOSIT(3, "[3] Deposit amount", "Amount = "),
    CHANGE_PIN(4, "[4] Change pin", "Old pin = ", "New pin = "),
    LOGOUT(0, "[0] Logout user"),
    INFORMATION(9, "[9] Display all options");

    private final int shortcut;
    private final String description;
    private final String[] paramPrompts;

    UserCommand (int shortcut, String description, String...paramPrompts) {
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

    public static UserCommand shortcutToCommand(String shortcutStr) {
        try {
            int shortcut = Integer.parseInt(shortcutStr);
            Optional<UserCommand> userCommand = Arrays.stream(UserCommand.values()).filter(cmd -> cmd.shortcut == shortcut).findFirst();
            return userCommand.orElse(UserCommand.INFORMATION);
        } catch (NumberFormatException e) {
            return UserCommand.INFORMATION;
        }
    }

    public static String getAllCommandsDescription() {
        return Arrays.stream(UserCommand.values())
                .map(UserCommand::getDescription)
                .reduce((a, b) -> a.concat("\n").concat(b)).get();
    }
}
