package fr.epita.assistants.myide.domain.entity.Features.GitFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class FeaturePull implements Feature {

    @Override
    public ExecutionReport execute(Project project, Object... params)
    {
        try {
            exe(project, params);
            return new ExecReport(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new ExecReport(false);
        }
        catch (GitAPIException e)
        {
            e.printStackTrace();
            return new ExecReport(false);
        }
    }

    public void exe(Project project, Object... params) throws IOException, GitAPIException
    {
        Git git = Git.open(new File(project.getRootNode().getPath().toString() + "/.git"));
        PullResult res = git.pull().call();
        boolean a = true;
    }


    @Override
    public Type type() {
        return Mandatory.Features.Git.PULL;
    }
}
