package ru.kravchenko.designer.repositiry;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class NodeRepository {

    private List<String> nodeList = new ArrayList<>();

    public void insert(String node) {
        if (node == null) return;
        nodeList.add(node);
    }

    public List<String> getAll() {
        return nodeList;
    }

    public void showAll() {
        nodeList.forEach(System.out::println);
    }

    public void insertAll(List<String> nodeListAny) {
        nodeList.addAll(nodeListAny);
    }

    public Integer getSize () {
        return nodeList.size();
    }

}
