package com.github.shanbei.shanbeiuser.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String postName;
}
