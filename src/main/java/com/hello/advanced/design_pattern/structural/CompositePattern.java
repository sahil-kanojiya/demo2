package com.hello.advanced.design_pattern.structural;

import java.util.ArrayList;
import java.util.List;

public class CompositePattern {

    public static void main(String[] args) {
        FileSystem root = new Folder("users");
        ((Folder) root).add(new File("hello1.txt"));
        ((Folder) root).add(new File("hello2.txt"));
        FileSystem myDocs = new Folder("myDocs");
        ((Folder) myDocs).add(new File("hello3.txt"));
        ((Folder) root).add(myDocs);
        System.out.println(root);
    }

}


interface FileSystem {
    void show();
}

class File implements FileSystem {

    private String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void show() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                '}';
    }
}


class Folder implements FileSystem {

    private String name;
    private List<FileSystem> list;

    public Folder(String name) {
        this.name = name;
        this.list = new ArrayList<>();
    }

    public void add(FileSystem fileSystem) {
        list.add(fileSystem);
    }

    @Override
    public void show() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}



