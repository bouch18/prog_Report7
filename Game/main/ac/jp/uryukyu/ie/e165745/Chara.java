package ac.jp.uryukyu.ie.e165745;

/**
 * Created by e165745 on 2017/01/31.
 */
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/*
 * Created on 2005/10/10
 *
 */

/**
 * @author mori
 *
 */
/*public class Chara implements Common {
    // キャラクターのイメージ
    private Image image;

    // キャラクターの座標
    private int x, y;
    // キャラクターの向いている方向（LEFT,RIGHT,UP,DOWNのどれか）
    private int direction;
    // キャラクターのアニメーションカウンタ
    private int count_1;

    // キャラクターアニメーション用スレッド
    private Thread threadAnime;

    // マップへの参照
    private Map map;

    // パネルへの参照
    private MainPanel panel;

    public Chara(int x, int y, String filename, Map map, MainPanel panel) {
        this.x = x;
        this.y = y;

        direction = DOWN;
        count_1 = 0;

        this.map = map;
        this.panel = panel;

        // イメージをロード
        loadImage(filename);

        // キャラクターアニメーション用スレッド開始
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }

    public void draw(Graphics g) {
        // countとdirectionの値に応じて表示する画像を切り替える
        g.drawImage(image, x * CS, y * CS, x * CS + CS, y * CS + CS,
                count_1 * CS, direction * CS, count_1 * CS + CS, direction * CS + CS, panel);
    }

    public void move(int dir) {
        // dirの方向でぶつからなければ移動する
        switch (dir) {
            case LEFT:
                if (!map.isHit(x-1, y)) x--;
                direction = LEFT;
                break;
            case RIGHT:
                if (!map.isHit(x+1, y)) x++;
                direction = RIGHT;
                break;
            case UP:
                if (!map.isHit(x, y-1)) y--;
                direction = UP;
                break;
            case DOWN:
                if (!map.isHit(x, y+1)) y++;
                direction = DOWN;
                break;
        }
    }

    private void loadImage(String filename) {
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        image = icon.getImage();
    }

    // アニメーションクラス
    private class AnimationThread extends Thread {
        public void run() {
            while (true) {
                // countを切り替える
                if (count_1 == 0) {
                    count_1 = 1;
                } else if (count_1 == 1) {
                    count_1 = 0;
                }

                panel.repaint();

                // 300ミリ秒休止＝300ミリ秒おきにキャラクターの絵を切り替える
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
*/