/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

<<<<<<< HEAD:src/main/java/frc/robot/commands/FloopCloseCommand.java
public class FloopCloseCommand extends InstantCommand {
    public FloopCloseCommand() {
=======
public class FloopStopCommand extends Command {
    public FloopStopCommand() {
>>>>>>> master:src/main/java/frc/robot/commands/FloopStopCommand.java
        requires(Robot.floop);
    }

    @Override
    protected void initialize() {
<<<<<<< HEAD:src/main/java/frc/robot/commands/FloopCloseCommand.java
        Robot.floop.close();
=======
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.floop.stop();
>>>>>>> master:src/main/java/frc/robot/commands/FloopStopCommand.java
    }
}
