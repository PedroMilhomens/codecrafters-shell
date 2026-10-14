import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        Shell shell = new Shell();

        shell.running = true;

        while (shell.running) {
            System.out.print("$ ");

            String input = sc.nextLine();

            shell.command(input);
        }
    }
}

class Shell {
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
            System.out.println(command + ": command not found");
        }
        argument = "";
    }

    private void echo(String argument) {
        System.out.println(argument);
    }

    private void type(String argument) {
        String path = System.getenv("PATH");
        String[] pathDirs = path.split(":");
        boolean found = false;

        if (commands.containsKey(argument)) {
            System.out.println(argument + " is a shell builtin");
        }
        else {
            for (int i = 0; i < pathDirs.length; i++) {
                File file = new File(pathDirs[i], argument);
                if (file.exists() && file.canExecute()) {
                    System.out.println(argument + " is " + file.getAbsolutePath());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println(argument + ": not found");
            }
        }
    }
}