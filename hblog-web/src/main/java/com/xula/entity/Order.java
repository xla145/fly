package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author caixb
 */
public class Order extends BasePojo {

    @Temporary
    private static final long serialVersionUID = 1L;

    @Id
    private String orderId;                //订单id
    private String orderName;            //订单名称
    private Integer buyUid;                //买家uid
    private String buyName;            //买家名称
    private String buyMobile;            //买家手机号
    private Integer sellUid;            //卖家uid
    private Integer payStatus;            //支付状态 0：未支付   6：已支付
    private Integer isSmsNotice;        //支付成功是否短信通知0 不通知， 1：通知
    private Integer catId; // 产品类型
    private Integer productGroupType;    //商品一级分类
    private String pid;                    //商品id
    private String productImg;
    private String productName;            //商品名称
    private String productInfo;            //商品详细信息
    private String skuId; // 规格编号
    private String specPath;//规格路径
    private String specPathName;// 规格名称
    private BigDecimal productPrice;        //商品售价
    private BigDecimal productOriginalPrice;//商品原价
    private Integer buyCount;            //购买数量
    private BigDecimal totalAmount;            //订单总额（售价*数量）
    private BigDecimal payCash;                //订单现金支付金额
    private BigDecimal payNoncash;            //订单非现金支付金额
    private Date createTime;            //订单创建时间
    private Date updateTime;            //订单更新时间
    private Date expireTime;        //订单超时时间
    private Date finishTime;            //订单完成时间
    private String remark;                //备注
    private Integer supplierId;   //供应商编号
    private String supplierName;  //供应商名字
    //订单类型 1-普通订单 2-拼团订单
    private Integer orderType;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Integer getBuyUid() {
        return buyUid;
    }

    public void setBuyUid(Integer buyUid) {
        this.buyUid = buyUid;
    }

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public String getBuyMobile() {
        return buyMobile;
    }

    public void setBuyMobile(String buyMobile) {
        this.buyMobile = buyMobile;
    }

    public Integer getSellUid() {
        return sellUid;
    }

    public void setSellUid(Integer sellUid) {
        this.sellUid = sellUid;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getIsSmsNotice() {
        return isSmsNotice;
    }

    public void setIsSmsNotice(Integer isSmsNotice) {
        this.isSmsNotice = isSmsNotice;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductOriginalPrice() {
        return productOriginalPrice;
    }

    public void setProductOriginalPrice(BigDecimal productOriginalPrice) {
        this.productOriginalPrice = productOriginalPrice;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPayCash() {
        return payCash;
    }

    public void setPayCash(BigDecimal payCash) {
        this.payCash = payCash;
    }

    public BigDecimal getPayNoncash() {
        return payNoncash;
    }

    public void setPayNoncash(BigDecimal payNoncash) {
        this.payNoncash = payNoncash;
    }

    public Integer getProductGroupType() {
        return productGroupType;
    }

    public void setProductGroupType(Integer productGroupType) {
        this.productGroupType = productGroupType;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpecPath() {
        return specPath;
    }

    public void setSpecPath(String specPath) {
        this.specPath = specPath;
    }

    public String getSpecPathName() {
        return specPathName;
    }

    public void setSpecPathName(String specPathName) {
        this.specPathName = specPathName;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
