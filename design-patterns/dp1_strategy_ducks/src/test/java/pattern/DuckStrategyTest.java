package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.behaviors.FlyBehavior;
import pattern.behaviors.QuackBehavior;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DuckStrategyTest {

    @Mock
    private FlyBehavior mockFlyBehavior;

    @Mock
    private QuackBehavior mockQuackBehavior;

    @Mock
    private FlyBehavior alternativeMockFlyBehavior;

    @Mock
    private QuackBehavior alternativeMockQuackBehavior;

    private Duck testDuck;

    @BeforeEach
    void setUp() {
        // Create an anonymous Duck implementation for testing
        testDuck = new Duck() {
            @Override
            public void display() {
                // Not relevant for strategy pattern testing
            }
        };
    }

    @Test
    void performFly_ShouldDelegateToBehavior() {
        // Arrange
        testDuck.setFlyBehavior(mockFlyBehavior);

        // Act
        testDuck.performFly();

        // Assert
        verify(mockFlyBehavior).fly();
    }

    @Test
    void performQuack_ShouldDelegateToBehavior() {
        // Arrange
        testDuck.setQuackBehavior(mockQuackBehavior);

        // Act
        testDuck.performQuack();

        // Assert
        verify(mockQuackBehavior).quack();
    }

    @Test
    void changingFlyBehavior_ShouldUseNewBehavior() {
        // Arrange
        testDuck.setFlyBehavior(mockFlyBehavior);
        testDuck.performFly();
        verify(mockFlyBehavior).fly();

        // Act
        testDuck.setFlyBehavior(alternativeMockFlyBehavior);
        testDuck.performFly();

        // Assert
        verify(alternativeMockFlyBehavior).fly();
    }

    @Test
    void changingQuackBehavior_ShouldUseNewBehavior() {
        // Arrange
        testDuck.setQuackBehavior(mockQuackBehavior);
        testDuck.performQuack();
        verify(mockQuackBehavior).quack();

        // Act
        testDuck.setQuackBehavior(alternativeMockQuackBehavior);
        testDuck.performQuack();

        // Assert
        verify(alternativeMockQuackBehavior).quack();
    }
}
