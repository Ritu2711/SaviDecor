
package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class SetupDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("sticky_message_label")
    @Expose
    private String stickyMessageLabel;
    @SerializedName("default_cat_id")
    @Expose
    private String defaultCatId;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("toolbar_icon")
    @Expose
    private String toolbarIcon;
    @SerializedName("toolbar_background_image")
    @Expose
    private String toolbarBackgroundImage;
    @SerializedName("splash_screen_image")
    @Expose
    private String splashScreenTitle;
    @SerializedName("splash_screen_background")
    @Expose
    private String splashScreenBackground;
    @SerializedName("links")
    @Expose
    private List<HomeLinks> links = new ArrayList<HomeLinks>();
    @SerializedName("cart")
    @Expose
    private String cart;

    @Override
    public String toString() {
        return "SetupDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", stickyMessageLabel='" + stickyMessageLabel + '\'' +
                ", defaultCatId='" + defaultCatId + '\'' +
                ", links=" + links +
                '}';
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The webUrl
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * @param webUrl The web_url
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     * @return The stickyMessageLabel
     */
    public String getStickyMessageLabel() {
        return stickyMessageLabel;
    }

    /**
     * @param stickyMessageLabel The sticky_message_label
     */
    public void setStickyMessageLabel(String stickyMessageLabel) {
        this.stickyMessageLabel = stickyMessageLabel;
    }

    /**
     * @return The defaultCatId
     */
    public String getDefaultCatId() {
        return defaultCatId;
    }

    /**
     * @param defaultCatId The default_cat_id
     */
    public void setDefaultCatId(String defaultCatId) {
        this.defaultCatId = defaultCatId;
    }

    public String getToolbarIcon() {
        return toolbarIcon;
    }

    public void setToolbarIcon(String toolbarIcon) {
        this.toolbarIcon = toolbarIcon;
    }

    public String getToolbarBackgroundImage() {
        return toolbarBackgroundImage;
    }

    public void setToolbarBackgroundImage(String toolbarBackgroundImage) {
        this.toolbarBackgroundImage = toolbarBackgroundImage;
    }

    public String getSplashScreenTitle() {
        return splashScreenTitle;
    }

    public void setSplashScreenTitle(String splashScreenTitle) {
        this.splashScreenTitle = splashScreenTitle;
    }

    public String getSplashScreenBackground() {
        return splashScreenBackground;
    }

    public void setSplashScreenBackground(String splashScreenBackground) {
        this.splashScreenBackground = splashScreenBackground;
    }


    /**
     * @return The links
     */
    public List<HomeLinks> getLinks() {
        return links;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    /**
     * @param links The links
     */
    public void setLinks(List<HomeLinks> links) {
        this.links = links;
    }

}
