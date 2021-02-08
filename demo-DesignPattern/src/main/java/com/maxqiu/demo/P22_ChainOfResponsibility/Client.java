package com.maxqiu.demo.P22_ChainOfResponsibility;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        // 创建审批人
        DepartmentApprover departmentApprover = new DepartmentApprover("张主任");
        CollegeApprover collegeApprover = new CollegeApprover("李院长");
        SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("佟校长");

        // 需要将各个审批级别的下一个设置好
        departmentApprover.setApprover(collegeApprover);
        collegeApprover.setApprover(schoolMasterApprover);
        // 处理人构成环形
        schoolMasterApprover.setApprover(departmentApprover);

        // 创建一个请求
        PurchaseRequest purchaseRequest = new PurchaseRequest(1, 1000);
        // 张主任去处理请求
        departmentApprover.processRequest(purchaseRequest);
        // 李院长去处理请求（最终依旧被张主任处理）
        collegeApprover.processRequest(purchaseRequest);
    }

}
