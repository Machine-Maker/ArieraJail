package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

public class LogCmd extends SubCommand {

    public void onCommand(Player p, String[] args) {

    }

    public String name() {
        return "log";
    }

    public String info() {
        return "Gives a book with a log of players and their jail times";
    }

    public String[] aliases() {
        return new String[] { "l" };
    }

    public String permission() {
        return "arierajail.log";
    }

    public int argsReq() {
        return 0;
    }

    public String format() {
        return "/aj log";
    }
}
