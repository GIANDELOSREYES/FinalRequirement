import com.sun.jdi.IntegerValue;

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


    Player myPlayer = new Player();

    public Player getMyPlayer() {
        return myPlayer;
    }

    public static void main(String[] args) {
        Player myPlayer = new MainGame().getMyPlayer();
        Player Computer = new Player();
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


        JLabel BatoGunting = new JLabel(new ImageIcon(MainGame.class.getResource("BatoVsGunting.gif")));
        BatoGunting.setHorizontalAlignment(SwingConstants.CENTER);
        BatoGunting.setVerticalAlignment(SwingConstants.CENTER);


        //JPanel for Bato vs Bato

        Image BatoDraw = new ImageIcon(MainGame.class.getResource("BatoVsBato.gif")).getImage();

        JPanel BatoBato = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(BatoDraw, 0, 0, getWidth(), getHeight(), this);
            }
        };

        //JPanel for Bato vs Papel

        Image BatoLose = new ImageIcon(MainGame.class.getResource("BatoVsPapel.gif")).getImage();

        JPanel BatoPapel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(BatoLose, 0, 0, getWidth(), getHeight(), this);
            }
        };


        //JPanel for Bato Win Render


        JLabel BatoWinRender = new JLabel(new ImageIcon(MainGame.class.getResource("BatoVSGuntingRender.gif")));
        BatoWinRender.setHorizontalAlignment(SwingConstants.CENTER);
        BatoWinRender.setVerticalAlignment(SwingConstants.CENTER);


        //JPanel for Bato Lose Render

        Image BatoLoseRenderImg = new ImageIcon(MainGame.class.getResource("BatoVSPapelRender.gif")).getImage();


        JPanel BatoLoseRender = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(BatoLoseRenderImg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.add(BatoGunting, "BatoisWin");
        mainPanel.add(BatoBato, "BatoisDraw");
        mainPanel.add(BatoPapel, "BatoisLose");
        mainPanel.add(BatoWinRender, "BatoWinRender");
        mainPanel.add(BatoLoseRender, "BatoLoseRender");


        //---------------------------------

        Image winBg = myPlayer.getWithLifebg();

        final JPanel[] WinPanel = {new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(winBg, 0, 0, getWidth(), getHeight(), this);
            }
        }};
        WinPanel[0].setLayout(new BorderLayout());
        WinPanel[0].setOpaque(true);
        WinPanel[0].setBounds(0,0,size.width,size.height);

        JLabel XPlabel = new JLabel("Acquired XP: "+myPlayer.getAccumulatedXP());
        XPlabel.setBounds(270,315, 300,100);
        XPlabel.setForeground(Color.white);



        JButton doubleItButton = new JButton("Double It!"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // draw rounded background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                // let the button draw the text (button is non-opaque so it won't fill its background)
                super.paintComponent(g);
            }

            @Override
            public boolean contains(int x, int y) {
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                return shape.contains(x, y);
            }
        };
        doubleItButton.setBounds(150, 430, 480, 50);
        doubleItButton.setForeground(new Color(0,0,128));
        doubleItButton.setBackground(new Color(246, 205, 90));
        doubleItButton.setBorderPainted(false);
        doubleItButton.setFocusPainted(false);
        doubleItButton.setContentAreaFilled(false);

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, MainGame.class.getResourceAsStream("Cubao_Free_Regular.otf") // leading "/" means root of resources
            );
            customFont = customFont.deriveFont(Font.BOLD, 30f);
            Font customFontLight = customFont.deriveFont(Font.PLAIN, 25f);

            XPlabel.setFont(customFont);
            doubleItButton.setFont(customFontLight);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        doubleItButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel,"PlayerChoice");
                int ToAdd = myPlayer.getAccumulatedXP()*2;
                myPlayer.addAccumulatedXP(ToAdd);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                doubleItButton.setBackground(Color.WHITE);
                doubleItButton.setForeground(new Color(106, 199, 63));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                doubleItButton.setForeground(new Color(0,0,128));
                doubleItButton.setBackground(new Color(246, 205, 90));
            }
        });






        JLayeredPane mainPane = new JLayeredPane();
        mainPane.setLayout(null);
        mainPane.setPreferredSize(size);
        mainPane.add(WinPanel[0], Integer.valueOf(0));

        mainPane.add(XPlabel, Integer.valueOf(1));
        mainPane.add(doubleItButton, Integer.valueOf(1));

        //Game Over

        Image over = new ImageIcon((MainGame.class.getResource("Over.png"))).getImage();
        JPanel GameOver = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(over,0,0,getWidth(),getHeight(), this);
            }
        };
        GameOver.setOpaque(true);
        GameOver.setLayout(new BorderLayout());
        GameOver.setBounds(0, 0, size.width, size.height);

        JLabel TotalXP = new JLabel("Total XP: "+myPlayer.getXP());
        TotalXP.setForeground(Color.white);
        TotalXP.setBounds(290,315, 300,100);

        JButton PlayAgain = new JButton("Play Again?"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // draw rounded background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.dispose();
                // let the button draw the text (button is non-opaque so it won't fill its background)
                super.paintComponent(g);
            }

            @Override
            public boolean contains(int x, int y) {
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);
                return shape.contains(x, y);
            }
        };
        PlayAgain.setForeground(new Color(106, 199, 63));
        PlayAgain.setBackground(Color.WHITE);
        PlayAgain.setBounds(150, 420, 230, 60);
        PlayAgain.setFocusPainted(false);
        PlayAgain.setBorderPainted(false);
        PlayAgain.setContentAreaFilled(false);

        JButton ReturnToMenu = new JButton("Return To Menu!"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // draw rounded background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.dispose();
                // let the button draw the text (button is non-opaque so it won't fill its background)
                super.paintComponent(g);
            }

            @Override
            public boolean contains(int x, int y) {
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);
                return shape.contains(x, y);
            }
        };

        ReturnToMenu.setForeground(new Color(0,0,128));
        ReturnToMenu.setBackground(new Color(246, 205, 90));
        ReturnToMenu.setBounds(400, 420, 240, 60);
        ReturnToMenu.setFocusPainted(false);
        ReturnToMenu.setBorderPainted(false);
        ReturnToMenu.setContentAreaFilled(false);

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, MainGame.class.getResourceAsStream("Cubao_Free_Regular.otf") // leading "/" means root of resources
            );
            customFont = customFont.deriveFont(Font.PLAIN, 30f);
            Font customFontBolder = customFont.deriveFont(Font.BOLD, 23f);

            TotalXP.setFont(customFont);
            PlayAgain.setFont(customFontBolder);
            ReturnToMenu.setFont(customFontBolder);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        PlayAgain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myPlayer.setLife(3);
                Computer.setLife(3);

                int currentAccumulatedXP = myPlayer.getAccumulatedXP();
                myPlayer.setXP(currentAccumulatedXP);
                myPlayer.setAccumulatedXP(20);

                myPlayer.setImagewithLife(new ImageIcon(MainGame.class.getResource("bigwin.png")).getImage());
                cardLayout.show(mainPanel, "PlayerChoice");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                PlayAgain.setForeground(Color.WHITE);
                PlayAgain.setBackground(new Color(106, 199, 63));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                PlayAgain.setForeground(new Color(106, 199, 63));
                PlayAgain.setBackground(Color.WHITE);
            }
        });

        ReturnToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ReturnToMenu.setBackground(new Color(0,0,128));
                ReturnToMenu.setForeground(new Color(246, 205, 90));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ReturnToMenu.setForeground(new Color(0,0,128));
                ReturnToMenu.setBackground(new Color(246, 205, 90));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel,"MainMenu");
            }
        });


        JLayeredPane layeredPaneForGameOver = new JLayeredPane();
        layeredPaneForGameOver.setPreferredSize(size);

        layeredPaneForGameOver.add(GameOver, Integer.valueOf(0));
        layeredPaneForGameOver.add(PlayAgain, Integer.valueOf(1));
        layeredPaneForGameOver.add(TotalXP, Integer.valueOf(1));
        layeredPaneForGameOver.add(ReturnToMenu, Integer.valueOf(1));
        mainPanel.add(layeredPaneForGameOver, "GameOver");
        mainPanel.add(mainPane, "WinPanel");

        Image drawBg = new ImageIcon(MainGame.class.getResource("draw.png")).getImage();

        JPanel DrawPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(drawBg, 0,0,getWidth(),getHeight(),this);
            }
        };
        DrawPanel.setOpaque(true);
        DrawPanel.setLayout(new BorderLayout());
        DrawPanel.setBounds(0, 0, size.width, size.height);

        mainPanel.add(DrawPanel, "drawPanel");
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
                myPlayer.setUserChooses("Bato");
                GameElement[] choices = new GameElement[]{Bato, Gunting};
                Random computerChoice = new Random();
                int randomIndex = computerChoice.nextInt(choices.length);
                GameElement selectedChoice = choices[randomIndex];

                String userChoice = myPlayer.getUserChooses();

                boolean win;
                win = Bato.isWin(selectedChoice);


                    if (win) {
                        Computer.getDamage();
                        XPlabel.setText("Acquired XP: " + myPlayer.getAccumulatedXP());
                        updateWinBackground(myPlayer, Computer);
                        cardLayout.show(mainPanel,"BatoisWin");

                        mainPane.remove(WinPanel[0]);

                        Image newWinBg = updateWinBackground(myPlayer, Computer);

                        WinPanel[0] = new JPanel(){
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                g.drawImage(newWinBg, 0,0,getWidth(),getHeight(),this);
                            }
                        };

                        WinPanel[0].setLayout(new BorderLayout());
                        WinPanel[0].setOpaque(true);
                        WinPanel[0].setBounds(0,0,size.width,size.height);

                        mainPane.add(WinPanel[0], Integer.valueOf(0));


                        Timer showTimer = new Timer(7400, ex -> {
                            cardLayout.show(mainPanel, "BatoWinRender");

                            if(Computer.isAlive()) {

                                Timer showTimer1 = new Timer(4800, exx -> {
                                    cardLayout.show(mainPanel, "WinPanel");
                                });

                                showTimer1.setRepeats(false);
                                showTimer1.start();
                            }else{
                                Timer showTimer1 = new Timer(4800, exx -> {
                                    cardLayout.show(mainPanel, "GameOver");
                                });

                                showTimer1.setRepeats(false);
                                showTimer1.start();
                            }

                        });

                        showTimer.setRepeats(false);
                        showTimer.start();


                    } else if (userChoice.equalsIgnoreCase(selectedChoice.getName())) {
                        cardLayout.show(mainPanel, "BatoisDraw");
                        Timer showTimer = new Timer(7400, ex -> {
                            cardLayout.show(mainPanel,"drawPanel");

                            Timer showTimer1 = new Timer(3000, exx -> {
                                cardLayout.show(mainPanel, "PlayerChoice");
                            });

                            showTimer1.setRepeats(false);
                            showTimer1.start();
                        });

                        showTimer.setRepeats(false);
                        showTimer.start();

                }else {
                    cardLayout.show(mainPanel,"BatoisLose");

                    Timer showTimer = new Timer(8000, ex -> {
                        cardLayout.show(mainPanel, "BatoLoseRender");
                    });

                    showTimer.setRepeats(false);
                    showTimer.start();
                }
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

        public static Image updateWinBackground(Player myPlayer, Player Computer){

        Image newImage = null;

            if(myPlayer.getLife()==3 && Computer.getLife()==2){
                newImage = new ImageIcon(MainApp.class.getResource("bigwin.png")).getImage();
            } else if (myPlayer.getLife()==3 && Computer.getLife()==1){
                 newImage = new ImageIcon(MainApp.class.getResource("3-1WIN.png")).getImage();
            } else if (myPlayer.getLife()==2 && Computer.getLife()==2) {
                 newImage = new ImageIcon(MainApp.class.getResource("2-2WIN.png")).getImage();

            }else if (myPlayer.getLife()==2 && Computer.getLife()==1) {
                newImage = new ImageIcon(MainApp.class.getResource("2-1WIN.png")).getImage();
            }else if (myPlayer.getLife()==1 && Computer.getLife()==2) {
                newImage = new ImageIcon(MainApp.class.getResource("1-2WIN.png")).getImage();
            }else if (myPlayer.getLife()==1 && Computer.getLife()==1) {
                 newImage = new ImageIcon(MainApp.class.getResource("1-1WIN.png")).getImage();
            }


            if (newImage!=null){
                myPlayer.setImagewithLife(newImage);
            }
            return  newImage;
        }

}


