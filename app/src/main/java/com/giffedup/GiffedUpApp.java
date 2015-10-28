package com.giffedup;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.parse.Parse;

/**
 * Created by vinay-r on 8/10/15.
 */
public class GiffedUpApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationData.getInstance().init(this);
        Fresco.initialize(this);
        Parse.initialize(this, "SnXKOhUUDEQ64ibzttzu5qWPDXBOwSfzTl4afVHF", "Dlr8mAK6QHoydGNjuIAIAZrdLKunO6FBa4MfTuZk");

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
//        query.getInBackground("mV3HHywlRZ", new GetCallback<ParseObject>() {
//            public void done(ParseObject object, ParseException e) {
//                if (e != null) {
//                    e.printStackTrace();
//                } else {
//                    System.out.println(object.getString("title"));
//                    object.getParseObject("original").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
//                        @Override
//                        public void done(ParseObject object, ParseException e) {
//                            if(e != null)
//                                e.printStackTrace();
//
//                            ImageConfigurationModel model = ImageConfigurationModel.getConfiguration(object);
//                            System.out.println("main: " + model.getUrl());
//                        }
//                    });
//
//
//
//                    List<ParseObject> feeds = object.getList("feeds");
//
//                    for(final ParseObject obj : feeds) {
//
//                        obj.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
//                            @Override
//                            public void done(ParseObject object, ParseException e) {
//
//                                Content content = new Content(object.getParseObject("content"));
////                                System.out.println(content.getOriginalImage().getUrl());
//                                System.out.println(object.getString("title"));
//                            }
//                        });
//
//                        System.out.println();
//                        System.out.println();
//                    }
//                }
//            }
//        });
    }


}
