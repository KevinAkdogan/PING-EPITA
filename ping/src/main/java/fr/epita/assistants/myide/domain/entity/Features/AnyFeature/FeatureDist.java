package fr.epita.assistants.myide.domain.entity.Features.AnyFeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FeatureDist implements Feature {

    public void exe(Project project, Object... params) throws IOException {
        String zipFileName = project.getRootNode().getPath().toString() + ".zip";
        File zipFile = new File(zipFileName);
        File file = new File(project.getRootNode().getPath().toString() + "/.myideignore");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);

        while ((line = br.readLine()) != null) {
            File trash = new File(project.getRootNode().getPath().toString() + "/" + line);
            if (trash.isDirectory()) {
                zipDirectory(trash, trash.getName(), zos);
            } else {
                zos.putNextEntry(new ZipEntry(project.getRootNode().getPath().toString() + "/" + trash.getName()));
                byte[] bytes = Files.readAllBytes(Path.of(trash.getPath()));
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
                //trash.delete();
            }
        }
        zos.close();
    }

    private void zipDirectory(File directory, String parentPath, ZipOutputStream zos) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    zipDirectory(file, parentPath + "/" + file.getName(), zos);
                } else {
                    zos.putNextEntry(new ZipEntry(parentPath + "/" + file.getName()));
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    zos.write(bytes, 0, bytes.length);
                    zos.closeEntry();
                    //file.delete();
                }
            }
        }
        //directory.delete();
    }


    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            exe(project, params);
            return new ExecReport(true);
        } catch (IOException e) {
            e.printStackTrace();
            return new ExecReport(false);
        }
    }

    @Override
    public Type type() {
        return Mandatory.Features.Any.DIST;
    }
}
