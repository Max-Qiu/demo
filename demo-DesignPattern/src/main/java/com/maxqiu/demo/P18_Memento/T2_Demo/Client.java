package com.maxqiu.demo.P18_Memento.T2_Demo;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        // 创建游戏存储
        ArchiveManager archiveManager = new ArchiveManager();

        // 创建游戏角色
        GameRole gameRole = new GameRole();
        gameRole.setVit(100);
        gameRole.setDef(100);
        System.out.println("和boss大战前的状态");
        gameRole.display();

        // 把当前状态保存caretaker
        String archiveId = archiveManager.setMemento(gameRole.createMemento());
        System.out.println("存档完毕！存档ID：" + archiveId);

        System.out.println("和boss大战~~~");
        gameRole.setDef(0);
        gameRole.setVit(0);
        gameRole.display();

        System.out.println("没打过，读取存档！");
        gameRole.recoverGameRoleFromMemento(archiveManager.getMemento(archiveId));
        System.out.println("恢复后的状态");
        gameRole.display();
    }

}
