package machine;

import java.util.Collections;
import java.util.List;

public class ActualMachine {

    private int availWater;
    private int availMilk;
    private int availCoffee;
    private int availCups;
    private int money;
    private MachineState state = MachineState.HOME;

    public ActualMachine(int availWater, int availMilk, int availCoffee, int availCups, int money) {
        this.availWater = availWater;
        this.availMilk = availMilk;
        this.availCoffee = availCoffee;
        this.availCups = availCups;
        this.money = money;
    }

    public void command(String input) {
        switch (this.state) {
            case HOME:
                MachineState state = MachineState.valueOf(input.toUpperCase());
                run(state);
                return;
            case BUY:
                if ("back".equals(input)) {
                    this.state = MachineState.HOME;
                    return;
                }
                int kind = Integer.parseInt(input);
                if (canMake(kind)) {
                    System.out.println("I have enough resources, making you a coffee!");
                } else {
                    System.out.println("Sorry, not enough" + whatIsShort(kind));
                    this.state = MachineState.HOME;
                    return;
                }
                buy(kind);
                this.state = MachineState.HOME;
                return;
            case ADD_WATER:
                addWater(Integer.parseInt(input));
                run(MachineState.ADD_MILK);
                return;
            case ADD_MILK:
                addMilk(Integer.parseInt(input));
                run(MachineState.ADD_COFFEE);
                return;
            case ADD_COFFEE:
                addCoffee(Integer.parseInt(input));
                run(MachineState.ADD_CUPS);
                return;
            case ADD_CUPS:
                addCups(Integer.parseInt(input));
                this.state = MachineState.HOME;
        }
    }

    public void run(MachineState state) {
        switch (state) {
            case HOME:
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                this.state = MachineState.HOME;
                return;
            case BUY:
                System.out.println("What do you want to buy? " +
                        "1 - espresso, 2 - latte, " +
                        "3 - cappuccino, " +
                        "back - to main menu:");
                this.state = MachineState.BUY;
                return;
            case FILL:
                System.out.println("Write how many ml of water you want to add:");
                this.state = MachineState.ADD_WATER;
                return;
            case ADD_MILK:
                System.out.println("Write how many ml of milk you want to add:");
                this.state = MachineState.ADD_MILK;
                return;
            case ADD_COFFEE:
                System.out.println("Write how many grams of coffee beans you want to add:");
                this.state = MachineState.ADD_COFFEE;
                return;
            case ADD_CUPS:
                System.out.println("Write how many disposable cups of coffee you want to add:");
                this.state = MachineState.ADD_CUPS;
                return;
            case REMAINING:
                printStats();
                return;
            case TAKE:
                take();
                return;
            case EXIT:
                this.state = MachineState.EXIT;
        }
    }

    /*public void run(String input) {
        switch (state) {
            case HOME:
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                state = MachineState.HOME_MENU;
                return;
            case HOME_MENU:
                state = MachineState.valueOf(input.toUpperCase());
                return;
            case BUY:
                System.out.println("What do you want to buy? " +
                        "1 - espresso, 2 - latte, " +
                        "3 - cappuccino, " +
                        "back - to main menu:");
                state = MachineState.BUY_MENU;
            case BUY_MENU:
                if ("back".equals(input)) {
                    state = MachineState.HOME;
                }
                int kind = Integer.parseInt(input);
                if (canMake(kind)) {
                    System.out.println("I have enough resources, making you a coffee!");
                } else {
                    System.out.println("Sorry, not enough" + whatIsShort(kind));
                    state = MachineState.HOME;
                }
                buy(kind);
                state = MachineState.HOME;
                return;
            case FILL:
                System.out.println("Write how many ml of water you want to add:");
                state = MachineState.ADD_WATER;
                return;
            case ADD_WATER:
                addWater(Integer.parseInt(input));
                System.out.println("Write how many ml of milk you want to add:");
                state = MachineState.ADD_MILK;
                return;
            case ADD_MILK:
                addMilk(Integer.parseInt(input));
                System.out.println("Write how many grams of coffee beans you want to add:");
                state = MachineState.ADD_COFFEE;
                return;
            case ADD_COFFEE:
                addCoffee(Integer.parseInt(input));
                System.out.println("Write how many disposable cups of coffee you want to add:");
                state = MachineState.ADD_CUPS;
                return;
            case ADD_CUPS:
                addCups(Integer.parseInt(input));
                state = MachineState.HOME;
                return;
            case REMAINING:
                printStats();
                state = MachineState.HOME;
                return;
            case TAKE:
                take();
                state = MachineState.HOME;
                return;
            case EXIT:
                state = MachineState.EXIT;
        }
    }*/