class MainApp extends Player {

    public void demo(Player myPlayer){
        GameElement Bato = new GameElement("Bato", "Papel", "Gunting");
        GameElement Gunting = new GameElement("Gunting", "Bato", "Papel");
        GameElement Papel = new GameElement("Papel", "Gunting", "Bato");

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
        //mainPanel
        JPanel mainPanel = new JPanel(new CardLayout());

        //cardlayout

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();



        //JPanel for Bato vs Gunting
        Image BatoWin = new ImageIcon(MainApp.class.getResource("BatoVsGunting.gif")).getImage();

        JPanel BatoGunting = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(BatoWin, 0, 0, getWidth(), getHeight(), this);
            }
        };
        //JPanel for Bato vs Bato

        Image BatoDraw = new ImageIcon(MainApp.class.getResource("BatoVsBato.gif")).getImage();

        JPanel BatoBato = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(BatoDraw, 0, 0, getWidth(), getHeight(), this);
            }
        };

        //JPanel for Bato vs Papel

        Image BatoLose = new ImageIcon(MainApp.class.getResource("BatoVsPapel.gif")).getImage();

        JPanel BatoPapel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(BatoLose, 0, 0, getWidth(), getHeight(), this);
            }
        };

        mainPanel.add(BatoGunting, "BatoisWin");
        mainPanel.add(BatoBato, "BatoisDraw");
        mainPanel.add(BatoPapel, "BatoisLose");



        int accumulatedXP = Player.loadXP();
        Scanner userInput = new Scanner(System.in);

        while (myPlayer.isAlive() && Computer.isAlive()) {
            GameElement[] choices = new GameElement[]{Bato, Papel, Gunting};
            Random computerChoice = new Random();
            int randomIndex = computerChoice.nextInt(choices.length);
            GameElement selectedChoice = choices[randomIndex];

            boolean win;

            boolean isUsed = false;
            boolean isUsedCom = false;
            int UP2LifeUsed = 2;

            String userChoice = myPlayer.getUserChooses();


            if (userChoice.equalsIgnoreCase("Bato")) {
                win = Bato.isWin(selectedChoice);

                if (win) {
                    Computer.getDamage();
                    cardLayout.show(mainPanel,"BatoisWin");
                    accumulatedXP += 20;
                    myPlayer.setAccumulatedXP(accumulatedXP);

                    if ((Computer.getLife() == 1 && !isUsedCom) && (!Objects.equals(myPlayer.getCurrentBooster(), "None"))) {
                        if (Objects.equals(myPlayer.getCurrentBooster(), "1UP")) {
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
                    cardLayout.show(mainPanel,"BatoisDraw");
                } else {
                    myPlayer.getDamage();
                    cardLayout.show(mainPanel,"BatoisLose");
                    if ((myPlayer.getLife() > 0 && !isUsed) && (Objects.equals(myPlayer.getCurrentBooster(), "1UP"))) {
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
                    } else if ((myPlayer.getLife() > 0 && (UP2LifeUsed > 0)) && (Objects.equals(myPlayer.getCurrentBooster(), "2UP"))) {
                        System.out.println("Would you like to add life from booster [" + UP2LifeUsed + "remaining]? (yes/no): ");
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

    }





        //-----------------------------------------------------------




        }
    }
