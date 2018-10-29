package enoch.evotree.evotreebackend.repository;

import enoch.evotree.evotreebackend.model.Node;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends CrudRepository<Node, Long> {

}
