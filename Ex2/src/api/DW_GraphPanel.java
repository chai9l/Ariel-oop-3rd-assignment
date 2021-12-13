package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DW_GraphPanel extends JPanel {
    DW_GraphAlgo graphAlgo;
    DW_Graph graph;
    private int kRADIUS = 10;
    private int mWin_h = 1500;
    private int mWin_w = 1500;

    public DW_GraphPanel(DW_Graph graph) {
        this.graph = graph;
        this.setSize(mWin_w,mWin_h);
        new DW_GraphAlgo().init(graph);
        this.setBackground(new Color(0xFF3D3A3A, true));
    }
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void paint(Graphics g) {
        Iterator<NodeData> nodes = graph.nodeIter();
        graph.getmaxMin();
        while(nodes.hasNext()){
            myNode n = (myNode) nodes.next();
            double x = 400 + 2*kRADIUS + (n.getLocation().x()-graph.offset_x)*graph.factor_x ;
            double y = 2*kRADIUS + 150 + (n.getLocation().y()-graph.offset_y)*graph.factor_y;
            Color c = Color.ORANGE;
            if(n.getTag() == -1){
                c = Color.ORANGE;
            }else if(n.getTag() == 2)  {
                c = Color.red;
            }

            g.setColor(c);
            g.fillOval((int)x ,(int)y,2*kRADIUS,2*kRADIUS);
            g.setColor(Color.white);
            g.drawString("" +n.getKey(),(int)x,(int)y);
            Iterator <myNode> neighbors = n.getNi().iterator();
            while(neighbors.hasNext()) {
                myNode p = (myNode) neighbors.next();
                double _x = 2*kRADIUS + 400 + (p.getLocation().x()-graph.offset_x)*graph.factor_x;
                double _y = 2*kRADIUS + 150 + (p.getLocation().y()-graph.offset_y)*graph.factor_y;
                Color c2 = new Color(0xEC9033);
                myEdge edge = (myEdge) graph.getEdge(n.getKey(),p.getKey());
                if(edge.getTag() == 1) {
                    c2 = new Color(0x0C0000);
                }
                g.setColor(c2);
                int cor = 5;
                g.drawLine((int) x + (kRADIUS/2) + cor ,(int) y + (kRADIUS/2) + cor ,(int) _x + (kRADIUS/2)+ cor,(int) _y + (kRADIUS/2)+ cor);;
                drawArrowLine(g,(int)x + (kRADIUS/2)+cor,(int)y + (kRADIUS/2)+cor,(int)_x + (kRADIUS/2) +cor,(int)_y + (kRADIUS/2)+cor,15,5);
            }
        }
    }

}
