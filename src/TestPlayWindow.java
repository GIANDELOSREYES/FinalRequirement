import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

public class TestPlayWindow {

    public TestPlayWindow() throws IOException, FontFormatException {
        JFrame frame = new JFrame("Bato-Bato Pick");
        Dimension size = new Dimension(768, 630);
        frame.setSize(size);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image backgroundImg = new ImageIcon(getClass().getResource("Background.gif")).getImage();

        JPanel backgroundPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0,0,getWidth(),getHeight(),this);
            }
        };
        backgroundPanel.setOpaque(true);
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBounds(0, 0, size.width, size.height);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(size);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        final int arc = 50;
        JButton startButton = new JButton("Start") {
            @Override
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

        startButton.setBounds(290, 430, 200, 50);
        startButton.setBackground(new Color(241, 109, 124));
        startButton.setForeground(Color.white);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);


        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setBackground(Color.WHITE);
                startButton.setForeground(new Color(81, 112, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setBackground(new Color(241, 109, 124));
                startButton.setForeground(Color.white);
            }
        });

        JButton UnlocksButton = new JButton("Unlocks"){
            @Override
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
        UnlocksButton.setBounds(290, 500, 200, 50);
        UnlocksButton.setBackground(new Color(246, 205, 90));
        UnlocksButton.setForeground(new Color(0,0,128));
        UnlocksButton.setBorderPainted(false);
        UnlocksButton.setFocusPainted(false);
        UnlocksButton.setContentAreaFilled(false);


        UnlocksButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                UnlocksButton.setBackground(Color.WHITE);
                UnlocksButton.setForeground(new Color(81, 112, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                UnlocksButton.setBackground(new Color(246, 205, 90));
                UnlocksButton.setForeground(new Color(0,0,128));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                Player player = new Player();
                player.getUnlocksFrame();
            }
        });

        try {
            // Load font from resources
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("Cubao_Free_Regular.otf") // leading "/" means root of resources
            );

            // Derive size and style
            customFont = customFont.deriveFont(Font.BOLD, 30f);

            // Apply to a button or label
            startButton.setFont(customFont);
            UnlocksButton.setFont(customFont);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        layeredPane.add(startButton, Integer.valueOf(1));
        layeredPane.add(UnlocksButton, Integer.valueOf(1));

        frame.add(layeredPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException, FontFormatException, InterruptedException {

        //Preloader preloader = new Preloader();
        //preloader.setVisible(true);

        //Thread.sleep(7900);

        //preloader.setVisible(false);

        new TestPlayWindow();
    }
}

class Preloader extends JWindow {

    public Preloader() {
        JLabel animation = new JLabel(new ImageIcon(getClass().getResource("ock.gif")));
        add(animation);
        pack();
        setLocationRelativeTo(null);
    }

}
