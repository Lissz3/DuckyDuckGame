package com.isabelrosado.fruitytoad.Scenes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.MainMenuScreen;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Screens.ScreenI;
import com.isabelrosado.fruitytoad.Sprites.Frog;

public class HUD extends ScreenI {
    private Frog frog;
    private int score;
    private Label lblScore;
    private Label lblWarning;
    private Window pauseScreen;
    private Window lvlScreen;
    private Window gameOverScreen;
    private Window winScreen;
    private Window saveScoreScreen;
    private boolean paused;
    private Label lblLevel;
    private final int gameLevel;
    private boolean inPausedScreen;
    private boolean inLevelScreen;

    public HUD(final FruityToad game, Frog frog, int level) {
        super(game, "Skins/hud.json", false, false);
        gameLevel = level;
        this.frog = frog;
        defineScreen();
    }

    @Override
    protected void defineScreen() {
        setScore(0);
        setInLevelScreen(false);
        setInPausedScreen(false);
        lblScore = stg.getRoot().findActor("lblScore");
        getLblScore().setText(getScore());
        lblWarning = stg.getRoot().findActor("lblWarning");
        getLblWarning().setText(game.getBundle().get("playscreen.popup"));
        Button btnShowLvls = stg.getRoot().findActor("btnLvls");
        Button btnPause = stg.getRoot().findActor("btnPause");
        Button btnJump = stg.getRoot().findActor("btnJump");
        lblLevel = stg.getRoot().findActor("lblLvl");
        switch (gameLevel) {
            default:
            case 1:
                lblLevel.setText(game.getBundle().get("playscreen.slvl.lvl1"));
                break;
            case 2:
                lblLevel.setText(game.getBundle().get("playscreen.slvl.lvl2"));
                break;
        }

        //independent pause menu
        createPauseScreen();

        //select level menu
        createLvlScreen();

        //game over popup
        createGameOverPopUp();

        //win popup
        createWinPopUp();

        //save score popup
        createScorePopUp();

        stg.addActor(lvlScreen);
        stg.addActor(pauseScreen);
        stg.addActor(gameOverScreen);
        stg.addActor(winScreen);
        stg.addActor(saveScoreScreen);

        btnPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isInLevelScreen()) {
                    soundBtn.play(FruityToad.FX_VOLUME);
                    setPaused(true);
                    setInPausedScreen(true);
                    pauseScreen.setVisible(true);
                }
            }
        });

        btnShowLvls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isInPausedScreen()) {
                    soundBtn.play(FruityToad.FX_VOLUME);
                    setPaused(true);
                    setInLevelScreen(true);
                    lvlScreen.setVisible(true);
                }
            }
        });

        btnJump.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                frog.setJumping(true);
            }
        });
    }

    @Override
    public void show() {

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Label getLblScore() {
        return lblScore;
    }

    public Label getLblWarning() {
        return lblWarning;
    }

    public void update() {
        lblScore.setText(getScore());
    }

    private void createPauseScreen() {
        TextButton btnContinue = new TextButton(game.getBundle().get("playscreen.pausem.cont"), skin);
        TextButton btnRetry = new TextButton(game.getBundle().get("playscreen.pausem.retry"), skin);
        TextButton btnExit = new TextButton(game.getBundle().get("playscreen.pausem.exit"), skin);
        pauseScreen = new Window(game.getBundle().get("playscreen.pause"), skin);
        pauseScreen.getTitleLabel().setAlignment(Align.center);
        pauseScreen.add(btnContinue);
        pauseScreen.row();
        pauseScreen.add(btnRetry);
        pauseScreen.row();
        pauseScreen.add(btnExit);
        pauseScreen.setSize(stg.getWidth() / 2.5f, stg.getHeight() / 2.5f);
        pauseScreen.setPosition(FruityToad.V_WIDTH / 2 - pauseScreen.getWidth() / 2, FruityToad.V_HEIGHT / 2 - pauseScreen.getHeight() / 2);
        pauseScreen.setMovable(false);
        pauseScreen.setVisible(false);

        btnRetry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameLevel == 1) {
                    game.setFinalScore(0);
                }
                soundBtn.play(FruityToad.FX_VOLUME);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new PlayScreen(game, gameLevel));
            }
        });
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setFinalScore(0);
                soundBtn.play(FruityToad.FX_VOLUME);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        btnContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                setInPausedScreen(false);
                pauseScreen.setVisible(false);
                setPaused(false);
            }
        });

    }

    private void createLvlScreen() {
        TextButton btnLvl1 = new TextButton(game.getBundle().get("playscreen.slvl.lvl1"), skin);
        TextButton btnLvl2 = new TextButton(game.getBundle().get("playscreen.slvl.lvl2"), skin);
        TextButton btnCancel = new TextButton(game.getBundle().get("playscreen.cancel"), skin);
        lvlScreen = new Window(game.getBundle().get("playscreen.selectlvl"), skin);
        lvlScreen.getTitleLabel().setAlignment(Align.center);
        lvlScreen.add(btnLvl1).row();
        lvlScreen.add(btnLvl2).row();
        lvlScreen.add(btnCancel);
        lvlScreen.setSize(stg.getWidth() / 2.5f, stg.getHeight() / 2.5f);
        lvlScreen.setPosition(FruityToad.V_WIDTH / 2 - pauseScreen.getWidth() / 2, FruityToad.V_HEIGHT / 2 - pauseScreen.getHeight() / 2);
        lvlScreen.setMovable(false);
        lvlScreen.setVisible(false);

        btnLvl1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                game.setFinalScore(0);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new PlayScreen(game, 1));
            }
        });

        btnLvl2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                game.setFinalScore(0);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new PlayScreen(game, 2));
                lblLevel.setText(game.getBundle().get("playscreen.slvl.lvl2"));
            }
        });

        btnCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                lvlScreen.setVisible(false);
                setInPausedScreen(false);
                setPaused(false);
            }
        });

    }

    private void createGameOverPopUp() {
        Drawable skull = skin.getDrawable("Icon_Skull");
        Image skullImg = new Image(skull);
        TextButton btnRetry = new TextButton(game.getBundle().get("playscreen.pausem.retry"), skin);
        TextButton btnExit = new TextButton(game.getBundle().get("playscreen.pausem.exit"), skin);
        gameOverScreen = new Window(game.getBundle().get("playscreen.gameover"), skin);
        gameOverScreen.getTitleLabel().setAlignment(Align.center);
        gameOverScreen.add(skullImg).padBottom(5);
        gameOverScreen.row();
        gameOverScreen.add(btnRetry);
        gameOverScreen.row();
        gameOverScreen.add(btnExit);
        gameOverScreen.setSize(stg.getWidth() / 2.5f, stg.getHeight() / 2.5f);
        gameOverScreen.setPosition(FruityToad.V_WIDTH / 2 - pauseScreen.getWidth() / 2, FruityToad.V_HEIGHT / 2 - pauseScreen.getHeight() / 2);
        gameOverScreen.setMovable(false);
        gameOverScreen.setVisible(false);

        btnRetry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameLevel == 1) {
                    game.setFinalScore(0);
                }
                soundBtn.play(FruityToad.FX_VOLUME);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new PlayScreen(game, gameLevel));
            }
        });
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    private void createWinPopUp() {
        Drawable crown = skin.getDrawable("Icon_Crown");
        Image crownImg = new Image(crown);
        TextButton btnNextLevel = new TextButton(game.getBundle().get("playscreen.win.next"), skin);
        TextButton btnExit = new TextButton(game.getBundle().get("playscreen.pausem.exit"), skin);
        winScreen = new Window(game.getBundle().get("playscreen.win"), skin);
        winScreen.getTitleLabel().setAlignment(Align.center);
        winScreen.add(crownImg).padBottom(5);
        winScreen.row();
        winScreen.add(btnNextLevel);
        winScreen.row();
        winScreen.add(btnExit);
        winScreen.setSize(stg.getWidth() / 2.5f, stg.getHeight() / 2.5f);
        winScreen.setPosition(FruityToad.V_WIDTH / 2 - pauseScreen.getWidth() / 2, FruityToad.V_HEIGHT / 2 - pauseScreen.getHeight() / 2);
        winScreen.setMovable(false);
        winScreen.setVisible(false);

        btnNextLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                setInPausedScreen(false);
                game.getScreen().dispose();
                if (gameLevel != game.getTotalLevels()) {
                    game.setScreen(new PlayScreen(game, gameLevel + 1));
                }
            }
        });
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    private void createScorePopUp() {
        Drawable crown = skin.getDrawable("Icon_Crown");
        Image crownImg = new Image(crown);
        TextButton btnSave = new TextButton(game.getBundle().get("playscreen.save"), skin);
        TextButton btnExit = new TextButton(game.getBundle().get("playscreen.pausem.exit"), skin);
        final TextField name = new TextField(game.getBundle().get("recmenu.name"), skin);

        saveScoreScreen = new Window(game.getBundle().get("playscreen.win"), skin);
        saveScoreScreen.getTitleLabel().setAlignment(Align.center);
        saveScoreScreen.add(crownImg).padBottom(5).colspan(2);
        saveScoreScreen.row();
        saveScoreScreen.add(name).align(Align.center).colspan(2);
        saveScoreScreen.row();
        saveScoreScreen.add(btnSave);
        saveScoreScreen.add(btnExit);
        saveScoreScreen.setSize(stg.getWidth() / 2.5f, stg.getHeight() / 2.5f);
        saveScoreScreen.setPosition(FruityToad.V_WIDTH / 2 - pauseScreen.getWidth() / 2, FruityToad.V_HEIGHT / 2 - pauseScreen.getHeight() / 2);
        saveScoreScreen.setMovable(false);
        saveScoreScreen.setVisible(false);

        btnSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                game.getPreferences().putString(name.getText(), game.getFinalScore() + "");
                game.getPreferences().flush();
                game.setFinalScore(0);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                setInPausedScreen(false);
                game.getScreen().dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isInPausedScreen() {
        return inPausedScreen;
    }

    public void setInPausedScreen(boolean inPausedScreen) {
        this.inPausedScreen = inPausedScreen;
    }

    public boolean isInLevelScreen() {
        return inLevelScreen;
    }

    public void setInLevelScreen(boolean inLevelScreen) {
        this.inLevelScreen = inLevelScreen;
    }

    public Window getGameOverScreen() {
        return gameOverScreen;
    }

    public Window getWinScreen() {
        return winScreen;
    }

    public Window getSaveScoreScreen() {
        return saveScoreScreen;
    }

    public int getGameLevel() {
        return gameLevel;
    }

}

