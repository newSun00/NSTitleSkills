package org.nano.nstitleskills.manager;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.entity.Player;
import org.nano.nstitleskills.data.container.TitleCondition;
import org.nano.nstitleskills.data.register.StatRegistry;

import java.util.Map;

public class StatUserManager {
    private final StatRegistry registry = StatRegistry.getInstance();
    private final AuraSkillsApi api = AuraSkillsApi.get();
    /**
     * @param target < 대상
     * @param unique < 대상 칭호
     * @return <
     * 조건이 맞으면 true
     * 안맞으면 false
     */
    public boolean checkCondition(Player target, String unique){
        TitleCondition condition = registry.getConditions(unique);
        Map<Skills,Integer> skillsCondition = condition.getSkillsCondition();
        Map<Stats, Integer> statsCondition = condition.getStatsCondition();

        SkillsUser user = api.getUser(target.getUniqueId());

        return skillsCondition.entrySet()
                .stream()
                .allMatch(entry -> user.getSkillLevel(entry.getKey()) >= entry.getValue()) &&
                statsCondition.entrySet()
                        .stream()
                        .allMatch(entry-> user.getStatLevel(entry.getKey()) >= entry.getValue());
    }

}
