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
				if(entity instanceof Item){
					player.getInventory().addItem((Item) entity);
				}
				
			}
		});
		
		int[][] position = {{265, 17},
		                    {325, 17},
		                    {385, 17},
		                    {445, 17},
		                    {265, 77},
		                    {325, 77},
		                    {385, 77},
		                    {445, 77}};
		
		inventoryEntity = new InventoryEntity(0, 0, IMAGE_PATH+"inventar.png", position);
		
		player = new Player(600, 200, IMAGE_PATH+"icon64.png");
		player.getInventory().addInventoryListener(inventoryEntity);
		
		
		//erstes TestObjekt erstellen (in diesem Fall eine "Schatzkarte");
		entityManager.addEntity(inventoryEntity);
		entityManager.addEntity(new TreasureMap(200,200,IMAGE_PATH+"karte.png"));
		entityManager.addEntity(new TreasureMap(400,200,IMAGE_PATH+"karte.png"));
		entityManager.addEntity(new TreasureMap(800,400,IMAGE_PATH+"karte.png"));
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
		
	}


	private void quit(){
		entityManager.destroy();
		Display.destroy();
		System.exit(0);
	}
}
