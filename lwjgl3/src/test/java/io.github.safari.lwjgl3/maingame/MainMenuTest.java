import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LibGDXHeadlessTest {

    private static HeadlessApplication app;

    @BeforeClass
    public static void setup() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        app = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {}

            @Override
            public void resize(int width, int height) {}

            @Override
            public void render() {}

            @Override
            public void pause() {}

            @Override
            public void resume() {}

            @Override
            public void dispose() {}
        }, config);
    }

    @AfterClass
    public static void teardown() {
        app.exit();
        app = null;
    }

    @Test
    public void testBitmapFontCreation() {
        // Most már Gdx.files nem null, így létrehozhatsz BitmapFontot
        BitmapFont font = new BitmapFont();  // Nem dob NullPointerException-t
        // További tesztelés...
        assert font != null;
    }
}
