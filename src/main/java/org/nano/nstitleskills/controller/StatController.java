package org.nano.nstitleskills.controller;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.StatModifier;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.nano.nstitle.data.registy.RegistryCore;
import org.nano.nstitle.manager.TitleManager;
import org.nano.nstitleskills.NSTitleSkills;
import org.nano.nstitleskills.data.container.TitleStat;
import org.nano.nstitleskills.data.register.FileRegistry;
import org.nano.nstitleskills.data.register.StatRegistry;
import org.nano.nstitleskills.manager.ConditionManager;
import org.nano.nstitleskills.manager.StatManager;
import org.nano.nstitleskills.manager.StatUserManager;

import java.util.List;

public class StatController {
    private final StatRegistry registry = StatRegistry.getInstance();
    private final RegistryCore core = RegistryCore.getInstance();
    private final FileRegistry file = FileRegistry.getInstance();
    private final AuraSkillsApi api = AuraSkillsApi.get();
    /**
     *
     * @param unique < 칭호
     * @param rate < 증감률
     * @descripiton
     * +
     * @warning
     * - 설정하면 실시간으로 조건이 변경됨
     * refresh 해줄 것
     */
    public void setConditionByStats(String unique, Stats stats, int rate){
        ConditionManager manager = new ConditionManager();
        int value = manager.getConditionStat(unique,stats);
        if ( rate == 0 ) value = 0;
        registry.getConditions(unique)
                .getStatsCondition()
                .put(stats,value+rate);

        refresh(unique);
        file.getDbFile().save(unique);
    }
    /**
     *
     * @param unique < 칭호
     * @param rate < 증감률
     * @descripiton
     * +
     * @warning
     * - 설정하면 실시간으로 조건이 변경됨
     * refresh 해줄 것
     */
    public void setConditionBySkills(String unique, Skills skills, int rate){
        ConditionManager manager = new ConditionManager();
        int value = manager.getConditionLv(unique,skills);
        if ( rate == 0 ) value = 0;
        registry.getConditions(unique)
                .getSkillsCondition()
                .put(skills,value+rate);

        refresh(unique);
        file.getDbFile().save(unique);

    }
    /**
     *
     * @param unique < 칭호
     * @param rate < 증감률
     * @descripiton
     * +
     * @warning
     * - 설정하면 실시간으로 유저의 스텟 상태가 변해야 함.
     * refresh 해줄 것
     */
    public void addStatsPoint(String unique, Stats stats, int rate){
        StatManager manager = new StatManager();
        int value = manager.getAdditionalStat(unique,stats);
        registry.getStats(unique)
                .getAdditionalStats()
                .put(stats,value+rate);

        refresh(unique);
        file.getDbFile().save(unique);

    }

    /**
     * @param sender < 플레아어
     * @param unique < 칭호 고유 이름
     * @descripiton
     *  + 기존에 적용되던거 빼고 새롭게 적용 ( 업데이트 기능 추가 )
     *  + 현재 플레이어가 끼고 있는 모든 칭호를 가져와서 스텟 적용
     * @warning
     *  - 지금 끼고 있는게 조건이 업데이트가 되어서 착용이 불가능할 경우 끼고 있는 칭호에서 빼기
     *      @see org.nano.nstitle.api.EquipTitleEvent
     */
    public void apply(Player sender, String unique) {
        SkillsUser user = api.getUser(sender.getUniqueId());
        TitleStat statContainer = registry.getStats(unique);

        statContainer.getAdditionalStats()
                .forEach((stat, value1) -> {
                    user.addStatModifier(new StatModifier(unique + ".Title." + stat.name(), stat, value1));
                });
    }

    /**
     * @param sender < 새로고침 대상
     * @descripiton
     * + 자신이 착용중인 칭호를 새로고침함 ( 이 과정에서 조건이 맞는 지 재확인 )
     */
    public void refresh(Player sender) {
        Bukkit.getScheduler().runTaskLater(NSTitleSkills.getProvidingPlugin(NSTitleSkills.class),()->{
            TitleManager tm = new TitleManager();
            StatUserManager manager = new StatUserManager();
            initModifier(sender);

            List<String> equipList = core.getUserRegistry()
                    .gettInv(sender.getUniqueId())
                    .getEquipTitle()
                    .values()
                    .stream()
                    .toList();

            if (equipList.isEmpty()) return;

            equipList.stream()
                    .filter(tm::hasTitle)
                    .forEach(unique -> {
                        if (manager.checkCondition(sender, unique))
                            apply(sender, unique);
                        else
                            tm.equip(sender, tm.getTitle(unique));
                    });
        },10L);
    }
    /**
     * @param sender < 새로고침 대상
     * @param unique < 칭호
     * @descripiton
     * + 해당 칭호를 장착한 사람만 새로고침
     */
    public void refreshByUnique(Player sender,String unique) {
        Bukkit.getScheduler().runTaskLater(NSTitleSkills.getProvidingPlugin(NSTitleSkills.class),()->{
            TitleManager tm = new TitleManager();
            StatUserManager manager = new StatUserManager();

            List<String> equipList = core.getUserRegistry()
                    .gettInv(sender.getUniqueId())
                    .getEquipTitle()
                    .values()
                    .stream()
                    .toList();

            if (equipList.isEmpty()) return;
            if (!equipList.contains(unique)) return;

            initModifier(sender,unique);

            for ( String equip : equipList ){
                if ( equip.equals(unique) && tm.hasTitle(equip) ){
                    if ( manager.checkCondition(sender, unique) ){
                        apply(sender,unique);
                    }else{
                        tm.equip(sender,tm.getTitle(unique));
                    }
                }
            }

        },10L);
    }
    public void refresh(){
        Bukkit.getOnlinePlayers().forEach(this::refresh);
    }
    public void refresh(String unique){
        Bukkit.getOnlinePlayers().forEach(online->{
            refreshByUnique(online,unique);
        });
    }

    /**
     * @param sender < 초기화 할 플레이어
     * @descripiton 현재 적용된 모든 스텟을 제외함.
     */
    public void initModifier(Player sender){
        SkillsUser user = api.getUser(sender.getUniqueId());
        for ( String key : user.getStatModifiers().keySet()){
            if ( key.contains(".Title.")){
                user.removeStatModifier(key);
            }
        }
    }
    public void initModifier(Player sender,String unique){
        SkillsUser user = api.getUser(sender.getUniqueId());
        for ( String key : user.getStatModifiers().keySet()){
            if ( key.contains(unique+".Title.")){
                user.removeStatModifier(key);
            }
        }
    }

}
