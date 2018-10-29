package enoch.evotree.evotreebackend.model;

import javax.persistence.*;

@Entity
@Table(name="Node")
public class Node {

    public Node() {

    }

    public Node(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private long childNodeId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getChildNodeId() {
        return childNodeId;
    }

    public void setChildNodeId(long childNodeId) {
        this.childNodeId = childNodeId;
    }

    public long getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(long parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    private long parentNodeId;

}
