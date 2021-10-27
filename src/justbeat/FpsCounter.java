package justbeat;
/*
* 현재 FPS값을 체크하는 코드
* count()가 호출될때마다 카운팅 하다가
* 1초가 지나면 몇까지 카운팅 했는지 출력한다.
*/
import java.util.Date;
 
public class FpsCounter{
    private Date lastTime = new Date();
    // lastTime은 기준 시간입니다.
    // 처음 생성당시의 시간을 기준으로 그 다음 1초가 지날때마다 갱신됩니다.
    private long frameCount = 0, nowFps = 0;
    // frameCount는 프레임마다 갱신되는 값입니다.
    // nowFps는 1초마다 갱신되는 값입니다.
    
    public void count(){
        Date nowTime = new Date();
        long diffTime = nowTime.getTime() - lastTime.getTime();
        // 기준시간 으로부터 몇 초가 지났는지 계산합니다.
 
        if (diffTime >= 1000) {
            // 기준 시간으로 부터 1초가 지났다면
            nowFps = frameCount;
            frameCount = 0;
            // nowFps를 갱신하고 카운팅을 0부터 다시합니다.
            lastTime = nowTime;
            // 1초가 지났으므로 기준 시간또한 갱신합니다.
        }
 
        frameCount++;
        // 기준 시간으로 부터 1초가 안지났다면 카운트만 1 올리고 넘깁니다.
    }
    
    public String getFps(){
        return nowFps + "";
    }
}    // end of class