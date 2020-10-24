package player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Player extends Sprite {
    private World world;    // physics world where our body will be
    private Body body;      // physics body attached to player

    public Player(World world, String name, float x, float y) {
        super(new Texture(name));
        this.world = world;
        setPosition(x - getWidth() / 2f, y - getHeight() / 2f);
        createBody();
    }

    void createBody() {
        // create a bodyDef, to define body before creating
        /**
         * BodyDef Types:
         * StaticBody       - Not effected by gravity or other forces. Will not move
         * Kinematic Body   - Not effected by gravity but IS effected by other forces (being pushed)
         * Dynamic Body     - Is effected by gravity & other forces
         */
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        body = world.createBody(bodyDef);   // create body in physics world with bodyDef

        // creates hit box shape to be passed into FixtureDef
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                (getWidth() / 2)  / GameInfo.PPM,
                (getHeight() / 2)  / GameInfo.PPM
        );

        // defines mass of body, fraction, shape etc (options for fixture)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;   // shape
        fixtureDef.density = 1f;     // density, how it handles gravity

        // applies fixture (shape) to the physics body
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");  // used in ContactListener methods in MainMenu. Used to identify fixtures that are making contact

        shape.dispose();
    }

    // updates player position according to body
    public void updatePlayer() {
        this.setPosition(
                body.getPosition().x * GameInfo.PPM,
                body.getPosition().y * GameInfo.PPM
        );
    }

    public Body getBody() {
        return this.body;
    }


}
