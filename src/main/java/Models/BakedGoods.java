package Models;

import Resources.LinkedList;

public class BakedGoods {
    public LinkedList<Recipe> recipes = new LinkedList<>();

    private String goodsName;
    private String originCountry;
    private String goodsDesc;
    private String URL;

    public BakedGoods(String goodsName, String originCountry, String goodsDesc, String URL) {
        this.goodsName = goodsName;
        this.originCountry = originCountry;
        this.goodsDesc = goodsDesc;
        this.URL = URL;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return  goodsName + ' ' + originCountry + ' ' + goodsDesc;
    }
}
