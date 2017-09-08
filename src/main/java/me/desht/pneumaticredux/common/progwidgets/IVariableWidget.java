package me.desht.pneumaticredux.common.progwidgets;

import me.desht.pneumaticredux.common.ai.DroneAIManager;

import java.util.Set;

public interface IVariableWidget {
    void setAIManager(DroneAIManager aiManager);

    void addVariables(Set<String> variables);
}
