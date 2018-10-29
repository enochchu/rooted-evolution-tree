package enoch.evotree.evotreebackend.controller;

import enoch.evotree.evotreebackend.exception.NoSuchNodeException;
import enoch.evotree.evotreebackend.model.Node;
import enoch.evotree.evotreebackend.repository.NodeRepository;
import enoch.evotree.evotreebackend.service.NodeService;
import enoch.evotree.evotreebackend.service.constants.NodeConstant;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NodeController {

    private NodeService nodeService;
    private NodeRepository nodeRepository;

    public NodeController(
        NodeService nodeService, NodeRepository nodeRepository) {

        this.nodeService = nodeService;
        this.nodeRepository = nodeRepository;
    }

    /* Nodes */

    @GetMapping("/nodes")
    public List<Node> getAllNodes() {
        return (List<Node>)nodeRepository.findAll();
    }

    @PostMapping("/nodes/new")
    public Node createParentNode(@RequestBody Node node) {
        return nodeRepository.save(node);
    }

    /* Individual Nodes */

    @GetMapping("/nodes/{id}")
    public Node getNodeById(@PathVariable long id)
        throws NoSuchNodeException {

        return nodeService.getNode(id);
    }

    @PutMapping("/nodes/{id}/update")
    public Node updateNodeById(@PathVariable long id, @RequestBody Node newNode)
        throws NoSuchNodeException {

        return nodeService.updateNode(id, newNode.getName());
    }

    @DeleteMapping("/nodes/{id}/delete")
    public Node deleteNodeById(@PathVariable long id)
        throws NoSuchNodeException {

        return nodeService.deleteNode(id);
    }

    /* Parent Nodes */
    @GetMapping("/nodes/{id}/parent-node")
    public Node getParentNode(@PathVariable long id)
        throws NoSuchNodeException {

        Node node = nodeService.getNode(id);

        long parentNodeId = node.getParentNodeId();

        if (parentNodeId == NodeConstant.NO_NODE) {
            return null;
        }

        return nodeService.getNode(node.getParentNodeId());
    }

    /* Child Nodes */
    @GetMapping("/nodes/{id}/child-node")
    public Node getChildNode(@PathVariable long id) throws NoSuchNodeException {
        Node node = nodeService.getNode(id);

        long childNode = node.getChildNodeId();

        if (childNode == NodeConstant.NO_NODE) {
            return null;
        }

        return nodeService.getNode(node.getChildNodeId());
    }
}
