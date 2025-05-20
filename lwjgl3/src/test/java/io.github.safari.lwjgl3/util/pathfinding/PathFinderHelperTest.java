package io.github.safari.lwjgl3.util.pathfinding;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.safari.lwjgl3.maingame.GamemodelInstance;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class PathFinderHelperTest {

    /*
    PathFinderHelper helper;

    @BeforeEach
    void setup() {
        helper = new PathFinderHelper();
    }

    static Stream<Arguments> routeTestData() {
        return Stream.of(
            Arguments.of(new Vector2(0, 0), new Vector2(0, 0), Collections.emptyList()),
            Arguments.of(new Vector2(0, 0), new Vector2(10, 10), null)
        );
    }

    @ParameterizedTest
    @MethodSource("routeTestData")
    void testFindRoute(Vector2 start, Vector2 end, List<Vector2> expected) {
        Array<Node> staticNodesMock = new Array<>();
        staticNodesMock.add(new Node(new Vector2(5, 5), 0));
        staticNodesMock.add(new Node(new Vector2(8, 8), 1));

        try (MockedStatic<PathGraph> mockedPathGraph = Mockito.mockStatic(PathGraph.class);
             MockedStatic<GamemodelInstance> mockedGameModelInstance = Mockito.mockStatic(GamemodelInstance.class)) {

            mockedPathGraph.when(() -> PathGraph.STATIC_NODES).thenReturn(staticNodesMock);
            mockedPathGraph.when(() -> PathGraph.isNodeVisible(Mockito.any(), Mockito.any(), Mockito.anyList()))
                .thenReturn(true);

            var gameModelMock = Mockito.mock(io.github.safari.lwjgl3.maingame.GameModel.class);
            Mockito.when(gameModelMock.getEnvironments()).thenReturn(Collections.emptyList());
            mockedGameModelInstance.when(() -> GamemodelInstance.gameModel).thenReturn(gameModelMock);

            List<Vector2> result = helper.findRoute(start, end);

            if (expected != null) {
                assertEquals(expected, result);
            } else {
                assertNotNull(result);
                if (!start.equals(end)) {
                    assertFalse(result.isEmpty());
                } else {
                    assertTrue(result.isEmpty());
                }
            }
        }
    }

     */
}
