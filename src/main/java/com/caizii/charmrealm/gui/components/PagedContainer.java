package com.caizii.charmrealm.gui.components;

import com.caizii.charmrealm.gui.types.EButtonType;
import com.caizii.charmrealm.library.RealmConfigLibrary;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;


@Getter
@Setter
public abstract class PagedContainer extends CharmGUIBase {

    protected int currentPage = 1;
    protected int totalPages = 1;
    protected List<Integer> containerRange;

    public PagedContainer(Player owner) {
        super(owner);

    }

    @Override
    public void postCustomGUIInitialize() {
        super.postCustomGUIInitialize();
        containerRange = RealmConfigLibrary.getButtonRange(EButtonType.GENERATED,this);
    }

    public void switchToTargetPage(int pageNumber) {

    }

    public void switchToNextPage() {

    }

    public void switchToPreviousPage() {
    }

    public void upDatePagedContainer() {

    }

}
