package org.firstinspires.ftc.teamcode.tutorielsSL;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@Disabled
@TeleOp
public class DcMotorAndServoOpMode extends OpMode {
    DcMotorAndServoClassSL test = new DcMotorAndServoClassSL();

    @Override
    public void init() {
        test.init(hardwareMap);
    }

    @Override
    public void loop() {
        double motorSpeed = gamepad1.left_stick_y;
        test.setMotorSpeed(motorSpeed);

        if (gamepad1.a){
            test.setServoPos(0.5);
        }
        else if (gamepad1.b) {
            test.setServoPos(1.0);
        }
        else {
            test.setServoPos(0.0);
        }

        if (gamepad1.x) {
            test.setServoRot(0.5);
        }
        else if (gamepad1.y) {
            test.setServoRot(-0.5);
        }
        else {
            test.setServoRot(0.0);
        }

        telemetry.addData("Position moteur",test.motorPosition());
        telemetry.addData("Position servo", test.servoPosition());
        telemetry.addData("Puissance servo continu", test.rotationServoRotPower());
        telemetry.update();

    }

}
