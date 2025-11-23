import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.random.*;

public class MainGame extends Player {

    public static void saveXP(int xp) {
        try (FileWriter writer = new FileWriter("player_xp.txt")) {
            writer.write(String.valueOf(xp));
        } catch (IOException e) {
            System.out.println("Error saving XP.");
        }
    }

    public static int loadXP() {
        File file = new File("player_xp.txt");

        if (!file.exists()) {
            return 0;
        }

        try (Scanner reader = new Scanner(file)) {
            if (reader.hasNextInt()) {
                return reader.nextInt();
            }
        } catch (Exception e) {
            System.out.println("Error loading XP.");
        }

        return 0;
    }

    Player myPlayer = new Player();
    Player Computer = new Player();

    public Player getMyPlayer() {
        return myPlayer;
    }

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        GameElement Bato = new GameElement("Bato", "Papel", "Gunting");
        GameElement Gunting = new GameElement("Gunting", "Bato", "Papel");
        GameElement Papel = new GameElement("Papel", "Gunting", "Bato");


        MainGame game = new MainGame();
        Player myPlayer = new Player();
        Player Computer = new Player();

        File claimFileOfBooster1 = new File("currentbooster.txt");
        if (claimFileOfBooster1.exists()) {
            try (Scanner scanner = new Scanner(claimFileOfBooster1)) {

                String line = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
                if (line.equals("1UP")) {
                    myPlayer.setCurrentBooster("1UP");
                } else if (line.equals("2UP")) {
                    myPlayer.setCurrentBooster("2UP");
                } else {
                    myPlayer.setCurrentBooster("None");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        boolean isUsed = false;
        boolean isUsedCom = false;
        int UP2LifeUsed = 2;


        //-------------------------------------
        int accumulatedXP = loadXP();
        System.out.println(myPlayer.getCurrentBooster());
        System.out.println("Loaded XP: " + accumulatedXP);

        while (myPlayer.isAlive() && Computer.isAlive()) {
            System.out.print("Enter your choice(bato, gunting, papel): ");
            String userChoice = userInput.nextLine();

            System.out.println();
            GameElement[] choices = new GameElement[]{Bato, Papel, Gunting};
            Random computerChoice = new Random();
            int randomIndex = computerChoice.nextInt(choices.length);
            GameElement selectedChoice = choices[randomIndex];

            System.out.println("Player chooses: " + userChoice);
            System.out.println("Computer chooses: " + selectedChoice.getName());

            boolean win;


            if (userChoice.equalsIgnoreCase("Bato")) {
                win = Bato.isWin(selectedChoice);

                if (win) {
                    Computer.getDamage();
                    System.out.println("You win! Computer took damage!");
                    System.out.println("Computer life: " + Computer.getLife());
                    accumulatedXP += 20;
                    myPlayer.setAccumulatedXP(accumulatedXP);
                    System.out.println("Acquired XP: " + myPlayer.getAccumulatedXP());

                    if ((Computer.getLife() == 1 && !isUsedCom)&&(!Objects.equals(myPlayer.getCurrentBooster(), "None"))) {
                        if (Objects.equals(myPlayer.getCurrentBooster(), "1UP")){
                            Computer.setCurrentBooster(myPlayer.getCurrentBooster());
                            Computer.applyBoosterEffect();
                            System.out.println("Computer used a life booster! Computer's current life: " + Computer.getLife());
                            isUsedCom = true;
                        } else if (Objects.equals(myPlayer.getCurrentBooster(), "2UP")) {
                            Computer.setCurrentBooster("Special");
                            Computer.applyBoosterEffect();
                            System.out.println("Computer used a life booster! Computer's current life: " + Computer.getLife());
                            isUsedCom = true;
                        }
                    }
                } else if (userChoice.equalsIgnoreCase(selectedChoice.getName())) {
                    System.out.println("Draw! No one took damage!");
                } else {
                    myPlayer.getDamage();
                    System.out.println("You Lose! You took damage!");
                    System.out.println("Your life: " + myPlayer.getLife());

                    if ((myPlayer.getLife() > 0 && !isUsed)&& (Objects.equals(myPlayer.getCurrentBooster(),"1UP"))) {
                        System.out.println("Would you like to use your life booster " + myPlayer.getCurrentBooster() + "? (yes/no): ");
                        String userBoosterChooses = userInput.nextLine();

                        if (userBoosterChooses.equalsIgnoreCase("yes")) {
                            myPlayer.applyBoosterEffect();
                            isUsed = true;
                            System.out.println("You used a life booster! Your current life: " + myPlayer.getLife());
                        } else if (userBoosterChooses.equalsIgnoreCase("no")) {
                            System.out.println("You chose not to use a life booster.");
                        } else {
                            System.out.println("Invalid choice. No booster used.");
                        }
                    }else if ((myPlayer.getLife() >0 && (UP2LifeUsed>0))&& (Objects.equals(myPlayer.getCurrentBooster(),"2UP"))){
                        System.out.println("Would you like to add life from booster [" +UP2LifeUsed+ "remaining]? (yes/no): ");
                            String userBoosterChooses = userInput.nextLine();

                        if (userBoosterChooses.equalsIgnoreCase("yes")) {
                            myPlayer.applyBoosterEffect();
                            isUsed = true;
                            System.out.println("You used a life booster! Your current life: " + myPlayer.getLife());
                            UP2LifeUsed--;
                        } else if (userBoosterChooses.equalsIgnoreCase("no")) {
                            System.out.println("You chose not to use a life booster.");
                        } else {
                            System.out.println("Invalid choice. No booster used.");
                        }
                    }
                }
            } else if (userChoice.equalsIgnoreCase(("Gunting"))) {
                win = Gunting.isWin(selectedChoice);

                if (win) {
                    Computer.getDamage();
                    System.out.println("You win! Computer took damage!");
                    System.out.println("Computer life: " + Computer.getLife());
                    accumulatedXP += 20;
                    myPlayer.setAccumulatedXP(accumulatedXP);
                    System.out.println("Acquired XP: " + myPlayer.getAccumulatedXP());

                    if ((Computer.getLife() == 1 && !isUsedCom)&&(!Objects.equals(myPlayer.getCurrentBooster(), "None"))) {
                        if (Objects.equals(myPlayer.getCurrentBooster(), "1UP")){
                            Computer.setCurrentBooster(myPlayer.getCurrentBooster());
                            Computer.applyBoosterEffect();
                            System.out.println("Computer used a life booster! Computer's current life: " + Computer.getLife());
                            isUsedCom = true;
                        } else if (Objects.equals(myPlayer.getCurrentBooster(), "2UP")) {
                            Computer.setCurrentBooster("Special");
                            Computer.applyBoosterEffect();
                            System.out.println("Computer used a life booster! Computer's current life: " + Computer.getLife());
                            isUsedCom = true;
                        }
                    }
                } else if (userChoice.equalsIgnoreCase(selectedChoice.getName())) {
                    System.out.println("Draw! No one took damage!");
                } else {
                    myPlayer.getDamage();
                    System.out.println("You Lose! You took damage!");
                    System.out.println("Your life: " + myPlayer.getLife());

                    if ((myPlayer.getLife() > 0 && !isUsed)&& (Objects.equals(myPlayer.getCurrentBooster(),"1UP"))) {
                        System.out.println("Would you like to use your life booster " + myPlayer.getCurrentBooster() + "? (yes/no): ");
                        String userBoosterChooses = userInput.nextLine();

                        if (userBoosterChooses.equalsIgnoreCase("yes")) {
                            myPlayer.applyBoosterEffect();
                            isUsed = true;
                            System.out.println("You used a life booster! Your current life: " + myPlayer.getLife());
                        } else if (userBoosterChooses.equalsIgnoreCase("no")) {
                            System.out.println("You chose not to use a life booster.");
                        } else {
                            System.out.println("Invalid choice. No booster used.");
                        }
                    }else if ((myPlayer.getLife() >0 && (UP2LifeUsed>0))&& (Objects.equals(myPlayer.getCurrentBooster(),"2UP"))){
                        System.out.println("Would you like to add life from booster [" +UP2LifeUsed+ "remaining]? (yes/no): ");
                        String userBoosterChooses = userInput.nextLine();

                        if (userBoosterChooses.equalsIgnoreCase("yes")) {
                            myPlayer.applyBoosterEffect();
                            isUsed = true;
                            System.out.println("You used a life booster! Your current life: " + myPlayer.getLife());
                            UP2LifeUsed--;
                        } else if (userBoosterChooses.equalsIgnoreCase("no")) {
                            System.out.println("You chose not to use a life booster.");
                        } else {
                            System.out.println("Invalid choice. No booster used.");
                        }
                    }
                }
            } else if (userChoice.equalsIgnoreCase(("Papel"))) {
                win = Papel.isWin(selectedChoice);

                if (win) {
                    Computer.getDamage();
                    System.out.println("You win! Computer took damage!");
                    System.out.println("Computer life: " + Computer.getLife());
                    accumulatedXP += 20;
                    myPlayer.setAccumulatedXP(accumulatedXP);
                    System.out.println("Acquired XP: " + myPlayer.getAccumulatedXP());

                    if ((Computer.getLife() == 1 && !isUsedCom)&&(!Objects.equals(myPlayer.getCurrentBooster(), "None"))) {
                        if (Objects.equals(myPlayer.getCurrentBooster(), "1UP")){
                            Computer.setCurrentBooster(myPlayer.getCurrentBooster());
                            Computer.applyBoosterEffect();
                            System.out.println("Computer used a life booster! Computer's current life: " + Computer.getLife());
                            isUsedCom = true;
                        } else if (Objects.equals(myPlayer.getCurrentBooster(), "2UP")) {
                            Computer.setCurrentBooster("Special");
                            Computer.applyBoosterEffect();
                            System.out.println("Computer used a life booster! Computer's current life: " + Computer.getLife());
                            isUsedCom = true;
                        }
                    }
                } else if (userChoice.equalsIgnoreCase(selectedChoice.getName())) {
                    System.out.println("Draw! No one took damage!");
                } else {
                    myPlayer.getDamage();
                    System.out.println("You Lose! You took damage!");
                    System.out.println("Your life: " + myPlayer.getLife());

                    if ((myPlayer.getLife() > 0 && !isUsed)&& (Objects.equals(myPlayer.getCurrentBooster(),"1UP"))) {
                        System.out.println("Would you like to use your life booster " + myPlayer.getCurrentBooster() + "? (yes/no): ");
                        String userBoosterChooses = userInput.nextLine();

                        if (userBoosterChooses.equalsIgnoreCase("yes")) {
                            myPlayer.applyBoosterEffect();
                            isUsed = true;
                            System.out.println("You used a life booster! Your current life: " + myPlayer.getLife());
                        } else if (userBoosterChooses.equalsIgnoreCase("no")) {
                            System.out.println("You chose not to use a life booster.");
                        } else {
                            System.out.println("Invalid choice. No booster used.");
                        }
                    }else if ((myPlayer.getLife() >0 && (UP2LifeUsed>0))&& (Objects.equals(myPlayer.getCurrentBooster(),"2UP"))){
                        System.out.println("Would you like to add life from booster [" +UP2LifeUsed+ "remaining]? (yes/no): ");
                        String userBoosterChooses = userInput.nextLine();

                        if (userBoosterChooses.equalsIgnoreCase("yes")) {
                            myPlayer.applyBoosterEffect();
                            isUsed = true;
                            System.out.println("You used a life booster! Your current life: " + myPlayer.getLife());
                            UP2LifeUsed--;
                        } else if (userBoosterChooses.equalsIgnoreCase("no")) {
                            System.out.println("You chose not to use a life booster.");
                        } else {
                            System.out.println("Invalid choice. No booster used.");
                        }
                    }
                }
            }

            if ((myPlayer.getLife() > 1 || Computer.getLife() > 1) && (myPlayer.isAlive() && Computer.isAlive())) {
                System.out.print("Would you like to double your XP for the next round? (yes/no): ");
                String doubleXPChoice = userInput.nextLine();
                System.out.println();

                if (doubleXPChoice.equalsIgnoreCase("yes")) {
                    accumulatedXP *= 2;
                } else if (doubleXPChoice.equalsIgnoreCase("no")) {
                    continue;
                } else {
                    System.out.println("Invalid choice. XP remains the same.");
                }
            } else if (myPlayer.getLife() == 1 && Computer.getLife() == 1) {
                System.out.print("Last Round! Double or Nothing round!");
                accumulatedXP *= 2;
                System.out.println();
            } else {
                break;
            }

        }


        //-----------------------------------------------------------


        if (myPlayer.getLife()>0){
            myPlayer.setXP(myPlayer.getAccumulatedXP());
            System.out.println("You win! Your current XP: "+myPlayer.getXP());
            saveXP(myPlayer.getXP());
        }else{
            myPlayer.setAccumulatedXP(0);
            myPlayer.setXP(0);
            myPlayer.setCurrentBooster("None");
            System.out.println();
            System.out.println("You Lose! Your current XP: "+0);
            saveXP(0);

            try {
                try (FileWriter writer = new FileWriter("currentbooster.txt")) {
                    writer.write(String.valueOf(myPlayer.getCurrentBooster()));
                }
            } catch (IOException ex) {
                System.out.println("Error saving current booster.");
            }



        }

    }
}
