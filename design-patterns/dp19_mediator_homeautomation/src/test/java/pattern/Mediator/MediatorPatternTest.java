package pattern.Mediator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.Devices.Device;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class MediatorPatternTest {

    @Mock
    private Mediator mockMediator;

    private TestDevice testDevice;

    // A concrete test implementation of Device for testing purposes
    private static class TestDevice extends Device {
        public TestDevice(Mediator mediator) {
            super(mediator);
        }

        public void sendEvent(String event) {
            mediator.notify(this, event);
        }
    }

    @BeforeEach
    void setUp() {
        testDevice = new TestDevice(mockMediator);
    }

    @Test
    void testDeviceSendsEventToMediator() {
        // Test that device can send events to mediator
        String testEvent = "testEvent";
        testDevice.sendEvent(testEvent);
        
        // Verify that mediator's notify method was called with correct parameters
        verify(mockMediator).notify(testDevice, testEvent);
    }

    @Test
    void testMultipleDeviceEvents() {
        // Test multiple event notifications
        testDevice.sendEvent("event1");
        testDevice.sendEvent("event2");

        // Verify that mediator received both events in order
        verify(mockMediator).notify(testDevice, "event1");
        verify(mockMediator).notify(testDevice, "event2");
        verifyNoMoreInteractions(mockMediator);
    }    

    @Test
    void testMediatorToDeviceCommunication() {
        // Create a mock mediator
        Mediator mockMediator = mock(Mediator.class);

        // Create two test devices
        TestDevice device1 = new TestDevice(mockMediator);
        TestDevice device2 = new TestDevice(mockMediator);

        // Test that first device can trigger the mediator
        device1.sendEvent("event1");

        // Verify that mediator received the event from device1
        verify(mockMediator).notify(device1, "event1");

        // Test chained communication through mockMediator
        // When device1 sends "chainedEvent", mediator should notify device2
        doAnswer(invocation -> {
            Device sender = invocation.getArgument(0);
            String event = invocation.getArgument(1);
            if (sender == device1 && "chainedEvent".equals(event)) {
                device2.sendEvent("response");
            }
            return null;
        }).when(mockMediator).notify(any(Device.class), eq("chainedEvent"));

        // Trigger the chain of communication
        device1.sendEvent("chainedEvent");

        // Verify that both notifications happened in order
        var inOrder = inOrder(mockMediator);
        inOrder.verify(mockMediator).notify(device1, "chainedEvent");
        inOrder.verify(mockMediator).notify(device2, "response");
    }

    @Test
    void testDeviceInitialization() {
        // Mock a new mediator
        Mediator anotherMockMediator = mock(Mediator.class);
        
        // Create a new device with the mock mediator
        TestDevice newDevice = new TestDevice(anotherMockMediator);
        
        // Send an event to verify the device was properly initialized with the mediator
        newDevice.sendEvent("initTest");
        
        // Verify the correct mediator was used
        verify(anotherMockMediator).notify(newDevice, "initTest");
    }
}
