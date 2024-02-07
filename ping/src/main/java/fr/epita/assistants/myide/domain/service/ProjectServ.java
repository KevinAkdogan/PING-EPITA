package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.ExecReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Nodes.DossierNode;
import fr.epita.assistants.myide.domain.entity.Proj;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.aspect.Asp;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ProjectServ implements ProjectService {

    public NodeService nodeServe = new NodeServ();

    @Override
    public Project load(Path root) {
        var doss = new DossierNode(root);
        Proj project = new Proj(doss);
        File f = root.resolve(Paths.get(".git")).toFile();
        if (f.exists() && f.isDirectory()) {
            project.addAsp(new Asp(Mandatory.Aspects.GIT));
        }
        File ff = root.resolve(Paths.get("pom.xml")).toFile();
        if (ff.exists() && !ff.isDirectory()) {
            project.addAsp(new Asp(Mandatory.Aspects.MAVEN));
        }
        return project;
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params) {
        @NotNull Optional<Feature> feat = project.getFeature(featureType);
        if (!feat.isPresent()) {
            return new ExecReport(false);
        }
        return feat.get().execute(project, params);
    }

    @Override
    public NodeService getNodeService() {
        return nodeServe;
    }
}
