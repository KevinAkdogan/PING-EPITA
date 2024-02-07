package fr.epita.assistants.myide.domain.entity.Features.MavenFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Proj;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FeatureClean implements Feature {

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        StringBuilder sb = new StringBuilder();
        try {
            Process process = new ProcessBuilder("mvn", "clean", "-f", project.getRootNode().getPath().toString() + "/pom.xml").start();
            ((Proj) project).addProcess(process);
            InputStream stdIn = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdIn);
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            while ((line = br.readLine()) != null)
                sb.append(line + "\n");
            int exitcode = process.waitFor();
            return new ExecReport(true, sb.toString());
        }
        catch (IOException e)
        {
            return new ExecReport(false, sb.toString());
        }
        catch (InterruptedException e)
        {
            return new ExecReport(false, sb.toString());
        }
    }

    @Override
    public Type type()
    {
        return Mandatory.Features.Maven.CLEAN;
    }
}
