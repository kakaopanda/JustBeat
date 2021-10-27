package justbeat;

public class FpsDelayer{
    private long TARGET_FPS; // ��ǥ FPS
    private int MSPF;       // Mill Second Per Frame
    
    public FpsDelayer(long targetFps){
        TARGET_FPS = targetFps;
        MSPF = (int)Math.round((1/TARGET_FPS));
  		// ��ǥ�� FPS�� ���߱� ���� �� �����ӿ� �� �и��ʰ� �ʿ����� ����Ѵ�.
		// ������� 30 FPS�� ���Ѵٸ� 1000ms / 30Frame = 33 ms/frame �� �ȴ�.
    }
    
    public void delay(int operatingTime){
    // �̹� ������ ����ð��� ������ ��ǥ FPS���� ���߾� �������� ������Ų��.
        if(operatingTime > MSPF){
		// ��, ����ð��� �ʹ� ����ٸ� ��ü���� ������.
            return;
        }else{
            try{
                Thread.sleep( (int)(MSPF - operatingTime) );
            }catch(Exception e){}
        }    
    }    // end of delay
	public void delay(long operatingTime){
		delay((int)operatingTime);
	}
}    // end of class