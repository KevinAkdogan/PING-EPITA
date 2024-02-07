package fr.epita.assistants.myide.domain.entity.Features.MavenFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;

public class FeatureTree implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        String path = project.getRootNode().getPath().toString();
        if (!path.endsWith("/"))
            path += "/";
        processBuilder.directory(new File(path));

        String arg = null;
        if (params.length > 0)
            arg = (String) params[0];

        processBuilder.command("mvn", "dependency:tree", arg);

        try
        {
            var res = processBuilder.start();
            while (res.isAlive() == true){}
            if (res.exitValue() != 0)
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
        return Mandatory.Features.Maven.TREE;
    }
}
