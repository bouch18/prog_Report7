package ac.jp.uryukyu.ie.e165745;

/*
 * Created by e165745 on 2017/01/31.

 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import javax.imageio.stream.*;
/*
 * Created on 2005/10/09
 *
 */

/**
 * @author mori
 *
 */
class MainPanel extends JPanel implements KeyListener, Common{
    // パネルサイズ
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;

    // 行数（単位：マス）
    private static final int ROW = 15;
    // 列数（単位：マス）
    private static final int COL = 15;
    // チップセットのサイズ（単位：ピクセル）
    private static final int CS = 32;

    // マップ 0:床 1:壁
/*    private int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};*/

    // チップセット
    private Image floorImage;
    private Image wallImage;
    private Image heroImage;

    // 勇者の座標
    private int x, y;
    // 勇者のアニメーションカウンタ
    private int count;

    // キャラクターアニメーション用スレッド
    private Thread threadAnime;

    public MainPanel() {
        // パネルの推奨サイズを設定
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // イメージをロード
        loadImage();

        // 勇者の位置を初期化
        x = 1;
        y = 1;

        count = 1;

        // パネルがキー操作を受け付けるように登録する
        setFocusable(true);
        addKeyListener(this);

        // キャラクターアニメーション用スレッド開始
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // マップを描く
        drawMap(g);

        // 勇者を描く
        //drawChara(g);
        g.drawImage(heroImage, 0, 0, this);
    }

    public void keyPressed(KeyEvent e) {
        // 押されたキーを調べる
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT :
                // 左キーだったら勇者を1歩左へ
                move(LEFT);
                break;
            case KeyEvent.VK_RIGHT :
                // 右キーだったら勇者を1歩右へ
                move(RIGHT);
                break;
            case KeyEvent.VK_UP :
                // 上キーだったら勇者を1歩上へ
                move(UP);
                break;
            case KeyEvent.VK_DOWN :
                // 下キーだったら勇者を1歩下へ
                move(DOWN);
                break;
        }

        // 勇者の位置を動かしたので再描画
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    private boolean isHit(int x, int y) {
        // (x,y)に壁があったらぶつかる
        /*if (map[y][x] == 1) {
            return true;
        }*/

        // なければぶつからない
        return false;

    }

    private void move(int dir) {
        // dirの方向でぶつからなければ移動する
        switch (dir) {
            case LEFT:
                if (!isHit(x-1, y)) x--;
                break;
            case RIGHT:
                if (!isHit(x+1, y)) x++;
                break;
            case UP:
                if (!isHit(x, y-1)) y--;
                break;
            case DOWN:
                if (!isHit(x, y+1)) y++;
                break;
        }
    }
//ImageIO.read(new File("image/wall.gif"));
    private void loadImage() {
        /*try {
            //File filename = new File("imagge/hero.gif");
            ImageInputStream in = createImaeInputStream(getClass().getResourceAsStream("image/hero.gif"));
            heroImage = ImageIO.read(in);

            //BufferedImage Img = ImageIO.read(getClass().getResourceAsStream("image/hero.gif"));
            //heroImage = Img.getSubimage(0, 0, 64, 32);
            //BufferedImage Img = ImageIO.read(getClass().getResourceAsStream("image/hero.gif"));
            //heroImage = new BufferedImage(64, 64, Img.getType());
        }catch(IOException e) {
            System.out.println("ファイルがありません。");
        }*/
        ImageIcon icon = new ImageIcon(getClass().getResource("image/hero.gif"));
        heroImage = icon.getImage();

        icon = new ImageIcon(getClass().getResource("image/floor.gif"));
        floorImage = icon.getImage();

        icon = new ImageIcon(getClass().getResource("image/wall.gif"));
        wallImage = icon.getImage();
    }

    private void drawChara(Graphics g) {
        // countの値に応じて表示する画像を切り替える
        //g.drawImage(heroImage, x*CS, y*CS, x*CS+CS, y*CS+CS,
        //        0, 0, 64, 64, this);
        //g.drawImage(heroImage, 0, 0, this);

    }

    private void drawMap(Graphics g) {
        /*for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                // mapの値に応じて画像を描く
                switch (map[i][j]) {
                    case 0 : // 床
                        g.drawImage(floorImage, j * CS, i * CS, this);
                        break;
                    case 1 : // 壁
                        g.drawImage(wallImage, j * CS, i * CS, this);
                        break;
                }
            }
        }*/
    }

    // アニメーションクラス
    private class AnimationThread extends Thread {
        public void run() {
            while (true) {
                // countを切り替える
                if (count == 1) {
                    count = 0;
                } else if (count == 0) {
                    count = 1;
                }

                repaint();

                // 300ミリ秒休止＝300ミリ秒おきに勇者の絵を切り替える
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}