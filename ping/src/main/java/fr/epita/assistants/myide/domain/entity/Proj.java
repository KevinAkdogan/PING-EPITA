package fr.epita.assistants.myide.domain.entity;

import fr.epita.assistants.myide.domain.entity.Mandatory.Aspects;
import fr.epita.assistants.myide.domain.entity.aspect.Asp;

import java.util.*;

public class Proj implements Project{
    public Node rootNode;
    public Set<Aspect> aspects;

    public List<Process> processes = new ArrayList<>();

    public Proj(Node root)
    {
        this.rootNode = root;
        this.aspects = new HashSet<Aspect>(Arrays.asList(new Asp(Aspects.ANY)));
    }

    public void addProcess(Process process)
    {
        processes.add(process);
    }

    public void addAsp(Aspect asp)
    {
        this.aspects.add(asp);
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public Set<Aspect> getAspects() {
        return aspects;
    }

    @Override
    public Optional<Feature> getFeature(Feature.Type featureType) {
        List<Feature> allFeatures = getFeatures();
        for (var feat: allFeatures)
        {
            if (feat.type() == featureType)
            {
                return Optional.of(feat);
            }
        }
        return Optional.empty();
    }
}
