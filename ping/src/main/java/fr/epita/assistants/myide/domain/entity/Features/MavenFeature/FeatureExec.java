package fr.epita.assistants.myide.domain.entity.Features.MavenFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;


import java.io.File;
import java.io.IOException;

public class FeatureExec implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(project.getRootNode().getPath().toString()));
        processBuilder.command("mvn", "exec");
        try
        {
            var str = processBuilder.start();
            while (str.isAlive() == true){}
            if (str.exitValue() != 0)
                return new ExecReport(false);
            else
                return new ExecReport(true);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new ExecReport(false);
        }
    }

    @Override
    public Type type()
    {
        return Mandatory.Features.Maven.EXEC;
    }
}
