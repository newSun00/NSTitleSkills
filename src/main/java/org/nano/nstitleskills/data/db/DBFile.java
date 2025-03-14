package org.nano.nstitleskills.data.db;

import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.Stats;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nano.nstitleskills.NSTitleSkills;
import org.nano.nstitleskills.data.container.TitleCondition;
import org.nano.nstitleskills.data.container.TitleStat;
import org.nano.nstitleskills.data.register.StatRegistry;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DBFile {
    private final StatRegistry statRegistry = StatRegistry.getInstance();
    private File file;
    private FileConfiguration configuration;

    private void setUp(String unique){
        File pluginFolder = Objects.requireNonNull(NSTitleSkills.getProvidingPlugin(NSTitleSkills.class)).getDataFolder();
        File folder = new File(pluginFolder, "data");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file = new File(folder, unique+".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void save(String unique){
        Bukkit.getScheduler().runTask(NSTitleSkills.getProvidingPlugin(NSTitleSkills.class),()->{
            setUp(unique);
            delete(unique);

            TitleCondition condition = statRegistry.getConditions(unique);
            TitleStat additional = statRegistry.getStats(unique);

            String path = "Condition-Skills";
            String finalPath2 = path;
            condition.getSkillsCondition().forEach((key, value) -> {
                String name = key.name();
                configuration.set(finalPath2 + "." + name, value);
            });
            path = "Condition-Stats";
            String finalPath = path;
            condition.getStatsCondition().forEach((key, value) -> {
                String name = key.name();
                configuration.set(finalPath + "." + name, value);
            });
            path = "Additional-Stats";
            String finalPath1 = path;
            additional.getAdditionalStats().forEach((key, value) -> {
                String name = key.name();
                configuration.set(finalPath1 + "." + name, value);
            });
            configuration.set(path+"."+"XP",additional.getAdditionalXP());

            saveFile();
        });
    }
    public void load(String unique) {
        Bukkit.getScheduler().runTask(NSTitleSkills.getProvidingPlugin(NSTitleSkills.class), () -> {
            setUp(unique);

            TitleCondition condition = statRegistry.getConditions(unique);
            TitleStat additional = statRegistry.getStats(unique);

            // Condition-Skills 불러오기
            String path = "Condition-Skills";
            if (configuration.contains(path)) {
                String finalPath2 = path;
                Objects.requireNonNull(configuration.getConfigurationSection(path)).getKeys(false).forEach(key -> {
                    Skills skill = Skills.valueOf(key);
                    int value = configuration.getInt(finalPath2 + "." + key);
                    condition.getSkillsCondition().put(skill, value);
                });
            }

            // Condition-Stats 불러오기
            path = "Condition-Stats";
            if (configuration.contains(path)) {
                String finalPath1 = path;
                Objects.requireNonNull(configuration.getConfigurationSection(path)).getKeys(false).forEach(key -> {
                    Stats stat = Stats.valueOf(key);
                    int value = configuration.getInt(finalPath1 + "." + key);
                    condition.getStatsCondition().put(stat, value);
                });
            }

            // Additional-Stats 불러오기
            path = "Additional-Stats";
            if (configuration.contains(path)) {
                String finalPath = path;
                Objects.requireNonNull(configuration.getConfigurationSection(path)).getKeys(false).forEach(key -> {
                    if ( key.equals("XP")){
                        double value = configuration.getDouble(finalPath+"."+key);
                        additional.setAdditionalXP(value);
                    }else {
                        Stats stat = Stats.valueOf(key);
                        int value = configuration.getInt(finalPath + "." + key);
                        additional.getAdditionalStats().put(stat, value);
                    }
                });
            }

            statRegistry.setConditions(unique,condition);
            statRegistry.setStats(unique,additional);

        });
    }

    public void loadAll(){
        Bukkit.getScheduler().runTask(NSTitleSkills.getProvidingPlugin(NSTitleSkills.class),()-> {

            File pluginFolder = NSTitleSkills.getProvidingPlugin(NSTitleSkills.class).getDataFolder();
            File folder = new File(pluginFolder, "data");
            if (!folder.exists()) {
                return;
            }
            File[] files = folder.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".yml")) {
                    String fileNameWithoutExtension = file.getName().replace(".yml", "");
                    load(fileNameWithoutExtension);
                }
            }
        });
    }
    private void saveFile(){
        try {
            configuration.save(file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private void delete(String unique){
        setUp(unique);
        configuration.getKeys(false)
                .forEach(string -> configuration.set(string,null) );
    }

}
