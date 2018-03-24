package cn.jrry.common.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Node implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2483611778673483214L;
    private Long id;
    private String text;
    private String state;
    private Boolean checked;
    private Map<String,Object> attributes = Maps.newHashMap();
    private List<Node> children  = Lists.newArrayList();
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Boolean getChecked() {
        return checked;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    public List<Node> getChildren() {
        return children;
    }
    public void setChildren(List<Node> children) {
        this.children = children;
    }
    @Override
    public String toString() {
        return "Node [id=" + id + ", text=" + text + ", state=" + state + ", checked=" + checked + ", attributes="
                + attributes + ", children=" + children + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + ((checked == null) ? 0 : checked.hashCode());
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        if (checked == null) {
            if (other.checked != null)
                return false;
        } else if (!checked.equals(other.checked))
            return false;
        if (children == null) {
            if (other.children != null)
                return false;
        } else if (!children.equals(other.children))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }
    
//    id: node id, which is important to load remote data
//    text: node text to show
//    state: node state, 'open' or 'closed', default is 'open'. When set to 'closed', the node have children nodes and will load them from remote site
//    checked: Indicate whether the node is checked selected.
//    attributes: custom attributes can be added to a node
//    children: an array nodes defines some children nodes

}
