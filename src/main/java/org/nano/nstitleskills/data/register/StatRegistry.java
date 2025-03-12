package org.nano.nstitleskills.data.register;

import org.nano.nstitleskills.data.container.TitleCondition;
import org.nano.nstitleskills.data.container.TitleStat;

import java.util.HashMap;
import java.util.Map;

public class StatRegistry {
    public static StatRegistry instance;
    public static StatRegistry getInstance(){
        if (instance == null) {
            instance = new StatRegistry();
        }
        return instance;
    }

    private final Map<String, TitleCondition> conditions;
    private final Map<String, TitleStat> stats;

    public StatRegistry() {
        conditions = new HashMap<>();
        stats = new HashMap<>();
    }

    public TitleCondition getConditions(String unique) {
        if ( conditions.get(unique) == null ) conditions.put(unique,new TitleCondition());
        return conditions.get(unique);
    }

    public TitleStat getStats(String unique)  {
        if ( stats.get(unique) == null ) stats.put(unique,new TitleStat());
        return stats.get(unique);
    }

    public void setStats(String unique, TitleStat stats) {
        this.stats.put(unique, stats);
    }

    public void setConditions(String unique, TitleCondition conditions) {
        this.conditions.put(unique,conditions);
    }
}
