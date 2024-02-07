package fr.epita.assistants.myide.domain.entity.aspect;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Features.AnyFeature.FeatureCleanUp;
import fr.epita.assistants.myide.domain.entity.Features.AnyFeature.FeatureDist;
import fr.epita.assistants.myide.domain.entity.Features.AnyFeature.FeatureSearch;
import fr.epita.assistants.myide.domain.entity.Features.GitFeature.FeatureAdd;
import fr.epita.assistants.myide.domain.entity.Features.GitFeature.FeatureCommit;
import fr.epita.assistants.myide.domain.entity.Features.GitFeature.FeaturePull;
import fr.epita.assistants.myide.domain.entity.Features.GitFeature.FeaturePush;
import fr.epita.assistants.myide.domain.entity.Features.MavenFeature.*;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Asp implements Aspect {
    public Aspect.Type type;

    public Asp(Aspect.Type type)
    {
        this.type = type;
    }

    @Override
    public Aspect.Type getType()
    {
        return type;
    }

    @Override
    public @NotNull List<Feature> getFeatureList()
    {
        List<Feature> features = new ArrayList<>();

        if (type == Mandatory.Aspects.ANY)
        {
            features.add(new FeatureCleanUp());
            features.add(new FeatureDist());
            features.add(new FeatureSearch());
        }
        else if (type == Mandatory.Aspects.GIT)
        {
            features.add(new FeatureAdd());
            features.add(new FeaturePull());
            features.add(new FeaturePush());
            features.add(new FeatureCommit());

        }
        else if (type == Mandatory.Aspects.MAVEN)
        {
            features.add(new FeatureClean());
            features.add(new FeatureCompile());
            features.add(new FeatureExec());
            features.add(new FeatureInstall());
            features.add(new FeaturePackage());
            features.add(new FeatureTest());
            features.add(new FeatureTree());
        }
        return features;
    }
}
