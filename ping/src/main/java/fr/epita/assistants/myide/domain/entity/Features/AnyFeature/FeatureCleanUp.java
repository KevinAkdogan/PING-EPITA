package fr.epita.assistants.myide.domain.entity.Features.AnyFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FeatureCleanUp implements Feature {

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            exe(project, params);
            return new ExecReport(true);
        } catch (IOException e) {
            return new ExecReport(false);
        }
    }


    public void exe(Project project, Object... params) throws IOException {
        File file = new File(project.getRootNode().getPath().toString() + "/.myideignore");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null)
        {
            File trash = new File(project.getRootNode().getPath().toString() + "/" + line);
            if (trash.isDirectory())
                deleteDirectory(trash);
            else
                trash.delete();
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                    deleteDirectory(file);
                else
                    file.delete();
            }
        }
        directory.delete();
    }


    @Override
    public Type type() {
        return Mandatory.Features.Any.CLEANUP;
    }
}
