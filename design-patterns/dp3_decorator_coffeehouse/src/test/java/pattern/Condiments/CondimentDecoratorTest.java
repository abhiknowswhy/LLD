package pattern.Condiments;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pattern.Beverage.Beverage;
import pattern.Beverage.Beverage.Size;

class MockCondimentDecorator extends CondimentDecorator {
    public MockCondimentDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mock Condiment";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.50;
    }
}

class CondimentDecoratorTest {
    @Mock
    private Beverage mockBeverage;
    
    private CondimentDecorator decorator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        decorator = new MockCondimentDecorator(mockBeverage);
    }

    @Test
    void testGetDescription() {
        // Arrange
        when(mockBeverage.getDescription()).thenReturn("Mock Coffee");

        // Act
        String description = decorator.getDescription();

        // Assert
        assertEquals("Mock Coffee, Mock Condiment", description);
        verify(mockBeverage).getDescription();
    }

    @Test
    void testCost() {
        // Arrange
        when(mockBeverage.cost()).thenReturn(1.00);

        // Act
        double cost = decorator.cost();

        // Assert
        assertEquals(1.50, cost, 0.001);
        verify(mockBeverage).cost();
    }

    @Test
    void testGetSize() {
        // Arrange
        when(mockBeverage.getSize()).thenReturn(Size.GRANDE);

        // Act
        Size size = decorator.getSize();

        // Assert
        assertEquals(Size.GRANDE, size);
        verify(mockBeverage).getSize();
    }

    @Test
    void testSetSize() {
        // Arrange
        Size newSize = Size.VENTI;

        // Act
        decorator.setSize(newSize);

        // Assert
        assertEquals(newSize, decorator.size);
    }

    @Test
    void testMultipleDecorators() {
        // Arrange
        Beverage baseBeverage = mock(Beverage.class);
        when(baseBeverage.getDescription()).thenReturn("Base Coffee");
        when(baseBeverage.cost()).thenReturn(1.00);

        // Act
        Beverage firstDecorator = new MockCondimentDecorator(baseBeverage);
        Beverage secondDecorator = new MockCondimentDecorator(firstDecorator);

        // Assert
        assertEquals("Base Coffee, Mock Condiment, Mock Condiment", secondDecorator.getDescription());
        assertEquals(2.00, secondDecorator.cost(), 0.001);
    }

    @Test
    void testSizeAffectsCost() {
        // Arrange
        when(mockBeverage.getSize()).thenReturn(Size.TALL, Size.GRANDE, Size.VENTI);
        when(mockBeverage.cost()).thenReturn(1.00);

        // Create three decorators with different sizes
        Beverage tallDecorator = new MockCondimentDecorator(mockBeverage);
        Beverage grandeDecorator = new MockCondimentDecorator(mockBeverage);
        Beverage ventiDecorator = new MockCondimentDecorator(mockBeverage);

        // Assert all sizes have expected base cost plus decorator cost
        assertEquals(1.50, tallDecorator.cost(), 0.001);
        assertEquals(1.50, grandeDecorator.cost(), 0.001);
        assertEquals(1.50, ventiDecorator.cost(), 0.001);
    }
}
