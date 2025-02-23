package com.checkout.workflows.four.conditions.request;

import com.checkout.workflows.four.conditions.WorkflowConditionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class WorkflowConditionRequest {

    private WorkflowConditionType type;

    public WorkflowConditionRequest(final WorkflowConditionType type) {
        this.type = type;
    }

}

