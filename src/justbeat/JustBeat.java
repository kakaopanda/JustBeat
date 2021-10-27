package justbeat;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JustBeat extends JFrame {

	// DB Connection.
	DBConnection connection = new DBConnection();
	
	// Maximum 30 FPS Support.
	public final static long fps_max = Long.MAX_VALUE;
	public static int fps_count;
	public static long fps_time;
	public static String fps_calculate;
    FpsDelayer fpsDelayer = new FpsDelayer(fps_max);
    FpsCounter fpsCounter = new FpsCounter();
    
    // Timer Task (※ 한 번 cancel한 TimerTask는 다시 스케쥴 할 수 없다.(Timer, TimerTask에 대한 객체를 메소드를 통해 매번 재생성))
	static TimerTask introTask;
	static TimerTask startTask;
	static TimerTask loadTask;
	static TimerTask resultTask;
	static TimerTask modeTask;
	static TimerTask rhythmTask;
	
	// Graphics & menuBar
	private Graphics screenGraphic;
	private Image screenImage;
	private Image background = new ImageIcon(Main.class.getResource("../images/introBackground_test.gif")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	
	// Button Image
	private ImageIcon mainButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/mainButtonEntered.png"));
	private ImageIcon mainButtonBasicImage = new ImageIcon(Main.class.getResource("../images/mainButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon startButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/startButtonEntered.png"));
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.gif"));
	private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/quitButtonEntered.png"));
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/quitButtonBasic.gif"));
	private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButton.gif"));
	private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButton.gif"));
	private ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButton.gif"));
	private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButton.gif"));
	private ImageIcon raidleftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButton.gif"));
	private ImageIcon raidleftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButton.gif"));
	private ImageIcon raidrightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButton.gif"));
	private ImageIcon raidrightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButton.gif"));
	private ImageIcon easyButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/easyButtonEntered.png"));
	private ImageIcon easyButtonBasicImage = new ImageIcon(Main.class.getResource("../images/easyButtonBasic.gif"));
	private ImageIcon hardButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/hardButtonEntered.png"));
	private ImageIcon hardButtonBasicImage = new ImageIcon(Main.class.getResource("../images/hardButtonBasic.gif"));
	private ImageIcon easyRhythmButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/easyRhythmButtonEntered.png"));
	private ImageIcon easyRhythmButtonBasicImage = new ImageIcon(Main.class.getResource("../images/easyRhythmButtonBasic.gif"));
	private ImageIcon hardRhythmButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/hardRhythmButtonEntered.png"));
	private ImageIcon hardRhythmButtonBasicImage = new ImageIcon(Main.class.getResource("../images/hardRhythmButtonBasic.gif"));
	private ImageIcon resultButtonBasicImage = new ImageIcon(Main.class.getResource("../images/resultButtonBasic.png"));
	private ImageIcon resultButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/resultButtonEntered.png"));
	private ImageIcon rulesButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rulesButtonBasic.gif"));
	private ImageIcon rulesButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rulesButtonEntered.png"));
	private ImageIcon rankingButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rankingButtonBasic.png"));
	private ImageIcon rankingButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rankingButtonEntered.png"));
	private ImageIcon optionButtonBasicImage = new ImageIcon(Main.class.getResource("../images/optionButtonBasic.png"));
	private ImageIcon optionButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/optionButtonEntered.png"));
	private ImageIcon playmodeButtonBasicImage = new ImageIcon(Main.class.getResource("../images/playmodeButtonBasicImage.png"));
	private ImageIcon playmodeButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/playmodeButtonEnteredImage.png"));
	private ImageIcon rhythmmodeButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rhythmmodeButtonBasicImage.png"));
	private ImageIcon rhythmmodeButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rhythmmodeButtonEnteredImage.png"));
	private ImageIcon pauseButtonBasicImage = new ImageIcon(Main.class.getResource("../images/pauseButtonBasicImage.gif"));
	
	// Button Object
	private JButton mainButton = new JButton(mainButtonBasicImage);
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton rhythmleftButton = new JButton(raidleftButtonBasicImage);
	private JButton rhythmrightButton = new JButton(raidrightButtonBasicImage);
	private JButton easyButton = new JButton(easyButtonBasicImage);
	private JButton hardButton = new JButton(hardButtonBasicImage);
	private JButton easyRhythmButton = new JButton(easyRhythmButtonBasicImage);
	private JButton hardRhythmButton = new JButton(hardRhythmButtonBasicImage);
	private JButton resultButton = new JButton(resultButtonBasicImage);
	private JButton rulesButton = new JButton(rulesButtonBasicImage);
	private JButton rankingButton = new JButton(rankingButtonBasicImage);
	private JButton optionButton = new JButton(optionButtonBasicImage);
	private JButton playmodeButton = new JButton(playmodeButtonBasicImage);
	private JButton rhythmmodeButton = new JButton(rhythmmodeButtonBasicImage);
	private JButton pauseButton = new JButton(pauseButtonBasicImage);
	
	// Mouse coordinates
	private int mouseX, mouseY;

	//화면 전환 변수
	private boolean isInitialScreen = false;
	private boolean isMainScreen = false;
	private boolean isGameScreen = false;
	private boolean isResultScreen = false;
	private boolean isRulesScreen = false;
	private boolean isRankingScreen = false;
	private boolean isModeSelectScreen = false;
	private boolean isRhythmScreen = false;
	private boolean isRhythmGameScreen = false;
	
	// Lyrics Mode Track
	ArrayList<Track> trackList = new ArrayList<Track>();
	
	// Rhythm Mode Track
	ArrayList<Track> modeList = new ArrayList<Track>();

	// Lyrics Mode Image
	private Image titleImage;
	private Image selectedImage;

	// Rhythm Mode Image
	private Image titleRhythmImage;
	private Image selectedRhythmImage;
	
	// Common Image
	private Image resultImage;
	private Image rulesImage;
	private Image rankingImage;
	
	// Music Variable declaration
	private Music selectedMusic;
	private Music RhythmMusic;
	private Music introLoadingMusic = new Music("IntroLoading.mp3",false);
	private Music introMusic = new Music("introBackground.mp3", true);
	private Music resultMusic = new Music("resultMusic.mp3", true);
	private Music digitalMusic = new Music("digital.mp3",false);
	
	// Select Variable
	private int nowSelected = 0;
	private int rhythmSelected = 0;
	
	// Game Variable
	public static Game game;
	
	// Score Variable & User Data
	public static Score score = new Score(); // 점수 기록 객체 생성 시점을 프로그램 시작시로 설정.
	public static DateTime time = new DateTime(); // 점수 기록 객체에 전달할 랭킹 기록 시각 정보.
	public static String name; // 점수 기록 객체에 전달할 랭킹 이름 정보.

	
	public JustBeat() {
		//Lyrics Mode
		trackList.add(new Track("first_title.png","firstBackground_edit.gif","firstGameBackground.gif","DreamMachine-Lifestream-select.mp3","DreamMachine-Lifestream-full.mp3","DreamMachine-Lifestream"));
		trackList.add(new Track("second_title.png","secondBackground.gif","secondGameBackground.gif","DreamMachine-TheFirstSnow.mp3","DreamMachine-TheFirstSnow.mp3","DreamMachine-TheFirstSnow"));
		trackList.add(new Track("third_title.png","thirdBackground.gif","thirdGameBackground.gif","DreamMachine-Insight.mp3","DreamMachine-Insight.mp3","DreamMachine-Insight"));
		
		//Rhythm Mode
		modeList.add(new Track("first_mode_title.png","firstmodeBackground.gif","firstmodeGameBackground.gif","SpaceInvader-Activation-select.mp3","SpaceInvader-Activation.mp3","First"));
		modeList.add(new Track("second_mode_title.png","secondmodeBackground.gif","secondmodeGameBackground.gif","SpaceInvader-LostControl-select.mp3","SpaceInvader-LostControl.mp3","Second"));
		modeList.add(new Track("third_mode_title.png","thirdmodeBackground.gif","thirdmodeGameBackground.gif","MarcoLazovic-EarlyMorning-select.mp3","MarcoLazovic-EarlyMorning.mp3","Third"));		
		
		//Graphic Setting Option
		setUndecorated(true);
		setTitle("Just Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		addKeyListener(new KeyListener());
		
		// Fps Count
	    FpsDelayer fpsDelayer = new FpsDelayer(60);
	    FpsCounter fpsCounter = new FpsCounter();
		Count fps = new Count("Count");		
		fpsDelayer.delay(fps.diffTime);
		fpsCounter.count();
		fps.update();
		fps.gameLoop();
		
		
		// Button Visible Setting
		mainButton.setVisible(false);
		startButton.setVisible(false);
		quitButton.setVisible(false);
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		rhythmleftButton.setVisible(false);
		rhythmrightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		easyRhythmButton.setVisible(false);
		hardRhythmButton.setVisible(false);
		resultButton.setVisible(false);
		rulesButton.setVisible(false);
		rankingButton.setVisible(false);
		optionButton.setVisible(false);
		playmodeButton.setVisible(false);
		rhythmmodeButton.setVisible(false);
		pauseButton.setVisible(false);
		
		// Intro Music & Intro Background
		introLoadingMusic.start();
		background = new ImageIcon(Main.class.getResource("../images/IntroLoading_edit.gif")).getImage();
		
		// Intro Loading
		introTask = introLoading();
		final Timer timer = new Timer();
		timer.schedule(introTask, 10000);
		
		// exitButton Information
		exitButton.setBounds(1245, 15, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				try {
					Thread.sleep(1000);
				}catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(exitButton);
		
		// startButton Information
		startButton.setBounds(500, 342, 232, 78);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				background = new ImageIcon(Main.class.getResource("../images/loadingBackground_edit.gif")).getImage();
				isInitialScreen = true;
				isMainScreen = false;
				isGameScreen = false;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
				
				modeTask = modeTask();
				final Timer timer = new Timer();
				timer.schedule(modeTask, 5000);
			}
		});
		add(startButton);
		
		// quitButton Information
		quitButton.setBounds(500, 542, 232, 78);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				try {
					Thread.sleep(1000);
				}catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(quitButton);
		
		// mainButton Information
		mainButton.setBounds(10, 0, 70, 50);
		mainButton.setBorderPainted(false);
		mainButton.setContentAreaFilled(false);
		mainButton.setFocusPainted(false);
		mainButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mainButton.setIcon(mainButtonEnteredImage);
				mainButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mainButton.setIcon(mainButtonBasicImage);
				mainButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				nowSelected = 0; // main으로 전환되면서 선택된 노래에 대한 정보 초기화.(첫번째 음악으로 변경)
				rhythmSelected = 0;
				
				//Music End.
				introMusic.close();
				if(isMainScreen)
					selectedMusic.close();
				if(isResultScreen)
					resultMusic.close();
				if(isGameScreen)
					Game.gameMusic.close();
				if(isRhythmScreen)
					RhythmMusic.close();
				
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				background = new ImageIcon(Main.class.getResource("../images/loadingBackground2_edit.gif")).getImage();
				isInitialScreen = true;
				isMainScreen = false;
				isGameScreen = false;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
				
				loadTask = loadTask();
				final Timer timer = new Timer();
				timer.schedule(loadTask, 5000);
			}
		});
		add(mainButton);
		
		// leftButton Information
		leftButton.setBounds(20, 635, 80, 80);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				selectLeft();
			}
		});
		add(leftButton);
		
		// rightButton Information
		rightButton.setBounds(1180, 635, 80, 80);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				selectRight();
			}
		});
		add(rightButton);
		
		// rhythmleftButton Information
		rhythmleftButton.setBounds(20, 635, 80, 80);
		rhythmleftButton.setBorderPainted(false);
		rhythmleftButton.setContentAreaFilled(false);
		rhythmleftButton.setFocusPainted(false);
		rhythmleftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rhythmleftButton.setIcon(raidleftButtonEnteredImage);
				rhythmleftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rhythmleftButton.setIcon(raidleftButtonBasicImage);
				rhythmleftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				selectLeftRhythm();
			}
		});
		add(rhythmleftButton);
				
		// rhythmrightButton Information
		rhythmrightButton.setBounds(1180, 635, 80, 80);
		rhythmrightButton.setBorderPainted(false);
		rhythmrightButton.setContentAreaFilled(false);
		rhythmrightButton.setFocusPainted(false);
		rhythmrightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rhythmrightButton.setIcon(raidrightButtonEnteredImage);
				rhythmrightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rhythmrightButton.setIcon(rightButtonBasicImage);
				rhythmrightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				selectRightRhythm();
			}
		});
		add(rhythmrightButton);
		
		// easyButton Information
		easyButton.setBounds(293, 640, 270, 90);
		easyButton.setBorderPainted(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setFocusPainted(false);
		easyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyButton.setIcon(easyButtonEnteredImage);
				easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				easyButton.setIcon(easyButtonBasicImage);
				easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Main.MODE_SELECT = 0;
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Easy Mode Play.
				gameStart(nowSelected, "Easy");
				
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = true;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
			}
		});
		add(easyButton);
		
		// hardButton Information
		hardButton.setBounds(724, 640, 270, 90);
		hardButton.setBorderPainted(false);
		hardButton.setContentAreaFilled(false);
		hardButton.setFocusPainted(false);
		hardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hardButton.setIcon(hardButtonEnteredImage);
				hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hardButton.setIcon(hardButtonBasicImage);
				hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Main.MODE_SELECT = 0;
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Hard Mode Play.
				gameStart(nowSelected, "Hard");
				
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = true;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
			}
		});
		add(hardButton);
		
		// easyRhythmButton Information
		easyRhythmButton.setBounds(293, 640, 270, 90);
		easyRhythmButton.setBorderPainted(false);
		easyRhythmButton.setContentAreaFilled(false);
		easyRhythmButton.setFocusPainted(false);
		easyRhythmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyRhythmButton.setIcon(easyRhythmButtonEnteredImage);
				easyRhythmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				easyRhythmButton.setIcon(easyRhythmButtonBasicImage);
				easyRhythmButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Main.MODE_SELECT = 1;
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Easy Mode Play.
				rhythmgameStart(rhythmSelected, "Easy");
				
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);					
				rhythmrightButton.setVisible(false);						
				
				//Screen Setting.
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = true;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;					
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
			}
		});
		add(easyRhythmButton);
				
		// hardRhythmButton Information
		hardRhythmButton.setBounds(724, 640, 270, 90);
		hardRhythmButton.setBorderPainted(false);
		hardRhythmButton.setContentAreaFilled(false);
		hardRhythmButton.setFocusPainted(false);
		hardRhythmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hardRhythmButton.setIcon(hardRhythmButtonEnteredImage);
				hardRhythmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hardRhythmButton.setIcon(hardRhythmButtonBasicImage);
				hardRhythmButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Main.MODE_SELECT = 1;
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
						
				//Hard Mode Play.
				rhythmgameStart(rhythmSelected, "Hard");
						
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
						
				//Screen Setting.
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = true;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
			}
		});
		add(hardRhythmButton);
		
		// resultButton Information
		resultButton.setBounds(2, 50, 232, 100);
		resultButton.setBorderPainted(false);
		resultButton.setContentAreaFilled(false);
		resultButton.setFocusPainted(false);
		resultButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				resultButton.setIcon(resultButtonEnteredImage);
				resultButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				resultButton.setIcon(resultButtonBasicImage);
				resultButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Music All close.
				introMusic.close();
				selectedMusic.close();
				resultMusic.close();
				
				//New Music Start.
				resultMusic = new Music("resultMusic.mp3", true);
				resultMusic.start();
				
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				background = new ImageIcon(Main.class.getResource("../images/resultBackground.png")).getImage();
				isInitialScreen = false;
				isGameScreen = false;
				isMainScreen = false;
				isResultScreen = true;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
				
				game.close();
				setFocusable(true);
				game.operation_print();
				name = JOptionPane.showInputDialog("랭킹에 기록될 이름을 입력해주세요.");
				scoreRecord();
				scoreData();
			}
		});
		add(resultButton);
		
		// rulesButton Information
		rulesButton.setBounds(500, 442, 232, 78);
		rulesButton.setBorderPainted(false);
		rulesButton.setContentAreaFilled(false);
		rulesButton.setFocusPainted(false);
		rulesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rulesButton.setIcon(rulesButtonEnteredImage);
				rulesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rulesButton.setIcon(rulesButtonBasicImage);
				rulesButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				background = new ImageIcon(Main.class.getResource("../images/rulesBackground.gif")).getImage();
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = false;
				isResultScreen = false;
				isRulesScreen = true;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
			}
		});
		add(rulesButton);
		
		
		// rankingButton Information
		rankingButton.setBounds(290, 80, 800, 180);
		rankingButton.setBorderPainted(false);
		rankingButton.setContentAreaFilled(false);
		rankingButton.setFocusPainted(false);
		rankingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rankingButton.setIcon(rankingButtonEnteredImage);
				rankingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rankingButton.setIcon(rankingButtonBasicImage);
				rankingButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				background = new ImageIcon(Main.class.getResource("../images/rankingBackground.gif")).getImage();
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = false;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = true;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
			}
		});
		add(rankingButton);
		
		// playmodeButton Information
		playmodeButton.setBounds(230, 343, 200, 200);
		playmodeButton.setBorderPainted(false);
		playmodeButton.setContentAreaFilled(false);
		playmodeButton.setFocusPainted(false);
		playmodeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playmodeButton.setIcon(playmodeButtonEnteredImage);
				playmodeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				playmodeButton.setIcon(playmodeButtonBasicImage);
				playmodeButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = false;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
				
				background = new ImageIcon(Main.class.getResource("../images/fastLoadingBackground.gif")).getImage();
				startTask = startTask();
				final Timer timer = new Timer();
				timer.schedule(startTask, 3000);
			}
		});
		add(playmodeButton);
		
		// rhythmmodeButton Information
		rhythmmodeButton.setBounds(858, 330, 200, 200);
		rhythmmodeButton.setBorderPainted(false);
		rhythmmodeButton.setContentAreaFilled(false);
		rhythmmodeButton.setFocusPainted(false);
		rhythmmodeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rhythmmodeButton.setIcon(rhythmmodeButtonEnteredImage);
				rhythmmodeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rhythmmodeButton.setIcon(rhythmmodeButtonBasicImage);
				rhythmmodeButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Button Setting.
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				//Screen Setting.
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = false;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
				
				background = new ImageIcon(Main.class.getResource("../images/fastLoadingBackground.gif")).getImage();
				rhythmTask = rhythmTask();
				final Timer timer = new Timer();
				timer.schedule(rhythmTask, 3000);
			}
		});
		add(rhythmmodeButton);
		
		// pauseButton Information
		pauseButton.setBounds(865, 325, 200, 200);
		pauseButton.setBorderPainted(false);
		pauseButton.setContentAreaFilled(false);
		pauseButton.setFocusPainted(false);
		pauseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				pauseButton.setIcon(pauseButtonBasicImage);
				pauseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				pauseButton.setIcon(pauseButtonBasicImage);
				pauseButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				
				//Button Setting.
				/*
				startButton.setVisible(false);
				quitButton.setVisible(false);
				exitButton.setVisible(true);
				mainButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(false);
				rankingButton.setVisible(false);
				optionButton.setVisible(false);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				*/
				//Screen Setting.
				/*
				isInitialScreen = false;
				isMainScreen = false;
				isGameScreen = false;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
				*/
				try {
					pause();
					System.out.println(game.getState());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(pauseButton);
		
		// menuBar Information
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) { // 드래그를 통한 창 이동 활성화.
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);
	}

	// painting option
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D)screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	// Screen Setting
	public void screenDraw(Graphics2D g) {
		
		g.drawImage(background, 0, 0, null);
		
		if(isInitialScreen)
		{
			g.drawImage(background, 0, 0, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("",Font.PLAIN,13));
			g.drawString("Copyrightⓒ  Team Color All Rights Reserved.", 490, 50);
		}
		if(isModeSelectScreen)
		{
			g.drawImage(background, 0, 0, null);
		}
		if(isMainScreen)
		{
			g.drawImage(selectedImage, 0, 0, null);
			g.drawImage(titleImage, 400, 40, null);
		}
		if(isGameScreen)
		{
			game.screenDraw(g);
		}
		if(isResultScreen)
		{
			g.drawImage(resultImage, 0, 0, null);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Liberty BT",Font.BOLD,100));
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawString(Integer.toString(Game.score), 850, 215);
			g.drawString(Integer.toString((int)Game.accuracy), 750, 627);
			g.setFont(new Font("Liberty BT",Font.BOLD,80));
			g.drawString(Integer.toString(Game.total_beat), 375, 295);	
			g.setFont(new Font("Liberty BT",Font.BOLD,60));
			g.drawString(Integer.toString(Game.perfect), 300, 378);
			g.drawString(Integer.toString(Game.great), 585, 378);
			g.drawString(Integer.toString(Game.good), 895, 378);
			g.drawString(Integer.toString(Game.early), 270, 450);
			g.drawString(Integer.toString(Game.late), 570, 450);
			g.drawString(Integer.toString(Game.miss), 885, 450);
			g.drawString(Integer.toString(Game.max_combo), 360, 530);
			g.drawString(Integer.toString(Game.acc_combo), 665, 530);
		}
		if(isRulesScreen)
		{
			g.drawImage(rulesImage, 0, 0, null);
		}
		if(isRankingScreen)
		{
			g.drawImage(rankingImage, 0, 0, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("",Font.PLAIN,70));
			g.drawString(DBConnection.ranking[0][0], 305, 290);
			g.drawString(DBConnection.ranking[0][1], 800, 290);
				
			g.setColor(Color.WHITE);
			g.setFont(new Font("",Font.PLAIN,60));
			g.drawString(DBConnection.ranking[1][0], 335, 450);
			g.drawString(DBConnection.ranking[1][1], 830, 450);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("",Font.PLAIN,50));
			g.drawString(DBConnection.ranking[2][0], 365, 610);
			g.drawString(DBConnection.ranking[2][1], 860, 610);
		}
		if(isRhythmScreen)
		{
			g.drawImage(selectedRhythmImage, 0, 0, null);
			g.drawImage(titleRhythmImage, 400, 40, null);	
		}
		
		
		// Fps Count
		Count fps = new Count("Count");
		fpsDelayer.delay(fps.diffTime);
		fpsCounter.count();
		fps.update();
		fps.gameLoop();
		fps_count = fps.count;
		fps_time = fps.diffTime;
		fps_calculate = fps.fpsCounter.getFps();
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("",Font.PLAIN,15));
		g.drawString(fps_count + " TF", 30, 60);
		g.drawString(fps_time + " MS", 30, 80);
		g.drawString(fpsCounter.getFps() + " FPS", 1200, 60);
		
		
		// Graphic Update Duration
		paintComponents(g);
		try {
			Thread.sleep(1);
		}catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();
		
	}
	
	
	// Lyrics Mode
	public void selectTrack(int nowSelected){
		if(selectedMusic != null)
			selectedMusic.close();
		titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage())).getImage();
		selectedImage= new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
		selectedMusic.start();
	}
	
	// Rhythm Mode
	public void selectRhythm(int rhythmSelected) {
		if(RhythmMusic != null)
			RhythmMusic.close();
		titleRhythmImage = new ImageIcon(Main.class.getResource("../images/" + modeList.get(rhythmSelected).getTitleImage())).getImage();
		selectedRhythmImage = new ImageIcon(Main.class.getResource("../images/" + modeList.get(rhythmSelected).getStartImage())).getImage();
		RhythmMusic = new Music(modeList.get(rhythmSelected).getStartMusic(), true);
		RhythmMusic.start();
	}
	
	// Lyrics Mode Selection
	public void selectLeft() {
		if(nowSelected == 0)
			nowSelected = trackList.size() - 1;
		else
			nowSelected--;
		selectTrack(nowSelected);
	}
	public void selectRight() {
		if(nowSelected == trackList.size() -1)
			nowSelected = 0;
		else
			nowSelected++;
		selectTrack(nowSelected);
	}
	
	// Rhythm Mode Selection
	public void selectLeftRhythm() {
		if(rhythmSelected == 0)
			rhythmSelected = modeList.size() - 1;
		else
			rhythmSelected--;
		selectRhythm(rhythmSelected);
	}
	public void selectRightRhythm() {
		if(rhythmSelected == modeList.size() -1)
			rhythmSelected = 0;
		else
			rhythmSelected++;
		selectRhythm(rhythmSelected);
	}
	
	// Lyrics Mode Game Object
	public void gameStart(int nowSelected, String difficulty){
		setFocusable(true); // 포커스(Focus)를 받을 수 있도록 설정.
		requestFocus(); // 게임 시작 시 정상적인 키 이벤트를 위한 프로그램의 포커스(Focus) 요청.
		if(selectedMusic != null)
			selectedMusic.close();
		
		//Button Setting.
		startButton.setVisible(false);
		quitButton.setVisible(false);
		exitButton.setVisible(true);
		mainButton.setVisible(true);
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		easyRhythmButton.setVisible(false);
		hardRhythmButton.setVisible(false);
		resultButton.setVisible(false);
		rulesButton.setVisible(false);
		rankingButton.setVisible(false);
		optionButton.setVisible(false);
		playmodeButton.setVisible(false);
		rhythmmodeButton.setVisible(false);
		
		//Screen Setting.
		background = new ImageIcon(Main.class.getResource("../images/"+trackList.get(nowSelected).getGameImage())).getImage();
		isInitialScreen = false;
		isMainScreen = false;
		isGameScreen = true;
		isResultScreen = false;
		isRulesScreen = false;
		isRankingScreen = false;
		isModeSelectScreen = false;
		isRhythmScreen = false;
		isRhythmGameScreen = false;
		
		//Game Start.
		game = new Game(trackList.get(nowSelected).getTitleName(),difficulty,trackList.get(nowSelected).getGameMusic());
		setFocusable(true);
		game.start();
		
		if(nowSelected==0)
		{
			resultTask = resultTask();
			final Timer timer = new Timer();
			timer.schedule(resultTask, 194000);
		}
		else if(nowSelected==1)
		{
			resultTask = resultTask();
			final Timer timer = new Timer();
			timer.schedule(resultTask, 159000);	
		}
		else if(nowSelected==2)
		{
			resultTask = resultTask();
			final Timer timer = new Timer();
			timer.schedule(resultTask, 213000);	
		}
	}
	
	// Rhythm Mode Game Object
	public void rhythmgameStart(int rhythmSelected, String difficulty){
		setFocusable(true); // 포커스(Focus)를 받을 수 있도록 설정.
		requestFocus(); // 게임 시작 시 정상적인 키 이벤트를 위한 프로그램의 포커스(Focus) 요청.
		if(RhythmMusic != null)
			RhythmMusic.close();
		
		//Button Setting.
		startButton.setVisible(false);
		quitButton.setVisible(false);
		exitButton.setVisible(true);
		mainButton.setVisible(true);
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		easyRhythmButton.setVisible(false);
		hardRhythmButton.setVisible(false);
		resultButton.setVisible(false);
		rulesButton.setVisible(false);
		rankingButton.setVisible(false);
		optionButton.setVisible(false);
		playmodeButton.setVisible(false);
		rhythmmodeButton.setVisible(false);
		
		//Screen Setting.
		background = new ImageIcon(Main.class.getResource("../images/"+modeList.get(rhythmSelected).getGameImage())).getImage();
		isInitialScreen = false;
		isMainScreen = false;
		isGameScreen = true;
		isResultScreen = false;
		isRulesScreen = false;
		isRankingScreen = false;
		isModeSelectScreen = false;
		isRhythmScreen = false;
		isRhythmGameScreen = false;
		
		//Game Start.
		game = new Game(modeList.get(rhythmSelected).getTitleName(),difficulty,modeList.get(rhythmSelected).getGameMusic());
		setFocusable(true);
		game.start();
		
		if(rhythmSelected==0)
		{
			resultTask = resultTask();
			final Timer timer = new Timer();
			timer.schedule(resultTask, 194000);
		}
		else if(rhythmSelected==1)
		{
			resultTask = resultTask();
			final Timer timer = new Timer();
			timer.schedule(resultTask, 159000);	
		}
		else if(rhythmSelected==2)
		{
			resultTask = resultTask();
			final Timer timer = new Timer();
			timer.schedule(resultTask, 213000);	
		}
	}
	
	// Score Recording Information
	public void scoreRecord() {
		score.record("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■"+"\n");
		try {
			score.record(time.date().toString()+"\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		score.record("Player : "+name+"\n");
		score.record("Score : "+Integer.toString(Game.score)+"\n");
		score.record("Acc_Combo : "+Integer.toString(Game.acc_combo)+"\n");
		score.record("Max_Combo : "+Integer.toString(Game.max_combo)+"\n");
		score.record("Total_Beat : "+Integer.toString(Game.total_beat)+"\n");
		score.record("Accuracy : "+Double.toString(Game.accuracy)+"\n");
		score.record("Perfect : "+Integer.toString(Game.perfect)+"\n");
		score.record("Great : "+Integer.toString(Game.great)+"\n");
		score.record("Good : "+Integer.toString(Game.good)+"\n");
		score.record("Early : "+Integer.toString(Game.early)+"\n");
		score.record("Late : "+Integer.toString(Game.late)+"\n");
		score.record("Miss : "+Integer.toString(Game.miss)+"\n");
		score.record("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■"+"\n");
		
		try {
			connection.st = connection.con.createStatement();
			connection.st.executeUpdate("insert into rank values ('"+name+"', '"+Game.score+"','"+Game.acc_combo+"','"
			+Game.max_combo+"','"+Game.total_beat+"','"+Game.accuracy+"','"+Game.perfect+"','"+Game.great+"','"+Game.good+"','"+
					Game.early+"','"+Game.late+"','"+Game.miss+"');");
			connection.rs = connection.st.executeQuery("select * from rank order by score*-1;");
			
			int count = 0;
			while(connection.rs.next()) // 1위 ~ 3위의 데이터만 추출.
			{
				if(count<3)
					{
						connection.st = connection.con.createStatement();
						String name = connection.rs.getString("Name");
						String score = connection.rs.getString("Score");
						String acc_combo = connection.rs.getString("Acc_Combo");
						String max_combo = connection.rs.getString("Max_Combo");
						String total_beat = connection.rs.getString("Total_Beat");
						String accuracy = connection.rs.getString("Accuracy");
						String perfect = connection.rs.getString("Perfect");
						String great = connection.rs.getString("Great");
						String good = connection.rs.getString("Good");
						String early = connection.rs.getString("Early");
						String late = connection.rs.getString("Late");
						String miss = connection.rs.getString("Miss");
						
						connection.ranking[count][0] = name;
						connection.ranking[count][1] = score;
						connection.ranking[count][2] = acc_combo;
						connection.ranking[count][3] =	max_combo;
						connection.ranking[count][4] = total_beat;
						connection.ranking[count][5] = accuracy;
						connection.ranking[count][6] = perfect;
						connection.ranking[count][7] = great;
						connection.ranking[count][8] = good;
						connection.ranking[count][9] = early;
						connection.ranking[count][10] = late;
						connection.ranking[count][11] = miss;
						
						System.out.printf("%d위 : %s 점수 : %s \t 총 콤보 : %s \t 최고 콤보 : %s \t 정확도 : %s \n",count+1,
								connection.ranking[count][0],connection.ranking[count][1],connection.ranking[count][2],
								connection.ranking[count][3],connection.ranking[count][5]);
						connection.st.close();
					}
				count++;
			}
			System.out.println("Ranking Update Sucessful.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void scoreData() {
		score.data(name+" ");
		score.data(Integer.toString(Game.score)+" ");
		score.data(Double.toString(Game.accuracy)+" "+"\n");
	}

	public TimerTask introLoading() {
		TimerTask intro_task = new TimerTask() {
			@Override
			public void run() {
				// 기본 배경 음악 시작.
				introMusic.start();
				
				// 화면 전환 변수 기본 초기값.
				isInitialScreen = true;
				isMainScreen = false;
				isGameScreen = false;
				isResultScreen = false;
				isRulesScreen = false;
				isRankingScreen = false;
				isModeSelectScreen = false;
				isRhythmScreen = false;
				isRhythmGameScreen = false;
				
				// 버튼에 대한 기본 초기값.
				mainButton.setVisible(true);
				startButton.setVisible(true);
				quitButton.setVisible(true);
				leftButton.setVisible(false);
				rightButton.setVisible(false);
				easyButton.setVisible(false);
				hardButton.setVisible(false);
				easyRhythmButton.setVisible(false);
				hardRhythmButton.setVisible(false);
				resultButton.setVisible(false);
				rulesButton.setVisible(true);
				rankingButton.setVisible(true);
				optionButton.setVisible(true);
				playmodeButton.setVisible(false);
				rhythmmodeButton.setVisible(false);
				rhythmleftButton.setVisible(false);
				rhythmrightButton.setVisible(false);
				
				background = new ImageIcon(Main.class.getResource("../images/introBackground_test.gif")).getImage();
			}
		};
		return intro_task;
	}
	
	// < Task >
	// Intro Loading Task
	 public TimerTask loadTask() {
		TimerTask load_task = new TimerTask() {
		
		@Override
		public void run() {
			
			//New Music Start.
			introMusic = new Music("introBackground.mp3", true);
			introMusic.start();
			
			//Button Setting.
			startButton.setVisible(true);
			quitButton.setVisible(true);
			exitButton.setVisible(true);
			mainButton.setVisible(true);
			rulesButton.setVisible(true);
			leftButton.setVisible(false);
			rightButton.setVisible(false);
			easyButton.setVisible(false);
			hardButton.setVisible(false);
			easyRhythmButton.setVisible(false);
			hardRhythmButton.setVisible(false);
			resultButton.setVisible(false);
			rankingButton.setVisible(true);
			optionButton.setVisible(true);
			playmodeButton.setVisible(false);
			rhythmmodeButton.setVisible(false);
			rhythmleftButton.setVisible(false);
			rhythmrightButton.setVisible(false);
			
			//Game exit.
			if(isGameScreen)
			{
				game.close();
			}
			//Screen Setting.
			background = new ImageIcon(Main.class.getResource("../images/introBackground_test.gif")).getImage();
			isInitialScreen = true;
			isMainScreen = false;
			isGameScreen = false;
			isResultScreen = false;
			isRulesScreen = false;
			isRankingScreen = false;
			isModeSelectScreen = false;
			isRhythmScreen = false;
			isRhythmGameScreen = false;
		}
	};			
		return load_task;
	}
	 
	// Mode Select Screen Loading Task
	 public TimerTask modeTask() {
		 TimerTask mode_task = new TimerTask() 
		 {
			 @Override
			 public void run() {
					//Button Setting.
					startButton.setVisible(false);
					quitButton.setVisible(false);
					exitButton.setVisible(true);
					mainButton.setVisible(true);
					leftButton.setVisible(false);
					rightButton.setVisible(false);
					easyButton.setVisible(false);
					hardButton.setVisible(false);
					easyRhythmButton.setVisible(false);
					hardRhythmButton.setVisible(false);
					resultButton.setVisible(false);
					rulesButton.setVisible(false);
					rankingButton.setVisible(false);
					optionButton.setVisible(false);
					playmodeButton.setVisible(true);
					rhythmmodeButton.setVisible(true);
					rhythmleftButton.setVisible(false);
					rhythmrightButton.setVisible(false);
					
					//Screen Setting.
					background = new ImageIcon(Main.class.getResource("../images/modeSelectBackground.gif")).getImage();
					isInitialScreen = false;
					isMainScreen = false;
					isGameScreen = false;
					isResultScreen = false;
					isRulesScreen = false;
					isRankingScreen = false;
					isModeSelectScreen = true;
					isRhythmScreen = false;
					isRhythmGameScreen = false;
			 }
		 };
		 return mode_task;
	 }
	 
	 // Lyrics Mode Loading Task
	 public TimerTask startTask() {
		 TimerTask start_task = new TimerTask() 
		 {
			 @Override
			 public void run() {
				//Music Setting.
					introMusic.close();
					selectTrack(0);
					
					//Button Setting.
					startButton.setVisible(false);
					quitButton.setVisible(false);
					exitButton.setVisible(true);
					mainButton.setVisible(true);
					leftButton.setVisible(true);
					rightButton.setVisible(true);
					easyButton.setVisible(true);
					hardButton.setVisible(true);
					easyRhythmButton.setVisible(false);
					hardRhythmButton.setVisible(false);
					resultButton.setVisible(false);
					rulesButton.setVisible(false);
					rankingButton.setVisible(false);
					optionButton.setVisible(false);
					playmodeButton.setVisible(false);
					rhythmmodeButton.setVisible(false);
					rhythmleftButton.setVisible(false);
					rhythmrightButton.setVisible(false);
					
					//Screen Setting.
					background = new ImageIcon(Main.class.getResource("../images/firstBackground.gif")).getImage();
					isInitialScreen = false;
					isMainScreen = true;
					isGameScreen = false;
					isResultScreen = false;
					isRulesScreen = false;
					isRankingScreen = false;
					isModeSelectScreen = false;
					isRhythmScreen = false;
					isRhythmGameScreen = false;
			 }
		 };
		 return start_task;
	 }
	 
	// Rhythm Mode Loading Task
	 public TimerTask rhythmTask() {
		 TimerTask rhythm_task = new TimerTask() 
		 {
			 @Override
			 public void run() {
				//Music Setting.
					introMusic.close();
					selectRhythm(0);
					
					//Button Setting.
					startButton.setVisible(false);
					quitButton.setVisible(false);
					exitButton.setVisible(true);
					mainButton.setVisible(true);
					leftButton.setVisible(false);
					rightButton.setVisible(false);
					easyButton.setVisible(false);
					hardButton.setVisible(false);
					easyRhythmButton.setVisible(true);
					hardRhythmButton.setVisible(true);
					resultButton.setVisible(false);
					rulesButton.setVisible(false);
					rankingButton.setVisible(false);
					optionButton.setVisible(false);
					playmodeButton.setVisible(false);
					rhythmmodeButton.setVisible(false);
					rhythmleftButton.setVisible(true);
					rhythmrightButton.setVisible(true);
					
					//Screen Setting.
					isInitialScreen = false;
					isMainScreen = false;
					isGameScreen = false;
					isResultScreen = false;
					isRulesScreen = false;
					isRankingScreen = false;
					isModeSelectScreen = false;
					isRhythmScreen = true;
					isRhythmGameScreen = false;
			 }
		 };
		 return rhythm_task;
	 }
	 
	 // Result Screen Loading Task
	 public TimerTask resultTask() {
		 TimerTask result_task = new TimerTask()
		{
			 @Override
			 public void run() {
				//Music All close.
					introMusic.close();
					selectedMusic.close();
					resultMusic.close();
					
					//New Music Start.
					resultMusic = new Music("resultMusic.mp3", true);
					resultMusic.start();
					
					//Button Setting.
					startButton.setVisible(false);
					quitButton.setVisible(false);
					exitButton.setVisible(true);
					mainButton.setVisible(true);
					leftButton.setVisible(false);
					rightButton.setVisible(false);
					easyButton.setVisible(false);
					hardButton.setVisible(false);
					easyRhythmButton.setVisible(false);
					hardRhythmButton.setVisible(false);
					resultButton.setVisible(false);
					rulesButton.setVisible(false);
					rankingButton.setVisible(false);
					optionButton.setVisible(false);
					playmodeButton.setVisible(false);
					rhythmmodeButton.setVisible(false);
					rhythmleftButton.setVisible(false);
					rhythmrightButton.setVisible(false);
					
					//Screen Setting.
					background = new ImageIcon(Main.class.getResource("../images/resultBackground.gif")).getImage();
					isInitialScreen = false;
					isGameScreen = false;
					isMainScreen = false;
					isResultScreen = true;
					isRulesScreen = false;
					isRankingScreen = false;
					isModeSelectScreen = false;
					isRhythmScreen = false;
					isRhythmGameScreen = false;
					
					game.close();
					setFocusable(true);
					game.operation_print();
					name = JOptionPane.showInputDialog("랭킹에 기록될 이름을 입력해주세요.");
					scoreRecord();
					scoreData();
			 }
		};
		return result_task;
	 }
	 
	 // Pause
	 public static void pause() {
		    try {
		      System.in.wait();
		    } catch (Exception e) { }
		  }

}