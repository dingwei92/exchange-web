package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by zlli on 2020/6/17.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */
@ApiModel("订阅申请")
public class SubApplyModel {
    @ApiModelProperty("订阅服务记录")
    private ServerSubscribes subscribe;
    @ApiModelProperty("订阅服务申请人")
    private ServerSubscribeApply apply;
    @ApiModelProperty("订阅字段ID")
    private List<Integer> cataloglist;
    @ApiModelProperty(hidden = true)
    private List<String> fieldResult;
    @ApiModelProperty("订阅字段条件  type =1 1个条件  2 是2个条件     {\"17405\":{\"type\":\"1\",\"con1\":\"1\"},\"17406\":{\"type\":\"2\",\"con1\":\"2\",\"con2\":\"3\"}}\n")
    private String conditionResult;

    public ServerSubscribes getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(ServerSubscribes subscribe) {
        this.subscribe = subscribe;
    }

    public ServerSubscribeApply getApply() {
        return apply;
    }

    public void setApply(ServerSubscribeApply apply) {
        this.apply = apply;
    }

    public List<Integer> getCataloglist() {
        return cataloglist;
    }

    public void setCataloglist(List<Integer> cataloglist) {
        this.cataloglist = cataloglist;
    }

    public List<String> getFieldResult() {
        return fieldResult;
    }

    public void setFieldResult(List<String> fieldResult) {
        this.fieldResult = fieldResult;
    }

    public String getConditionResult() {
        return conditionResult;
    }

    public void setConditionResult(String conditionResult) {
        this.conditionResult = conditionResult;
    }
}
