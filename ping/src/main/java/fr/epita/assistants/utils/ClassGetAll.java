package fr.epita.assistants.utils;


import fr.epita.assistants.myide.domain.entity.Nodes.DossierNode;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassGetAll {

    public Set<Class> classes = new HashSet<>();

    public Set<Class> getClasses() {
        return classes;
    }

    public void findAllClasses(String packageName) throws ClassNotFoundException, MalformedURLException {
        DossierNode dn = new DossierNode(Path.of(packageName));
        for (var child : dn.getChildren()) {
            if (child.isFile()) {
                String name = child.getPath().toString();
                if (name.contains(".class") && !(name.contains("$"))) {
                    List<String> list = List.of(name.split("/"));
                    String str = "";
                    String str2 = "";
                    boolean condi = false;
                    for (var string : list)
                    {
                        if (condi == false)
                        {
                            str = str + "/" + string;
                            if (string.equals("classes"))
                            {
                                condi = true;
                            }
                        }
                        else
                        {
                            str2 = str2 + "." + string;
                        }
                    }
                    str = str.substring(1, str.length());
                    str2 = str2.substring(1, str2.lastIndexOf('.'));
                    File f = new File(str + "/");
                    URL[] cp = {f.toURI().toURL()};
                    URLClassLoader urlcl = new URLClassLoader(cp);
                    Class classem = urlcl.loadClass(str2);
                    if (classem != null)
                        classes.add(classem);
                    else
                        System.out.println(str2 + "n'existe pas");
                }
            }
            else
            {
                findAllClasses(child.getPath().toString());
            }
        }
    }

    private Class getClass(String packageName)
    {
        try
        {
            return (Class.forName(packageName));
        }
        catch (ClassNotFoundException e) {
    
        }
        return null;
    }
}
