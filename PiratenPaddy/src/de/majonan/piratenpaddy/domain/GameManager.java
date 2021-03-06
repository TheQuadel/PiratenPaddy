package de.majonan.piratenpaddy.domain;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.majonan.piratenpaddy.valueobjects.Area;
import de.majonan.piratenpaddy.valueobjects.Callback;
import de.majonan.piratenpaddy.valueobjects.Character;
import de.majonan.piratenpaddy.valueobjects.Cursor;
import de.majonan.piratenpaddy.valueobjects.Entity;
import de.majonan.piratenpaddy.valueobjects.InventoryEntity;
import de.majonan.piratenpaddy.valueobjects.Item;
import de.majonan.piratenpaddy.valueobjects.Player;
import de.majonan.piratenpaddy.valueobjects.Point;
import de.majonan.piratenpaddy.valueobjects.Sprite;
import de.majonan.piratenpaddy.valueobjects.items.Candle;
import de.majonan.piratenpaddy.valueobjects.items.TreasureMap;
import de.majonan.piratenpaddy.valueobjects.listeners.ClickListener;
import de.majonan.piratenpaddy.valueobjects.listeners.KeyboardListener;
import de.majonan.piratenpaddy.valueobjects.listeners.MouseMoveListener;


public class GameManager {
	
	public static boolean DEBUG = !true;
	
	//Konstanten
	public static final String GAME_NAME = "PiratenPaddy";
	public static final int GAME_HEIGHT = 720;
	public static final int GAME_WIDTH = 1280;
	public static final String IMAGE_PATH ="res/img/";
	
	public static final int ZINDEX_BACKGROUND = -5;
	public static final int ZINDEX_INVENTORY = 1000;

	public static final int ZINDEX_CURSOR = 2000;
	
	private Cursor cursor;
	private InputManager inputManager;
	private EntityManager entityManager;
	private InventoryEntity inventoryEntity;
	private Player player;
	private Area area;
	//sprivate Cursor cursor;
	
	//Konstruktor
	public GameManager(){
		this.init();
		this.run();
		this.quit();
	}
	
