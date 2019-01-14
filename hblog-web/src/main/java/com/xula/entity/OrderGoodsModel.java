package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 * @ClassName: ProductOrderActivityModel
 * @date 2017年7月19日
 */
public class OrderGoodsModel extends Order {
    @Temporary
    private static final long serialVersionUID = 1L;
    private String adresMobile;//收货人电话
    private String adresName;//收货人
    private String adresAddr;//收货地址
    private String message;//留言
    private Integer orderGoodsStatus;//订单状态
    private String orderGoodsStatusName;//订单状态对应的名字
    private String courierCompany;//快递公司
    private String courierNumber;//快递单号
    private BigDecimal freight;//运费
    private boolean isShip;//是否发货
    private String remark;//备注
    private String supplierName;//商家
    private BigDecimal totalOriginalPrice;//进货总价
    private Integer stock;//商品库存
    private Integer refundStatus;// 退款状态
    private String refundStatusName;// 退款状态
    //'拼团状态 0-拼团中（未成团） 1-拼团成功（以成团） 2-拼团失败（过期） 3-拼团下单成功',
    private Integer status;
    //拼团团号
    private String grouponId;
    /**
     * 附加费用
     */
    private BigDecimal attachCost;
    /**
     * 附加备注信息
     */
    private String attachRemark;
    /**
     * 附加操作人
     */
    private String attachOperator;
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getAdresMobile() {
        return adresMobile;
    }

    public void setAdresMobile(String adresMobile) {
        this.adresMobile = adresMobile;
    }

    public String getAdresName() {
        return adresName;
    }

    public void setAdresName(String adresName) {
        this.adresName = adresName;
    }

    public String getAdresAddr() {
        return adresAddr;
    }

    public void setAdresAddr(String adresAddr) {
        this.adresAddr = adresAddr;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOrderGoodsStatus() {
        return orderGoodsStatus;
    }

    public void setOrderGoodsStatus(Integer orderGoodsStatus) {
        this.orderGoodsStatus = orderGoodsStatus;
    }

    public String getCourierCompany() {
        return courierCompany;
    }

    public void setCourierCompany(String courierCompany) {
        this.courierCompany = courierCompany;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public boolean isShip() {
        return isShip;
    }

    public void setShip(boolean ship) {
        isShip = ship;
    }

    public BigDecimal getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(BigDecimal totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    public String getOrderGoodsStatusName() {
        return orderGoodsStatusName;
    }

    public void setOrderGoodsStatusName(String orderGoodsStatusName) {
        this.orderGoodsStatusName = orderGoodsStatusName;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatusName() {
        return refundStatusName;
    }

    public void setRefundStatusName(String refundStatusName) {
        this.refundStatusName = refundStatusName;
    }

    public BigDecimal getAttachCost() {
        return attachCost;
    }

    public void setAttachCost(BigDecimal attachCost) {
        this.attachCost = attachCost;
    }

    public String getAttachRemark() {
        return attachRemark;
    }

    public void setAttachRemark(String attachRemark) {
        this.attachRemark = attachRemark;
    }

    public String getAttachOperator() {
        return attachOperator;
    }

    public void setAttachOperator(String attachOperator) {
        this.attachOperator = attachOperator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGrouponId() {
        return grouponId;
    }

    public void setGrouponId(String grouponId) {
        this.grouponId = grouponId;
    }
}
