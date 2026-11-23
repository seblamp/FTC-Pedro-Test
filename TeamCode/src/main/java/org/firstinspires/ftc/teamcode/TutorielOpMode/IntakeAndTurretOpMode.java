package org.firstinspires.ftc.teamcode.TutorielOpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.IntakeAndTurret;


//@Disabled
@TeleOp
public class IntakeAndTurretOpMode extends OpMode {

    IntakeAndTurret test = new IntakeAndTurret();

    @Override
    public void init() {
        test.init(hardwareMap);
    }

    @Override
    public void loop() {

        // Intake in
        if (gamepad1.aWasPressed()) {
            if (test.getIntakePower() == 0.5) {
                test.setIntakePower(0.0);
            } else {
                test.setIntakePower(0.5);
            }
        }

        // Intake out
        if (gamepad1.bWasPressed()) {
            if (test.getIntakePower() == -0.5) {
                test.setIntakePower(0.0);
            } else {
                test.setIntakePower(-0.5);
            }
        }

        test.setTurretPower(gamepad1.right_stick_x);

        test.updateTurretEncoder();

        telemetry.addData("Power intake", test.getIntakePower());
        telemetry.addData("Switch magnétique fermée", test.isMagneticSwitchClosed());
        telemetry.addData("Turret home ?", test.isTurretHomed());
        telemetry.addData("Ticks turret", test.getTurretTicks());
        telemetry.addData("Angle turret", test.getTurretAngle());
        telemetry.update();
    }
}
