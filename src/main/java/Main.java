import java.util.Scanner;
import shell.Shell;

public class Main {
    public static void main(String[] args) throws Exception {
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
