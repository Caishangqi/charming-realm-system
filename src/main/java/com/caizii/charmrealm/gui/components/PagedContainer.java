package com.caizii.charmrealm.gui.components;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public abstract class PagedContainer extends CharmGUIBase {

    protected int currentPage = 0;
    protected int totalPages = 1;

    public PagedContainer(Player owner) {
        super(owner);
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
