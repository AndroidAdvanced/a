package com.example.rupal.a.data;

import com.example.rupal.a.model.FetchQuestions;
import com.example.rupal.a.model.JobScreenModel;
import com.example.rupal.a.model.QuestionsCategories;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("get_quotes.php")
    Observable<GetQuotes> getQuotes();

    @GET("question_cat.php")
    Observable<QuestionsCategories> getQuestionsCategory();

 /*   @GET("job.php?")
    Observable<JobScreenModel> getJobData(@Query("filter") String filter);*/

    @GET("job.php?")
    Observable<JobScreenModel> getJobData();

    @GET("fetch_question.php")
    Observable<FetchQuestions> getQuestions(@Query("myData") String myData);

    @POST("feedback.php")
    Observable<String> postFeedBack(@Query("name") String name, @Query("mobile") String mobile,
                                    @Query("email") String email, @Query("comment") String comment);

    @POST("report_question.php")
    Observable<String> postReportQuestion(@Query("cat_name") String cat_name, @Query("question") String question,
                                    @Query("complain") String complain);

    @POST("lucky_draw.php")
    Observable<String> postLuckyDraw(@Query("name") String name, @Query("age") String age, @Query("mobile") String mobile,
                                     @Query("email") String email, @Query("comment") String comment);

}
