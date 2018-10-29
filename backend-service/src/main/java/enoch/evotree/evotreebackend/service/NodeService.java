package enoch.evotree.evotreebackend.service;

import enoch.evotree.evotreebackend.exception.NoSuchNodeException;
import enoch.evotree.evotreebackend.model.Node;

import java.util.List;

public interface NodeService {

    Node createNode(String name);

    Node createChildNode(String name, long parentNodeId)
        throws NoSuchNodeException;

    Node getNode(long id) throws NoSuchNodeException;

    Node deleteNode(long id) throws NoSuchNodeException;

    Node fetchNode(long id);

    Node updateNode(long id, String name) throws NoSuchNodeException;

    List<Node> getAllNodes();
}
