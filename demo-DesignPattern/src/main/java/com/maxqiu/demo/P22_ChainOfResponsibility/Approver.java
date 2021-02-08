package com.maxqiu.demo.P22_ChainOfResponsibility;

/**
 * 抽象处理者
 * 
 * @author Max_Qiu
 */
public abstract class Approver {

    /**
     * 下一个处理者
     */
    protected Approver approver;

    /**
     * 处理人名称
     */
    protected String name;

    public Approver(String name) {
        this.name = name;
    }

    /**
     * 设置下一个处理者
     * 
     * @param approver
     */
    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    /**
     * 处理审批请求的方法，得到一个请求, 处理是具体子类完成，因此该方法做成抽象
     * 
     * @param purchaseRequest
     */
    public abstract void processRequest(PurchaseRequest purchaseRequest);

}
