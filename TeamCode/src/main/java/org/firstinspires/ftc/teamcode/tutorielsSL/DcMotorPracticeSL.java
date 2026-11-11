package org.firstinspires.ftc.teamcode.tutorielsSL;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mechanismsSL.DcMotorClassSL;

//@Disabled
@TeleOp
public class DcMotorPracticeSL extends OpMode {

    DcMotorClassSL bench = new DcMotorClassSL();

    @Override
    public void init() { bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        double motorSpeed = gamepad1.left_stick_y;
        bench.setMotorSpeed(motorSpeed);

        if(gamepad1.a){
            bench.setMotorZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else {
            bench.setMotorZeroPowerBehaviour((DcMotor.ZeroPowerBehavior.FLOAT));
        }

        telemetry.addData("Motor Revolutions",bench.getMotorRevs());
        telemetry.addData("Motor Zero Behaviour", bench.getMotorZeroPowerBehaviour());
    }
    
}
