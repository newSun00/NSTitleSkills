package org.nano.nstitleskills.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nano.nstitle.manager.TitleManager;
import org.nano.nstitleskills.etc.StatsGui;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if ( commandSender instanceof Player p && p.isOp()){
            if ( strings.length == 1){
                TitleManager tm = new TitleManager();
                if ( tm.hasTitle(strings[0])){
                    new StatsGui(tm.getTitle(strings[0])).open(p);
                }else p.sendMessage(" 없는 칭호 ");
            }
        }
        return false;
    }
}
