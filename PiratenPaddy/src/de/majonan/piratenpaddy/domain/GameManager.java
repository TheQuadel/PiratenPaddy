package de.majonan.piratenpaddy.domain;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.util.List;
import java.util.Vector;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.majonan.piratenpaddy.valueobjects.Entity;
import de.majonan.piratenpaddy.valueobjects.InventoryEntity;
import de.majonan.piratenpaddy.valueobjects.Item;
import de.majonan.piratenpaddy.valueobjects.Player;
import de.majonan.piratenpaddy.valueobjects.items.TreasureMap;


public class GameManager {
	
	//Konstanten
	public static final String GAME_NAME = "PiratenPaddy";
	public static final int GAME_HEIGHT = 720;
	public static final int GAME_WIDTH = 1280;
	public static final String IMAGE_PATH ="res/img/";
	
	private EntityManager entityManager;
	private InventoryEntity inventoryEntity;
	private Player player;
	
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
			//FIXME: Icon für das Fenster setzen, scheint momentan noch nicht zu funktionieren
//			Display.setIcon(new ByteBuffer[] {
//			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(IMAGE_PATH+"icon16.png")), false, false, null),
//			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(IMAGE_PATH+"icon32.png")), false, false, null)
//			});
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
//		}catch (IOException e) {
//			System.err.println("Could not find icons");
//		}
		
		//OpenGL-Zeug
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); 
		glOrtho(0, GAME_WIDTH, GAME_HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		
		entityManager = new EntityManager();
		
		entityManager.addHoverListener(new EntityHoverListener() {
			
			@Override
			public void onHover(Entity entity) {
				entity.setHighlighted(true);
				
			}
		});
		
		entityManager.addClickListener(new EntityClickListener() {
			
			@Override
			public void onClicked(Entity entity) {
				if(entity instanceof Item && !player.getInventory().containsItem((Item) entity)){
					player.getInventory().addItem((Item) entity);
					Item i = ((Item) entity);
					i.setCollected(true);
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
		
		inventoryEntity = new InventoryEntity(0, 720-150, IMAGE_PATH+"inventar2.png", position);
		
		player = new Player(200, 300, IMAGE_PATH+"paddy.png");
		player.getInventory().addInventoryListener(inventoryEntity);
		player.setFootPosition(46, 241);
		
		
		//erstes TestObjekt erstellen (in diesem Fall eine "Schatzkarte");
		entityManager.addEntity(new Entity(0, 0, IMAGE_PATH+"bg1.png") {
			
			@Override
			public void lookAt() {
				// TODO Auto-generated method stub
				
			}
		});
		entityManager.addEntity(inventoryEntity);
		entityManager.addEntity(new TreasureMap(538,464,IMAGE_PATH+"map.png",IMAGE_PATH+"karte64.png"));
		//entityManager.addEntity(new TreasureMap(400,200,IMAGE_PATH+"karte.png",IMAGE_PATH+"karte64.png"));
		//entityManager.addEntity(new TreasureMap(800,400,IMAGE_PATH+"karte.png",IMAGE_PATH+"karte64.png"));
		entityManager.addEntity(player);
		
		

	}
	
	private void run(){
		while(!Display.isCloseRequested()) {
			this.step();
			this.draw();
			Display.update();
			Display.sync(60);
		}
	}

	
	private void draw() {
		glClear(GL_COLOR_BUFFER_BIT);
		
		entityManager.draw();
	}


	private void step() {
		//Inputs behandeln
		int mouseX = Mouse.getX();
		int mouseY = GAME_HEIGHT-Mouse.getY();
		entityManager.deHighlightAll();
		entityManager.update(mouseX, mouseY);
		if(Mouse.isButtonDown(0)){
			System.out.println("MouseButtonDown!");
			player.moveTo(mouseX, mouseY);
		}
		player.tick();
		
	}


	private void quit(){
		entityManager.destroy();
		Display.destroy();
		System.exit(0);
	}
}
