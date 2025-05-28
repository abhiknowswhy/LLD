package pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MonsterMockTest {

    @Test
    void testMonsterCloneWithMockito() throws CloneNotSupportedException {
        // Create a mock Monster
        Monster mockMonster = mock(Monster.class, withSettings()
            .useConstructor("TestMonster", Monster.MonsterType.MUTANT, true, 500)
            .defaultAnswer(CALLS_REAL_METHODS));

        // Test cloning
        Monster clonedMonster = mockMonster.copy();
        
        // Verify the clone is not the same instance
        assertNotSame(mockMonster, clonedMonster);
        
        // Test that changing clone doesn't affect original
        clonedMonster.setName("ModifiedMonster");
        assertNotEquals(clonedMonster.getName(), mockMonster.getName());
    }

    @Test
    void testMonsterPropertiesWithMockito() {
        // Create a mock Monster with specific properties
        Monster mockMonster = mock(Monster.class, withSettings()
            .useConstructor("TestMonster", Monster.MonsterType.DRAGON, false, 300)
            .defaultAnswer(CALLS_REAL_METHODS));
        
        // Verify constructor set the properties correctly
        assertEquals("TestMonster", mockMonster.getName());
        assertEquals(Monster.MonsterType.DRAGON, mockMonster.getType());
        
        // Test toString contains correct information
        String monsterString = mockMonster.toString();
        assertTrue(monsterString.contains("TestMonster"));
        assertTrue(monsterString.contains("dragon"));
        assertTrue(monsterString.contains("300"));
        assertTrue(monsterString.contains("I am friendly"));
    }

    @Test
    void testMonsterSpecialAbilityWithMockito() {
        // Create a spy instead of a mock to track the real method call
        Monster spyMonster = spy(new MockMonsterForTesting("TestMonster", Monster.MonsterType.MUTANT, true, 500));
        
        // Call specialAbility
        spyMonster.specialAbility();
        
        // Verify specialAbility was called exactly once
        verify(spyMonster, times(1)).specialAbility();
    }

    @Test
    void testMonsterDeepCloneWithMockito() throws CloneNotSupportedException {
        // Create a mock Monster with specific behavior
        Monster mockMonster = mock(Monster.class, withSettings()
            .useConstructor("TestMonster", Monster.MonsterType.ARTIFICIAL, true, 400)
            .defaultAnswer(CALLS_REAL_METHODS));
        
        // Perform cloning
        Monster clonedMonster = mockMonster.copy();
        
        // Verify the clone has correct properties
        assertEquals(mockMonster.getName(), clonedMonster.getName());
        assertEquals(mockMonster.getType(), clonedMonster.getType());
        
        // Verify it's a deep clone
        clonedMonster.setName("ModifiedClone");
        assertNotEquals(mockMonster.getName(), clonedMonster.getName());
    }
    
    // Helper class for testing
    private static class MockMonsterForTesting extends Monster {
        public MockMonsterForTesting(String name, MonsterType type, boolean isHostile, int powerLevel) {
            super(name, type, isHostile, powerLevel);
        }
        
        @Override
        public void specialAbility() {
            System.out.println("Test special ability");
        }
    }
}
