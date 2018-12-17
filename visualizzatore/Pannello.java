import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.lang.*;
public class Pannello extends JPanel
{
    static 
    {
        //System.loadLibrary("serialLib");
        System.load("/home/davide/3A_INF_INF_2018_19/visualizzatore/libserialLib.so");
    }
    public static native int apriSeriale(String port);
    public static native void leggiDaSeriale();
    public static native int prendiValoreLetto(int pos);
    public static native void chiudiSeriale();

    private double a,b,c;
    Pannello()
    {
        a=1;
        b=0;
        c=0;
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        Pannello p = new Pannello();
        frame.setContentPane(p);
        frame.setBounds(0,0,500,500);
        frame.setVisible(true);        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Ciclo infinito di lettura della seriale
        apriSeriale("/dev/ttyUSB0");
        System.out.println("Seriale aperta");
        while(true)
        {
            try{Thread.sleep(100);}catch(Exception e){}
            leggiDaSeriale();
            p.a=((double)(prendiValoreLetto(0)-'a'-13))/10;
            p.b=(prendiValoreLetto(1)-'a')/10;
            p.c=(prendiValoreLetto(2)-'a')/10;
            p.repaint();
            p.revalidate();
        }
        //chiudiSeriale();
    }

    double parabola(double x)
    {
        return a*x*x+b*x+c;
    }

    void disegnaParabola(Graphics g)
    {
        int w=getWidth();
        int h=getHeight();
        g.clearRect(0,0,w,h);
        //intervalli del piano da visualizzare
        double xmin=-10,xmax=10;
        double ymin=-10,ymax=10;
        //rapporto di dimensioni tra piano cartesiano e schermo
        double xstep = (xmax-xmin)/w;        
        double ystep = (ymax-ymin)/h;

        //disegna assi
        g.drawLine(0,h/2,w,h/2); //x
        g.drawLine(w/2,0,w/2,h); //y

        //per ogni punto orizzontale dello schermo
        int i;
        for(i=0;i<w;i++)
        {
            //cordinata x nel piano a partire dal punto nello schermo
            double x = i*xstep+xmin;
            //coordinata y nel piano
            double y = parabola(x);
            //coordinata y nello schermo
            int dy = -(int)((y+ymin)/ystep);
            //disegno del punto (linea di un solo punto)
            g.drawLine(i,dy,i,dy);
        }        
    }

    @Override
    public void paintComponent(Graphics g)
    {
        disegnaParabola(g); 
    }
}
