package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import pattern.Devices.Light.DimmerLightOffCommand;
import pattern.Devices.Light.DimmerLightOnCommand;
import pattern.Devices.Light.Light;
import pattern.Devices.Light.LightOnCommand;
import pattern.Devices.Light.LightOffCommand;
import pattern.Devices.CeilingFan.CeilingFan;
import pattern.Devices.CeilingFan.CeilingFanHighCommand;
import pattern.Devices.CeilingFan.CeilingFanMediumCommand;
import pattern.Devices.CeilingFan.CeilingFanLowCommand;
import pattern.Devices.CeilingFan.CeilingFanOffCommand;

// Mock Command implementation for testing
class MockCommand implements Command {
    boolean executed = false;
    boolean undone = false;

    @Override
    public void execute() {
        executed = true;
    }

    @Override
    public void undo() {
        undone = true;
    }
}

class RemoteControlTest {

    private RemoteControl remoteControl;
    private MockCommand onCommand;
    private MockCommand offCommand;

    @BeforeEach
    void setUp() {
        remoteControl = new RemoteControl();
        onCommand = new MockCommand();
        offCommand = new MockCommand();
        remoteControl.setCommand(0, onCommand, offCommand);
    }

    @Test
    void testOnButtonWasPushedExecutesOnCommand() {
        remoteControl.onButtonWasPushed(0);
        assertTrue(onCommand.executed, "On command should be executed");
    }

    @Test
    void testOffButtonWasPushedExecutesOffCommand() {
        remoteControl.offButtonWasPushed(0);
        assertTrue(offCommand.executed, "Off command should be executed");
    }

    @Test
    void testUndoButtonWasPushedUndoesLastCommand() {
        remoteControl.onButtonWasPushed(0);
        remoteControl.undoButtonWasPushed();
        assertTrue(onCommand.undone, "Undo should call undo on last executed command");
    }

    @Test
    void testSetCommandOverridesPreviousCommand() {
        MockCommand newOnCommand = new MockCommand();
        MockCommand newOffCommand = new MockCommand();
        remoteControl.setCommand(0, newOnCommand, newOffCommand);

        remoteControl.onButtonWasPushed(0);
        assertTrue(newOnCommand.executed, "New on command should be executed");
        assertFalse(onCommand.executed, "Old on command should not be executed");
    }

    @Test
    void testToStringContainsCommandClassNames() {
        String output = remoteControl.toString();
        assertTrue(output.contains("MockCommand"), "toString should contain command class names");
    }

    // --- Tests using real Light and CeilingFan command classes ---

    @Test
    void testLightOnOffCommands() {
        Light light = new Light("Living Room");
        LightOnCommand lightOn = new LightOnCommand(light);
        LightOffCommand lightOff = new LightOffCommand(light);
        DimmerLightOnCommand dimmerLightOn = new DimmerLightOnCommand(light);
        DimmerLightOffCommand dimmerLightOff = new DimmerLightOffCommand(light);

        remoteControl.setCommand(1, lightOn, lightOff);
        remoteControl.setCommand(2, dimmerLightOn, dimmerLightOff);

        remoteControl.onButtonWasPushed(1);
        assertTrue(light.isOn(), "Light should be on after onButtonWasPushed");
        assertEquals(100, light.getLevel(), "Light level should be 100 after on");

        remoteControl.onButtonWasPushed(2);
        assertEquals(75, light.getLevel(), "Light level should be 75 after dimmer on");
        remoteControl.offButtonWasPushed(2);
        assertEquals(0, light.getLevel(), "Light level should be 0 after dimmer off");
        remoteControl.undoButtonWasPushed();
        assertTrue(light.isOn(), "Light should be on after undoing dimmer command");

        remoteControl.offButtonWasPushed(1);
        assertFalse(light.isOn(), "Light should be off after offButtonWasPushed");
        assertEquals(0, light.getLevel(), "Light level should be 0 after off");        

        remoteControl.undoButtonWasPushed();
        assertTrue(light.isOn(), "Light should be on after undoing off");
    }

    @Test
    void testCeilingFanHighMediumLowOffCommands() {
        CeilingFan fan = new CeilingFan("Bedroom");
        CeilingFanHighCommand fanHigh = new CeilingFanHighCommand(fan);
        CeilingFanMediumCommand fanMedium = new CeilingFanMediumCommand(fan);
        CeilingFanLowCommand fanLow = new CeilingFanLowCommand(fan);
        CeilingFanOffCommand fanOff = new CeilingFanOffCommand(fan);

        // Test HIGH
        remoteControl.setCommand(2, fanHigh, fanOff);
        remoteControl.onButtonWasPushed(2);
        assertEquals(CeilingFan.HIGH, fan.getSpeed(), "Fan should be on high after onButtonWasPushed");

        // Test OFF
        remoteControl.offButtonWasPushed(2);
        assertEquals(CeilingFan.OFF, fan.getSpeed(), "Fan should be off after offButtonWasPushed");

        // Undo OFF (should restore HIGH)
        remoteControl.undoButtonWasPushed();
        assertEquals(CeilingFan.HIGH, fan.getSpeed(), "Fan should be on high after undoing off");

        // Test MEDIUM
        remoteControl.setCommand(3, fanMedium, fanOff);
        remoteControl.onButtonWasPushed(3);
        assertEquals(CeilingFan.MEDIUM, fan.getSpeed(), "Fan should be on medium after onButtonWasPushed");

        // Undo MEDIUM (should restore previous speed, which was HIGH)
        remoteControl.undoButtonWasPushed();
        assertEquals(CeilingFan.HIGH, fan.getSpeed(), "Fan should be on high after undoing medium");

        // Test LOW
        remoteControl.setCommand(4, fanLow, fanOff);
        remoteControl.onButtonWasPushed(4);
        assertEquals(CeilingFan.LOW, fan.getSpeed(), "Fan should be on low after onButtonWasPushed");

        // Undo LOW (should restore previous speed, which was HIGH)
        remoteControl.undoButtonWasPushed();
        assertEquals(CeilingFan.HIGH, fan.getSpeed(), "Fan should be on high after undoing low");
    }
}