package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TestMagneticSwitch {

private DigitalChannel magneticSwitch;

public void init(HardwareMap hwMap){
    magneticSwitch = hwMap.get(DigitalChannel.class,"magnetic_switch");
    magneticSwitch.setMode(DigitalChannel.Mode.INPUT);
    }

public boolean isMagneticSwitchClosed(){
    return !magneticSwitch.getState();
}

}
