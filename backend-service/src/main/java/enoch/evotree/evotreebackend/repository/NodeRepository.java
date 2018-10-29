package enoch.evotree.evotreebackend.repository;

import enoch.evotree.evotreebackend.model.Node;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends CrudRepository<Node, Long> {

    List<Node> findAllByParentNodeId(long parentNodeId);

}
