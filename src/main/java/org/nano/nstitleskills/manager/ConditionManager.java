package org.nano.nstitleskills.manager;

import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.Stats;
import org.nano.nstitleskills.data.register.StatRegistry;

public class ConditionManager {
    private final StatRegistry statRegistry = StatRegistry.getInstance();

    public int getConditionLv(String unique, Skills skills) {
        return statRegistry.getConditions(unique)
                .getSkillsCondition()
                .getOrDefault(skills, 0);
    }

    public int getConditionStat(String unique, Stats stats) {
        return statRegistry.getConditions(unique)
                .getStatsCondition()
                .getOrDefault(stats, 0);
    }

}
