package com.rico.comm;

import java.io.Serializable;
import java.util.List;

public interface INode extends Serializable {

    Long getId();

    Long getParentId();

    List<INode> getChildren();

    default Boolean getHasChildren() {
        return false;
    }
}
