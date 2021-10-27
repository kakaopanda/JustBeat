package justbeat;
/*
* ���� FPS���� üũ�ϴ� �ڵ�
* count()�� ȣ��ɶ����� ī���� �ϴٰ�
* 1�ʰ� ������ ����� ī���� �ߴ��� ����Ѵ�.
*/
import java.util.Date;
 
public class FpsCounter{
    private Date lastTime = new Date();
    // lastTime�� ���� �ð��Դϴ�.
    // ó�� ��������� �ð��� �������� �� ���� 1�ʰ� ���������� ���ŵ˴ϴ�.
    private long frameCount = 0, nowFps = 0;
    // frameCount�� �����Ӹ��� ���ŵǴ� ���Դϴ�.
    // nowFps�� 1�ʸ��� ���ŵǴ� ���Դϴ�.
    
    public void count(){
        Date nowTime = new Date();
        long diffTime = nowTime.getTime() - lastTime.getTime();
        // ���ؽð� ���κ��� �� �ʰ� �������� ����մϴ�.
 
        if (diffTime >= 1000) {
            // ���� �ð����� ���� 1�ʰ� �����ٸ�
            nowFps = frameCount;
            frameCount = 0;
            // nowFps�� �����ϰ� ī������ 0���� �ٽ��մϴ�.
            lastTime = nowTime;
            // 1�ʰ� �������Ƿ� ���� �ð����� �����մϴ�.
        }
 
        frameCount++;
        // ���� �ð����� ���� 1�ʰ� �������ٸ� ī��Ʈ�� 1 �ø��� �ѱ�ϴ�.
    }
    
    public String getFps(){
        return nowFps + "";
    }
}    // end of class