package devices;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import particles.Particle;
import particles.ParticleController;

public class Circle extends Device {

    private final Color c;
    private final Point body;
    private final double r;

    public Circle(Point p, Point f) {
        body = new Point(p.x, p.y);
        r = p.distance(f);
        c = new Color((int) (180 + Math.random() * 40), (int) (180 + Math.random() * 40), (int) (180 + Math.random() * 40));
    }

    @Override
    public void update(ParticleController p) {
        Vector2D v, r1;
        for (Particle a : p.getParticles(this::contains)) {
            v = new Vector2D(a.getXVel(), a.getYVel());
            r1 = new Vector2D(body.x - a.x, body.y - a.y);
            double ang;
            if (a.getX() != 0 || a.getYVel() != 0) {
                try {
                    ang = Vector2D.angle(v, r1);
                } catch (Exception e) {
                    return;
                }
                double v1 = a.getVel();
                v1 = v1 * Math.cos(ang);
                a.changeXVel((float) (-2 * v1 * (body.x - a.x) / Math.sqrt(Math.pow(body.x - a.x, 2) + Math.pow(body.y - a.y, 2))));
                a.changeYVel((float) (-2 * v1 * (body.y - a.y) / Math.sqrt(Math.pow(body.x - a.x, 2) + Math.pow(body.y - a.y, 2))));
            }
            a.setX((float) (a.getX() - (r - Math.sqrt(Math.pow(body.x - a.x, 2) + Math.pow(body.y - a.y, 2))) * (body.x - a.x) / Math.sqrt(Math.pow(body.x - a.x, 2) + Math.pow(body.y - a.y, 2))));
            a.setY((float) (a.getY() - (r - Math.sqrt(Math.pow(body.x - a.x, 2) + Math.pow(body.y - a.y, 2))) * (body.y - a.y) / Math.sqrt(Math.pow(body.x - a.x, 2) + Math.pow(body.y - a.y, 2))));
        }

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(c);
        g.fillOval((int) (body.x - r), (int) (body.y - r), (int) (2 * r), (int) (2 * r));
    }

    @Override
    public boolean onClick() {
        return true;
    }

    @Override
    public boolean contains(Point p) {
        return body.getLocation().distance(p) < r;
    }

    public static Circle create(Point[] p) {
        return new Circle(p[0], p[1]);
    }

    public static Shape[] createAffectedShape(Point[] a) {
        double r = a[0].distance(a[1]);
        return new Shape[]{new Ellipse2D.Float((int) (a[0].x - r), (int) (a[0].y - r), (int) (2 * r), (int) (2 * r))};
    }

    public static Shape[] createMenuShape(Point a) {
        return new Shape[]{new Ellipse2D.Float(a.x - 20, a.y - 20, 40, 40)};
    }

}
