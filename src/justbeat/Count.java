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
    // 초당 60프레임을 원함
    FpsCounter fpsCounter = new FpsCounter();
    // 현재 FPS를 측정하고 보여주는 클래스
    boolean pause = false;
    public long diffTime = 0;
    void gameLoop(){
    		long startTime = 0, finishTime = 0;
            startTime = new Date().getTime();
            
            update();    // 좌표이동, 수치증감같은 각종 연산 수행
            
            finishTime = new Date().getTime();
            diffTime = finishTime - startTime;
            
            fpsDelayer.delay((int)diffTime);
            // 이번 루프의 연산시간을 인자로 줌
            fpsCounter.count();
            // FPS를 카운트하도록 매 반복마다 호출.
    }
    
    public static int count = 0;
    void update(){
        count++;
        int delay = (int)(Math.random() * 8) + 6;
        // 0.006초 ~ 0.015초 만큼 무작위 딜레이를 준다.
        try{
            Thread.sleep(delay);
            // CPU 연산을 delay 만큼 쉬게하는 메서드
        }catch(Exception e){}
    }
}
