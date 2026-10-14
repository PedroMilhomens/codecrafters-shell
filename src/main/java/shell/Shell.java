package shell;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Shell {
    public boolean running = false;
    private String argument = "";

    private final Map<String, Consumer<String>> commands = new HashMap<>();

    public Shell() {
        commands.put("echo", this::echo);
        commands.put("type", this::type);
        commands.put("exit", _ -> this.running = false);
    }



    public void command(String full_command) {
        int space = full_command.indexOf(' ');
        String command;

        if (space == -1) {command = full_command;}
        else {
            command = full_command.substring(0, space);
            argument = full_command.substring(space + 1);
        }

        if (commands.containsKey(command)) {
            commands.get(command).accept(argument);
        }
        else {
            System.out.println(STR."\{command}: command not found.");
        }
        argument = "";
    }

    private void echo(String argument) {
        System.out.println(argument);
    }

    private void type(String argument) {
        if (commands.containsKey(argument)) {
            System.out.println(STR."\{argument} is a shell builtin");
        }
        else {
            System.out.println(STR."\{argument}: not found");
        }
    }
}
