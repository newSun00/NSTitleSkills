package org.nano.nstitleskills.event;

import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.nano.nstitle.data.registy.server.dto.Title;
import org.nano.nstitle.util.helper.namespace.KeyHelper;
import org.nano.nstitleskills.controller.StatController;
import org.nano.nstitleskills.etc.StatsGui;

public class GuiClickEvent implements Listener {
    private final StatController controller = new StatController();
    @EventHandler
    public void click(InventoryClickEvent e){
        if ( e.getInventory().getHolder() instanceof StatsGui gui ){
            e.setCancelled(true);
            if ( e.getCurrentItem() == null ) return;

            Player p = (Player) e.getWhoClicked();
            int slot = e.getRawSlot();
            ItemStack item = e.getCurrentItem();
            Title title = gui.getTitle();

            KeyHelper helper = new KeyHelper();


            if ( slot >= 9 && slot <= 23 ){
                for ( Skills sKills : Skills.values()){
                    if (helper.hasKey(item, sKills.name())){
                        switch (e.getClick()){
                            case LEFT -> controller.setConditionBySkills(title.getUnique(), sKills, 1);
                            case RIGHT -> controller.setConditionBySkills(title.getUnique(), sKills, -1);
                            case SHIFT_LEFT -> controller.setConditionBySkills(title.getUnique(), sKills, 10);
                            case SHIFT_RIGHT -> controller.setConditionBySkills(title.getUnique(), sKills, -10);
                            case MIDDLE -> controller.setConditionBySkills(title.getUnique(),sKills,0);
                        }
                    }
                }
            }
            else if ( slot == 40 ){
                if (helper.hasKey(item, "XP")){
                    switch (e.getClick()){
                        case LEFT -> controller.addXPPoint(title.getUnique(), 0.1);
                        case RIGHT -> controller.addXPPoint(title.getUnique(), -0.1);
                        case SHIFT_LEFT -> controller.addXPPoint(title.getUnique(), 1.0);
                        case SHIFT_RIGHT -> controller.addXPPoint(title.getUnique(), -1.0);
                        case MIDDLE -> controller.addXPPoint(title.getUnique(),0);
                    }
                }
            }
            else if( slot >= 27 && slot <= 34 ){
                for ( Stats stats : Stats.values()){
                    if (helper.hasKey(item, stats.name())){
                        switch (e.getClick()){
                            case LEFT -> controller.setConditionByStats(title.getUnique(), stats, 1);
                            case RIGHT -> controller.setConditionByStats(title.getUnique(), stats, -1);
                            case SHIFT_LEFT -> controller.setConditionByStats(title.getUnique(), stats, 10);
                            case SHIFT_RIGHT -> controller.setConditionByStats(title.getUnique(), stats, -10);
                            case MIDDLE -> controller.setConditionByStats(title.getUnique(), stats, 0);
                        }
                    }
                }
            }else if( slot >= 45 ){
                for ( Stats stats : Stats.values()){
                    if (helper.hasKey(item, stats.name())){
                        switch (e.getClick()){
                            case LEFT -> controller.addStatsPoint(title.getUnique(), stats, 1);
                            case RIGHT -> controller.addStatsPoint(title.getUnique(), stats, -1);
                            case SHIFT_LEFT -> controller.addStatsPoint(title.getUnique(), stats, 10);
                            case SHIFT_RIGHT -> controller.addStatsPoint(title.getUnique(), stats, -10);
                            case MIDDLE -> controller.addStatsPoint(title.getUnique(), stats, 0);
                        }
                    }
                }
            }
            new StatsGui(title).open(p);
        }
    }
}
