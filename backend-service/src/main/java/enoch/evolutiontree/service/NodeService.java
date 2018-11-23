package enoch.evolutiontree.service;

import enoch.evolutiontree.exception.CannotDeleteRootNodeException;
import enoch.evolutiontree.exception.NoSuchNodeException;
import enoch.evolutiontree.model.Node;

import java.util.List;

public interface NodeService {

    Node createNode(String name);

    Node createChildNode(String name, long parentNodeId)
        throws NoSuchNodeException;

    Node getNode(long id) throws NoSuchNodeException;

    Node deleteNode(long id) throws CannotDeleteRootNodeException, NoSuchNodeException;

    Node fetchNode(long id);

    List<Node> getNodesByParentNodeId(long parentNodeId);

    List<Node> getRootNodes();

    Node updateNode(long id, String name) throws NoSuchNodeException;

    List<Node> getAllNodes();
}
