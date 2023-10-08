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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

/**
 * This class implements represents a map of the UW campus with buildings and paths
 * connecting parts of campus together.
 */

public class CampusMap implements ModelAPI {

    // AF(this): this.CampusMap represents a map of UW Campus, where this.allBuildings
    // represents a list of buildings at UW and this.graph represents a graph in which
    // nodes are locations on campus and edges are distances between two points on campus

    // RI: this.graph != null, this.allBuilding != null, and every CampusBuilding in
    // this.allBuildings is != null. Additionally, there exists a node (location) in this.graph
    // for each building in this.allBuildings.

    public static final boolean DEBUG = false;
    private List<CampusBuilding> allBuildings;
    private Graph<Double, Point> graph;

    /**
     * Creates a CampusMap
     * @spec.effects this CampusMap represents a map of a particular campus
     */
    public CampusMap() {
        this.allBuildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        graph = new Graph<>();
        List<CampusPath> allPaths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        for (CampusPath p : allPaths) {
            Graph.Node<Point> a = new Graph.Node<>(new Point(p.getX1(), p.getY1()));
            Graph.Node<Point> b = new Graph.Node<>(new Point(p.getX2(), p.getY2()));
            graph.insertNode(a);
            graph.insertNode(b);
            graph.insertEdge(a, b, p.getDistance());
        }
        checkRep();
    }

    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        Set<String> shortNames = new HashSet<>();
        for (CampusBuilding building : allBuildings) {
            shortNames.add(building.getShortName());
        }
        return shortNames.contains(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        checkRep();
        for (CampusBuilding building : allBuildings) {
            if (building.getShortName().equals(shortName)) {
                return building.getLongName();
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        Map<String, String> shortToLong = new HashMap<>();
        for (CampusBuilding building : allBuildings) {
            shortToLong.put(building.getShortName(), building.getLongName());
        }
        return shortToLong;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        checkRep();
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException();
        }
        CampusBuilding building1 = null;
        CampusBuilding building2 = null;

        for (CampusBuilding building : allBuildings) {
            if (building.getShortName().equals(startShortName)) {
                building1 = building;
            }
            if (building.getShortName().equals(endShortName)) {
                building2 = building;
            }
        }

        if (building1 == null || building2 == null) {
            throw new IllegalArgumentException();
        }
        Point startPoint = new Point(building1.getX(),
                building1.getY());
        Point endPoint = new Point(building2.getX(),
                building2.getY());

        return Algorithm.dijkstra(graph, startPoint, endPoint);
    }


    // checks to ensure that the rep invariant of CampusMap holds throughout the class
    private void checkRep() {
        // Check that this.graph is not null, that our set of nodes (keys) are not null,
        // and that none of the Nodes in our graph are null.
        assert ((allBuildings != null) && (graph != null));
        // checks that none of our edges in our graph are null and that we don't contain
        // two of the exact same Edge.
        if (DEBUG)  {
            for (CampusBuilding building : allBuildings) {
                assert (building != null);
                Point location = new Point(building.getX(), building.getY());
                assert (graph.containsNode(new Graph.Node<Point>(location)));
            }
        }
    }
}
