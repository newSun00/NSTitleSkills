package org.nano.nstitleskills.data.container;

import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.Stats;

import java.util.HashMap;
import java.util.Map;

public class TitleCondition {
    /**
    * @descripiton 조건
     * Skills 조건 Map<Skills,Integer>
     * Stats 조건 Map<Stats,Integer>
     */
    private final Map<Skills, Integer> skillsCondition;
    private final Map<Stats, Integer> statsCondition;

    public TitleCondition() {
        this.skillsCondition = new HashMap<>();
        this.statsCondition = new HashMap<>();
    }

    public Map<Skills, Integer> getSkillsCondition() {
        return skillsCondition;
    }

    public Map<Stats, Integer> getStatsCondition() {
        return statsCondition;
    }
}
