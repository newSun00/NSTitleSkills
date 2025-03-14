package org.nano.nstitleskills.manager;

import dev.aurelium.auraskills.api.stat.Stats;
import org.nano.nstitleskills.data.register.StatRegistry;

public class StatManager {
    private final StatRegistry registry = StatRegistry.getInstance();

    public int getAdditionalStat(String unique, Stats stats){
        return registry.getStats(unique)
                .getAdditionalStats()
                .getOrDefault(stats,0);
    }

    public double getAdditionalXP(String unique) {
        return registry.getStats(unique)
                .getAdditionalXP();

    }
}
