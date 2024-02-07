package fr.epita.assistants.myide.domain.entity.Features.GitFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;


public class FeaturePush implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            execute2(project, params);
            return new ExecReport(true);
        }
        catch (IOException e)
        {
            return new ExecReport(false);
        }
        catch (GitAPIException e)
        {
            return new ExecReport(false);
        }
    }

    public void execute2(Project project, Object... params) throws IOException, GitAPIException {
        Git git = Git.open(new File(project.getRootNode().getPath().toString() + "/.git"));
        PushCommand push = git.push();
        Iterable<PushResult> pushResults = push.call();

        for (PushResult res : pushResults) {
            for (RemoteRefUpdate up : res.getRemoteUpdates())
            {
                if (up.getStatus() != RemoteRefUpdate.Status.OK)
                    throw new TransportException("Push failed. Remote up status: " + up.getStatus());
            }
        }
    }

    @Override
    public Type type()
    {
        return Mandatory.Features.Git.PUSH;
    }
}