    public MachineState getState() {
        return state;
    }

    /*public MachineState run(String input) {
        //System.out.println("Write action (buy, fill, take, remaining, exit):");
        //String response = scanner.nextLine();
        switch (state) {
            case REMAINING:
                printStats();
                return MachineState.HOME;
            case BUY:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                String buyResponse = scanner.nextLine();
                if ("back".equals(buyResponse)) {
                    return true;
                }
                int kind = Integer.parseInt(buyResponse);
                if (canMake(kind)) {
                    System.out.println("I have enough resources, making you a coffee!");
                } else {
                    System.out.println("Sorry, not enough" + whatIsShort(kind));
                    return true;
                }
                buy(kind);
                return true;
            case "fill":
                fill();
                return true;
            case "take":
                take();
                return true;
            case "exit":
                return false;
        }
        return false;
    }*/

    public void printStats() {
        System.out.println("The coffee machine has:");
        System.out.println(availWater + " ml of water");
        System.out.println(availMilk + " ml of milk");
        System.out.println(availCoffee + " g of coffee beans");
        System.out.println(availCups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    public String whatIsShort(int kind) {
        String ingredient = "";
        switch (kind) {
            case 1:
                ingredient = Collections.max(List.of(
                        (availWater / 250 == 0 ? "water" : ""),
                        (availCoffee / 16 == 0 ? "coffee" : ""),
                        (availCups == 0 ? "cups" : "")
                ));
                break;
            case 2:
                ingredient = Collections.max(List.of(
                        (availWater / 350 == 0 ? "water" : ""),
                        (availMilk / 75 == 0 ? "milk" : ""),
                        (availCoffee / 20 == 0 ? "coffee" : ""),
                        (availCups == 0 ? "cups" : "")
                ));
                break;
            case 3:
                ingredient = Collections.max(List.of(
                        (availWater / 200 == 0 ? "water" : ""),
                        (availMilk / 100 == 0 ? "milk" : ""),
                        (availCoffee / 12 == 0 ? "coffee" : ""),
                        (availCups == 0 ? "cups" : "")
                ));
                break;
        }
        return ingredient;
    }

    public boolean canMake(int kind) {
        int maxCoffee = 0;
        switch (kind) {
            case 1:
                maxCoffee = Collections.min(List.of((availWater / 250), (availCoffee / 16), availCups));
                break;
            case 2:
                maxCoffee = Collections.min(List.of((availWater / 350), (availMilk / 75), (availCoffee / 20), availCups));
                break;
            case 3:
                maxCoffee = Collections.min(List.of((availWater / 200), (availMilk / 100), (availCoffee / 12), availCups));
                break;
        }
        return maxCoffee > 0;
    }

    public void buy(int kind) {
        switch (kind) {
            case 1:
                availWater -= 250;
                availCoffee -= 16;
                availCups -= 1;
                money += 4;
                break;
            case 2:
                availWater -= 350;
                availMilk -= 75;
                availCoffee -= 20;
                availCups -= 1;
                money += 7;
                break;
            case 3:
                availWater -= 200;
                availMilk -= 100;
                availCoffee -= 12;
                availCups -= 1;
                money += 6;
                break;
        }
    }

    public void addWater(int amount) {
        availWater += amount;
    }

    public void addMilk(int amount) {
        availMilk += amount;
    }

    public void addCoffee(int amount) {
        availCoffee += amount;
    }

    public void addCups(int amount) {
        availCups += amount;
    }

    /*public void fill() {
        System.out.println("Write how many ml of water you want to add:");
        int amtWater = Integer.parseInt((scanner.nextLine()));
        System.out.println("Write how many ml of milk you want to add:");
        int amtMilk = Integer.parseInt((scanner.nextLine()));
        System.out.println("Write how many grams of coffee beans you want to add:");
        int amtCoffee = Integer.parseInt((scanner.nextLine()));
        System.out.println("Write how many disposable cups of coffee you want to add:");
        int numCups = Integer.parseInt((scanner.nextLine()));
        availWater += amtWater;
        availMilk += amtMilk;
        availCoffee += amtCoffee;
        availCups += numCups;
    }*/

    public void take() {
        System.out.println("I give you $" + money);
        money = 0;
    }
}

