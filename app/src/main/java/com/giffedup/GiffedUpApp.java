package com.giffedup;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.giffedup.model.ImageConfigurationModel;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by vinay-r on 8/10/15.
 */
public class GiffedUpApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationData.getInstance().init(this);
        Fresco.initialize(this);
        ParseObject.registerSubclass(ImageConfigurationModel.class);
        Parse.initialize(this, "SnXKOhUUDEQ64ibzttzu5qWPDXBOwSfzTl4afVHF", "Dlr8mAK6QHoydGNjuIAIAZrdLKunO6FBa4MfTuZk");

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
//        query.getInBackground("09w409VVU2", new GetCallback<ParseObject>() {
//            public void done(ParseObject object, ParseException e) {
//                if (e != null) {
//                    e.printStackTrace();
//                } else {
//                    System.out.println(object.getString("title"));
//                    List<ParseObject> feeds = object.getList("feeds");
//
//                    for(final ParseObject obj : feeds) {
//
//                        obj.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
//                            @Override
//                            public void done(ParseObject object, ParseException e) {
//                                System.out.println(object.getParseObject("original").get);
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
