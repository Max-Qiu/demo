package com.maxqiu.demo.P18_Memento.T2_Demo;

import lombok.Getter;
import lombok.Setter;

/**
 * 游戏角色
 * 
 * @author Max_Qiu
 */
@Getter
@Setter
public class GameRole {

    private int vit;
    private int def;

    /**
     * 创建存档
     * 
     * @return
     */
    public Archive createMemento() {
        return new Archive(vit, def);
    }

    /**
     * 从备忘录对象，恢复角色的状态
     * 
     * @param archive
     */
    public void recoverGameRoleFromMemento(Archive archive) {
        this.vit = archive.getVit();
        this.def = archive.getDef();
    }

    /**
     * 显示当前游戏角色的状态
     */
    public void display() {
        System.out.println("角色当前的攻击力：" + this.vit + " 防御力: " + this.def);
    }

}
