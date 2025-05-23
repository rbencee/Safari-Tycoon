package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloneableMoveToActionTest {

    @Test
    void testCloneCreatesEquivalentButIndependentAction() {
        MoveToAction original = new MoveToAction();
        original.setStartPosition(10f, 20f);
        original.setPosition(100f, 200f);
        original.setDuration(1.0f);
        original.setInterpolation(Interpolation.linear);

        CloneableMoveToAction originalCloneable = new CloneableMoveToAction(original);

        CloneableMoveToAction cloned = (CloneableMoveToAction) originalCloneable.clone();

        assertNotSame(originalCloneable, cloned);

        assertEquals(originalCloneable.getX(), cloned.getX());
        assertEquals(originalCloneable.getY(), cloned.getY());
        assertEquals(originalCloneable.getStartX(), cloned.getStartX());
        assertEquals(originalCloneable.getStartY(), cloned.getStartY());

        assertEquals(originalCloneable.getInterpolation(), cloned.getInterpolation());

        float durationDiff = Math.abs(originalCloneable.getDuration() - cloned.getDuration());
        assertTrue(durationDiff <= 0.2f, "Duration difference is not within expected range: " + durationDiff);
    }
}
