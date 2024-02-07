package fr.epita.assistants.myide.domain.entity.Features.AnyFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FeatureSearch implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        List<File> files = null;
        try
        {
            files = Files.list(Paths.get(project.getRootNode().getPath().toString()))
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .collect(Collectors.toList());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (params[0] != "")
        {
            for (var idx : files)
            {
                if (idx == params[0])
                {
                    return new ExecReport(true);
                }
            }
        }
        return new ExecReport(false);
    }

    @Override
    public Type type() {
        return Mandatory.Features.Any.SEARCH;
    }
}
