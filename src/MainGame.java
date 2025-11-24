import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
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



        Dimension size = new Dimension(768, 630);
        JPanel mainPanel = new JPanel(new CardLayout());
        JLabel readyLabel = new JLabel(new ImageIcon(MainGame.class.getResource("ReadySetGo.gif")));

        JPanel howToPlayPanel;
        Image backgroundImgHow = new ImageIcon(MainGame.class.getResource("How.png")).getImage();



        howToPlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImgHow, 0, 0, getWidth(), getHeight(), this);
            }
        };

        howToPlayPanel.setOpaque(true);
        howToPlayPanel.setLayout(new BorderLayout());
        howToPlayPanel.setBounds(0, 0, size.width, size.height);

        JButton backMenu = new JButton("Back to Menu");
        backMenu.setBounds(210, 530, 170, 40);
        backMenu.setBackground(new Color(17, 89, 201));
        backMenu.setForeground(Color.white);
        backMenu.setFocusPainted(false);
        backMenu.setBorderPainted(false);
        backMenu.setContentAreaFilled(true);


        JButton PlayButton = new JButton("Play");
        PlayButton.setBounds(400, 530, 170, 40);
        PlayButton.setBackground(new Color(106, 199, 63));
        PlayButton.setForeground(Color.white);
        PlayButton.setFocusPainted(false);
        PlayButton.setBorderPainted(false);
        PlayButton.setContentAreaFilled(true);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, MainGame.class.getResourceAsStream("Cubao_Free_Regular.otf") // leading "/" means root of resources
            );

            customFont = customFont.deriveFont(Font.PLAIN, 20f);
            Font customFont1 = customFont.deriveFont(Font.PLAIN, 25f);

            backMenu.setFont(customFont);
            PlayButton.setFont(customFont1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        JLayeredPane backMenuLayered = new TestPlayWindow().getLayeredPane();

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        JFrame frame = new JFrame("Bato-Bato Pick");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPaneHow = new JLayeredPane();
        layeredPaneHow.setPreferredSize(size);
        layeredPaneHow.add(howToPlayPanel, Integer.valueOf(0));
        layeredPaneHow.add(backMenu, Integer.valueOf(1));
        layeredPaneHow.add(PlayButton, Integer.valueOf(1));

        mainPanel.add(readyLabel, "ReadySetGo");
        mainPanel.add(layeredPaneHow, "HowToPlay");
        mainPanel.add(backMenuLayered, "MainMenu");
        cardLayout.show(mainPanel, "HowToPlay");


        backMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.next(mainPanel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                backMenu.setBackground(Color.WHITE);
                backMenu.setForeground(new Color(81, 112, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backMenu.setBackground(new Color(17, 89, 201));
                backMenu.setForeground(Color.white);
            }
        });



        Image backgroundImgChoice = new ImageIcon(MainGame.class.getResource("PlayerChoice.gif")).getImage();

        JPanel backgroundPanelChoice = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImgChoice, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanelChoice.setOpaque(true);
        backgroundPanelChoice.setLayout(new BorderLayout());
        backgroundPanelChoice.setBounds(0, 0, size.width, size.height);

        JLayeredPane layeredPaneChoice = new JLayeredPane();
        layeredPaneChoice.setPreferredSize(size);
        layeredPaneChoice.add(backgroundPanelChoice, Integer.valueOf(0));

        mainPanel.add(layeredPaneChoice, "PlayerChoice");

        PlayButton.addMouseListener(new MouseAdapter() {;


            @Override
            public void mouseEntered(MouseEvent e) {
                PlayButton.setBackground(Color.WHITE);
                PlayButton.setForeground(new Color(106, 199, 63));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                PlayButton.setBackground(new Color(106, 199, 63));
                PlayButton.setForeground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "ReadySetGo");

                Timer showTimer = new Timer(2700, ex -> {
                    cardLayout.show(mainPanel, "PlayerChoice");
                });

                showTimer.setRepeats(false);
                showTimer.start();
            }
        });

        final int arc = 50;

        JButton batoButton = new JButton("Rock"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                String text = getText();
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getAscent();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 4;

                g2.setColor(new Color(0, 0, 0, 130));
                g2.drawString(text, x +1, y + 2);


                g2.setColor(getForeground());
                g2.drawString(text, x, y);
                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            public boolean contains(int x, int y) {
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc);
                return shape.contains(x, y);
            }};
        batoButton.setBounds(40, 550, 190, 50);
        batoButton.setBackground(new Color(255, 215, 0));
        batoButton.setForeground(Color.white);
        batoButton.setFocusPainted(false);
        batoButton.setBorderPainted(false);
        batoButton.setContentAreaFilled(false);

        JButton papelButton = new JButton("PAPER"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                String text = getText();
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getAscent();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 4;

                g2.setColor(new Color(0, 0, 0, 130));
                g2.drawString(text, x +1, y + 2);


                g2.setColor(getForeground());
                g2.drawString(text, x, y);
                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            public boolean contains(int x, int y) {
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc);
                return shape.contains(x, y);
            }};
        papelButton.setBounds(300, 550, 190, 50);
        papelButton.setBackground(new Color(38, 196, 86));
        papelButton.setForeground(Color.white);
        papelButton.setFocusPainted(false);
        papelButton.setBorderPainted(false);
        papelButton.setContentAreaFilled(false);

        JButton guntingButton = new JButton("SCISSORS"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                String text = getText();
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getAscent();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 4;

                g2.setColor(new Color(0, 0, 0, 130));
                g2.drawString(text, x +1, y + 2);


                g2.setColor(getForeground());
                g2.drawString(text, x, y);
                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            public boolean contains(int x, int y) {
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc);
                return shape.contains(x, y);
            }};
        guntingButton.setBounds(550, 550, 190, 50);
        guntingButton.setBackground(new Color(81, 112, 255));
        guntingButton.setForeground(new Color(255, 222, 23));
        guntingButton.setFocusPainted(false);
        guntingButton.setBorderPainted(false);
        guntingButton.setContentAreaFilled(false);

        layeredPaneChoice.add(batoButton, Integer.valueOf(1));
        layeredPaneChoice.add(papelButton, Integer.valueOf(1));
        layeredPaneChoice.add(guntingButton, Integer.valueOf(1));

        batoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                batoButton.setBackground(Color.WHITE);
                batoButton.setForeground(new Color(255, 215, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                batoButton.setBackground(new Color(255, 215, 0));
                batoButton.setForeground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {



            }
        });

        papelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                papelButton.setBackground(Color.WHITE);
                papelButton.setForeground(new Color(38, 196, 86));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                papelButton.setBackground(new Color(38, 196, 86));
                papelButton.setForeground(Color.white);
            }
        });

        guntingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                guntingButton.setBackground(Color.WHITE);
                guntingButton.setForeground(new Color(81, 112, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                guntingButton.setBackground(new Color(81, 112, 255));
                guntingButton.setForeground(new Color(255, 222, 23));
            }
        });



        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, MainGame.class.getResourceAsStream("Cubao_Free_Regular.otf") // leading "/" means root of resources
            );

            customFont = customFont.deriveFont(Font.PLAIN, 30f);

            batoButton.setFont(customFont);
            papelButton.setFont(customFont);
            guntingButton.setFont(customFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }




        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);










        }

    }

    public void seeOutput(){
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

        int accumulatedXP = Player.loadXP();
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