	/**
	 * Initialisieren des GameManagers. Erstellen des Fensters, setzen des Titels, OpenGL initialisieren
	 */
	private void init(){
		try{
			Display.setDisplayMode(new DisplayMode(GAME_WIDTH, GAME_HEIGHT));
			Display.setTitle(GAME_NAME);
			//FIXME: Icon f�r das Fenster setzen, scheint momentan noch nicht zu funktionieren
//			Display.setIcon(new ByteBuffer[] {
//			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(IMAGE_PATH+"icon16.png")), false, false, null),
//			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(IMAGE_PATH+"icon32.png")), false, false, null)
//			});
			Display.setVSyncEnabled(true);
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
//		}catch (IOException e) {
//			System.err.println("Could not find icons");
//		}
		
		
		Mouse.setGrabbed(true);
		
		//OpenGL-Zeug
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); 
		glOrtho(0, GAME_WIDTH, GAME_HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		showLoadingScreen();
		
		inputManager = new InputManager();
		entityManager = new EntityManager();
		
		entityManager.addHoverListener(new EntityHoverListener() {
			
			@Override
			public void onHover(Entity entity) {
				if(entity instanceof Item || (entity instanceof Character && !(entity instanceof Player))){
					entity.setHighlighted(true);
					cursor.changeSprite("inspect");
				}
				
				
			}
		});
		
		entityManager.addClickListener(new EntityClickListener() {
			
			@Override
			public void onClicked(Entity entity) {
//				if(entity instanceof Item && !player.getInventory().containsItem((Item) entity)){
//					player.getInventory().addItem((Item) entity);
//					entity.setZIndex(ZINDEX_INVENTORY+2);
//					entityManager.resort();
//					Item i = ((Item) entity);
//					i.setCollected(true);
//				}
				
				System.out.println("Entity Clicked!");
				if(entity instanceof Item){
					System.out.println("Item Clicked!");
					final Item item = (Item) entity;
					Point target = item.getNearestPoint();
					if(target == null){
						target = area.getWalkablePointNextTo(new Point((int)item.getX(), (int)item.getY()));
					}
					player.moveTo(target.x, target.y, new Callback() {
						
						@Override
						public void execute() {
							player.lookAt(item);
							
						}
					});
					
				}
				
			}
		});
		
		int[][] position = {{713, 17},
		                    {783, 17},
		                    {853, 17},
		                    {923, 17},
		                    {713, 81},
		                    {783, 81},
		                    {853, 81},
		                    {823, 81}};
		
		inventoryEntity = new InventoryEntity(0, 720-15, 1280, 150, position);
		inventoryEntity.addSprite("default", (new Sprite(1280, 150, 0, 0)).addFrame(IMAGE_PATH+"inventar2.png", 5000));
		inventoryEntity.changeSprite("default");
		inventoryEntity.setZIndex(ZINDEX_INVENTORY);
		
		//player = new Player(200, 300, 100, 256);
		//player.addSprite("default", (new Sprite(100, 256, 46, 241)).addFrame(IMAGE_PATH+"paddy.png", 200).addFrame(IMAGE_PATH+"paddyw.png", 200));
		player = new Player(500, 600, 147, 449);
		player.addSprite("default", (new Sprite(147, 449, 60, 433)).addFrame(IMAGE_PATH+"paddy3.png", 200).addFrame(IMAGE_PATH+"paddy3.png", 200));
		player.changeSprite("default");
		player.getInventory().addInventoryListener(inventoryEntity);
		
		
		//erstes TestObjekt erstellen (in diesem Fall eine "Schatzkarte");
		//entityManager.addEntity(
		
		area = new Area(0, 0, 720, 1280);
		area.addSprite("default", (new Sprite(720,1280,0,0)).addFrame(IMAGE_PATH+"paddyszimmer.png", 10000));
		area.setMask((new Sprite(720,1280,0,0)).addFrame(IMAGE_PATH+"paddyszimmer_mask.png", 10000));
		area.changeSprite("default");
		area.setZIndex(ZINDEX_BACKGROUND);
		area.addPointsToWalkablePolygon(new Point(376, 525),
				new Point(695, 527),
				new Point(704, 547),
				new Point(1103, 539),
				new Point(1276, 652),
				new Point(1274, 710),
				new Point(279, 710),
				new Point(290, 679),
				new Point(244, 622),
				new Point(340, 538),
				new Point(373, 534));
		entityManager.addEntity(area);
		
		entityManager.addEntity(inventoryEntity);
		
//		Entity map = new TreasureMap(538,464,130,30);
//		map.addSprite("default", (new Sprite(130,30,0,0).addFrame(IMAGE_PATH+"map.png", 1000)));
//		map.addSprite("collected", (new Sprite(130,30,0,0).addFrame(IMAGE_PATH+"karte64.png", 2000)));
//		map.changeSprite("default");	
//		entityManager.addEntity(map);
		
		Item candle = new Candle(16,237);
		entityManager.addEntity(candle);
		//entityManager.addEntity(new TreasureMap(400,200,IMAGE_PATH+"karte.png",IMAGE_PATH+"karte64.png"));
		//entityManager.addEntity(new TreasureMap(800,400,IMAGE_PATH+"karte.png",IMAGE_PATH+"karte64.png"));
		entityManager.addEntity(player);
		
		cursor = new Cursor(24, 24, 24, 24);
		cursor.addSprite("walk", (new Sprite(16,24,8,12)).addFrame(IMAGE_PATH+"cursor/steps.png", 2000));
		cursor.addSprite("pointer", (new Sprite(19,26,8,13)).addFrame(IMAGE_PATH+"cursor/hand.png", 2000));
		cursor.addSprite("inspect", (new Sprite(19,26,8,13)).addFrame(IMAGE_PATH+"cursor/eye.png", 2000));
		cursor.changeSprite("pointer");
		entityManager.addEntity(cursor);

		
		
//		Sprite test = new Sprite(8,4,0,0);
//		test.addFrame(IMAGE_PATH+"test2.png", 20000);
//		for(int j=0; j<8; j++){
//			System.out.println("\nPixel["+j+"]");
//			int[] col = test.getColorOfPixel(j, 1);
//			for(int i=0; i<col.length; i++){
//				System.out.println("col["+i+"] == "+col[i]);
//			}
//		}
		
		
		
		player.say("Oh,Hallo!", 1000);
		
		inputManager.addKeyBoardListener(new KeyboardListener() {
			
			@Override
			public boolean onKeyUp(int button) {
				System.out.println("KeyEvent: [Up|"+Keyboard.getKeyName(button)+"]");
				return false;
			}
			
			@Override
			public boolean onKeyPressed(int button) {
				//System.out.println("KeyEvent: [Pressed|"+Keyboard.getKeyName(button)+"]");
				return false;
			}
			
			@Override
			public boolean onKeyDown(int button) {
				System.out.println("KeyEvent: [Down|"+Keyboard.getKeyName(button)+"]");
				if(Keyboard.getKeyName(button).equals("ESCAPE")){
					quit();
				}
				if(Keyboard.getKeyName(button).equals("D")){
					DEBUG = !DEBUG;
					Mouse.setGrabbed(!DEBUG);
				}
				if(Keyboard.getKeyName(button).equals("W")){
					if(area.isPositonWalkable(inputManager.getMouseX(), inputManager.getMouseY())){
						cursor.changeSprite("walk");
					}else{
						cursor.changeSprite("pointer");
					}
				}
				return false;
			}
		});
		
		inputManager.addClickListener(new ClickListener() {
			
			@Override
			public boolean onMouseUp(int key, int x, int y) {
				System.out.println("MouseEvent: [Up|"+Mouse.getButtonName(key)+" x:"+x+" y: "+y+"]");
				if(area.isPositonWalkable(x, y)){
					player.moveTo(x, y);
				}
				entityManager.clicked(x, y);
				return false;
			}
			
			@Override
			public boolean onMouseDown(int key, int x, int y) {
				//System.out.println("MouseEvent: [Down|"+Mouse.getButtonName(key)+"]");
				return false;
			}
			
			@Override
			public boolean onMouseClicked(int key, int x, int y) {
				//System.out.println("MouseEvent: [Clicked|"+Mouse.getButtonName(key)+"]");
				return false;
			}
		});
	
		inputManager.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public boolean onMouseMove(int x, int y, int dx, int dy) {
				//System.out.println("MouseEvent: [Moved| x:"+x+" y: "+y+" dx:"+dx+" dy:"+dy+"]");
				Display.setTitle(GAME_NAME+" [x:"+x+" y: "+y+"]");
				cursor.setPosition(x, y);
				if(area.isPositonWalkable(x, y)){
					cursor.changeSprite("walk");
				}else{
					cursor.changeSprite("pointer");
				}
				if((y > 700 && dy < -10 && inventoryEntity.isHidden()) || (y > 718 && inventoryEntity.isHidden())){
					inventoryEntity.show();
				}
				if((!inventoryEntity.isHidden() && y < 720-150 && dy > 8) || (!inventoryEntity.isHidden() && y < 720-150-40)){
					inventoryEntity.hide();
				}
				entityManager.deHighlightAll();
				entityManager.mouseMove(x, y);
				return false;
			}
		});

	}
	
	private void run(){
		while(!Display.isCloseRequested()) {
			this.step();
			this.draw();
			Display.update();
			Display.sync(60);
		}
	}

	private void showLoadingScreen(){
		Entity loadScr = new Entity(0,0,GAME_WIDTH, GAME_HEIGHT) {

		};
		loadScr.addSprite("default", (new Sprite(GAME_WIDTH, GAME_HEIGHT,0,0)).addFrame(IMAGE_PATH+"ladescreen.png", 10000));
		loadScr.changeSprite("default");
		glClear(GL_COLOR_BUFFER_BIT);
		loadScr.draw();
		Display.update();
	}
	private void draw() {
		glClear(GL_COLOR_BUFFER_BIT);
		
		entityManager.draw();
		if(DEBUG){
			glDisable(GL_TEXTURE_2D);
			glEnable(GL_COLOR_MATERIAL);
			glLineWidth(2); 
			if(area.isPositonWalkable(inputManager.getMouseX(), inputManager.getMouseY())){
				glColor3f(.3f, .8f, .0f);
			}else{
				glColor3f(.8f, .3f, 0f);
			}
			glBegin(GL_LINES);
			glVertex3f(0, 0, 0);
			glVertex3f(inputManager.getMouseX(), inputManager.getMouseY(), 0);
			glEnd();
		}
	}


	private void step() {
		//Inputs behandeln
		inputManager.tick();
		int mouseX = Mouse.getX();
		int mouseY = GAME_HEIGHT-Mouse.getY();
		

		
		player.tick();
		inventoryEntity.tick();
		player.getInventory().notifyListeners();
	}


	private void quit(){
		entityManager.destroy();
		Display.destroy();
		System.exit(0);
	}
}
