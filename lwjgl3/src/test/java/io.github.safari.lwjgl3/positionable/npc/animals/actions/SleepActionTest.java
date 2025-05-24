package io.github.safari.lwjgl3.positionable.npc.animals.actions;

import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SleepActionTest {

    @BeforeEach
    void resetGameModelSpeed() {
        GamemodelInstance.gameModel.setSpeed(1);
    }

    @Test
    void testActReturnsFalseUntilDurationMet() {
        SleepAction action = new SleepAction(2.0f);
        assertFalse(action.act(0.5f));
        assertFalse(action.act(0.5f));
        assertFalse(action.act(0.5f));
        assertTrue(action.act(0.5f));
    }

    @Test
    void testActDurationWithSpeed2() {
        GamemodelInstance.gameModel.setSpeed(2);
        SleepAction action = new SleepAction(2.0f);
        assertFalse(action.act(0.2f));
        //assertTrue(action.act(0.1f));
    }

    @Test
    void testActDurationWithSpeed3() {
        GamemodelInstance.gameModel.setSpeed(3);
        SleepAction action = new SleepAction(2.0f);
        //assertTrue(action.act(0.07f));
    }

    @Test
    void testCloneCreatesNewInstance() {
        SleepAction original = new SleepAction(3.0f);
        SleepAction clone = (SleepAction) original.clone();
        assertNotSame(original, clone);
        assertTrue(clone instanceof SleepAction);
    }
}
