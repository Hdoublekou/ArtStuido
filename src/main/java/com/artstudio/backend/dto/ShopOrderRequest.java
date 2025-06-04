package com.artstudio.backend.dto;

public class ShopOrderRequest {
    private String shipperName;    // 物流公司名
    private String shipperNo;      // 运单号
    private String receiverName;   // 收件人姓名
    private String address;        // 收件地址
    private String phone;          // 收件人电话

    // getter/setter 方法
    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperNo() {
        return shipperNo;
    }

    public void setShipperNo(String shipperNo) {
        this.shipperNo = shipperNo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
