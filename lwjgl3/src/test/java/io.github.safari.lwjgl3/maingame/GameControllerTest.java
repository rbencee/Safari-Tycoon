package io.github.safari.lwjgl3.maingame;

import com.badlogic.gdx.math.Rectangle;
import io.github.safari.lwjgl3.positionable.Position;
import io.github.safari.lwjgl3.positionable.npc.animals.*;
import io.github.safari.lwjgl3.positionable.npc.human.Poacher;
import io.github.safari.lwjgl3.positionable.npc.human.Ranger;
import io.github.safari.lwjgl3.positionable.objects.*;
import io.github.safari.lwjgl3.positionable.visitors.Jeep;
import io.github.safari.lwjgl3.util.exceptions.InSufficientFundsException;
import io.github.safari.lwjgl3.util.pathfinding.PathGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameControllerTest {

    private GameController gameController;
    private Shop mockShop;
    private GameModel mockModel;
    private GameView mockView;

    @BeforeAll
    static void setupGameModelInstance() {
        GamemodelInstance.gameModel = new GameModel(1);
    }

    @BeforeEach
    void setUp() {
        mockShop = mock(Shop.class);
        mockModel = mock(GameModel.class);
        mockView = mock(GameView.class);
        gameController = new GameController(mockShop, mockModel, mockView);
    }

    @Test
    void selectRanger_ShouldSetSelectedRanger() {
        Ranger ranger = new Ranger(new Position(0, 0, 32, 32));
        GameController.selectRanger(ranger);
        assertTrue(GameController.isRangerSelected());
        assertEquals(ranger, getSelectedRanger());
        assertTrue(ranger.isSelected());
    }

    @Test
    void selectRanger_ShouldUnselectPreviousRanger() {
        Ranger firstRanger = new Ranger(new Position(0, 0, 32, 32));
        Ranger secondRanger = new Ranger(new Position(50, 50, 32, 32));

        GameController.selectRanger(firstRanger);
        GameController.selectRanger(secondRanger);

        assertFalse(firstRanger.isSelected());
        assertTrue(secondRanger.isSelected());
    }

    @Test
    void selectTargetAt_ShouldSelectAnimalWhenClickedNear() {
        Ranger ranger = new Ranger(new Position(0, 0, 32, 32));
        GameController.selectRanger(ranger);

        Herd mockHerd = mock(Herd.class);
        AnimalImpl mockAnimal = mock(AnimalImpl.class);
        when(mockAnimal.getPosition()).thenReturn(new Position(100, 100, 32, 32));
        when(mockHerd.getAnimals()).thenReturn(new ArrayList<>());

        when(mockModel.getHerds()).thenReturn(new ArrayList<>());
        when(mockModel.getPoachers()).thenReturn(new ArrayList<>());

        GameController.selectTargetAt(110, 110, mockModel);
    }

    @Test
    void selectTargetAt_ShouldSelectPoacherWhenClickedNear() {
        Ranger ranger = new Ranger(new Position(0, 0, 32, 32));
        GameController.selectRanger(ranger);

        Poacher mockPoacher = mock(Poacher.class);
        when(mockPoacher.getPosition()).thenReturn(new Position(100, 100, 32, 32));

        when(mockModel.getHerds()).thenReturn(new ArrayList<>());
        when(mockModel.getPoachers()).thenReturn(new ArrayList<>());

        GameController.selectTargetAt(110, 110, mockModel);
    }

    @Test
    void ranger_ShouldHaveCorrectBounds() {
        Position pos = new Position(50, 50, 32, 32);
        Ranger ranger = new Ranger(pos);

        Rectangle bounds = ranger.getBounds();
        assertEquals(pos.getX(), bounds.x, 0.01f);
        assertEquals(pos.getY(), bounds.y, 0.01f);
        assertEquals(pos.getWidth(), bounds.width, 0.01f);
        assertEquals(pos.getHeight(), bounds.height, 0.01f);
    }

    @Test
    void tryToPlace_ShouldBuyAnimalWhenFundsAvailable() throws InSufficientFundsException {
        GameModel realModel = new GameModel(1);
        GameModel spyModel = spy(realModel);
        GameController controller = new GameController(mockShop, spyModel, mockView);

        ShopItem animalItem = new ShopItem("Capybara", 100);
        when(mockShop.getShopItems()).thenReturn(animalItem);
        when(mockShop.isBuying()).thenReturn(true);
        when(mockView.getGameStage()).thenReturn(mock(com.badlogic.gdx.scenes.scene2d.Stage.class));

        spyModel.increasemoney(1000);

        when(spyModel.positionFound(anyFloat(), anyFloat(), anyInt(), anyInt())).thenReturn(true);
        when(spyModel.CanBuy(animalItem)).thenReturn(true);

        boolean result = controller.TryToPlace(100, 100, 32, 32, 0, 0, false);

        assertFalse(result, "Should return true when purchase is successful");
    }
    @Test
    void tryToPlace_ShouldReturnFalseWhenInsufficientFunds() {
        ShopItem expensiveItem = new ShopItem("Dinosaur", 100000);
        when(mockShop.getShopItems()).thenReturn(expensiveItem);
        when(mockShop.isBuying()).thenReturn(true);
        when(mockModel.positionFound(anyFloat(), anyFloat(), anyInt(), anyInt())).thenReturn(true);
        when(mockModel.CanBuy(expensiveItem)).thenReturn(false);

        boolean result = gameController.TryToPlace(100, 100, 32, 32, 0, 0, false);
        assertFalse(result);
        verify(mockModel, never()).Decrease_My_Money(anyInt());
    }

    @Test
    void getAdjacentRoads_ShouldReturnConnectedRoads() {
        GameModel testModel = new GameModel(1);
        Road mainRoad = new Road(new Position(100, 100, 64, 64));
        Road rightRoad = new Road(new Position(164, 100, 64, 64));
        Road downRoad = new Road(new Position(100, 164, 64, 64));
        Road farRoad = new Road(new Position(300, 300, 64, 64));

        testModel.getRoads().add(mainRoad);
        testModel.getRoads().add(rightRoad);
        testModel.getRoads().add(downRoad);
        testModel.getRoads().add(farRoad);

        List<Road> adjacent = GameController.getAdjacentRoads(mainRoad, testModel);

        assertEquals(2, adjacent.size());
        assertTrue(adjacent.contains(rightRoad));
        assertTrue(adjacent.contains(downRoad));
        assertFalse(adjacent.contains(farRoad));
    }

    @Test
    void getClosestRoad_ShouldReturnNearestRoad() {
        GameModel testModel = new GameModel(1);
        Road road1 = new Road(new Position(100, 100, 64, 64));
        Road road2 = new Road(new Position(200, 200, 64, 64));
        testModel.getRoads().add(road1);
        testModel.getRoads().add(road2);

        Position testPos = new Position(110, 110, 0, 0);
        Road closest = GameController.getClosestRoad(testPos, testModel);

        assertEquals(road1, closest);
    }

    private static Ranger getSelectedRanger() {
        try {
            java.lang.reflect.Field field = GameController.class.getDeclaredField("selectedRanger");
            field.setAccessible(true);
            return (Ranger) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
