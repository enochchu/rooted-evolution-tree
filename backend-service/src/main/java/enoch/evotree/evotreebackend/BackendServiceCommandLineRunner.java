package enoch.evotree.evotreebackend;

import enoch.evotree.evotreebackend.model.Node;
import enoch.evotree.evotreebackend.service.NodeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BackendServiceCommandLineRunner implements CommandLineRunner {

    private NodeService nodeService;

    public BackendServiceCommandLineRunner(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public void run(String... args) throws Exception {
        /* Set up some dummy data */

        Node parentNode = nodeService.createNode("ParentNode");

        Node childNode = nodeService.createChildNode(
            "ChildNode", parentNode.getId());

        Node grandChildrenNode = nodeService.createChildNode(
            "GrandChildren", childNode.getId());
    }

}
