package org.nano.nstitleskills.event;

import dev.aurelium.auraskills.api.event.skill.XpGainEvent;
import dev.aurelium.auraskills.api.stat.Stat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.nano.nstitle.data.registy.RegistryCore;
import org.nano.nstitleskills.data.register.StatRegistry;

import java.util.List;

public class XpGrindEvent implements Listener {
    private final RegistryCore core = RegistryCore.getInstance();
    private final StatRegistry stat = StatRegistry.getInstance();
    @EventHandler
    public void xpGrind(XpGainEvent e){
        Player p = e.getPlayer();

        List<String> equip = core.getUserRegistry()
                .gettInv(p.getUniqueId())
                .getEquipTitle()
                .values()
                .stream()
                .toList();

        for ( String unique : equip ){
            if ( stat.getStats(unique).getAdditionalXP() != 0 ){
                double v = stat.getStats(unique).getAdditionalXP();

                double basic = e.getAmount() * ( 1+v /100 );
                e.setAmount(basic);
            }

        }


    }
}
