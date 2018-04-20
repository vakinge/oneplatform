package com.oneplatform.demo.dao.entity;

import com.jeesuite.mybatis.core.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "demo_order")
public class DemoOrderEntity extends BaseEntity {
	
	public static enum OrderStatus{
		NEW, PAID,DELIVERED,RECEIVED,CANCEL;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "seller_id")
    private Integer sellerId;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "buyer_id")
    private Integer buyerId;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "product_id")
    private String productId;
    
    @Column(name = "product_name")
    private String productName;

    private BigDecimal amount;

    private String status;

    private String memo;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return seller_id
     */
    public Integer getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return seller_name
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @param sellerName
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * @return buyer_id
     */
    public Integer getBuyerId() {
        return buyerId;
    }

    /**
     * @param buyerId
     */
    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    /**
     * @return buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * @param buyerName
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
    
    

    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return created_by
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return updated_by
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public String getStatusName() {
    	if(OrderStatus.NEW.name().equals(status))return "待付款";
    	if(OrderStatus.PAID.name().equals(status))return "待发货";
    	if(OrderStatus.DELIVERED.name().equals(status))return "待收货";
    	if(OrderStatus.RECEIVED.name().equals(status))return "已收货";
    	return null;
    }
}