package enoch.evotree.evotreebackend.service;

import enoch.evotree.evotreebackend.exception.CannotDeleteRootNodeException;
import enoch.evotree.evotreebackend.exception.NoSuchNodeException;
import enoch.evotree.evotreebackend.model.Node;

import java.util.List;

public interface NodeService {

    Node createNode(String name);

    Node createChildNode(String name, long parentNodeId)
        throws NoSuchNodeException;

    Node getNode(long id) throws NoSuchNodeException;

    Node deleteNode(long id) throws CannotDeleteRootNodeException, NoSuchNodeException;

    Node fetchNode(long id);

    List<Node> getParentNodes();

    Node updateNode(long id, String name) throws NoSuchNodeException;

    List<Node> getAllNodes();
}
