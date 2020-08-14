package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zlli on 2019/8/23.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */
@ApiModel("查询")
public class SearchModel {
    @ApiModelProperty("查询关键字")
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
