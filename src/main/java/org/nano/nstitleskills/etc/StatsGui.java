package org.nano.nstitleskills.etc;

import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.Stats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.nano.nstitle.data.registy.server.dto.Title;
import org.nano.nstitle.util.builder.BookBuilder;
import org.nano.nstitle.util.builder.ItemBuilder;
import org.nano.nstitle.util.factory.ColorFactory;
import org.nano.nstitleskills.NSTitleSkills;
import org.nano.nstitleskills.manager.ConditionManager;
import org.nano.nstitleskills.manager.StatManager;
import org.nano.nstitleskills.util.item.ItemManger;

import java.util.List;
import java.util.Locale;

public class StatsGui implements InventoryHolder {

    private final Inventory inventory;
    private final Title title;

    public StatsGui(Title title) {
        this.title = title;
        this.inventory = Bukkit.createInventory(this,54, ColorFactory.factory.color("&0[ "+title.getUnique()+" ] 스텟 설정 변경").doneC());
    }
    private void setup(){
        Bukkit.getScheduler().runTask(NSTitleSkills.getProvidingPlugin(NSTitleSkills.class),()->{
            ItemManger manger = new ItemManger();
            ConditionManager cm = new ConditionManager();
            StatManager sm = new StatManager();


            ItemStack mold = ItemBuilder.builder
                    .create(Material.BLACK_STAINED_GLASS_PANE)
                    .model(1)
                    .display("")
                    .build();
            List.of(0, 1, 2, 3, 5, 6, 7, 8,
                            36,37,38,39,40,41,42,43,44)
                    .forEach(index-> inventory.setItem(index,mold));


            List<String> lore = manger.getConditionsLore(title.getLore(), title.getUnique() );
            ItemStack info = BookBuilder.builder
                    .create()
                    .display(title.getDisplay())
                    .lore(lore)
                    .build(1);
            inventory.setItem(4,info);

            int i = 9;
            for (Skills skills : Skills.values()){
                int lv = cm.getConditionLv(title.getUnique(),skills);
                ItemStack sk = BookBuilder.builder
                        .create()
                        .display(AuraSkillsColor.skillsColor(skills))
                        .lore(List.of(
                                skills.getDescription(Locale.KOREAN),
                                "",
                                "&a조건 &7(LV)&f: "+lv
                        ))
                        .addDataContainer(skills.name(),""+lv)
                        .model(1)
                        .build(1);
                inventory.setItem(i,sk);
                i++;
            }

            i = 27;
            for (Stats stats : Stats.values()){
                int lv = cm.getConditionStat(title.getUnique(),stats);
                ItemStack sk = BookBuilder.builder
                        .create()
                        .display(AuraSkillsColor.statsColor(stats))
                        .lore(List.of(
                                stats.getDescription(Locale.KOREAN),
                                "",
                                "&a조건 &7(LV)&f: "+lv
                        ))
                        .addDataContainer(stats.name(),""+lv)
                        .model(1)
                        .build(1);
                inventory.setItem(i,sk);
                i++;
            }

            i = 45;
            for (Stats stats : Stats.values()){
                int lv = sm.getAdditionalStat(title.getUnique(),stats);
                ItemStack sk = BookBuilder.builder
                        .create()
                        .display(AuraSkillsColor.statsColor(stats))
                        .lore(List.of(
                                stats.getDescription(Locale.KOREAN),
                                "",
                                "&a추가 &7(LV)&f: "+lv
                        ))
                        .addDataContainer(stats.name(),""+lv)
                        .model(1)
                        .build(1);
                inventory.setItem(i,sk);
                i++;
            }

            i = 40; // 칭호 추가 경험치 ++
                double xp = sm.getAdditionalXP(title.getUnique());

                ItemStack sk = BookBuilder.builder
                        .create()
                        .display("&a추가 경험치")
                        .lore(List.of(
                                "",
                                "&a추가 &7(XP)&f: "+xp + "%"
                        ))
                        .addDataContainer("XP",""+xp)
                        .model(1)
                        .build(1);
                inventory.setItem(i,sk);
        });
    }
    public void open(Player p){
        setup();
        p.openInventory(inventory);
    }
    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public Title getTitle() {
        return title;
    }
}
