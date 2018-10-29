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

        Node node1 = nodeService.createNode("AMOEBA");

        Node node2 = nodeService.createChildNode(
            "ALGAE", node1.getId());

        Node node3 = nodeService.createNode("LIZARD");

        Node node4 = nodeService.createChildNode(
            "SHREW", node3.getId());

        Node node5 = nodeService.createChildNode(
            "LEMUR", node4.getId());

        Node node6 = nodeService.createChildNode(
            "APE", node5.getId());
    }

}
