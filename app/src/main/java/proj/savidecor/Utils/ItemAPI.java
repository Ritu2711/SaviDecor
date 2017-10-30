package proj.savidecor.Utils;

import java.util.List;


import okhttp3.ResponseBody;
import proj.savidecor.Models.AllCARTProducts;
import proj.savidecor.Models.AllItems;
import proj.savidecor.Models.AllProducts;
import proj.savidecor.Models.Models;
import proj.savidecor.Models.SetupDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface
ItemAPI {
    //Collect Site Id


    @GET("setup.php")
    Call<SetupDetails> getSetup(@Query("site_id") int id, @Query("imei_no") String imei);

    @GET("setup.php")
    void setMy(@Query("site_id") int id, Callback<List<SetupDetails>> sd);

    @GET("category.php")
    Call<AllItems> getJSON();

    @GET("category.php")
    Call<AllItems> getSUBCAT(@Query("cat") int page);

    @GET("products.php")
    Call<AllProducts> getALL(@Query("id") int count, @Query("cat") int page, @Query("sortby") String sortquery);

    @GET("prod_details.php")
    Call<Models> getAllItemDetails(@Query("prod") String id);

    @GET("product_sort.php")
    Call<AllProducts> getAllProductsSort(@Query("cat") int page, @Query("sortby") String sortquery);

    @FormUrlEncoded
    @POST("send_email.php")
    Call<ResponseBody> insertData(@Field("imei") String imei, @Field("product_id") String productID, @Field("product_name") String product, @Field("name") String name, @Field("email") String email, @Field("phone") String phone, @Field("text") String text);

    @GET("search_product.php")
    Call<AllProducts> getAllProductssearch(@Query("search") String query, @Query("id") int count);

    @FormUrlEncoded
    @POST("add_to_cart.php")
    Call<ResponseBody> insertINcart(@Field("imei_no") String imei_no, @Field("pid") String pid, @Field("pname") String pname, @Field("price") String price, @Field("quantity") String quantity, @Field("options") String options);

    @GET("cart.php")
    Call<AllCARTProducts> getALLPRO(@Query("id") String imei);

    @POST("cart.php")
    Call<ResponseBody> getTasks(@Query("site_id") int siteid);

    @POST
    Call<ResponseBody> getHtml(@Url String wUrl, @Query("Udf2") String udf2, @Query("Udf3") String udf3, @Query("pName") String pID);

    @FormUrlEncoded
    @POST("update_cart.php")
    Call<ResponseBody> ChangeDB(@Field("imei_no") String imei_no, @Field("pid") String pid, @Field("pname") String pname, @Field("price") String price, @Field("quantity") String quantity, @Field("options") String notes);

    @FormUrlEncoded
    @POST("savetoken.php")
    Call<ResponseBody> SendToken(
            @Field("device_id") String deviceid, @Field("device_token") String devicetoken);

}
