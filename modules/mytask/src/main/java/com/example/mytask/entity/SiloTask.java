package com.example.mytask.entity;

import java.util.Objects;

public class SiloTask {
    private Integer inorout;
    private String taskId;
    private String siloId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SiloTask) {
            return Objects.equals(this.inorout,
                    ((SiloTask) obj).inorout) && Objects.equals(this.taskId,
                    ((SiloTask) obj).taskId) && Objects.equals(this.siloId,
                    ((SiloTask) obj).siloId);
        }
        return super.equals(obj);
    }

    public Integer getInorout() {
        return inorout;
    }

    public void setInorout(Integer inorout) {
        this.inorout = inorout;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSiloId() {
        return siloId;
    }

    public void setSiloId(String siloId) {
        this.siloId = siloId;
    }
}
