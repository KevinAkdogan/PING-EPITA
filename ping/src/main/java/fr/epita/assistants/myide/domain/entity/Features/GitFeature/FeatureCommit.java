package fr.epita.assistants.myide.domain.entity.Features.GitFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class FeatureCommit implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try 
        {
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

    public void exe(Project project, Object... params) throws IOException, GitAPIException {
        Git git = Git.open(new File(project.getRootNode().getPath().toString() + "/.git"));
        CommitCommand commit = git.commit();
        StringBuilder sb = new StringBuilder();
        for (var idx : params)
        {
            sb.append(idx.toString());
        }
        String contentMessage = sb.toString();
        commit.setMessage(contentMessage);
        commit.call();
    }

    @Override
    public Type type()
    {
        return Mandatory.Features.Git.COMMIT;
    }
}
