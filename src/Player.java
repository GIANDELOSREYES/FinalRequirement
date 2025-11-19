import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Player {
    private int XP;
    private int accumulatedXP;
    private String booster;
    private Image booster1IMG;
    private Image XP2IMG;
    private Image booster3IMG;
    private int life;
    private String currentBooster;




    public Player(){
        this.XP = loadXP();
        this.accumulatedXP = 0;
        this.booster = booster;
        this.life=3;
        this.booster1IMG = new ImageIcon(getClass().getResource("1UP.png")).getImage();
        this.XP2IMG = new ImageIcon(getClass().getResource("2UP.png")).getImage();
        this.booster3IMG = new ImageIcon(getClass().getResource("3UP.png")).getImage();
        this.currentBooster = "None";
    }

    public int getAccumulatedXP() {
        return accumulatedXP;
    }

    public void setAccumulatedXP(int accumulatedXP) {
        this.accumulatedXP +=accumulatedXP;
    }

    public void setXP(int accumulatedXP){
        this.XP += accumulatedXP;
    }

    public int getXP() {
        return XP;
    }

    public int getLife() {
        return life;
    }

    public String getBooster() {
        return booster;
    }


    public boolean isAlive(){
        return life != 0;
    }

    public void getDamage(){
        life--;
    }

    public String getCurrentBooster(){
        return currentBooster;
    }

    public Image getBooster1IMG(){
        return booster1IMG;
    }

    public Image getXP2IMG() {
        return XP2IMG;
    }

    public Image getBooster3IMG() {
        return booster3IMG;
    }

    public void oneLifeBooster(){
        this.life +=1;
    }

    public void secondLifeBooster(){
        this.life +=2;
    }

    public void unlockOneLifeBooster(Player player){
        if (player.getXP()>=200){
            this.booster1IMG = new ImageIcon(getClass().getResource("1UPunlocked.png")).getImage();
            player.oneLifeBooster();       // +1 LIFE HERE
            player.currentBooster = "1UP"; // mark booster as equipped
            System.out.println("1UP EQUIPPED! +1 life!");
        }else{
            System.out.println("Reach 200XP");
        }
    }

    public void  unlock2XP(Player player){
        if (player.getXP()>=5000){
            this.XP2IMG = new ImageIcon(getClass().getResource("2UPunlocked.png")).getImage();
            System.out.println("Claim");
        }else{
            System.out.println("Reach 5000XP");
        }
    }

    public void unlockSecondLifeBooster(Player player){
        if (player.getXP()>=10000){
            this.booster3IMG = new ImageIcon(getClass().getResource("3UPunlocked.png")).getImage();
            System.out.println("Equip");
        }else{
            System.out.println("Reach 10000XP");
        }
    }

    public void equipLifeBooster(Player player){
        if (player.getXP()>=200 && !Objects.equals(currentBooster, "3UP")){
            currentBooster="1UP";
        }else if (player.getXP()>=10000 && (!Objects.equals(currentBooster, "1UP"))){
            currentBooster="3UP";
        }else{
            System.out.println("Booster already equipped!");
        }
    }


    public static void main(String[] args) {
        JFrame Unlockframe = new JFrame("Player Test");
        Unlockframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Unlockframe.setSize(768, 630);


        Image backgroundUnlocksImg = new ImageIcon(Player.class.getResource("achievements.png")).getImage();

        JPanel backgroundUnlocksPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundUnlocksImg, 0,0,getWidth(),getHeight(),this);
            }
        };

        backgroundUnlocksPanel.setOpaque(true);
        backgroundUnlocksPanel.setLayout(new BorderLayout());
        backgroundUnlocksPanel.setBounds(0, 0, 768, 630);


        Dimension size = new Dimension(768, 630);
        JLayeredPane layer1 = new JLayeredPane();
        MainGame game = new MainGame();
        Player player = game.myPlayer;

        if(player.getXP()>=200){
            player.unlockOneLifeBooster(player);
        }
        System.out.println(player.getXP()+"xp");


        JPanel unlock1Panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);


                g.drawImage(player.getBooster1IMG(), 0,0,getWidth(),getHeight(),this);
            }
        };

        unlock1Panel.setBounds(0,0, 768, 630);
        unlock1Panel.setOpaque(false);


        layer1.setPreferredSize(size);
        layer1.add(backgroundUnlocksPanel, Integer.valueOf(0));

        layer1.add(unlock1Panel, Integer.valueOf(1));


        Unlockframe.add(layer1);
        Unlockframe.setVisible(true);
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

}


