package enoch.evotree.evotreebackend.controller;

import enoch.evotree.evotreebackend.exception.NoSuchNodeException;
import enoch.evotree.evotreebackend.model.Node;
import enoch.evotree.evotreebackend.service.NodeService;
import enoch.evotree.evotreebackend.service.constants.NodeConstant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class NodeController {

    private NodeService nodeService;

    public NodeController(
        NodeService nodeService) {

        this.nodeService = nodeService;
    }

    /* Nodes */

    @GetMapping("/nodes")
    public List<Node> getAllNodes() {
        return nodeService.getAllNodes();
    }

    @PostMapping("/nodes/new")
    public Node createParentNode(@RequestBody Node node) {
        return nodeService.createNode(node.getName());
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

    /* Add Child Node */
    @PostMapping("/nodes/{id}/child-node/new")
    public Node createNewChildNode(
            @PathVariable long id, @RequestBody Node newChildNode)
        throws NoSuchNodeException {

        return nodeService.createChildNode(newChildNode.getName(), id);
    }
}
