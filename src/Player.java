import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
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

    public void setCurrentBooster(String booster){
        this.currentBooster = booster;
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
            this.booster1IMG = new ImageIcon(getClass().getResource("1UPunlocked.png")).getImage();// +1 LIFE HERE // mark booster as equipped
        }else{
            System.out.println("Reach 200XP");
        }
    }

    public void  unlock2XP(Player player){
        if (player.getXP()>=5000){
            this.XP2IMG = new ImageIcon(getClass().getResource("2UPunlocked.png")).getImage();
        }else{
            System.out.println("Reach 5000XP");
        }
    }

    public void unlockSecondLifeBooster(Player player){
        if (player.getXP()>=10000){
            this.booster3IMG = new ImageIcon(getClass().getResource("3UPunlocked.png")).getImage();
        }else{
            System.out.println("Reach 10000XP");
        }
    }

    public void equipLifeBoosterFor1UP(Player player){
        if (player.getXP()>=200 && (Objects.equals(player.getCurrentBooster(), "None"))){
            player.setCurrentBooster("1UP");
        }
    }

    public void equipLifeBoosterFor2UP(Player player){
        if (player.getXP()>=10000 && (Objects.equals(player.getCurrentBooster(), "None"))){
            player.setCurrentBooster("2UP");
        }
    }

    public void applyBoosterEffect(){
        if (Objects.equals(currentBooster, "1UP")){
            oneLifeBooster();
        }else if (Objects.equals(currentBooster, "3UP")){
            secondLifeBooster();
        }
    }


    public void getUnlocksFrame() {
        JFrame Unlockframe = new JFrame("Player Test");
        Unlockframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Unlockframe.setSize(768, 700);



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
        backgroundUnlocksPanel.setBounds(0, 0, 768, 700);


        Dimension size = new Dimension(768, 700);
        JLayeredPane layer1 = new JLayeredPane();
        MainGame game = new MainGame();
        Player player = game.myPlayer;

        if(player.getXP()>=200){
            player.unlockOneLifeBooster(player);
        }

        if(player.getXP()>=5000){
            player.unlock2XP(player);
        }

        if(player.getXP()>=10000){
            player.unlockSecondLifeBooster(player);
        }


        JPanel unlock1Panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);


                g.drawImage(player.getBooster1IMG(), 0,0,getWidth(),getHeight(),this);
            }
        };

        JPanel unlock2Panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);


                g.drawImage(player.getXP2IMG(), 0,0,getWidth(),getHeight(),this);
            }
        };

        JPanel unlock3Panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);


                g.drawImage(player.getBooster3IMG(), 0,0,getWidth(),getHeight(),this);
            }
        };

        unlock1Panel.setBounds(0,0, 768, 630);
        unlock1Panel.setOpaque(false);
        unlock2Panel.setBounds(0,0, 768, 630);
        unlock2Panel.setOpaque(false);
        unlock3Panel.setBounds(0,0, 768, 630);
        unlock3Panel.setOpaque(false);

        JButton equip1 = new JButton("Equip"){

        };
        equip1.setBounds(70, 500, 200, 50);
        equip1.setBackground(new Color(72, 108, 223));
        equip1.setForeground(Color.white);
        equip1.setBorderPainted(false);
        equip1.setFocusPainted(false);
        equip1.setContentAreaFilled(true);

        System.out.print(player.currentBooster);

        equip1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(Objects.equals(player.getCurrentBooster(), "1UP")){
                    player.setCurrentBooster("None");
                    equip1.setText("Equip");
                    equip1.setBackground(new Color(72, 108, 223));
                    System.out.println(player.currentBooster);
                }else{
                    if (Objects.equals(player.getCurrentBooster(), "None")){
                        player.equipLifeBoosterFor1UP(player);
                        equip1.setText("Equipped");
                        equip1.setBackground(new Color(38, 196, 86));
                        System.out.println(player.currentBooster);
                    }else{
                        JOptionPane.showMessageDialog(
                                null,
                                player.getCurrentBooster()+ " booster already equipped!",
                                "Error 101",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            }
        });


        JButton claim = new JButton("CLAIM");
        claim.setBounds(290, 500, 200, 50);
        claim.setBackground(new Color(72, 108, 223));
        claim.setForeground(Color.white);
        claim.setBorderPainted(false);
        claim.setFocusPainted(false);
        claim.setContentAreaFilled(true);
        final int[] clicked = {0};




        JButton equip3 = new JButton("Equip");
        equip3.setBounds(510, 500, 200, 50);
        equip3.setBackground(new Color(72, 108, 223));
        equip3.setForeground(Color.white);
        equip3.setBorderPainted(false);
        equip3.setFocusPainted(false);
        equip3.setContentAreaFilled(true);

        equip3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(Objects.equals(player.getCurrentBooster(), "2UP")){
                    player.setCurrentBooster("None");
                    equip3.setText("Equip");
                    equip3.setBackground(new Color(72, 108, 223));
                    System.out.println(player.currentBooster);
                }else{
                    if (Objects.equals(player.getCurrentBooster(), "None")) {
                        player.equipLifeBoosterFor2UP(player);
                        equip3.setText("Equipped");
                        equip3.setBackground(new Color(38, 196, 86));
                        System.out.println(player.currentBooster);
                    }else{
                        JOptionPane.showMessageDialog(
                                null,
                                player.getCurrentBooster()+ " booster already equipped!",
                                "Error 101",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            }
        });


        JButton locked1UPButton = new JButton("Reach 200XP");
        locked1UPButton.setBounds(70, 500, 200, 50);
        locked1UPButton.setBackground(new Color(116, 166, 254));
        locked1UPButton.setForeground(Color.white);
        locked1UPButton.setBorderPainted(false);
        locked1UPButton.setFocusPainted(false);
        locked1UPButton.setContentAreaFilled(true);

        JButton lockedXPButton = new JButton("Reach 5000XP");
        lockedXPButton.setBounds(290, 500, 200, 50);
        lockedXPButton.setBackground(new Color(116, 166, 254));
        lockedXPButton.setForeground(Color.white);
        lockedXPButton.setBorderPainted(false);
        lockedXPButton.setFocusPainted(false);
        lockedXPButton.setContentAreaFilled(true);

        JButton locked2UPButton = new JButton("Reach 10000XP");
        locked2UPButton.setBounds(510, 500, 200, 50);
        locked2UPButton.setBackground(new Color(116, 166, 254));
        locked2UPButton.setForeground(Color.white);
        locked2UPButton.setBorderPainted(false);
        locked2UPButton.setFocusPainted(false);
        locked2UPButton.setContentAreaFilled(true);

        final int arc = 50;

        JButton BackButton = new JButton("Back"){
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // draw rounded background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
                g2.dispose();
                // let the button draw the text (button is non-opaque so it won't fill its background)
                super.paintComponent(g);
            }

            @Override
            public boolean contains(int x, int y) {
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc);
                return shape.contains(x, y);
            }
        };
        BackButton.setBounds(290, 580, 200, 40);
        BackButton.setBackground(new Color(246, 205, 90));
        BackButton.setForeground(new Color(0, 0, 128));
        BackButton.setBorderPainted(false);
        BackButton.setFocusPainted(false);
        BackButton.setContentAreaFilled(false);



        layer1.setPreferredSize(size);
        layer1.add(backgroundUnlocksPanel, Integer.valueOf(0));
        layer1.add(unlock1Panel, Integer.valueOf(1));
        layer1.add(unlock2Panel, Integer.valueOf(1));
        layer1.add(unlock3Panel, Integer.valueOf(1));
        layer1.add(BackButton, Integer.valueOf(1));

        //----------------------------
        if(player.getXP()>=200){
            layer1.add(equip1, Integer.valueOf(1));
        } else {
            layer1.add(locked1UPButton, Integer.valueOf(1));
        }


        if (player.getXP()>=5000) {
            layer1.add(claim, Integer.valueOf(1));
        }else{
            layer1.add(lockedXPButton, Integer.valueOf(1));
        }

        if (player.getXP()>=10000) {
            layer1.add(equip3, Integer.valueOf(1));
        }else {
            layer1.add(locked2UPButton, Integer.valueOf(1));
        }


        try {
            // Load font from resources
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    Player.class.getResourceAsStream("Cubao_Free_Regular.otf") // leading "/" means root of resources
            );

            // Derive size and style
            customFont = customFont.deriveFont(20f);

            // Apply to a button or label
            locked1UPButton.setFont(customFont);
            lockedXPButton.setFont(customFont);
            locked2UPButton.setFont(customFont);


        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Load font from resources
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    Player.class.getResourceAsStream("Cubao_Free_Regular.otf") // leading "/" means root of resources
            );

            Font customFontLight = Font.createFont(Font.TRUETYPE_FONT,
                    Player.class.getResourceAsStream("Cubao_Free_Regular.otf") // leading "/" means root of resources
            );

            // Derive size and style
            customFont = customFont.deriveFont(Font.BOLD,30f);
            customFontLight = customFontLight.deriveFont(Font.BOLD,25f);

            // Apply to a button or label
            equip1.setFont(customFont);
            equip3.setFont(customFont);
            claim.setFont(customFont);
            BackButton.setFont(customFontLight);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        claim.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int beforeXP = player.getXP();
                clicked[0]++;

                if (clicked[0] < 2) {
                    player.setXP(500);
                    // Save updated XP to file
                    try (FileWriter writer = new FileWriter("player_xp.txt")) {
                        writer.write(String.valueOf(player.getXP()));
                    } catch (IOException ex) {
                        System.out.println("Error saving XP.");
                    }

                    JOptionPane.showMessageDialog(
                            null,
                            "You have claimed 500 XP!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                claim.setText("Claimed");
                claim.setBackground(new Color(38, 196, 86));



                if(beforeXP<10000 && player.getXP()>=10000){
                    player.unlockSecondLifeBooster(player);
                    layer1.remove(locked2UPButton);
                    layer1.add(equip3, Integer.valueOf(1));
                    layer1.revalidate();
                    layer1.repaint();
                }
            }
        });

        BackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                BackButton.setBackground(Color.WHITE);
                BackButton.setForeground(new Color(81, 112, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                BackButton.setBackground(new Color(246, 205, 90));
                BackButton.setForeground(new Color(0,0,128));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Unlockframe.setVisible(false);
                try {
                    new TestPlayWindow();
                }catch (IOException ex){
                    ex.printStackTrace();
                }catch (FontFormatException ex){
                    ex.printStackTrace();
                }



            }
        });

        Unlockframe.add(layer1);
        Unlockframe.setLocationRelativeTo(null);
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
