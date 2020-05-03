package com.pongcle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Ball {
    public Sprite sprite;
    public Body body;
    public int ballRadius = 40;
    float simulationScale = 10f;
    public int velocity = 40;
    World world;

    Ball(World world, int ballRadius, int velocity, float simulationScale){
        this.world = world;
        this.ballRadius = ballRadius;
        this.velocity = velocity;
        this.simulationScale = simulationScale;
        sprite = createSprite(false);
        body = createBody();

    }
    public Ball(){
        world = new World(new Vector2(0, 0), true);
        sprite = createSprite(true);
        body = createBody();
    }

    /**
     * Creates renderable sprite.
     * @param isTest if isTest, don't use a texture.
     * @return renderable sprite object.
     */
    private Sprite createSprite(boolean isTest){
        Sprite sprite;
        if(isTest){
            sprite = new Sprite();
        }else{
            sprite = new Sprite(new Texture("purplecircle.png"));
        }
        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        sprite.setSize(this.ballRadius * 2, this.ballRadius * 2);
        return sprite;
    }

    /**
     * Creates the circular ball body for the physics simulation
     * @return circular physics body object.
     */
    private Body createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX()/simulationScale, sprite.getY()/simulationScale);
        Body ballBody = world.createBody(bodyDef);
        ballBody.setLinearVelocity(velocity, velocity/2);
        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(this.ballRadius / simulationScale);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 1f;
        fixtureDef.density = 1f;
        Fixture fixture = ballBody.createFixture(fixtureDef);
        ballShape.dispose();
        return ballBody;
    }

    /**
     * Makes the renderable sprite sync up with the physics engine.
     * The physics engine is smaller than the renderable screen.
     */
    public void syncSpriteBody(){
        sprite.setPosition(body.getPosition().x*simulationScale-sprite.getWidth()/2, body.getPosition().y*simulationScale-sprite.getHeight()/2);
//        sprite.setPosition(body.getPosition().x*simulationScale-10, body.getPosition().y*simulationScale+20);

//        sprite.setPosition((float) (body.getPosition().x*(simulationScale-0.5)), body.getPosition().y*(simulationScale+1));

    }

    /**
     * Checks the ball's slope, ensures it's not too steep
     * If a ball has a high slope, it will take forever to get
     * accoss the screen.
     */
    public void checkBallVelocitySlope(){
        float velX = body.getLinearVelocity().x;
        float velY = body.getLinearVelocity().y;
        float slope = Math.abs(velY / velX);
        if(slope > 0.8){
            body.setLinearVelocity((float) (velX*1.20), (float) (velY*0.80));
        }
    }
    /**
     * Makes the ball bounce of the top, bottom, right of the screen (keeps ball in bounds).
     * If the ball bounces off the right wall, give the player a point.
     */
    public void makeBallBounceOffWalls(){
        if(sprite.getY() < 0){
            body.setLinearVelocity(body.getLinearVelocity().x, Math.abs(body.getLinearVelocity().y));
        }
        if(sprite.getY() > 720-sprite.getHeight()){
            body.setLinearVelocity(body.getLinearVelocity().x, -Math.abs(body.getLinearVelocity().y));
        }

    }

    /**
     * Checks if single player scored, (hit the right wall)
     * If they did, make it bounce back by negating x velocity.
     * @return
     */
    public boolean didSinglePlayerScore(){
        if(sprite.getX() > Gdx.graphics.getWidth()-sprite.getWidth()){
            body.setLinearVelocity(-Math.abs(body.getLinearVelocity().x), body.getLinearVelocity().y);
            return true;
        }
        return false;
    }
    /**
     * Checks is the ball has went off the screen,
     * if the ball went of the screen, the player lost the singleplayer game.
     */
    public boolean didSinglePlayerLose(){
        if(sprite.getX() < -sprite.getWidth()){
            return true;
        }
        return false;
    }

    /**
     * Resets ball to random middle position in screen
     * @param velocityLeft decides if the ball should move left or right.
     */
    public void resetBall(boolean velocityLeft){
        int direction = 1;
        if(velocityLeft){
            direction = -1;
        }
        body.setTransform(50, (float) (4.00+Math.random()*68), 90);
        body.setLinearVelocity(direction*velocity, velocity/2);
    }

    /**
     * Checks is the ball has went off the screen,
     * if the ball went of the screen, someone scored
     * @return 1 if right player scored, 2 if left player scored.
     */
    public int checkForGoals(){
        if(sprite.getY()<-sprite.getHeight() || sprite.getY()>Gdx.graphics.getHeight()+sprite.getHeight()){
            resetBall(true);
        }
        if(sprite.getX() < -sprite.getWidth()){
            return 1;
        }
        if(sprite.getX() > Gdx.graphics.getWidth()+sprite.getWidth()){
            return 2;
        }
        return 0;
    }

}
