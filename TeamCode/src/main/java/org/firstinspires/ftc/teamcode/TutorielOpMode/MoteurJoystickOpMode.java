package org.firstinspires.ftc.teamcode.TutorielOpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MoteurJoystickOpMode extends OpMode {

    DcMotor motor;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motor");
    }
    //Le nom doit être identique à la configuration du DriverHub
    @Override

    public void loop() {

        double speed = gamepad1.left_stick_y;
        motor.setPower(speed);

        /*double speed = gamepad1.right_stick_y;
        motor.setPower(speed);*/
    }
}









