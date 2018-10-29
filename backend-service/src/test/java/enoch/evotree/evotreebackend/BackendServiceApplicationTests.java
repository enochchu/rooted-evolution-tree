package enoch.evotree.evotreebackend;

import enoch.evotree.evotreebackend.exception.NoSuchNodeException;
import enoch.evotree.evotreebackend.model.Node;
import enoch.evotree.evotreebackend.repository.NodeRepository;
import enoch.evotree.evotreebackend.service.NodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendServiceApplicationTests {

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private NodeService nodeService;

	@Test
	public void contextLoads() {
	    assertThat(nodeRepository).isNotNull();
	    assertThat(nodeService).isNotNull();
    }

    @Test
    public void shouldBeAbleToCreateParentNodeAndPersist()
            throws NoSuchNodeException {

	    Node parentNode = nodeService.createNode("ParentNode");

	    Node testParentNode = nodeService.getNode(parentNode.getId());

	    assertEquals(parentNode.getId(), testParentNode.getId());
    }

    @Test
    public void shouldBeAbleToCreateChildNodeAndPersist()
            throws NoSuchNodeException {

        Node parentNode = nodeService.createNode("ParentNode");

        Node childNode = nodeService.createChildNode(
            "ChildNode", parentNode.getId());

        parentNode = nodeService.getNode(parentNode.getId());

        assertEquals(childNode.getId(), parentNode.getChildNodeId());
        assertEquals(parentNode.getId(), childNode.getParentNodeId());
    }

}
