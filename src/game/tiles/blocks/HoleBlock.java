package game.tiles.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.util.AABB;
import game.util.Vector2f;

public class HoleBlock extends Block {

    public HoleBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    public boolean update(AABB p) {

        System.out.println("I am a hole");

        return false;
    }


    public void render(Graphics2D g){
        super.render(g);
        g.setColor(Color.green);
        g.drawRect((int) pos.x, (int) pos.y, w, h);
    }

}