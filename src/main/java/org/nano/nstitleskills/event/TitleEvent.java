package org.nano.nstitleskills.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.nano.nstitle.api.EquipTitleEvent;
import org.nano.nstitle.api.UnEquipTitleEvent;
import org.nano.nstitle.data.registy.server.dto.Title;
import org.nano.nstitle.util.message.Message;
import org.nano.nstitleskills.controller.StatController;
import org.nano.nstitleskills.manager.StatUserManager;

public class TitleEvent implements Listener {
    private final StatController controller = new StatController();
    @EventHandler
    public void equipTitle(EquipTitleEvent e){
        StatUserManager statUserManager = new StatUserManager();
        Title title = e.getTitle();
        Player p = e.getPlayer();
        if ( statUserManager.checkCondition(p,title.getUnique()) ){
            controller.refresh(p);
            Message.message(p,"&l&b! ] &f"+title.getDisplay()+" &f칭호를 착용했습니다!");
        }else {
            e.setCancelled(true);
            Message.message(p,"&l&c! ] &f조건에 맞지 않아 칭호를 착용할 수 없습니다!");
        }
    }
    @EventHandler
    public void unEquipTitle(UnEquipTitleEvent e) {
        controller.refresh(e.getPlayer());
    }
    @EventHandler
    public void join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        controller.refresh(p);
    }
}
