package org.nano.nstitleskills.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.nano.nstitle.data.registy.RegistryCore;
import org.nano.nstitle.data.registy.server.dto.Title;

import java.util.List;
import java.util.stream.Collectors;

public class Completer implements TabCompleter {
    //</tts <uniqueName>
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if ( commandSender instanceof Player player && player.isOp() ) {
            if (args.length == 1) {
                return getAllTitles().stream()
                        .filter(title -> title.toLowerCase().startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        return List.of();
    }
    public List<String> getAllTitles() {
        return RegistryCore.getInstance().getTitleRegistry()
                .getTitles()
                .stream()
                .map(Title::getUnique)
                .collect(Collectors.toList());
    }

}
