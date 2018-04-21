
package com.billinghouse.javascript.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class State {

    @SerializedName("currentRoute")
    private String mCurrentRoute;
    @SerializedName("mainMenu")
    private List<MainMenu> mMainMenu;
    @SerializedName("subMenu")
    private List<SubMenu> mSubMenu;

    public String getCurrentRoute() {
        return mCurrentRoute;
    }

    public void setCurrentRoute(String currentRoute) {
        mCurrentRoute = currentRoute;
    }

    public List<MainMenu> getMainMenu() {
        return mMainMenu;
    }

    public void setMainMenu(List<MainMenu> mainMenu) {
        mMainMenu = mainMenu;
    }

    public List<SubMenu> getSubMenu() {
        return mSubMenu;
    }

    public void setSubMenu(List<SubMenu> subMenu) {
        mSubMenu = subMenu;
    }

}
