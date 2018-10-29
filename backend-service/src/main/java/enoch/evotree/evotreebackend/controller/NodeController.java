package enoch.evotree.evotreebackend.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import enoch.evotree.evotreebackend.exception.CannotDeleteRootNodeException;
import enoch.evotree.evotreebackend.exception.NoSuchNodeException;
import enoch.evotree.evotreebackend.model.Node;
import enoch.evotree.evotreebackend.service.NodeService;
import enoch.evotree.evotreebackend.service.constants.NodeConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/experimental/nodes")
    public ResponseEntity<String> getParentNodesWithChildren() {
        List<Node> parentNodes = nodeService.getParentNodes();

        JsonArray jsonArray = new JsonArray();

        parentNodes.forEach(node -> {
            JsonObject jsonObject = createJsonObject(node);

            jsonArray.add(jsonObject);
        });

        // Set response entity
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        return ResponseEntity.ok().headers(headers).body(jsonArray.toString());
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
            throws CannotDeleteRootNodeException, NoSuchNodeException {

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

    private JsonObject createJsonObject(Node parentNode) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", parentNode.getId());
        jsonObject.addProperty("name", parentNode.getName());

        Node nextChildNode = nodeService.fetchNode(parentNode.getChildNodeId());

        if (nextChildNode != null) {
            jsonObject.add("child-node", createJsonObject(nextChildNode));
        }

        return jsonObject;
    }
}
