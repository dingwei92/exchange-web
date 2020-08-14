package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zlli on 2020/6/15.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */
@ApiModel("接口订阅列表")
public class ServerCatalogSearchModel {
    @ApiModelProperty("查询条件")
    private String search;
   /* @ApiModelProperty("数据来源ID")
    private String duuid;*/
    @ApiModelProperty("接口类别ID  serverCatalogTemplate.typeuuid")
    private String tuuid;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

   /* public String getDuuid() {
        return duuid;
    }

    public void setDuuid(String duuid) {
        this.duuid = duuid;
    }*/

    public String getTuuid() {
        return tuuid;
    }

    public void setTuuid(String tuuid) {
        this.tuuid = tuuid;
    }
}
