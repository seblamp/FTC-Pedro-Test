package org.firstinspires.ftc.teamcode.TutorielOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.TestMagneticSwitch;

import kotlin.properties.ObservableProperty;

@Disabled
@TeleOp
public class MagneticSwitchOpMode extends OpMode {

    TestMagneticSwitch magnetic = new TestMagneticSwitch();

    @Override
    public void init() {
        magnetic.init(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("Magnetic Switch Closed ?",magnetic.isMagneticSwitchClosed());
    }
}
