package fr.epita.assistants.myide.domain.entity.Features;

import fr.epita.assistants.myide.domain.entity.Feature;

public class ExecReport implements Feature.ExecutionReport {

    public boolean success = false;
    public String error = "";

    public ExecReport(boolean finished) {
        this.success = finished;
    }

    public ExecReport(boolean finished, String rep) {
        this.success = finished;
        this.error = rep;
    }

    public String getError() {
        return error;
    }

    public void setError(String err) {
        error = err;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }
}
