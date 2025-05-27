package pattern.Remotes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import pattern.TVs.TV;
import pattern.TVFactory;

@ExtendWith(MockitoExtension.class)
public class RemoteControlTest {
    @Mock
    TV mockTV;

    static class MockRemoteControl extends RemoteControl {
        public MockRemoteControl(TVFactory factory) {
            super(factory);
        }
        public void setTVInstance(TV tv) {
            this.tv = tv;
        }
    }

    MockRemoteControl remote;

    @BeforeEach
    void setUp() {
        remote = new MockRemoteControl(null);
        remote.setTVInstance(mockTV);
    }

    @Test
    void testOnDelegatesToTV() {
        remote.on();
        verify(mockTV, times(1)).on();
    }

    @Test
    void testOffDelegatesToTV() {
        remote.off();
        verify(mockTV, times(1)).off();
    }

    @Test
    void testSetChannelDelegatesToTV() {
        remote.setChannel(5);
        verify(mockTV, times(1)).tuneChannel(5);
    }

    @Test
    void testGetChannelDelegatesToTV() {
        when(mockTV.getChannel()).thenReturn(7);
        assertEquals(7, remote.getChannel(), "Remote should get current channel from TV");
        verify(mockTV, times(1)).getChannel();
    }
}
