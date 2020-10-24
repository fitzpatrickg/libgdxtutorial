package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.udemy.mylibgdxtester.GameMain;

import clouds.Cloud;
import helpers.GameInfo;
import player.Player;

public class MainMenu implements Screen, ContactListener {

    private GameMain game;
    private Texture bg;
    private Player player;
    private World world;
    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;

    public MainMenu(GameMain game) {
        this.game = game;

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(
                false,
                GameInfo.WIDTH / GameInfo.PPM,
                GameInfo.HEIGHT / GameInfo.PPM
        );
        box2DCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        debugRenderer = new Box2DDebugRenderer();

        // What is doSleep? Allow bodies in this world to sleep (if we are not moving or applying force to
        // them, we will not calculate position. This will increase performance
        world = new World(new Vector2(0, -9.8f), true);

        world.setContactListener(this);

        bg = new Texture("Game BG.png");
        player = new Player(
                world,"Player 1.png",
                GameInfo.WIDTH / 2,
                GameInfo.HEIGHT /2 + 250
        );

        Cloud c = new Cloud(world);

        /**
         * why is the player falling so slow? box2D (physics engine) uses 1:1 ratio (1 pixel = 1 meter)
         * The solution for this is to create our own pixel/meter ratio. We want 100px = 1 meter
         */
    }

    // same as create, first step in Screen lifecycle
    @Override
    public void show() {

    }

    void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            // moving the player with LinearImpulse moves the player right away
            player .getBody() .applyLinearImpulse( new Vector2(-0.1f, 0), player.getBody().getWorldCenter(),true);
            // you can also move a player using applyForce. It will move more over time
//            player.getBody() .applyForce(new Vector2(-5f, 0), player.getBody().getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            // moving the player with LinearImpulse moves the player right away
            player .getBody() .applyLinearImpulse( new Vector2(0.1f, 0), player.getBody().getWorldCenter(),true);
            // you can also move a player using applyForce. It will move more over time
//            player.getBody() .applyForce(new Vector2(5f, 0), player.getBody().getWorldCenter(), true);
        }
    }

    @Override
    public void render(float delta) {

        update(delta);

        player.updatePlayer();

        Gdx.gl.glClearColor(1, 0, 0, 1);    // sets background color (behind bg image)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   // clears screen

        game.getBatch().begin();  // must call begin() before drawing onto the screen
        // must draw between being() and end()
        game.getBatch().draw(bg, 0, 0);
        //x & y position are set in constructor player.setPosition()
        game.getBatch().draw(player, player.getX(), player.getY() - player.getHeight() / 2f - 18);

        game.getBatch().end();  // must call end() after completing

        debugRenderer.render(world, box2DCamera.combined);  // takes in world and projection matrix
        // DeltaTime is time it takes for each frame to run
        // velocityIterations / positionIterations = how collision works higher = more precise, but more taxing on system
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);   // how often world will calculate physics
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        player.getTexture().dispose();
    }

    // ** ContactListener inherited methods **
    @Override
    public void beginContact(Contact contact) {
        /**
         * Since we are never use which fixture will be the player, we use this code to
         * be sure we always assign the player to firstBody.
         */
        Fixture firstBody;
        Fixture secondBody;

        if (contact.getFixtureA().getUserData() == "Player") {
            firstBody = contact.getFixtureA();
            secondBody = contact.getFixtureB();
        } else {
            secondBody = contact.getFixtureA();
            firstBody = contact.getFixtureB();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    // ** End ContactListener inherited methods **
}
