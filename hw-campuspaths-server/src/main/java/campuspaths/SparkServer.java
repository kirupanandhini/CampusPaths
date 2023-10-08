/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        CampusMap campusMap = new CampusMap(); // creates a CampusMap that we use in our methods


        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // route to request buildings on campus

        // route to calculate path between buildings

        // gets a list of building names from campus map
        Spark.get("/building-names", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Gson gson = new Gson();
                return gson.toJson(campusMap.buildingNames());
            }
        });
//
//
        // Let's have a "/shortest-path?start=NUMBER&end=NUMBER"
        // uses dijsktra's algorithm from campusMap to find the shortest path between the
        // start building and the end building
        Spark.get("/shortest-path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startString = request.queryParams("start");
                String endString = request.queryParams("end");
                if(startString == null || endString == null) {
                    // You can also have a message in "halt" that is displayed in the page.
                    Spark.halt(400, "must have start and end");
                }
                if(!campusMap.shortNameExists(startString) ||
                        !campusMap.shortNameExists(endString) ) {
                    Spark.halt(400, "must enter a valid short name of a building" +
                            " that exists in the map");
                }

                Path<Point> shortestPath = campusMap.findShortestPath(startString, endString);

                Gson gson = new Gson();
                String jsonResponse = gson.toJson(shortestPath);
                return jsonResponse;
            }
        });
    }
}
