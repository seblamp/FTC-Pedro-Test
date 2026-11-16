package org.firstinspires.ftc.teamcode.tutorielsSL;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


//@Disabled

@TeleOp
public class DcMotorAndServoOpModeSL extends OpMode {

    DcMotorAndServoSL test = new DcMotorAndServoSL();

    @Override
    public void init() {
        test.init(hardwareMap);
    }

    @Override
    public void loop() {

        if(gamepad1.left_bumper){
            test.setMotorSpeed(0.5);
        }
        else if (gamepad1.right_bumper) {
            test.setMotorSpeed(-0.5);
        }
        else {
            double motorSpeed = gamepad1.left_stick_y;
            test.setMotorSpeed(motorSpeed);
        }

        if (gamepad1.a){
            test.setServoPosition(0.2);
        }
        else if (gamepad1.b) {
            test.setServoPosition(0.8);
        }
        else {
            test.setServoPosition(0.0);
        }

        if (gamepad1.x){
            test.setServoRotation(0.5);
        }
        else if (gamepad1.y) {
            test.setServoRotation(-0.5);
        }
        else {
            test.setServoRotation(0.0);
        }

        telemetry.addData("Position moteur",test.motorPosition());
        telemetry.addData("Position servo",test.servoPosition());
        telemetry.addData("Puissance servo continu",test.rotationServoPower());
        telemetry.update();
    }
}
