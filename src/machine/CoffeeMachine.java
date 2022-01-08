package machine;

import java.util.Scanner;

public class CoffeeMachine {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ActualMachine coffeeMachine = new ActualMachine(400, 540, 120, 9, 550);

        while (coffeeMachine.getState() != MachineState.EXIT) {
            if (coffeeMachine.getState() == MachineState.HOME) {
                coffeeMachine.run(MachineState.HOME);
            }
            coffeeMachine.command(scanner.nextLine());
        }
    }
}
