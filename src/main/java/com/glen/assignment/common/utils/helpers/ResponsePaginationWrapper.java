package com.glen.assignment.common.utils.helpers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponsePaginationWrapper<T> {
    List<T> data;
    long total;
    int page;
}
