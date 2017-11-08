package proj.savidecor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sai on 30/10/17.
 */

public class MaterialDetail {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("material_name")
    @Expose
    public String materialName;
    @SerializedName("material_type")
    @Expose
    public String materialType;
    @SerializedName("yarn")
    @Expose
    public String yarn;
    @SerializedName("tufting")
    @Expose
    public String tufting;
    @SerializedName("finish")
    @Expose
    public String finish;
    @SerializedName("yarn_count")
    @Expose
    public String yarnCount;
    @SerializedName("yarn_ply")
    @Expose
    public String yarnPly;
    @SerializedName("total_weight")
    @Expose
    public String totalWeight;
    @SerializedName("pile_weight")
    @Expose
    public String pileWeight;
    @SerializedName("total_height")
    @Expose
    public String totalHeight;
    @SerializedName("pile_height")
    @Expose
    public String pileHeight;
    @SerializedName("tufts_sq_inch")
    @Expose
    public String tuftsSqInch;
    @SerializedName("tuft_binding_strength")
    @Expose
    public String tuftBindingStrength;
    @SerializedName("primary_backing_content")
    @Expose
    public String primaryBackingContent;
    @SerializedName("secondary_backing_content")
    @Expose
    public String secondaryBackingContent;
    @SerializedName("latex")
    @Expose
    public String latex;
    @SerializedName("finish2")
    @Expose
    public String finish2;
    @SerializedName("insect_resistance")
    @Expose
    public String insectResistance;
    @SerializedName("flammability_tests")
    @Expose
    public String flammabilityTests;
    @SerializedName("colour_fastedness_light")
    @Expose
    public String colourFastednessLight;
    @SerializedName("colour_fastedness_water")
    @Expose
    public String colourFastednessWater;
    @SerializedName("colour_fastedness_shampooing")
    @Expose
    public String colourFastednessShampooing;
    @SerializedName("dye_stuff")
    @Expose
    public String dyeStuff;
    @SerializedName("method_of_dyeing")
    @Expose
    public String methodOfDyeing;



    public MaterialDetail(String id) {
        this.materialName = id;
    }

    public MaterialDetail(String yarn, String tuffing) {
        this.yarn=yarn;
        this.tufting=tuffing;
    }

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


    @Override
    public String toString() {
        return "MaterialDetail{" +
                "id='" + id + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialType='" + materialType + '\'' +
                ", yarn='" + yarn + '\'' +
                ", tufting='" + tufting + '\'' +
                ", finish='" + finish + '\'' +
                ", yarnCount='" + yarnCount + '\'' +
                ", yarnPly='" + yarnPly + '\'' +
                ", totalWeight='" + totalWeight + '\'' +
                ", pileWeight='" + pileWeight + '\'' +
                ", totalHeight='" + totalHeight + '\'' +
                ", pileHeight='" + pileHeight + '\'' +
                ", tuftsSqInch='" + tuftsSqInch + '\'' +
                ", tuftBindingStrength='" + tuftBindingStrength + '\'' +
                ", primaryBackingContent='" + primaryBackingContent + '\'' +
                ", secondaryBackingContent='" + secondaryBackingContent + '\'' +
                ", latex='" + latex + '\'' +
                ", finish2='" + finish2 + '\'' +
                ", insectResistance='" + insectResistance + '\'' +
                ", flammabilityTests='" + flammabilityTests + '\'' +
                ", colourFastednessLight='" + colourFastednessLight + '\'' +
                ", colourFastednessWater='" + colourFastednessWater + '\'' +
                ", colourFastednessShampooing='" + colourFastednessShampooing + '\'' +
                ", dyeStuff='" + dyeStuff + '\'' +
                ", methodOfDyeing='" + methodOfDyeing + '\'' +
                '}';
    }
}
