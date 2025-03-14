package org.nano.nstitleskills;

import org.bukkit.plugin.java.JavaPlugin;
import org.nano.nstitleskills.command.Command;
import org.nano.nstitleskills.command.Completer;
import org.nano.nstitleskills.data.register.FileRegistry;
import org.nano.nstitleskills.event.GuiClickEvent;
import org.nano.nstitleskills.event.TitleEvent;
import org.nano.nstitleskills.event.XpGrindEvent;

import java.util.Objects;

public final class NSTitleSkills extends JavaPlugin {
    @Override
    public void onEnable() {
        new FileRegistry();
        Objects.requireNonNull(getCommand("tts")).setExecutor(new Command());
        Objects.requireNonNull(getCommand("tts")).setTabCompleter(new Completer());
        getServer().getPluginManager().registerEvents(new GuiClickEvent(),this);
        getServer().getPluginManager().registerEvents(new TitleEvent(),this);
        getServer().getPluginManager().registerEvents(new XpGrindEvent(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
