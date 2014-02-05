package de.majonan.piratenpaddy.domain;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.LWJGLException;
import org.newdawn.slick.opengl.ImageIOImageData;





import de.majonan.piratenpaddy.valueobjects.Item;
import de.majonan.piratenpaddy.valueobjects.items.TreasureMap;
import static org.lwjgl.opengl.GL11.*;


public class GameManager {
	
	//Konstanten
	public static final String GAME_NAME = "PiratenPaddy";
	public static final int GAME_HEIGHT = 720;
	public static final int GAME_WIDTH = 1280;
	public static final String IMAGE_PATH ="res/img/";
	
	private Item test;
	
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
			Display.setIcon(new ByteBuffer[] {
			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(IMAGE_PATH+"icon16.png")), false, false, null),
			        new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(IMAGE_PATH+"icon32.png")), false, false, null)
			});
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}catch (IOException e) {
			System.err.println("Could not find icons");
		}
		
		//OpenGL-Zeug
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); 
		glOrtho(0, GAME_WIDTH, GAME_HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		
		//erstes TestObjekt erstellen (in diesem Fall eine "Schatzkarte");
		test = new TreasureMap(200,200,IMAGE_PATH+"icon64.png");
		

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
		test.draw(true);
	}


	private void step() {
		//Inputs behandeln
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		
	}


	private void quit(){
		test.destroy();
		Display.destroy();
		System.exit(0);
	}
}
