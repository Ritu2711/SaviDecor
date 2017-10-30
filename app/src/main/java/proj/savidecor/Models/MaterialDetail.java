package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sai on 30/10/17.
 */

class MaterialDetail {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("material_name")
    @Expose
    private String materialName;
    @SerializedName("material_type")
    @Expose
    private String materialType;
    @SerializedName("yarn")
    @Expose
    private String yarn;
    @SerializedName("tufting")
    @Expose
    private String tufting;
    @SerializedName("finish")
    @Expose
    private String finish;
    @SerializedName("yarn_count")
    @Expose
    private String yarnCount;
    @SerializedName("yarn_ply")
    @Expose
    private String yarnPly;
    @SerializedName("total_weight")
    @Expose
    private String totalWeight;
    @SerializedName("pile_weight")
    @Expose
    private String pileWeight;
    @SerializedName("total_height")
    @Expose
    private String totalHeight;
    @SerializedName("pile_height")
    @Expose
    private String pileHeight;
    @SerializedName("tufts_sq_inch")
    @Expose
    private String tuftsSqInch;
    @SerializedName("tuft_binding_strength")
    @Expose
    private String tuftBindingStrength;
    @SerializedName("primary_backing_content")
    @Expose
    private String primaryBackingContent;
    @SerializedName("secondary_backing_content")
    @Expose
    private String secondaryBackingContent;
    @SerializedName("latex")
    @Expose
    private String latex;
    @SerializedName("finish2")
    @Expose
    private String finish2;
    @SerializedName("insect_resistance")
    @Expose
    private String insectResistance;
    @SerializedName("flammability_tests")
    @Expose
    private String flammabilityTests;
    @SerializedName("colour_fastedness_light")
    @Expose
    private String colourFastednessLight;
    @SerializedName("colour_fastedness_water")
    @Expose
    private String colourFastednessWater;
    @SerializedName("colour_fastedness_shampooing")
    @Expose
    private String colourFastednessShampooing;
    @SerializedName("dye_stuff")
    @Expose
    private String dyeStuff;
    @SerializedName("method_of_dyeing")
    @Expose
    private String methodOfDyeing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getYarn() {
        return yarn;
    }

    public void setYarn(String yarn) {
        this.yarn = yarn;
    }

    public String getTufting() {
        return tufting;
    }

    public void setTufting(String tufting) {
        this.tufting = tufting;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getYarnCount() {
        return yarnCount;
    }

    public void setYarnCount(String yarnCount) {
        this.yarnCount = yarnCount;
    }

    public String getYarnPly() {
        return yarnPly;
    }

    public void setYarnPly(String yarnPly) {
        this.yarnPly = yarnPly;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getPileWeight() {
        return pileWeight;
    }

    public void setPileWeight(String pileWeight) {
        this.pileWeight = pileWeight;
    }

    public String getTotalHeight() {
        return totalHeight;
    }

    public void setTotalHeight(String totalHeight) {
        this.totalHeight = totalHeight;
    }

    public String getPileHeight() {
        return pileHeight;
    }

    public void setPileHeight(String pileHeight) {
        this.pileHeight = pileHeight;
    }

    public String getTuftsSqInch() {
        return tuftsSqInch;
    }

    public void setTuftsSqInch(String tuftsSqInch) {
        this.tuftsSqInch = tuftsSqInch;
    }

    public String getTuftBindingStrength() {
        return tuftBindingStrength;
    }

    public void setTuftBindingStrength(String tuftBindingStrength) {
        this.tuftBindingStrength = tuftBindingStrength;
    }

    public String getPrimaryBackingContent() {
        return primaryBackingContent;
    }

    public void setPrimaryBackingContent(String primaryBackingContent) {
        this.primaryBackingContent = primaryBackingContent;
    }

    public String getSecondaryBackingContent() {
        return secondaryBackingContent;
    }

    public void setSecondaryBackingContent(String secondaryBackingContent) {
        this.secondaryBackingContent = secondaryBackingContent;
    }

    public String getLatex() {
        return latex;
    }

    public void setLatex(String latex) {
        this.latex = latex;
    }

    public String getFinish2() {
        return finish2;
    }

    public void setFinish2(String finish2) {
        this.finish2 = finish2;
    }

    public String getInsectResistance() {
        return insectResistance;
    }

    public void setInsectResistance(String insectResistance) {
        this.insectResistance = insectResistance;
    }

    public String getFlammabilityTests() {
        return flammabilityTests;
    }

    public void setFlammabilityTests(String flammabilityTests) {
        this.flammabilityTests = flammabilityTests;
    }

    public String getColourFastednessLight() {
        return colourFastednessLight;
    }

    public void setColourFastednessLight(String colourFastednessLight) {
        this.colourFastednessLight = colourFastednessLight;
    }

    public String getColourFastednessWater() {
        return colourFastednessWater;
    }

    public void setColourFastednessWater(String colourFastednessWater) {
        this.colourFastednessWater = colourFastednessWater;
    }

    public String getColourFastednessShampooing() {
        return colourFastednessShampooing;
    }

    public void setColourFastednessShampooing(String colourFastednessShampooing) {
        this.colourFastednessShampooing = colourFastednessShampooing;
    }

    public String getDyeStuff() {
        return dyeStuff;
    }

    public void setDyeStuff(String dyeStuff) {
        this.dyeStuff = dyeStuff;
    }

    public String getMethodOfDyeing() {
        return methodOfDyeing;
    }

    public void setMethodOfDyeing(String methodOfDyeing) {
        this.methodOfDyeing = methodOfDyeing;
    }


}
