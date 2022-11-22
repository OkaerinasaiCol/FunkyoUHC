package co.com.okaeri.funkyuhc.util;

import org.jetbrains.annotations.NotNull;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendToBot {

    // "PLAYER\tDEATH\tPLAYER" "UHC START" "PLAYER" "DEATH" "FunkyoEnma"
    // "PLAYER\tREVIVE\tPLAYER"
    // send_to_bot("PLAYER","REVIVE",new String[] {"FunkyoEnma",});
    // send_to_bot("PLAYER","DEATH",new String[] {"FunkyoEnma",});
    // send_to_bot("UHC START"," ",new String[] {" ",});

    @SuppressWarnings("ReassignedVariable")
    public SendToBot(String command, String subcommand, String @NotNull [] args) {

        String str_command = "\tFunkyUHC v" + "0.1.2.4" + "\t" + command + "\t" + subcommand;

        for (String arg : args) {
            str_command = str_command.concat("\t" + arg);
        }
        System.out.println(str_command);

        try (Socket socket = new Socket("192.168.88.26", 4028)) {
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeUTF(str_command);
            dOut.flush();
            dOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
 *   El comando debe de iniciar con 	FunkyUHC v<Version del plugin>
 *
 *   /------------------------------------------------Command
 *   |      /-----------------------------------------Sub Command
 *   |      |      /----------------------------------Arg 1
 *   |      |      |       /--------------------------Arg 2
 *   |      |      |       |       /------------------Arg 3
 *   |      |      |       |       |        /---------Arg 4
 *   |      |      |       |       |        |
 *   |      |      |       |       |        |
 *"TEAMS\tCREATE\tNAME\tCapitan\tPLAYERS\tCOLOR"  Arg 1 nombre del equipo - Arg 2 Capitan - Arg 3 Players (separados por coma) - Arg 4 Team color
 *"TEAMS\tDELETE\tNAME\tCAPITAN\tPLAYERS"
 *"TEAMS\tADD_PLAYER\tTEAM\tPLAYER"
 *"TEAMS\tCOLOR\tTEAM\tCOLOR"
 *"TEAMS\tRENAME\tTEAM\tNEW_NAME"
 *"TEAMS\tREMOVE_PLAYER\tTEAM\tPLAYER"
 *"TEAMS\tDELETE\tTEAM"
 *"UHC START"
 *"PLAYER\tDEATH\tPLAYER"
 *"PLAYER\tREVIVE\tPLAYER"
 */