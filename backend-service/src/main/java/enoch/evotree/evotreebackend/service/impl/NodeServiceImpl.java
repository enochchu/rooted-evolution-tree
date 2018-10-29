package enoch.evotree.evotreebackend.service.impl;

import enoch.evotree.evotreebackend.exception.NoSuchNodeException;
import enoch.evotree.evotreebackend.model.Node;
import enoch.evotree.evotreebackend.repository.NodeRepository;
import enoch.evotree.evotreebackend.service.NodeService;
import enoch.evotree.evotreebackend.service.constants.NodeConstant;

import org.springframework.stereotype.Service;

import java.util.Optional;

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

        Node childNode = createNode("ChildNode");

        childNode.setParentNodeId(parentNode.getId());

        parentNode.setChildNodeId(childNode.getId());

        nodeRepository.save(parentNode);

        return nodeRepository.save(childNode);
    }

    @Override
    public Node getNode(long id) throws NoSuchNodeException {
        Optional<Node> node = nodeRepository.findById(id);

        if (node.isEmpty()) {
            throw new NoSuchNodeException();
        }

        return node.get();
    }

    @Override
    public Node updateNode(long id, String name) throws NoSuchNodeException {
        Node node = getNode(id);

        node.setName(name);

        return nodeRepository.save(node);
    }

    @Override
    public Node deleteNode(long id) throws NoSuchNodeException {
        Node node = getNode(id);

        nodeRepository.delete(node);

        return node;
    }

}
