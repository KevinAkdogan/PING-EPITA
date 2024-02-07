package fr.epita.assistants.myide.domain.entity.Nodes;

import fr.epita.assistants.myide.domain.entity.Node;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DossierNode implements Node {
    public Path path;

    public DossierNode(Path path) {
        this.path = path;
    }

    @Override
    public Path getPath() {
        return path;
    }

    public void setPath(Path pathh) {
        this.path = pathh;
    }

    @Override
    public Type getType() {
        return Types.FOLDER;
    }

    public List<@NotNull Node> getChildren()
    {
        File directory = path.toFile();
        File[] fList = directory.listFiles();
        List<Node> listeAll = new ArrayList<>();
        if (fList != null)
        {
            for (File file : fList)
            {
                String absolutePath = file.getAbsolutePath();
                Path absolutePath2 = Paths.get(absolutePath);
                Path relativePath = convertToRelativePath(absolutePath2);
                if (file.isFile())
                    listeAll.add(new FichierNode(relativePath));
                else if (file.isDirectory())
                    listeAll.add(new DossierNode(relativePath));
            }
        }
        return listeAll;
    }

    private Path convertToRelativePath(Path absolutePath)
    {
        Path currentPath = Paths.get("").toAbsolutePath();
        return currentPath.relativize(absolutePath);
    }

    /*
    @Override
    public List<@NotNull Node> getChildren() {
        File directory = path.toFile();
        File[] fList = directory.listFiles();
        List<Node> listeAll = new ArrayList<>();
        if (fList != null) {
            for (File file : fList) {
                String chemin = file.getAbsolutePath();
                Path chemin2 = Paths.get(chemin);
                if (file.isFile()) {
                    listeAll.add(new FichierNode(chemin2));
                } else if (file.isDirectory()) {
                    listeAll.add(new DossierNode(chemin2));
                }
            }
        }
        return listeAll;
    }
    */
}
