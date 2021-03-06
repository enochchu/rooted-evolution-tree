package enoch.evolutiontree.service.impl;

import enoch.evolutiontree.exception.CannotDeleteRootNodeException;
import enoch.evolutiontree.exception.NoSuchNodeException;
import enoch.evolutiontree.model.Node;
import enoch.evolutiontree.repository.NodeRepository;
import enoch.evolutiontree.service.NodeService;
import enoch.evolutiontree.service.constants.NodeConstant;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * FIXME The API is getting narly
 */

@Service
public class NodeServiceImpl implements NodeService {

    private NodeRepository nodeRepository;

    public NodeServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Node createNode(String name) {
        Node node = new Node(name);

        node.setChildNodeId(NodeConstant.NO_NODE);
        node.setParentNodeId(NodeConstant.NO_NODE);

        return nodeRepository.save(node);
    }

    @Override
    public Node createChildNode(String name, long parentNodeId)
            throws NoSuchNodeException {

        Node parentNode = getNode(parentNodeId);

        Node childNode = createNode(name);

        childNode.setParentNodeId(parentNode.getId());

        parentNode.setChildNodeId(childNode.getId());

        nodeRepository.save(parentNode);

        return nodeRepository.save(childNode);
    }

    @Override
    public List<Node> getAllNodes() {
        return (List<Node>) nodeRepository.findAll();
    }

    @Override
    public Node getNode(long id) throws NoSuchNodeException {
        Optional<Node> node = nodeRepository.findById(id);

        if (!node.isPresent()) {
            throw new NoSuchNodeException();
        }

        return node.get();
    }

    @Override
    public List<Node> getNodesByParentNodeId(long parentNodeId) {
        return nodeRepository.findAllByParentNodeId(parentNodeId);
    }

    @Override
    public List<Node> getRootNodes() {
        return getNodesByParentNodeId(NodeConstant.NO_NODE);
    }

    @Override
    public Node updateNode(long id, String name) throws NoSuchNodeException {
        Node node = getNode(id);

        node.setName(name);

        return nodeRepository.save(node);
    }

    @Override
    public Node deleteNode(long id) throws CannotDeleteRootNodeException, NoSuchNodeException {

        Node node = getNode(id);

        // According to one of the rules, we cannot delete a root node
        // For now can we define a root node as one without a head?
        if (node.getParentNodeId() == NodeConstant.NO_NODE) {
            throw new CannotDeleteRootNodeException();
        }

        // It's pretty destructive. If this is a real thing, there should be
        // a way to roll back if fails.
        List<Long> deleteIdList = new ArrayList<>();

        deleteIdList.add(node.getId());

        Node nextNode = fetchNode(node.getChildNodeId());

        while(nextNode != null) {
            deleteIdList.add(nextNode.getId());

            nextNode = fetchNode(nextNode.getChildNodeId());
        }

        deleteIdList.stream().forEach(
            deleteId -> nodeRepository.deleteById(deleteId));

        return node;
    }

    @Override
    public Node fetchNode(long id) {
        Optional<Node> node = nodeRepository.findById(id);

        if (!node.isPresent()) {
            // Won't kick off exception
            return null;
        }

        return node.get();
    }

}
