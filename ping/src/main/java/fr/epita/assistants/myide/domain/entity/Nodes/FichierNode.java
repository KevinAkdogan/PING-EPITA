package fr.epita.assistants.myide.domain.entity.Nodes;

import fr.epita.assistants.myide.domain.entity.Node;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FichierNode implements Node {
    public Path path;

    public FichierNode(Path path) {
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
        return Types.FILE;
    }

    @Override
    public List<@NotNull Node> getChildren() {
        return new ArrayList<>();
    }
}
