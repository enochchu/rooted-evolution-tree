package enoch.evotree.evotreebackend.repository;

import enoch.evotree.evotreebackend.model.Node;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends CrudRepository<Node, Long> {

}
