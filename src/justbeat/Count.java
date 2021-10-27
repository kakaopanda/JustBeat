package justbeat;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;
 
import java.awt.Image;
import java.awt.image.BufferedImage;
 
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
 
import java.util.Date;
 
class Count extends Frame{

    Graphics gMain = null, gBuf = null;
    
    Count(String title){
        super(title);
        
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                pause = true;
                dispose();
                System.exit(0);
            }
        });
    }
    
    FpsDelayer fpsDelayer = new FpsDelayer(60);
    // �ʴ� 60�������� ����
    FpsCounter fpsCounter = new FpsCounter();
    // ���� FPS�� �����ϰ� �����ִ� Ŭ����
    boolean pause = false;
    public long diffTime = 0;
    void gameLoop(){
    		long startTime = 0, finishTime = 0;
            startTime = new Date().getTime();
            
            update();    // ��ǥ�̵�, ��ġ�������� ���� ���� ����
            
            finishTime = new Date().getTime();
            diffTime = finishTime - startTime;
            
            fpsDelayer.delay((int)diffTime);
            // �̹� ������ ����ð��� ���ڷ� ��
            fpsCounter.count();
            // FPS�� ī��Ʈ�ϵ��� �� �ݺ����� ȣ��.
    }
    
    public static int count = 0;
    void update(){
        count++;
        int delay = (int)(Math.random() * 8) + 6;
        // 0.006�� ~ 0.015�� ��ŭ ������ �����̸� �ش�.
        try{
            Thread.sleep(delay);
            // CPU ������ delay ��ŭ �����ϴ� �޼���
        }catch(Exception e){}
    }
}
