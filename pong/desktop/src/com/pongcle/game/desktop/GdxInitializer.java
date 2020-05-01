package com.pongcle.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pongcle.game.Pong;

//taken completely from https://badlogicgames.com/forum/viewtopic.php?f=11&t=10115#

/**
 * This class creates a headless game instance, which we then use to test game objects with unit tests.
 */
public class GdxInitializer {

    /**
     * Fake application listener class
     */
    @SuppressWarnings("EmptyClass")
    private static class TestApplication extends ApplicationAdapter {

    }


    /**
     * init the environement, once.
     */
    static {
        //init GDX environement to have the methods available
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.title = "Test";
        cfg.width = 2;
        cfg.height = 2;
        LwjglApplicationConfiguration.disableAudio = true;

        new LwjglApplication(new TestApplication(), cfg);

    }
}