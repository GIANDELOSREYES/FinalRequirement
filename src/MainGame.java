import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.random.*;

public class MainGame {

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

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        GameElement Bato = new GameElement("Bato","Papel", "Gunting");
        GameElement Gunting = new GameElement("Gunting","Bato", "Papel");
        GameElement Papel = new GameElement("Papel", "Gunting", "Bato");

        Player myPlayer = new Player("None");
        Player Computer = new Player("None");

        //-------------------------------------
        int accumulatedXP = loadXP();
        System.out.println("Loaded XP: " + accumulatedXP);

        while (myPlayer.isAlive()&&Computer.isAlive()){
            System.out.print("Enter your choice(bato, gunting, papel): ");
            String userChoice = userInput.nextLine();

            System.out.println();
            GameElement[] choices = new GameElement[]{Bato, Papel, Gunting};
            Random computerChoice = new Random();
            int randomIndex = computerChoice.nextInt(choices.length);
            GameElement selectedChoice = choices[randomIndex];

            System.out.println("Player chooses: "+userChoice);
            System.out.println("Computer chooses: "+selectedChoice.getName());

            boolean win;



            if (userChoice.equalsIgnoreCase("Bato")){
                win = Bato.isWin(selectedChoice);

                if (win){
                    Computer.getDamage();
                    System.out.println("You win! Computer took damage!");
                    System.out.println("Computer life: "+Computer.getLife());
                    accumulatedXP += 20;
                    myPlayer.setAccumulatedXP(accumulatedXP);
                    System.out.println("Acquired XP: "+ myPlayer.getAccumulatedXP());

                } else if (userChoice.equalsIgnoreCase(selectedChoice.getName())) {
                    System.out.println("Draw! No one took damage!");
                }else {
                    myPlayer.getDamage();
                    System.out.println("You Lose! You took damage!");
                    System.out.println("Your life: "+ myPlayer.getLife());
                }
            } else if (userChoice.equalsIgnoreCase(("Gunting"))) {
                win = Gunting.isWin(selectedChoice);

                if (win){
                    Computer.getDamage();
                    System.out.println("You win! Computer took damage!");
                    System.out.println("Computer life: "+Computer.getLife());
                    accumulatedXP += 20;
                    myPlayer.setAccumulatedXP(accumulatedXP);
                    System.out.println("Acquired XP: "+ myPlayer.getAccumulatedXP());
                } else if (userChoice.equalsIgnoreCase(selectedChoice.getName())) {
                    System.out.println("Draw! No one took damage!");
                }else {
                    myPlayer.getDamage();
                    System.out.println("You Lose! You took damage!");
                    System.out.println("Your life: "+ myPlayer.getLife());
                }
            } else if (userChoice.equalsIgnoreCase(("Papel"))){
                win = Papel.isWin(selectedChoice);

                if (win){
                    Computer.getDamage();
                    System.out.println("You win! Computer took damage!");
                    System.out.println("Computer life: "+Computer.getLife());
                    accumulatedXP += 20;
                    myPlayer.setAccumulatedXP(accumulatedXP);
                    System.out.println("Acquired XP: "+ myPlayer.getAccumulatedXP());
                } else if (userChoice.equalsIgnoreCase(selectedChoice.getName())) {
                    System.out.println("Draw! No one took damage!");
                }else {
                    myPlayer.getDamage();
                    System.out.println("You Lose! You took damage!");
                    System.out.println("Your life: "+ myPlayer.getLife());
                }
            }


            if ((myPlayer.getLife()>1||Computer.getLife()>1)&&(myPlayer.isAlive()&&Computer.isAlive())){
                System.out.print("Would you like to double your XP for the next round? (yes/no): ");
                    String doubleXPChoice = userInput.nextLine();
                System.out.println();

                if (doubleXPChoice.equalsIgnoreCase("yes")) {
                    accumulatedXP*=2;
                } else if (doubleXPChoice.equalsIgnoreCase("no")) {
                    continue;
                } else {
                    System.out.println("Invalid choice. XP remains the same.");
                }
            }else if (myPlayer.getLife()==1||Computer.getLife()==1){
                System.out.print("Last Round! Double or Nothing round!");
                accumulatedXP*=2;
                System.out.println();
            }else{
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
            System.out.println();
            System.out.println("You Lose! Your current XP: "+myPlayer.getXP());
            saveXP(0);
        }




    }

}



