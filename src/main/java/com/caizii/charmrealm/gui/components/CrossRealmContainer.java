package com.caizii.charmrealm.gui.components;

import com.caizii.charmrealm.library.Logger;
import com.caizii.charmrealm.library.OperateType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;

import static com.caizii.charmrealm.library.RealmCreateLibrary.getPlayerRealmYMLFile;

public abstract class CrossRealmContainer extends CharmGUIBase implements CrossRealmInteractable {

    @Getter
    @Setter
    protected String InteractRealmConfigName;
    public FileConfiguration InteractRealmConfig;
    protected File realmYMLFile;

    public CrossRealmContainer(Player owner, String InteractRealmConfigName) {
        super(owner);
        this.InteractRealmConfigName = InteractRealmConfigName;
        setPanelTargetRealmConfig();
    }

    public CrossRealmContainer(Player owner) {
        super(owner);
        InteractRealmConfigName = owner.getName();
        setPanelTargetRealmConfig();
    }


    @Override
    public void setPanelTargetRealmConfig() {
        Logger.log(true, false, Level.INFO, OperateType.CAUTION, MessageFormat.format("尝试获取名称为 {0} 的领域文件", InteractRealmConfigName));
        try {
            realmYMLFile = getPlayerRealmYMLFile(InteractRealmConfigName);
            if (realmYMLFile != null) {
                InteractRealmConfig = YamlConfiguration.loadConfiguration(realmYMLFile);
                Logger.log(false, true, Level.INFO, OperateType.ADD, MessageFormat.format("已将对应领域文件 {0} 注入到gui!", InteractRealmConfigName));
            } else {
                Logger.log(false, true, Level.INFO, OperateType.REMOVE, MessageFormat.format("没有对应领域文件: {0}", InteractRealmConfigName));
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }


    @Override
    public void savePanelTargetRealmConfig() {
        if (InteractRealmConfig == null) {
            Logger.log(true, false, Level.INFO, OperateType.REMOVE, "没有对应领域文件可保存!");

        } else {
            try {
                InteractRealmConfig.save(realmYMLFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean nativeEqual = super.equals(obj);
        return nativeEqual && ((CrossRealmContainer) obj).InteractRealmConfigName.equalsIgnoreCase(this.InteractRealmConfigName);
    }

    @Override
    public UUID getCrossRealmOwner() {
        if (InteractRealmConfig != null) {
            return UUID.fromString(InteractRealmConfig.getString("playerOwnerUUID"));
        } else throw new RuntimeException("InteractRealmConfig is null");
    }
}
